package 大厂刷题班.class14;

import java.util.TreeSet;

// 这个题要对比一下子数组那一系列的题，还有就是体系班class46的Code04_MaxSumLengthNoMore这个题
// 子数组   前缀和   有序表
public class Code02_MaxSubArraySumLessOrEqualK {
    // 请返回arr中，求个子数组的累加和，是<=K的，并且是最大的。
    // 返回这个最大的累加和
    public static int getMaxLessOrEqualK(int[] arr, int K) {
        // 记录i之前的，前缀和，按照有序表组织
        TreeSet<Integer> set = new TreeSet<Integer>();
        // 一个数也没有的时候，就已经有一个前缀和是0了
        // 这个一定要加上，很关键，不然会遗漏情况
        set.add(0);
        int max = Integer.MIN_VALUE;
        int sum = 0;
        // 每一步的i，都求子数组必须以i结尾的情况下，求个子数组的累加和，是<=K的，并且是最大的
        for (int i = 0; i < arr.length; i++) {
            // sum -> arr[0..i];   计算以i为结尾的前缀和
            sum += arr[i];
            // 找i之前的前缀和中大于等于K且最接近K的前缀和
            if (set.ceiling(sum - K) != null) {
                // 如果找到的话，就用 当前i位置的前缀和sum - i之前的前缀和中大于等于(sum - K)且最接近(sum - K)的前缀和  就是以i结尾的子数组中累加和小于等于K且最大的累加和大小
                max = Math.max(max, sum - set.ceiling(sum - K));
            }
            // 当前的前缀和加入到set中去，用于后续的计算
            set.add(sum);
        }
        // 返回最大值
        return max;

    }
}
