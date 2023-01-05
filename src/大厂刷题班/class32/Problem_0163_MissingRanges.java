package 大厂刷题班.class32;
import java.util.ArrayList;
import java.util.List;
// 模拟  数组
// https://leetcode.cn/problems/missing-ranges/【付费】
public class Problem_0163_MissingRanges {
    public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> ans = new ArrayList<>();
        for (int num : nums) {
            // 此时的数大于上一轮找到的缺失的边界起点，那么[lower, num - 1]范围就是缺失的区间，将其加入ans
            if (num > lower) {
                ans.add(miss(lower, num - 1));
            }
            // 遍历到的数达到了范围右边界了，说明后面再也不会有缺失的区间了，返回ans
            if (num == upper) {
                return ans;
            }
            // 此时存在的数为num，不存在的区间是num+1，将lower设置为num+1，作为下一轮缺失边界的起点
            lower = num + 1;
        }
        // 将剩余的缺失区间加入到ans
        if (lower <= upper) {
            ans.add(miss(lower, upper));
        }
        return ans;
    }

    // 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
    public static String miss(int lower, int upper) {
        String left = String.valueOf(lower);
        String right = "";
        if (upper > lower) {
            right = "->" + String.valueOf(upper);
        }
        return left + right;
    }
}
