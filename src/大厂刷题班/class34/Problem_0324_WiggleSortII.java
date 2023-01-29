package 大厂刷题班.class34;

// https://leetcode.cn/problems/wiggle-sort-ii/
// 需要两个前置知识：
// 1)无序数组中找到第K小快排方法  只要是回了改进版的快排方法即可，并不必须使用BFPRT。   BFPRT（线性查找算法）章节
// 2)完美洗牌问题   大厂刷题20节
// 快速排序   分治   数组
// 这道题的证明如果不想听就算了，只要会写代码就行了（上课的时候演示了证明，但是尴尬的是没有证明出来），因为这道题力扣题目中已经说了，保证所有输入数组都可以得到满足题目要求的结果，所以不用证明如果我们的方法做不出来，就一定凑不出来满足题目要求的结果
public class Problem_0324_WiggleSortII {
    // 时间复杂度O(N)，额外空间复杂度O(1)
    public void wiggleSort(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return;
        }
        // 数组大小
        int n = nums.length;
        // 第一步：先将整个数组分成两部分，来保证左半部分的任意一个数一定比右半部分的任意一个数小，用于后续我们用来将他们交错排列实现摆动序列
        findIndexNum(nums, 0, n - 1, n >> 1);
        // 第二步：将排列好的数组进行交错排列，实现摆动序列
        // 1、数组大小为偶数
        if ((n & 1) == 0) {
            // 先进行完美洗牌
            shuffle(nums, 0, n - 1);

            // 进行完美洗牌前数组状况：L1 L2 L3 L4 R1 R2 R3 R4
            // 经过完美洗牌后数组状况：R1 L1 R2 L2 R3 L3 R4 L4
            // 我们想要的摆动序列应该是下面这两种的任意一种:
            // 1）L1 R1 L2 R2 L3 R3 L4 R4 ×
            // 2）L4 R4 L3 R3 L2 R2 L1 R1 √
            // 虽然上面两个都符合题目要求，但实际我们只能实现第二种，第一种是不对的。

            // 再将整体逆序即可得到摆动序列，就可以得到第二种序列结果
            reverse(nums, 0, n - 1);

            // 我们无法直接对完美洗牌后的数组两两逆序得到第一种结果序列，因为这样操作后得到的结果提交会报答案错误
            // 做个实验，如果把上一行的code注释掉(reverse过程)，然后跑下面注释掉的for循环代码
            // for循环的代码就是两两交换，会发现对数器报错，说明两两交换是不行的, 必须整体逆序
            // for (int i = 0; i < nums.length; i += 2) {
            // 	swap(nums, i, i + 1);
            // }

            // 2、数组大小为奇数
        } else {
            // 0下标不动，直接从1下标开始进行完美洗牌，即可得到摆动序列
            shuffle(nums, 1, n - 1);
        }
    }

    // 第一步：无序数组中找到第K小的快排方法。这一部分其实比较简单，就是一个类似于荷兰国旗问题的代码
    public void findIndexNum(int[] nums, int l, int r, int index) {
        int num = 0;
        int[] range = null;

        while (l < r) {
            // 随机找一个基准数
            num = nums[l + (int) Math.random() * (r - l + 1)];
            // 将小于num的都放到数组左边，大于的放到数组右边，小于的放在数组中见
            range = partition(nums, l, r, num);

            // 如果index落在了中间范围上，就说明完成了我们想要的操作。这里的index就是中间下标位置，也就是说此时中间相等的部分就是这个数组的中位数
            if (index >= range[0] && index <= range[1]) {
                return;
            } else if (index < range[0]) {
                // 向左部分继续递归，只走一边
                r = range[0] - 1;
            } else {
                // 向右部分继续递归，只走一边
                l = range[1] + 1;
            }
        }
    }

    // 在数组nums的l~r范围上，将小于num的放左边，等于num的放中间，大于num的放右边
    public int[] partition(int[] nums, int l, int r, int num) {
        // 标记三部分的边界
        int less = l - 1;
        int more = r + 1;
        // 当前遍历到的位置
        int cur = l;
        while (cur < more) {
            if (nums[cur] > num) {
                swap(cur, --more, nums);
            } else if (nums[cur] < num){
                swap(cur++, ++less, nums);
            } else {
                cur++;
            }
        }
        // 返回中间部分的边界位置
        return new int[] {less + 1, more - 1};
    }

    // 交换
    public void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 第二步：完美洗牌——下标循环怼
    public void shuffle(int[] nums, int l, int r) {
        // 切成一块一块的解决，每一块的长度满足(3^k)-1
        while (r - l + 1 > 0) {
            // 当前要处理的长度
            int lenAndOne = r - l + 2;

            // 计算小于等于lenAndOne并且是离lenAndOne最近的，满足(3^k)-1的数
            // 也就是找到最大的k，满足3^k <= lenAndOne
            int bloom = 3;
            // 初始k为1
            int k = 1;
            // 保证3^k <= lenAndOne
            // 要记住这个方法，就是求3次幂的时候直接用循环滚下去，利用之前求出来的结果，只需要再乘一个3就行了，这样效率可以更高一些，比每一轮都重新用Math.pow求快很多
            while (bloom <= lenAndOne / 3) {
                bloom *= 3;
                k++;
            }

            // 此时我们就先处理长度为bloom - 1长度的范围，至于剩下的长度留到后面的循环去弄。bloom - 1满足3^k -1
            // 下面这个流程就是将符合要求的前k个数移动到数组的最前面，下面的流程其实举个具体的例子就能明白了
            // 当前要解决长度为bloom-1的块，一半就是再除2
            int m = (bloom - 1) / 2;
            // [L..R]的中点位置
            int mid = (l + r) / 2;
            // 要旋转的左部分为[L+m...mid], 右部分为arr[mid+1..mid+m]
            // 注意在这里，nums下标是从0开始的
            rotate(nums, l + m, mid, mid + m);

            // 开始下标循环怼
            cycles(nums, l - 1, bloom, k);

            // 解决了前bloom-1的部分，剩下的部分继续处理，将要处理范围的左边界设置为l + bloom - 1，继续循环
            l = l + bloom - 1;
        }
    }

    public void cycles(int[] nums, int base, int bloom, int k) {
        for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
            // 根据我们的结论公式，算出来下一个要在什么位置，注意这个位置并不是真实的数组下标位置
            int next = (2 * trigger) % bloom;
            int cur = next;
            int record = nums[next + base];
            int tmp = 0;
            nums[next + base] = nums[trigger + base];
            // 每一轮循环时trigger就相当于这一次下标循环怼的起始位置，只要是循环过程中下标再次回到trigger，就说明这个环已经遍历完一遍了
            while (cur != trigger) {
                // 根据公式计算下一个位置
                next = (2 * cur) % bloom;
                // 下面开始将数移动到要去的位置上
                tmp = nums[next + base];
                nums[next + base] = record;
                cur = next;
                record = tmp;
            }
        }
    }

    // 将l~m和m+1~r两个部分做整体交换
    public void rotate(int[] arr, int l, int m, int r) {
        // 先对这两个部分自己内部做逆序
        reverse(arr, l, m);
        reverse(arr, m + 1, r);
        // 然后再把这两个部分的整体进行逆序
        reverse(arr, l, r);
    }

    // 对数组arr内的l~r范围进行逆序
    public void reverse(int[] arr, int l, int r) {
        while (l < r) {
            swap(l++, r--,arr);
        }
    }
}
