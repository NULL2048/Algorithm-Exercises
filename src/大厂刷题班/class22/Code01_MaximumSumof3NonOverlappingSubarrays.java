package 大厂刷题班.class22;

// 范围尝试模型   子数组  滑动窗口
// 本题测试链接 : https://leetcode.cn/problems/maximum-sum-of-3-non-overlapping-subarrays/
public class Code01_MaximumSumof3NonOverlappingSubarrays {
    // 和本题无关，统计一个数组中任意0~i位置范围上最大累加和的子数组是多少。并不一定要i结尾
    public static int[] maxSumArray1(int[] arr) {
        int N = arr.length;
        int[] help = new int[N];
        // help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
        help[0] = arr[0];
        for (int i = 1; i < N; i++) {
            int p1 = arr[i];
            int p2 = arr[i] + help[i - 1];
            help[i] = Math.max(p1, p2);
        }
        // dp[i] 在0~i范围上，随意选一个子数组，累加和最大是多少？
        int[] dp = new int[N];
        dp[0] = help[0];
        for (int i = 1; i < N; i++) {
            int p1 = help[i];
            int p2 = dp[i - 1];
            dp[i] = Math.max(p1, p2);
        }
        return dp;
    }


    // 本题代码，这是我自己写的版本
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        // left[i]：从0~i范围上长度为k的子数组中，累加和最大是多少
        int[] left = new int[n];
        // leftStartIndex[i]：从0~i范围上长度为k的子数组中，累加和最大的起始下标是是多少，和left[]是成对使用的
        int[] leftStartIndex = new int[n];
        // right[i]：从i~n-1范围上长度为k的子数组中，累加和最大是多少
        int[] right = new int[n];
        // rightStartIndex[i]：从i~n-1范围上长度为k的子数组中，累加和最大的起始下标是是多少，和left[]是成对使用的
        int[] rightStartIndex = new int[n];

        // 构造left[]和leftStartIndex[]
        int sum = 0;
        // 先生成第一个长度为k的子数组的窗口
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        left[k - 1] = sum;
        leftStartIndex[k - 1] = 0;
        // 窗口向右滑动，构造left[]和leftStartIndex[]
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            // 比较以i结尾的长度为k的子数组累加和和left[i - 1]最大累加和，哪个大就将哪个值赋值给left，并记录当前这种情况的子数组起始位置。
            // 这里要写等于号，因为要保证值相同的情况下，字典序小的返回
            if (left[i - 1] >= sum) {
                left[i] = left[i - 1];
                leftStartIndex[i] = leftStartIndex[i - 1];
            } else {
                left[i] = sum;
                leftStartIndex[i] = i - k + 1;
            }
        }

        // 构造right[]和rightStartIndex[]
        sum = 0;
        // 先生成第一个长度为k的子数组的窗口
        for (int i = n - 1; i >= n - k; i--) {
            sum += nums[i];
        }
        right[n - k] = sum;
        rightStartIndex[n - k] = n - k;
        // 窗口向左滑动，构造right[]和rightStartIndex[]
        for (int i = n - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];
            // 比较以i开始的长度为k的子数组累加和和right[i + 1]最大累加和，哪个大就将哪个值赋值给right，并记录当前这种情况的子数组起始位置。
            // 这里不写等于号，因为要保证值相同的情况下，字典序小的返回
            if (right[i + 1] > sum) {
                right[i] = right[i + 1];
                rightStartIndex[i] = rightStartIndex[i + 1];
            } else {
                right[i] = sum;
                rightStartIndex[i] = i;
            }
        }

        // 构造长度为k的窗口，保证窗口的左部分和右部分至少有k个字符，然后将这个窗口向右滑动尝试所有的情况，然后从左右部分找到最大累加和的子数组，将所有情况都列举一边，找打三个数组累加和最大的情况，并将他们的起始位置返回
        sum = 0;
        // 一开始要保证左部分至少有k个字符
        for (int i = k - 1; i < 2 * k - 1; i++) {
            sum += nums[i];
        }
        int max = Integer.MIN_VALUE;
        int[] ans = new int[3];
        int ansSum;
        // 窗口做边界不能超过这个位置，因为要保证右部分至少有k个字符
        int limit =  n - 2 * k;
        for (int l = k; l <= limit; l++) {
            int r = l + k - 1;
            sum = sum - nums[l - 1] + nums[r];
            // 计算当前情况三个子数组的累加和
            ansSum = left[l - 1] + sum + right[r + 1];
            // 如果大于当前最大累加和，则重新更新max
            if (max < ansSum) {
                max = ansSum;
                ans[0] = leftStartIndex[l - 1];
                ans[1] = l;
                ans[2] = rightStartIndex[r + 1];
            }
        }

        return ans;
    }

    // 本题代码，左神写的，这个代码要稍微好一点，因为他没有用left和right数组，只用了一个range数组来记录每一种情况的最大累加和
    public static int[] maxSumOfThreeSubarrays1(int[] nums, int k) {
        int N = nums.length;
        // range[i]：记录以i开头的长度为k的子数组的最大累加和
        // 这个代码就只用了这一个数组来记录最大累加和，不像我们自己写的代码，还用了left和right两个数组，这也是这个代码块1ms的原因
        // 其他的整体思路是基本一致的
        int[] range = new int[N];
        // 记录从左往右判断的情况下最大累加和子数组的开始位置
        int[] left = new int[N];
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        range[0] = sum;
        // 先算0~i范围上任意子数组中，累加和最大的是多少
        left[k - 1] = 0;
        int max = sum;
        for (int i = k; i < N; i++) {
            sum = sum - nums[i - k] + nums[i];
            range[i - k + 1] = sum;
            left[i] = left[i - 1];
            if (sum > max) {
                max = sum;
                left[i] = i - k + 1;
            }
        }
        sum = 0;
        for (int i = N - 1; i >= N - k; i--) {
            sum += nums[i];
        }
        max = sum;
        // 再算i~n-1范围上任意子数组中，累加和最大的是多少
        int[] right = new int[N];
        right[N - k] = N - k;
        for (int i = N - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];
            right[i] = right[i + 1];
            if (sum >= max) {
                max = sum;
                right[i] = i;
            }
        }

        // 其实就是用一个长度为3的滑动窗口依次向右滑动，然后去看左部分累加和最大长度为3的子数组，中间窗口内部分的累加和，和右部分累加和最大长度为3的子数组都是多少
        // 然后三部分累加起来，在整个遍历过程中取最大值就是结果，并且这道题要我们返回划分点，所以还需要一些记录划分点的辅助数组
        int a = 0;
        int b = 0;
        int c = 0;
        max = 0;
        for (int i = k; i < N - 2 * k + 1; i++) { // 中间一块的起始点 (0...k-1)选不了 i == N-1
            int part1 = range[left[i - 1]];
            int part2 = range[i];
            int part3 = range[right[i + k]];
            if (part1 + part2 + part3 > max) {
                max = part1 + part2 + part3;
                a = left[i - 1];
                b = i;
                c = right[i + k];
            }
        }
        return new int[] { a, b, c };
    }

}
