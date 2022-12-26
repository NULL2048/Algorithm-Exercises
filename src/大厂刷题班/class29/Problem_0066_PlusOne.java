package 大厂刷题班.class29;
// 模拟   数学
public class Problem_0066_PlusOne {
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[] {0};
        }

        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            digits[i] = 0;
        }

        int[] ans = new int[n + 1];
        ans[0] = 1;
        return ans;
    }
}
