package 大厂刷题班.class33;
// 数组  数学
// https://leetcode.cn/problems/missing-number/
public class Problem_0268_MissingNumber {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        // 先算一下如果1~n全都填满下标0~n-1位置，总的和是多少（题目说的0~n是有n+1个数的，但是题目说了只是从里面选出n个数来，所以必然会少一个。
        // 我们去求0~n这个n+1个数的累加和，因为第一个数是0，所以其实就是求1~n这n个数的累加和）。所以这样计算出来的数就是如果0~n这n+1个数一个都不缺的话，应该的累加和是多少
        // 等差数列：(首项 + 尾项) * 项数 / 2
        int sum = ((1 + n) * n) >> 1;
        // 遍历一遍数，用前面算出来的加和减掉每一个数，最后剩下的差值，就是缺少的那个数。因为题目说了只缺少了一个数
        for (int i = 0; i < n; i++) {
            sum -= nums[i];
        }
        return sum;
    }
}
