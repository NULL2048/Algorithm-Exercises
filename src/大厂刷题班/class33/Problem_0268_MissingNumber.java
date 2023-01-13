package 大厂刷题班.class33;
// 数组  数学
// https://leetcode.cn/problems/missing-number/
public class Problem_0268_MissingNumber {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        // 先算一下如果1~n全都填满下标0~n-1位置，总的和是多少
        // 等差数列：(首项 + 尾项) * 项数 / 2
        int sum = ((1 + n) * n) >> 1;
        // 遍历一遍数，用加和减掉每一个数，最后剩下的差值，就是缺少的那个数
        for (int i = 0; i < n; i++) {
            sum -= nums[i];
        }
        return sum;
    }
}
