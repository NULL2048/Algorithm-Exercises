package 大厂刷题班.class07;

// 桶排序   鸽笼原理
// 这个题非常难，在面试场上也玩不出什么花，就算是出也大概就是这个题的原型，直接把这个题背下来就可以了
// 测试链接 : https://leetcode.cn/problems/maximum-gap/
public class Code03_MaxGap {
    public int maximumGap(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int max = nums[0];
        int min = nums[0];
        // 求出数组中的最大值和最小值
        for (int i = 1; i < n; i++) {
            max = Math.max(max, nums[i]);
            min = Math.min(min, nums[i]);
        }
        // 如果数组中的最大值和最小值是同一个数，说明整个数组是同一个值，最大差值为0
        if (max == min) {
            return 0;
        }

        // 每个桶中存三个数据，当前桶是否有值，桶中的最大值，桶中的最小值
        // 创建n + 1个桶，利用鸽笼原理来求出最大差值
        boolean[] hasNum = new boolean[n + 1];
        int[] maxs = new int[n + 1];
        int[] mins = new int[n + 1];
        // 遍历nums数组，将所有的数添加到桶中
        for (int i = 0; i < n; i++) {
            // 计算当前的数应该放到哪个桶中
            int index = bucket(n, max, min, nums[i]);
            // 记录每个桶中的最大值和最小值
            maxs[index] = hasNum[index] == true ? Math.max(maxs[index], nums[i]) : nums[i];
            mins[index] = hasNum[index] == true ? Math.min(mins[index], nums[i]) : nums[i];
            hasNum[index] = true;
        }

        // 注意，题目要求是相邻数的最大差值，只有前面桶的最大值和后面最近的有数的桶的最小值才是相邻的
        // 左边离当前位置最近的非空桶的下标，用来找到距离自己左边最近的非空桶的最大值
        int leftMaxIndex = -1;
        // 最大差值
        int ans = -1;
        // 遍历所有的桶，找最大差值
        for (int i = 0; i < n + 1; i++) {
            // 最大差值一定是相邻的两个非空头的最大值和最小值的差值（两个非空桶可能挨着，也可能中间隔了一个空桶）
            if (hasNum[i] == true) {
                if (leftMaxIndex != -1) {
                    ans = Math.max(ans, mins[i] - maxs[leftMaxIndex]);
                }
                // 更新最近的非空桶
                leftMaxIndex = i;
            }
        }

        return ans;
    }

    // 计算当前数应该放到哪个桶里
    // 使用long类型是为了避免在计算过程中产生溢出
    // 这个公式就硬记住
    public int bucket(long len, long max, long min, long num) {
        return (int) ((num - min) * len / (max - min));
    }
}
