package 体系学习班.class05;

/**
 * 给定一个数组arr，两个整数lower和upper，
 * 返回arr中有多少个子数组的累加和在[lower,upper]范围上
 *
 * https://leetcode.cn/problems/count-of-range-sum/submissions/
 */
public class Code01_CountOfRangeSum {
    public int countRangeSum(int[] nums, int lower, int upper) {
        // 空数组直接返回0
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 这道题我们需要用到前缀和，所以在一开始先把这个数组所有的前缀和求出来
        // 我们求一个数组指定区间的和时，就可以将其转换为两个前缀和相减的形式
        // sum(arr, i, j) = sum(arr, 0, j) - sum(arr, 0, i - 1)

        // 注意这里要用long类型，因为题干中说nums中的数都是int型，但是我们在求前缀和的时候，就有可能超过int范围，所以这里使用long避免数据溢出，以后这个问题一定要注意数据类型溢出的问题
        long[] sum = new long [nums.length];
        // 将第一个数的前缀和提前求出
        sum[0] = nums[0];
        // 通过一次遍历，将数组全部的前缀和求出
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        // 进入递归方法进行数据分割，这个时候传入的就是前缀和数组了，这道题也就转换成处理前缀和的题目
        return process(sum, 0, sum.length -1, lower, upper);
    }

    /**
     * 递归驱动，这里就是将大的数据，递归划分成小数据来进行梳理
     */
    public int process(long[] sum, int L, int R, int lower, int upper) {
        /**
         * 递归出口
         * 当递归到最后一层的时候，就会只剩下一个数，此时我们就可以认为这组数据就已经是有序了
         * 当前这一个数的含义就是以L(R)位置结尾的前缀和，我们在merge方法中，去统计前缀和是不是符合条件时，取得都是右组某个数和左组某个数之间有多少种子数组符合条件的情况
         * 但是忽略了只有右组一个数是否符合条件的情况，也就是从下标0开始，到某个数结尾的整个前缀和本身是不是就符合条件的情况
         * 所以在递归驱动中，我们就在递归到最后一层的时候，单独判断一下前缀和本身这个区间是不是就是符合条件的，符合条件则返回1
         */
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }

        // 中间值
        int mid = L + ((R - L) >> 1);

        // 进行递归归并
        return process(sum, L, mid, lower, upper) +
                process(sum, mid + 1, R, lower, upper) +
                merge(sum, L, mid, R, lower, upper);
    }

    // 先统计符合条件的子数组个数，然后再将左右两组进行归并排序
    /**
     * 这里我们讲解一下这道题的核心解题思路：
     * 这道题目的本质其实就是让我们去求在一个数组中，有多少个子数组的累加和是在[lower, upper]范围中的
     *
     * 当我们看到这个题是和求数组指定下标区间的和有关系，那么我们就马上想到这里可以先将累加和转换成前缀和的形式
     * 数组下标i~j的累加和就可以转换成两个前缀和相减的形式：sum(arr, i, j) = sum(arr, j, 0) - sum(arr, i - 1, 0)
     * 这样，我们就把这个问题转换成类前缀和的形式。
     *
     * 因为前缀和肯定都是从下标0开始计算的，所以我们可以把统计累加和在[lower, upper]范围中子数组的个数也换一个角度
     * 我们可以依次统计以指定下标位置为结尾的子数组，有多少个累加和在[lower, upper]范围中，举个例子：
     * 比如nums = {2,4,3,6}  下标依次是0~3
     * 我们统计过程就可以这样：
     * 1、以0下标结尾的子数组，有多少个累计和在[lower, upper]中：以0下标结尾的子数组只有下标0~0这一个num(0,0)
     * 2、以1下标结尾的子数组，有多少个累计和在[lower, upper]中：以1下标结尾的子数组有num(0,1)、num(1,1)
     * 3、以2下标结尾的子数组，有多少个累计和在[lower, upper]中：以2下标结尾的子数组有num(0,2)、num(1,2)、num(2,2)
     * 4、以3下标结尾的子数组，有多少个累计和在[lower, upper]中：以3下标结尾的子数组有num(0,3)、num(1,3)、num(2,3)、num(3,3)
     *
     * 总的来说就是依次以每一个位置为结尾，来看有多少个以此位置为结尾的子数组符合要求，这也就转换成了一组数，求一个数左边或者右边符合某种性质的数的个数问题，这种问题一般就用归并排序的思路
     *
     * 用上面讲的这两个基础，结合一下，我们就可以把题目转换为如下问题：
     * 我们假设0~i整体的累加和是x，这个数也是一个前缀和。我们去统计以i位置为结尾的子数组中，有多少个子数组累加和在[lower, upper]范围上
     * 也就是求有多少个j~i下标的子数组累加和在[lower, upper]范围上，i是固定的，j是浮动的。也就是说i是右组数的下标，j是左组数的下标
     * 这里我们就可以转换成前缀和的形式sum(arr, j, i) = sum(arr, 0, i) - sum(arr, 0, j - 1)是否在[lower, upper]范围上
     *
     * 我们以右组中的数为基础（注意，这里的左右组的数全部都是前缀和），也就是sum(arr, 0, i) = x这个数是确定的，然后[lower, upper]范围也是确定的。
     * 所以剩下的就是要去判断左组的数（前缀和）中的范围是不是在[x - upper, x - lower]范围中，
     * 只要是在这个范围中，就能确定sum(arr, j, i) = sum(arr, 0, i) - sum(arr, 0, j - 1)也一定在[lower, upper]范围中
     *
     * 至此，我们就通过用前缀和将题目转换成了以一个位置的数为基准，统计这个数位置的左边的所有数中大小在[lower, upper]范围上的个数，也就可以应用归并排序思路求解
     *
     * 我们之所以使用归并排序的思路，就是因为这样能够保证左右两组内部都是有序的，也就保证了我们在比较大小是不是符合条件时就不用回退指针了，时间复杂度大大降低
     */
    public int merge(long[] sum, int L, int mid, int R, int lower, int upper) {
        // 统计符合条件的子数组个数
        int ans = 0;
        // 窗口边界都从左组最左端开始，这个窗口就是看当以一个右组的数为基准时，左组有哪些数大小是符合范围条件的，因为组内都是有序的数，所以符合范围条件的书也肯定都是相邻连续的
        // 窗口左边界
        int leftWindow = L;
        // 窗口有边界
        int rightWindow = L;
        // [windowL, windowR)

        // 以右组为基准，依次遍历右组的每个数，以这个数的大小为基准，去看左组有哪些数字符合大小范围条件。这个过程就是判断符合条件的子数组个数
        // 之所以右组组内不去统计，是因为在上一层递归中，右组组内的符合条件的子数组个数已经统计完了，所有统计完的数都会被有序归并到一起，下一轮递归再去统计的时候就不需要考虑组内符合条件的子数组了
        // 而且组内的数也已经完成了排序，位置也不是原有位置了，没办法再去统计了
        for (int i = mid + 1; i <= R; i++) {
            // 求出左组的数要满足的大小范围
            long min = sum[i] - upper;
            long max = sum[i] - lower;

            /**
             * 下面去在左组进行窗口下标的移动，注意，这里因为左右两组组内都是有序的，所以就能保证窗口边界下标是不需要回退的，这样时间复杂度也就大大提高了。
             * 举一个例子：
             * 左组[2,5,8,9,11,15]    右组[6,7,7,8,10,11]   左右两组数存的都是前缀和  [lower, upper] = [-1,2]
             * leftWindow和rightWindow都是从左组最左边开始
             * 先以右组下标0为基准，求左组有那些数在[6-upper, 6-lower] = [4, 7]范围中
             * leftWindow就会指向左组下标1，rightWindow就会指向右组下标2，这样符合条件的个数就有2-1=1个
             * 然后右组下标向后移动一个位置来到下标1，这个时候左组窗口的两个下标是不需要回退的，
             * 因为组内都是大小有序的，所以此时右组指向的数一定比它的上一个数大，那么判断左组要符合的范围的时候[x-upper, x-lower]，因为x变大了，所以范围的左右边界也都变大了
             *
             * 所以当前leftWindow指向的数比之前要判断范围的左边界小，那么leftWindow肯定也比现在要判断的范围左边界小，因为左组组内也是有序的，所以leftWindow左边的书也肯定比现在的左边界要小
             * leftWindow是要找第一个大于等于左边界的位置，所以一定就不用回退了
             *
             * rightWindow同理，当前rightWindow位置左边的数比之前要判断范围的右边界小，那么rightWindow左边的数肯定也比现在要判断的范围右边界小，因为左组组内是有序的，并且现在要判断的右边界也变大了，
             * 所以rightWindow也就没有必要回退了，rightWindow是要找第一个大于右边界的位置
             */

            // 移动窗口左边界下标，找到第一个大于等于左边界的位置
            while(leftWindow <= mid && sum[leftWindow] < min) {
                leftWindow++;
            }
            // 移动窗口右边界下标，找到第一个大于右边界的位置
            while(rightWindow <= mid && sum[rightWindow] <= max) {
                rightWindow++;
            }

            // 两个边界下标相减就是符合条件的个数
            ans += (rightWindow - leftWindow);
        }

        // 统计完之后，下面就是进行常规的归并排序了，将两组已经统计完的数进行归并排序
        long[] help = new long[R - L + 1];
        int p1 = L;
        int p2 = mid + 1;
        int index = 0;

        while(p1 <= mid && p2 <= R) {
            help[index++] = sum[p1] < sum[p2] ? sum[p1++] : sum[p2++];
        }
        while(p1 <= mid) {
            help[index++] = sum[p1++];
        }
        while(p2 <= R) {
            help[index++] = sum[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            sum[i + L] = help[i];
        }

        return ans;
    }
}
