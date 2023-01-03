package 大厂刷题班.class31;
// 链表
// https://leetcode.cn/problems/linked-list-cycle/
public class Problem_0141_LinkedListCycle {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return false;
        }
        // 设置两个快慢指针，快指针一次走两步，满指针一次走一步
        ListNode slow = head.next; // n1 -> slow
        ListNode fast = head.next.next; // n2 -> fast
        // 快慢指针从头节点同时开始向后遍历，直到两个指针相交为止
        while (slow != fast) {
            // 如果快指针遍历到了null位置了，说明该节点是没有环的。有环的单链表不可能存在null结束位置
            if (fast.next == null || fast.next.next == null) {
                // 直接返回null
                return false;
            }
            // 快指针一次走两步，满指针一次走一步
            fast = fast.next.next;
            slow = slow.next;
        }

        return true;
    }
}
