package 大厂刷题班.class32;
// 数组   数组逆序
// https://leetcode.cn/problems/rotate-array/
public class Problem_0189_RotateArray {
    // 最优解，我自己写的代码
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        // 要注意k可能大于n，我们将k和n取模，这样就能跳过轮转一圈的情况
        k = k % n;
        // 前部分逆序
        reverse(nums, 0, n - k - 1);
        // 后部分逆序
        reverse(nums, n - k, n - 1);
        // 整体逆序
        reverse(nums, 0, n - 1);
    }

    // 将数组nums在l~r范围上逆序
    public void reverse(int[] nums, int l, int r) {
        int temp = 0;
        while (l < r) {
            temp = nums[l];
            nums[l] = nums[r];
            nums[r] = temp;
            l++;
            r--;
        }
    }

    // 也是最优解，左神的代码，但是比较复杂，不建议看这个了
    public static void rotate2(int[] nums, int k) {
        int N = nums.length;
        k = k % N;
        if (k == 0) {
            return;
        }
        int L = 0;
        int R = N - 1;
        int lpart = N - k;
        int rpart = k;
        int same = Math.min(lpart, rpart);
        int diff = lpart - rpart;
        exchange(nums, L, R, same);
        while (diff != 0) {
            if (diff > 0) {
                L += same;
                lpart = diff;
            } else {
                R -= same;
                rpart = -diff;
            }
            same = Math.min(lpart, rpart);
            diff = lpart - rpart;
            exchange(nums, L, R, same);
        }
    }

    public static void exchange(int[] nums, int start, int end, int size) {
        int i = end - size + 1;
        int tmp = 0;
        while (size-- != 0) {
            tmp = nums[start];
            nums[start] = nums[i];
            nums[i] = tmp;
            start++;
            i++;
        }
    }
}
