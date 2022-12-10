package 大厂刷题班.class25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// 两数之和：https://leetcode.cn/problems/two-sum/
// 本题测试链接 : https://leetcode.cn/problems/3sum/
// 双指针   双指针也要有一定的单调性，这道题我们提前给数组排序，在整个流程当中就会建立起一种单调性了，就可以用双指针来处理了
public class Code02_3Sum {
    // 我写的代码
    public List<List<Integer>> threeSum(int[] nums) {
        // 先排序，构造单调性
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        // 从右往左找尝试找第一个数
        for (int i = n - 1; i >= 0; i--) {
            // 如果这个数和它右边的数不相等（为了剔除相等的情况），我们就可以选择它作为第一个数
            if (i == n - 1 || nums[i] != nums[i + 1]) {
                // 选择的这个数左部分，用双指针指向左右边界，因为数组是有序的，可以利用单调性来找两个数，这两个数的和为-nums[i]，就可以保证选出的三个数加和为0
                int l = 0;
                int r = i - 1;
                // 利用单调性来找符合条件的两个数
                while (l < r) {
                    // l位置为第一个数或者它不能和其左边位置的数相等（提出相等的情况），这个前提下如果nums[l] + nums[r] == -nums[i]，那么这个就是我们要的结果
                    if ((l == 0 || nums[l - 1] != nums[l]) && nums[l] + nums[r] == -nums[i]) {
                        // 将答案假如list
                        List<Integer> threeSum = new ArrayList<>();
                        threeSum.add(nums[l]);
                        threeSum.add(nums[r]);
                        threeSum.add(nums[i]);
                        ans.add(threeSum);
                        // 只右移左指针，不移动右指针，因为此时可能还有别的位置的数和r位置的数能组合出nums[l] + nums[r] == -nums[i]
                        l++;
                        // 如果加和小于-nums[i]，那么就l右移，增加左侧的数
                    } else if (nums[l] + nums[r] < -nums[i]) {
                        l++;
                        // 如果加和大于-nums[i]，那么就r做移，减少右侧的数
                    } else {
                        r--;
                    }
                }
            }
        }

        // 返回答案
        return ans;
    }

    // 左神的代码
    public static List<List<Integer>> threeSum1(int[] nums) {
        Arrays.sort(nums);
        int N = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = N - 1; i > 1; i--) { // 三元组最后一个数，是arr[i]   之前....二元组 + arr[i]
            if (i == N - 1 || nums[i] != nums[i + 1]) {
                List<List<Integer>> nexts = twoSum(nums, i - 1, -nums[i]);
                for (List<Integer> cur : nexts) {
                    cur.add(nums[i]);
                    ans.add(cur);
                }
            }
        }
        return ans;
    }

    // nums[0...end]这个范围上，有多少个不同二元组，相加==target，全返回
    // {-1,5}     K = 4
    // {1, 3}
    public static List<List<Integer>> twoSum(int[] nums, int end, int target) {
        int L = 0;
        int R = end;
        List<List<Integer>> ans = new ArrayList<>();
        while (L < R) {
            if (nums[L] + nums[R] > target) {
                R--;
            } else if (nums[L] + nums[R] < target) {
                L++;
            } else { // nums[L] + nums[R] == target
                if (L == 0 || nums[L - 1] != nums[L]) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(nums[L]);
                    cur.add(nums[R]);
                    ans.add(cur);
                }
                L++;
            }
        }
        return ans;
    }

    public static int findPairs(int[] nums, int k) {
        Arrays.sort(nums);
        int left = 0, right = 1;
        int result = 0;
        while (left < nums.length && right < nums.length) {
            if (left == right || nums[right] - nums[left] < k) {
                right++;
            } else if (nums[right] - nums[left] > k) {
                left++;
            } else {
                left++;
                result++;
                while (left < nums.length && nums[left] == nums[left - 1])
                    left++;
            }
        }
        return result;
    }
}
