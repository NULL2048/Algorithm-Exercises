package 大厂刷题班.class29;
// 模拟
// https://leetcode.cn/problems/jump-game/
public class Problem_0055_JumpGame {
    public boolean canJump(int[] nums) {
        // 数组长度小于2，一定能走到最后
        if (nums == null || nums.length < 2) {
            return true;
        }

        // 记录当前能走到的最右下标位置，起始点从下标0开始，所以将maxRightIndex初始化为nums[0]
        int maxRightIndex = nums[0];
        // 开始向后遍历数组，同时更新maxRightIndex
        for (int i = 1; i < nums.length; i++) {
            // 当maxRightIndex大于等于数组最后一个下标位置了，就说明一定可以走到最后的位置，直接返回true
            if (maxRightIndex >= nums.length - 1) {
                return true;
            }

            // 当前遍历到了i位置，如果此时能达到的最右侧位置maxRightIndex小于i，那就说明永远都不可能走到i位置，也就更不可能走到最后的位置了，直接返回false
            if (maxRightIndex < i) {
                return false;
            }
            // 更新maxRightIndex，执行到这里就说明一定可以走到i位置
            // 用i+nums[i]和原maxRightIndex比较，选择更大的作为新的maxRightIndex
            maxRightIndex = Math.max(maxRightIndex, i + nums[i]);
        }

        // 能遍历完整个数组说明可以走到最后，直接返回true
        return true;
    }
}
