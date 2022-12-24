package 大厂刷题班.class28;

import java.util.ArrayList;
import java.util.List;

public class Problem_0046_Permute {
    public List<List<Integer>> permute(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return null;
        }

        // 存储答案
        List<List<Integer>> ans = new ArrayList<>();
        process(ans, 0, nums);
        return ans;
    }

    public void process(List<List<Integer>> ans, int index, int[] nums) {
        // 已经将所有位置都尝试完了
        if (index == nums.length) {
            // 需要单独新创建一个List<Integer>，避免所有的可能答案公用同一个内存空间
            List<Integer> path = new ArrayList<Integer>();
            // 将此事nums中的一种排列方案加入到path中
            for (int i = 0; i < nums.length; i++) {
                path.add(nums[i]);
            }
            // 将path加入到ans
            ans.add(path);
        } else {
            // 开始交换不同的位置，尝试出不同的排列组合
            // 从index开始排列，前面位置的就不用交换了，因为在之前一定都交换过了
            // 之所以i从index开始，就是要模拟index和index位置交换，实现出不用交换的一种情况
            for (int i = index; i < nums.length; i++) {
                // 尝试将每一个位置的数与index位置交换
                swap(i, index, nums);
                // 继续向下递归，尝试下一个位置的所有组合情况
                process(ans, index + 1, nums);
                // 恢复现场
                swap(i, index, nums);
            }
        }
    }

    // 交换
    public void swap(int a, int b, int[] nums) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
