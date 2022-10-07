package 大厂刷题班.class03;

import java.util.Arrays;
import java.util.TreeSet;

// 分治 暴力递归
// 本题测试链接 : https://leetcode.cn/problems/closest-subsequence-sum/
// 本题数据量描述:
// 1 <= nums.length <= 40
// -10^7 <= nums[i] <= 10^7
// -10^9 <= goal <= 10^9
// 通过这个数据量描述可知，需要用到分治，因为数组长度不大
// 而值很大，用动态规划的话，表会爆
public class Code07_ClosestSubsequenceSum {
    // 1、这个分支是采用有序表，如果想要优化常数时间，可以自己改写成用数组的方法，即方法二
    public int minAbsDifference(int[] nums, int goal) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return goal;
        }
        // 一共有多少个数字
        int n = nums.length;
        // 将数组分成两半
        int mid = (n - 1) >> 1;
        // 记录左半部分的所有累加和   采用有序表，默认升序组织数据
        TreeSet<Integer> sumL = new TreeSet<>();
        // 记录右半部分的所有累加和   采用有序表，默认升序组织数据
        TreeSet<Integer> sumR = new TreeSet<>();

        // 计算得到左右两部分的所有累加和
        process(0, mid, 0, nums, sumL);
        process(mid + 1, n - 1, 0, nums, sumR);

        // 找到最小的abs(sum - goal)，因为左右部分的累加和中肯定包含了0的情况，也就包括了只取右部分或左部分的数组成累加和的情况了
        int min = Math.abs(goal);
        for (Integer l : sumL) {
            // 确定一个左部分的累加和，去找最优的能和其匹配组成累加和的右部分的累加和
            int rest = goal - l;

            // 使用有序表的floor和ceiling，快速找到大于等于rest最小的数和小于等于rest最大的数，然后来看这两个数哪个和左部分累加和组合能让abs(sum - goal)最小（时间复杂度是LogN）
            Integer r1 = sumR.floor(rest);
            Integer r2 = sumR.ceiling(rest);

            if (r1 != null) {
                min = Math.min(min, Math.abs(rest - r1));
            } else {
                min = Math.min(min, Math.abs(rest));
            }

            if (r2 != null) {
                min = Math.min(min, Math.abs(rest - r2));
            } else {
                min = Math.min(min, Math.abs(rest));
            }
        }

        return min;
    }

    public void process(int index, int end, int sum, int[] nums, TreeSet<Integer> arrSum) {
        // basecase
        if (index == end + 1) {
            // 将找到的当前情况的累加和加入到set中
            arrSum.add(sum);
            return;
        }

        // 选择一：选择当前的数组成累加和
        process(index + 1, end, sum + nums[index], nums, arrSum);
        // 选择二：不选当前的数组成累加和
        process(index + 1, end, sum, nums, arrSum);
    }



    // 2、用数组来代替有序表，实现常熟优化，但实际时间复杂度和上面的写法是一样的
    public static int[] l = new int[1 << 20];
    public static int[] r = new int[1 << 20];

    public static int minAbsDifference1(int[] nums, int goal) {
        if (nums == null || nums.length == 0) {
            return goal;
        }
        int le = process1(nums, 0, nums.length >> 1, 0, 0, l);
        int re = process1(nums, nums.length >> 1, nums.length, 0, 0, r);
        Arrays.sort(l, 0, le);
        Arrays.sort(r, 0, re--);
        int ans = Math.abs(goal);
        for (int i = 0; i < le; i++) {
            int rest = goal - l[i];
            while (re > 0 && Math.abs(rest - r[re - 1]) <= Math.abs(rest - r[re])) {
                re--;
            }
            ans = Math.min(ans, Math.abs(rest - r[re]));
        }
        return ans;
    }

    public static int process1(int[] nums, int index, int end, int sum, int fill, int[] arr) {
        if (index == end) {
            arr[fill++] = sum;
        } else {
            fill = process1(nums, index + 1, end, sum, fill, arr);
            fill = process1(nums, index + 1, end, sum + nums[index], fill, arr);
        }
        return fill;
    }
}
