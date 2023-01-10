package 大厂刷题班.class33;
// 改进后的快排   快速排序  BFPRT算法
// https://leetcode.cn/problems/kth-largest-element-in-an-array/
public class Problem_0215_KthLargestElementInAnArray {
    public static int findKthLargest(int[] nums, int k) {
        // 数组长度
        int n = nums.length;
        // 当前这一轮改进后快排的左区间
        int L = 0;
        // 当前这一轮改进后快排的右区间
        int R = n - 1;
        // 当前这一轮改进后快排的左部分的边界，即L~l范围上都是小于v的数，l指向的也是小于v的数
        int l = L - 1;
        // 当前这一轮改进后快排的右部分的边界，即r~R范围上都是大于v的数，r指向的也是大于v的数
        int r = R + 1;
        // 找第k大的数，也就相当于找第n-k+1小的数
        // 但是数组下表是从0开始的，所以这里就是n-k即可
        k = n - k;

        while (true) {
            // 从L~R范围上随机找一个数作为基准
            // 这里先利用随机函数L + Math.random() * (R - L + 1)随机生成下标L~R范围上的数
            // 注意这里要注意在前面加上L，不然求出来的就是随机0~R-L了，我们需要随机L~R
            int v = nums[L + (int) (Math.random() * (R - L + 1))];

            // 下面的流程就是荷兰国旗问题了，将数组中小于v的放左边，等于v的放中间，大于v的放右边
            // 然后用l和r来标记三个部分的边界

            // 当前遍历到的位置，从L开始
            int cur = L;
            // 开始讲数组进行移动
            while (l < r && cur < r) {
                // 小于v的放左边
                if (nums[cur] < v) {
                    // cur放到++l位置
                    swap(++l, cur, nums);
                    // 大于v的放右边
                } else if (nums[cur] > v) {
                    // cur放到--r位置
                    swap(--r, cur, nums);
                    // 因为交换到cur位置的数是在右侧，还没有判断过他，所以交换到cur后还是需要在判断一次cur这个新数的大小，所以这里不执行cur++
                    continue;
                }
                // 继续向右遍历判断
                cur++;
            }

            // 至此，小于v的都在小于等于l的位置  l位置是小于v的数
            // 等于v的范围就是大于l小于r
            // 大于v的都在大于等于r的位置   r位置是大于v的数
            if (k > l && k < r) {
                // 如果k落在等于v范围上，直接返回v，v就第k小的数
                return v;
                // 如果此时k落在左部分，那么我们再去左部分去进行荷兰国旗操作
            } else if (k <= l) {
                // 新的范围就是L~l
                // 更新R
                R = l;
                // 在新的范围上初始化r和l
                r = l + 1;
                l = L - 1;
                // 如果此时k落在右部分，那么我们再去右部分去进行荷兰国旗操作
            } else {
                // 新的范围就是r~R
                // 更新L
                L = r;
                // 在新的范围上初始化r和l
                l = L - 1;
                r = R + 1;
            }
        }
    }

    // 交换操作
    public static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
