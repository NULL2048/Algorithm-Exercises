package 大厂刷题班.class33;
// 数组   前缀和    位运算   前缀乘积   后缀成绩
// https://leetcode.cn/problems/product-of-array-except-self/
public class Problem_0238_ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        // pos[i]表示i位置右侧的（不包括i位置）的后缀乘积
        int[] pos = new int[n];
        // 记录当前的后缀成绩
        int posProduct = 1;
        // 生成后缀乘积数组
        for (int i = n - 1; i >= 0; i--) {
            pos[i] = posProduct;
            posProduct *= nums[i];
        }

        // 记录最后的答案，存储每个位置除自身以外的乘积
        int[] ans = new int[n];
        // 0下标的结果一定是0下标右侧的后缀乘积
        ans[0] = pos[0];
        // 记录当前遍历到位置的左侧的前缀成绩（不包括当前位置）
        int preProduct = nums[0];
        // 从左往右开始遍历求每个位置的除自身以外的乘积
        for (int i = 1; i < n; i++) {
            // i位置的除自身以外的乘积就等于i左侧的前缀乘积 * i右侧的后缀成绩（因为这两个乘积都包含了除了i位置外其他所有数的乘积了）
            ans[i] = preProduct * pos[i];
            // 维持preProduct前缀乘积
            preProduct *= nums[i];
        }
        // 返回答案
        return ans;
    }
}
