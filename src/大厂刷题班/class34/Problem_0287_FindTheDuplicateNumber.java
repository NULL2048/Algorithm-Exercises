package 大厂刷题班.class34;
// 链表章节：第十题：单链表的相交节点问题（面试场上链表相关的题难度不会超过这道题）有环单链表的思路就能应用于这道题
// 链表  有环单链表的入环节点   快慢指针
// https://leetcode.cn/problems/find-the-duplicate-number/
public class Problem_0287_FindTheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        // 不能读将他们初始化为0，快慢指针确实都是从0位置开始移动的，但是如果我们初始化为0，就无法进入到第一个循环了，因为第一个循环条件是slow != fast
        // int slow = 0;
        // int fast = 0;
        int slow = nums[0];
        int fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }

        fast = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        // 此时slow就是数组中的数值，即重复的那个数
        return slow;
    }
}
