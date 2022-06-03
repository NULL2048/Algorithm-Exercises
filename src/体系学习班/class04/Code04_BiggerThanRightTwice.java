package 体系学习班.class04;

//  本题测试链接 : https://leetcode.com/problems/reverse-pairs/
// 翻转对（逆序对）
public class Code04_BiggerThanRightTwice {
    public int reversePairs(int[] nums) {
        // 数组为空或不足两个元素直接返回0
        if (nums == null || nums.length < 2) {
            return 0;
        }

        return process(nums, 0, nums.length - 1);
    }

    // 递归驱动，将数据去进行分割
    public static int process(int[] nums, int l, int r) {
        // 递归出口，递归分割到只剩下一个数的时候，就返回，向上开始归并
        if (l == r) {
            return 0;
        }

        int mid = l + ((r - l) >> 1);

        return process(nums, l, mid) + process(nums, mid + 1, r) + merge(nums, l, mid, r);
    }

    /**
     * 划分成了两组，[L....M] 和 [M+1....R]，对他们来进行处理
     */
    public static int merge(int[] nums, int l, int mid, int r) {
        // 记录本轮统计出来的翻转对个数
        int ans = 0;
        // 右组的下标起点
        int rightIndex = mid + 1;

        // 左右两组的下标都从左边开始向右移动
        for (int i = l; i <= mid; i++) {
            // 如果右组当前指向的数的二倍比左组当前指向的数小，说明这个数符合翻转对要求
            while (rightIndex <= r &&  (long) nums[rightIndex] * 2 < (long) nums[i]) {
                // 进入到merge方法的两组数肯定都分别已经排序好了，所以我们再将右组下标向右移动，判断下一个数是不是也满足要求
                // 遍历右组直到发现一个数的二倍比左组指向的数打，则结束本轮右组的遍历，因为数据是有序的，它本身已经比左组的数打了，它右边的数一定也是大的
                rightIndex++;
            }
            // 当前右组指针-右组左边界就是所有符合翻转对数的个数，将其累加到ans中
            ans += rightIndex - (mid + 1);

            // 下面就将左组的指针右移，因为此时左组的数已经小于右组指向的数的二倍了，我们需要看一下比当前左组指向的数右边更大的数是不是能满足翻转对条件
        }


        // 通过上面的过程，我们就求出了本轮的翻转对个数，下面我们再将两组数进行归并排序
        // 创建临时数组
        int[] help = new int[r - l + 1];
        int i = 0;
        int p1 = l;
        int p2 = mid + 1;

        // 进行归并排序
        while (p1 <= mid && p2 <= r) {
            help[i++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
        }

        while ( p1 <= mid) {
            help[i++] = nums[p1++];
        }

        while ( p2 <= r) {
            help[i++] = nums[p2++];
        }

        // 将help中已经排序好的数写回到nums中
        for (int j = 0; j < help.length; j++) {
            nums[l + j] = help[j];
        }

        // 返回翻转对个数
        return ans;
    }
}
