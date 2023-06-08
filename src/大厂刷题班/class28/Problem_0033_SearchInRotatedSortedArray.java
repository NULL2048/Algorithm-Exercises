package 大厂刷题班.class28;
// 二分
// https://leetcode.cn/problems/search-in-rotated-sorted-array/
public class Problem_0033_SearchInRotatedSortedArray {
    // 1、左神代码
    // arr，原本是有序数组，旋转过，而且左部分长度不知道（这道题规定了只会旋转一次）
    // 找num
    // num所在的位置返回
    public int search(int[] nums, int target) {
        // 设置左右边界指针
        int l = 0;
        int r = nums.length - 1;

        // 开始二分
        while (l <= r) {
            // 计算中间下标
            int m = (l + r) >> 1;

            // 如果中间值正好等于target，直接返回下标m
            if (nums[m] == target) {
                return m;
            }

            // 下面这个if分支是考虑题目如果说数组可能存在相同数字的情况下，需要加下面这个分支，来去除连续的相等数字带来的影响，执行完下面的分支，下标L、M、R上的数一定都是不同的
            // 力扣这道题已经说了数组中不存在相同数字，所以把下面的这个分支去掉也能提交通过
            // 如果三个下标位置的数相等，并且他们又不等于target（[L] == [M] == [R] != target），这种情况是没有办法二分的，因为无法确定断点在左侧还是在右侧
            if (nums[l] == nums[m] && nums[m] == nums[r]) {
                // 这种情况下让L往后走，L++，直到遇到一个不等于num[M]的位置，在当前L...R上继续二分
                while (l != m && nums[l] == nums[m]) {
                    l++;
                }

                // 执行到这里，有两种情况：
                // 1) L == M L...M都一路都相等
                // 2) 从L到M终于找到了一个不等的位置
                if (l == m) { // L...M 一路都相等
                    // L一直往右，到M了，一路都是num[M]，那么在M+1到R上二分
                    l = m + 1;
                    continue;
                }
            }

            // 执行到这里，arr[M]一定是 != num的
            // [L] [M] [R] 不都一样的情况, 如何二分的逻辑

            // [L] != [M]
            if (nums[l] != nums[m]) {
                // 如果[L] < [M]，说明左侧是有序的，那么断点应该在右侧
                if (nums[l] < nums[m]) {
                    // 如果target的范围就在左侧有序的范围内，那么我们就对左侧进行二分
                    if (target >= nums[l] && target < nums[m]) {
                        r = m - 1;
                        // 否则，target就应该在无序的右侧，我们就去右侧进行二分
                    } else {
                        l = m + 1;
                    }
                    // 这个情况就是右侧是有序的，断点在左侧
                } else {
                    // 如果target的范围就在右侧有序的范围内，那么我们就对右侧进行二分
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                        // 否则，target就应该在无序的左侧，我们就去左侧进行二分
                    } else {
                        r = m - 1;
                    }
                }
                // [M] != [R]
            } else {
                // 如果[M] < [R]，说明右侧是有序的，那么断点应该在左侧
                if (nums[m] < nums[r]) {
                    if (target > nums[m] && target <= nums[r]) {
                        l = m + 1;
                    } else {
                        r = m - 1;
                    }
                    // 这个情况就是左侧是有序的，断点在右侧
                } else {
                    if (target >= nums[l] && target < nums[m]) {
                        r = m - 1;
                    } else {
                        l = m + 1;
                    }
                }
            }
        }

        // 如果整个二分过程没有找到target，就说明数组中没有target，返回-1
        return -1;
    }


    // 2、下面是我自己写的代码，可能更好理解，更符合我自己的思路
    class Solution {
        public int search(int[] nums, int target) {
            // 设置左右边界指针
            int l = 0;
            int r = nums.length - 1;

            while (l <= r) {
                int mid = (l + r) >> 1;

                // 如果找到了答案，直接返回下标
                if (nums[mid] == target) {
                    return mid;
                }

                // 下面要判断一下l和r位置的数是不是等于target，这是为了避免l~r只有两个数的情况，这种情况算出来的mid一定等于l,这种情况下就无法进入if (nums[l] < nums[mid]) 分支了
                // 并且如果l和r位置的数正好是旋转对调过的,那么也就没办法在else if (nums[r] > nums[mid])分支中正确找到target了,因为如果r就是我们要找的数,他会去跳过右半段去左半段找(r = mid - 1),就丢掉了情况了,这个很好理解举个2个数的例子即可,例如[3,1]找1.
                if (nums[l] == target) {
                    return l;
                }

                if (nums[r] == target) {
                    return r;
                }

                // 如果左半边是有序的
                if (nums[l] < nums[mid]) {
                    // 如果target恰好在有序的左半边范围,那么我们就对左半边做二分法
                    if (target < nums[mid] && target >= nums[l]) {
                        r = mid - 1;
                        // 如果target不在有序的左半边范围,那么我们只能去右半边去找了
                    } else {
                        l = mid + 1;
                    }
                    // 如果右半边是有序的
                } else if (nums[r] > nums[mid]) {
                    // 如果target恰好在有序的右半边范围,那么我们就对右半边做二分法
                    if (target > nums[mid] && target <= nums[r]) {
                        l = mid + 1;
                        // 如果target不在有序的右半边范围,那么我们只能去左半边去找了
                    } else {
                        r = mid - 1;
                    }
                    // 这个必须要加,如果查找的数压根不在数组中,等查询到最后肯定是l==r,这种情况就相当于l==r==mid,又因为数组中不存在target,那么就不可能进入上面的任何分支,如果不加这个else就会在这个while中死循环下去
                } else {
                    break;
                }

            }

            // 如果整个二分过程没有找到target，就说明数组中没有target，返回-1
            return -1;
        }
    }

}
