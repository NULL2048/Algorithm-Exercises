package 大厂刷题班.class32;
// 二分法  数组
// https://leetcode.cn/problems/find-peak-element/
public class Problem_0162_FindPeakElement {
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        // 数组长度为1，直接返回0下标
        if (n == 1) {
            return 0;
        }

        // 先判断两个特殊情况，也就是数组两头的数是不是局部最小，如果是的话直接返回。反正题目只要求返回一个符合局部最小的数即可
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[n - 1] > nums[n - 2]) {
            return n - 1;
        }

        // 执行到这里，左右两端的数一定都小于相邻的数

        // 设置数组的左右范围
        int l = 0;
        int r = n - 1;
        // L...R 肯定有局部最大。
        // 最左端的数比相邻的数小，最右端的数比相邻的数小，因为相邻的数没有相等的，他们在坐标系上的线是一个向下凹的弧度，所在在中间必然有一个值既大于左边相邻的数，也大于右边相邻的数。
        // 开始通过二分法进行查找
        while (l < r - 1) { // 这里用的是L < R - 1，之所以用R - 1是因为怕越界，因为要想判断mid,mid-1,mid+1就要保证这三个位置都在L~R的范围里面，所以这里就要让L < R - 1。
            // 设置中间下标
            int mid = (l + r) >> 1;
            // 如果找到了符合局部最大的数，直接返回其下标
            if (nums[mid] > nums[mid - 1] && nums[mid] > nums[mid + 1]) {
                return mid;
            } else {
                // 如果中间位置的数小于其左侧相邻的数，说明mid-1的数至少是大于mid的，所以保留mid-1，去左半段继续查找，更新R的值即可
                // 我们要找凸线的最高点，此时mid-1位置比mid的更高，自然要去左侧找凸线的最高点
                if (nums[mid] < nums[mid - 1]) {
                    r = mid - 1;
                    // 反之，说明mid+1的数是大于等于mid的，所以保留mid+1，去右半段继续查找，更新L的值即可
                } else {
                    l = mid + 1;
                }
            }
        }

        // 如果跳出循环还没有找到局部最大，说明当前L~R范围的数只有2个或者1个，这样我们就直接判断这两个数谁比较大就返回谁。因为L位置一定比左侧的数大，R位置一定比右侧的大，只要是比较出L和R谁大，就能知道谁是局部最大了
        // 如何L = R，说明范围内只有一个数，直接将其返回即可
        return nums[l] > nums[r] ? l : r;
    }
}
