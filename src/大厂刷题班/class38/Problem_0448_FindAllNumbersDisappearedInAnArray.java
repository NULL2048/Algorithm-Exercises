package 大厂刷题班.class38;
import java.util.ArrayList;
import java.util.List;
// 数组  下标循环怼的最优解，时间复杂度O(1)，额外空间复杂度O(1)   这道题的相似题是【缺失的第一个正数】这道题
// https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array/
public class Problem_0448_FindAllNumbersDisappearedInAnArray {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        // 要返回的答案
        List<Integer> ans = new ArrayList<>();
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return ans;
        }

        for (int i = 0; i < nums.length; i++) {
            // 从i位置出发，去玩下标循环怼
            walk(nums, i);
        }

        // 完成下标循环怼后，所有不满足要求的位置，就是缺少这个位置上应该放置的数字
        // 因为完成下标循环怼后，就将所有的数都放到他应该放的位置了，剩下的就都是缺少的数字了
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != i + 1) {
                ans.add(i + 1);
            }
        }
        return ans;
    }

    // 代码中的下标循环怼是一个比较优化的写法，代码更加好写，并且不会把数据刷掉
    // 主要是通过交换来实现的，具体可以看课程这道题的后半部分，这一部分的板书没有写在process上，以后有时间了记一下，这次我因为时间比较紧，再加上我觉得这个流程挺好理解的，所以就没有记下来
    public void walk(int[] nums, int i) {
        // 整个流程其实是固定在i下标来进行的
        // 将每一个不符合nums[i] == i + 1的数都交换到i位置来进行操作
        // 所以这个循环怼是数字移动，但是下标不动
        while (nums[i] != i + 1) {
            // 此时i下标位置的数一定是不满足nums[i] == i + 1的
            // 计算nums[i]这个数本来应该在什么下标位置   nums[i] == nexti + 1
            int nexti = nums[i] - 1;
            // 如果nexti位置的数已经符合要求了，那么本轮下标循环怼就结束了
            if (nums[nexti] == nexti + 1) {
                break;
            }
            // nexti位置的数字也不符合要求，那么我们就把i位置的数字交换到nexti位置上，这样原本i位置上的数字就合法了
            // 将原本nexti位置上不合法的数字转移到i位置，再继续对i位置上新来的数字进行下标循环怼
            swap(nums, i, nexti);
        }
    }

    // 交换
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
