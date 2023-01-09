package 大厂刷题班.class33;

// 链表
// https://leetcode.cn/problems/reverse-linked-list/
public class Problem_0206_ReverseLinkedList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseList(ListNode head) {
        // 当前遍历到的节点，也就是要进行翻转操作的节点
        ListNode cur = head;
        // 当前节点在原链表中的下一个节点，避免遍历中断
        ListNode next = null;
        // 当前节点在原链表中的上一个节点，反转后cur的next就要指向pre
        ListNode pre = null;

        while (cur != null) {
            // 先记录上当前节点的下一个节点，避免遍历中断
            next = cur.next;
            // 将当前节点的next指向当前节点在原链表中的上一个节点，实现next指向反转
            cur.next = pre;
            // 在遍历到下一个节点时，当前节点就成了上一个节点，更新pre
            pre = cur;
            // 向后遍历
            cur = next;
        }

        return pre;
    }
}
