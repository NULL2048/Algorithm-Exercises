package 大厂刷题班.class20;

import java.util.Arrays;

// 这是一类问题：完美洗牌问题
// 下标循环怼
// 数组   分治
// 测试连接：https://leetcode.cn/problems/wiggle-sort/
public class Code03_ShuffleProblem {
    public void wiggleSort(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return;
        }
        // 这道题需要先排序，因为这道题本意并不是说要将数组中的数据按照完美洗牌两两交错着重排一遍，而是说按照小大小大小大这样的间隔顺序排列，而我们的完美洗牌代码只是按照位置来两两交错，
        // 如果想要左到题目要求的小大小大小大这种按照大小交错，就需要先把数组搞成有序的，然后再调用我们的完美洗牌代码就可以了。
        // 还要注意的是这个是280. 摆动排序，这个题要求是nums[0] <= nums[1] >= nums[2] <= nums[3]... 是带等号的，所以用完美洗牌的代码可以求解，
        // 但是324. 摆动排序 II这道题是不带等号的，所以就还需要考虑将相等的数不能挨着，完美洗牌问题做不到这个，完美洗牌只是单纯的将位置来进行交错排列，但是无法根据它们的大小来调整顺序，所以完美洗牌代码是有可能将两个相等的数挨着的
        // 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
        Arrays.sort(nums);
        int n = nums.length;
        // 这道题本身并没有限制数组长度是否是偶数，而我们完美洗牌代码必须保证传入的数组长度是偶数才可以，所以下面还需要做数组长度的奇偶判断，做相应的处理
        // 偶数情况，因为力扣题目要求的完美问题是小大小大小大这样间隔的，但是如果我们的数组是递增的，用我们这个完美洗盘的代码搞出来的4 1 5 2 6 3，但是我们最后想要的其实是1 4 2 5 3 6
        // 所以偶数情况在用我们的代码完成完美洗牌后，还需要两两为一组，在组内交换两个数的位置，才是力扣这道题最后的答案。
        if ((n & 1) == 0) {
            // 先进行完美洗牌的前半部分和后半部分的交叉排序
            shuffle(nums, 0, n - 1);
            // 在按照两个为一组，在内部交换两数位置，这样得到的结果就符合本题要求了
            int temp;
            for (int i = 0; i < n; i+= 2) {
                temp = nums[i];
                nums[i] = nums[i + 1];
                nums[i + 1] = temp;
            }
            // 奇数情况   例如1 2 3 4 5，第一个位置的1保持不动，然后去对后面四个数做下标循环怼，因为后面四个是偶数，符合下标循环怼的要求，最后结果正好是 1 4 2 5 3，是我们要的答案
        } else {
            // 将1~n-1范围上做完美洗牌，下标0位置不动，最后的结果就是本题要的结果。
            shuffle(nums, 1, n - 1);
        }
    }
    /**
     * 完美洗牌问题的算法模板，这是整个代码的核心
     * nums：数组长度必须为偶数
     * 在nums[l...r]上做完美洗牌的调整（nums[l...r]范围上一定要是偶数个数字）
     */
    public void shuffle(int[] nums, int l, int r) {
        // 切成一块一块的解决，每一块的长度满足(3^k)-1
        // 如果此时r > l，就说明此时还有范围要搞（l==r有1个数，也就不需要再变动了，符合公式要求的最低长度也是2），当r和l错过去了，就说明已经完成全部位置的操作了
        while (r > l)  {
            // 当前要处理的r - l范围上的数据
            // 长度为n
            int n = r - l + 1;

            // 计算小于等于len并且是离n最近的，满足(3^k)-1的数
            // 也就是找到最大的k，满足3^k <= n+1
            int k = 1; // 初始k为1
            int base = 3; // 初始值
            // 保证3^k <= n+1
            // 要记住这个方法，就是求3次幂的时候直接用循环滚下去，利用之前求出来的结果，只需要再乘一个3就行了，这样效率可以更高一些，比每一轮都重新用Math.pow求快很多
            while (base * 3 - 1 <= n) {
                base *= 3;
                k++;
            }
            // 此时我们就先处理长度为base - 1长度的范围，至于剩下的长度留到后面的循环去弄。base - 1满足3^k -1
            // 下面这个流程就是将符合要求的前k个数移动到数组的最前面，下面的流程其实举个具体的例子或者直接看笔记就能明白了
            // 当前要解决长度为base-1的块，一半就是再除2
            int half = (base - 1) >> 1;
            // [L..R]的中点位置
            int mid = (l + r) >> 1;
            // 要旋转的左部分为[L+half...mid], 右部分为arr[mid+1..mid+half]
            // 注意在这里，arr下标是从0开始的
            rotate(nums, l + half, mid, mid + 1, mid + 1 + half - 1);

            // 旋转完成后，从l开始算起，长度为base-1的部分进行下标连续推
            // 从l位置开始，往右n的长度这一段，做下标循环怼
            // 每一个环的起始位置依次为1,3,9...
            // 当前要处理数组的起始位置就是l，在后面算数组中真实下标时，都要加上l

            // 当前要处理数组的长度
            int len = base - 1;
            // trigger就是在我们结论中起始位置的下标，注意是从1开始的，如果想要求出来在数组中真实对应的下标，应该用start + trigger - 1(也就是用此时数组的左边界l加上trigger再减1，因为trigger是从1开始的，多算了一个)
            // 找到每一个出发位置trigger，一共k个（1、3、9...3^(k - 1)）
            // 每一个trigger都进行下标连续推
            // 出发位置是从1开始算的，而数组下标是从0开始算的。
            // i是为了来控制求3^(k - 1)，i会控制循环一共只会执行k次，但是因为trigger是从1开始的，所以最终其实只会有k-1个3相乘，也就得到了3 ^ (k - 1)
            for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
                // 当前遍历到的在数组中真实的下标位置是l + trigger - 1
                // 我们要将nums[l + trigger - 1]放到下一个要去的位置，所以这里要将该位置的值记录一下
                int preValue = nums[l + trigger - 1];
                // 根据我们的结论公式，算出来下一个要在什么位置，注意这个位置并不是真实的数组下标位置
                int cur = modifyIndex(trigger, len);
                // 每一轮循环时trigger就相当于这一次下标循环怼的起始位置，只要是循环过程中下标再次回到trigger，就说明这个环已经遍历完一遍了
                while (cur != trigger) {
                    // 当前来到的位置在数组的真实下标l + cur - 1，（l就是当前处理范围的最左边界下标）
                    int tmp = nums[l + cur - 1];
                    // 将上一个位置的值放到当前位置上
                    nums[l + cur - 1] = preValue;
                    // 将当前位置的值作为下一轮的上一个位置的值，我们要将nums[cur + l - 1]放到下一个位置上去
                    preValue = tmp;
                    // 根据公式计算下一个位置
                    cur = modifyIndex(cur, len);
                }
                // 当cur == trigger时会跳出循环，但此时trigger位置的值还没有放，所以要将preValue赋值给当前环的起始位置l + cur  - 1
                nums[l + cur - 1] = preValue;
            }

            // 解决了前base-1的部分，剩下的部分继续处理，将要处理范围的左边界设置为l + base - 1，继续循环
            l = l + base - 1;
        }
    }

    // 完美洗牌问题的公式结论，这个记住即可，不用管他的证明
    // 当前来到index位置，当前进行下标循环堆的数组长度是n，返回要将index位置的数据移动到哪个下标上
    public int modifyIndex(int index, int n) {
        // 分段函数，根据index不同来返回不同的下一个位置的下标
        if (index <= n / 2) {
            return index * 2;
        } else {
            return (index - n / 2) * 2 - 1;
        }
    }

    // 将l1~r1和l2~r2两个部分做整体交换
    // 这两个部分是连续的，即r1 + 1 = l2
    public void rotate(int[] nums, int l1, int r1, int l2, int r2){
        // 先对这两个部分自己内部做逆序
        reverse(nums, l1, r1);
        reverse(nums, l2, r2);
        // 然后再把这两个部分的整体进行逆序
        reverse(nums, l1, r2);
    }
    // 对数组nums内的l~r范围进行逆序
    public void reverse(int[] nums, int l, int r) {
        int temp;
        while (l < r) {
            temp = nums[l];
            nums[l++] = nums[r];
            nums[r--] = temp;
        }
    }


    // 下面是左神的代码，还有对数器
    static class Solution {
        // 数组的长度为len，调整前的位置是i，返回调整之后的位置
        // 下标不从0开始，从1开始
        public static int modifyIndex1(int i, int len) {
            if (i <= len / 2) {
                return 2 * i;
            } else {
                return 2 * (i - (len / 2)) - 1;
            }
        }

        // 数组的长度为len，调整前的位置是i，返回调整之后的位置
        // 下标不从0开始，从1开始
        public static int modifyIndex2(int i, int len) {
            return (2 * i) % (len + 1);
        }

        // 主函数
        // 数组必须不为空，且长度为偶数
        public static void shuffle(int[] arr) {
            if (arr != null && arr.length != 0 && (arr.length & 1) == 0) {
                shuffle(arr, 0, arr.length - 1);
            }
        }

        // 在arr[L..R]上做完美洗牌的调整（arr[L..R]范围上一定要是偶数个数字）
        public static void shuffle(int[] arr, int L, int R) {
            while (R - L + 1 > 0) { // 切成一块一块的解决，每一块的长度满足(3^k)-1
                int len = R - L + 1;
                int base = 3;
                int k = 1;
                // 计算小于等于len并且是离len最近的，满足(3^k)-1的数
                // 也就是找到最大的k，满足3^k <= len+1
                while (base <= (len + 1) / 3) { // base > (N+1)/3
                    base *= 3;
                    k++;
                }
                // 3^k -1
                // 当前要解决长度为base-1的块，一半就是再除2
                int half = (base - 1) / 2;
                // [L..R]的中点位置
                int mid = (L + R) / 2;
                // 要旋转的左部分为[L+half...mid], 右部分为arr[mid+1..mid+half]
                // 注意在这里，arr下标是从0开始的
                rotate(arr, L + half, mid, mid + half);
                // 旋转完成后，从L开始算起，长度为base-1的部分进行下标连续推
                cycles(arr, L, base - 1, k);
                // 解决了前base-1的部分，剩下的部分继续处理
                L = L + base - 1; // L ->     [] [+1...R]
            }
        }

        // 从start位置开始，往右len的长度这一段，做下标连续推
        // 出发位置依次为1,3,9...
        public static void cycles(int[] arr, int start, int len, int k) {
            // 找到每一个出发位置trigger，一共k个
            // 每一个trigger都进行下标连续推
            // 出发位置是从1开始算的，而数组下标是从0开始算的。
            for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
                int preValue = arr[trigger + start - 1];
                int cur = modifyIndex2(trigger, len);
                while (cur != trigger) {
                    int tmp = arr[cur + start - 1];
                    arr[cur + start - 1] = preValue;
                    preValue = tmp;
                    cur = modifyIndex2(cur, len);
                }
                arr[cur + start - 1] = preValue;
            }
        }

        // [L..M]为左部分，[M+1..R]为右部分，左右两部分互换
        public static void rotate(int[] arr, int L, int M, int R) {
            reverse(arr, L, M);
            reverse(arr, M + 1, R);
            reverse(arr, L, R);
        }

        // [L..R]做逆序调整
        public static void reverse(int[] arr, int L, int R) {
            while (L < R) {
                int tmp = arr[L];
                arr[L++] = arr[R];
                arr[R--] = tmp;
            }
        }

        public static void wiggleSort(int[] arr) {
            if (arr == null || arr.length == 0) {
                return;
            }
            // 假设这个排序是额外空间复杂度O(1)的，当然系统提供的排序并不是，你可以自己实现一个堆排序
            Arrays.sort(arr);
            if ((arr.length & 1) == 1) {
                shuffle(arr, 1, arr.length - 1);
            } else {
                shuffle(arr, 0, arr.length - 1);
                for (int i = 0; i < arr.length; i += 2) {
                    int tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                }
            }
        }

        // for test
        public static boolean isValidWiggle(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                if ((i & 1) == 1 && arr[i] < arr[i - 1]) {
                    return false;
                }
                if ((i & 1) == 0 && arr[i] > arr[i - 1]) {
                    return false;
                }
            }
            return true;
        }

        // for test
        public static void printArray(int[] arr) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }

        // for test
        public static int[] generateArray() {
            int len = (int) (Math.random() * 10) * 2;
            int[] arr = new int[len];
            for (int i = 0; i < len; i++) {
                arr[i] = (int) (Math.random() * 100);
            }
            return arr;
        }

        public static void main(String[] args) {
            for (int i = 0; i < 5000000; i++) {
                int[] arr = generateArray();
                wiggleSort(arr);
                if (!isValidWiggle(arr)) {
                    System.out.println("ooops!");
                    printArray(arr);
                    break;
                }
            }
        }
    }
}
