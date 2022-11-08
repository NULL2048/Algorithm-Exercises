package 大厂刷题班.class16;
import java.util.Arrays;
// 贪心
// 测试链接：https://leetcode.cn/problems/patching-array/
public class Code03_MinPatches {
    public int minPatches(int[] nums, int n) {
        int N = nums.length;
        // arr保证有序，且都是正数
        Arrays.sort(nums);
        // 这里要用long，因为累加和有可能超过int型范围，造成溢出
        // 凡是累加和的一定要留意累加和类型是否会溢出
        // 表示此时已经可以推出1~range范围的累加和了
        long range = 0;
        // 额外补充的数字个数
        int cnt = 0;
        // 遍历到的数组位置
        int i = 0;
        // 开始遍历数组
        while (i < N) {
            // 如果此时已经能退出来1~n的范围了，直接返回
            if (range >= n) {
                return cnt;
            }
            // 如果nums[i] <= range + 1，说明此时可以直接使用数组中已有的nums[i]来推高累加和范围，能够保证不遗漏累加和
            if (nums[i] <= range + 1) {
                // 推高累加和范围，就是直接加上nums[i]
                range += nums[i];
                // 继续向后遍历
                i++;
                // 如果此时nums[i]还不能直接用，就需要先补充一些数，使nums[i] <= range + 1条件成立后才可以用nums[i]
            } else {
                // 补充range+1这个数，能够最大限度地推高累加和范围，这样能够使额外补充的数最少。
                range += (range + 1);
                // 额外补充的数量加1
                cnt++;
            }

        }

        // 如果数组遍历完了，还是没有达到要求，后面就全部由补充的数来推高范围
        // 让range>=n时，就符合条件了，返回答案
        while (range < n) {
            // 全都补充range+1，最大限度地推高累加和范围，这样能够使额外补充的数最少。
            range += (range + 1);
            // 额外补充的数量加1
            cnt++;
        }
        // 返回答案
        return cnt;
    }
}
