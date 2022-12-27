package 体系学习班.class05;

// 荷兰国旗问题
public class Code02_NetherlandsFlag {
    public int[] partition(int[] arr, int l, int r, int num) {
        int less = l - 1;
        int more = r + 1;
        int cur = l;
        while (cur < more) {
            if (arr[cur] < num) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > num) {
                swap(arr, --more, cur);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    // https://leetcode.cn/problems/sort-colors/
    // 力扣的这道题本质就是荷兰国旗问题，用来划分的值就是0、1、2，下面的代码可以直接提交
    public void sortColors(int[] nums) {
        // 值为0的右区间边界
        int l = -1;
        // 值为2的左区间边界
        int r = nums.length;
        // 当前遍历到的数
        int cur = 0;

        while (cur < r) {
            if (nums[cur] < 1) {
                // 这里要注意cur++，因为交换到cur位置的数原本就在l~cur之前，这个范围cur已经滑过了，那么这个区间内的数一定都是等于1的，所以交换到cur位置他就一定是在中间区间的数，后面就直接让cur++去判断后面还没有滑过的数即可
                swap(nums, ++l, cur++);
            } else if (nums[cur] > 1) {
                // 因为交换到cur位置的数一定是原本在cur~r之间的数，这个范围cur还没有滑过，所以不能确定这个数和1的大小关系，所以这里cur不变，还是要在后面的循环中继续判断交换过来的数与1的大小关系
                swap(nums, --r, cur);
            } else {
                cur++;
            }
        }
    }

    // 交换两个数的位置
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
