package 大厂刷题班.class34;
// 链表
// 纯考coding能力，没有算法的东西
// https://leetcode.cn/problems/odd-even-linked-list/
public class Problem_0328_OddEvenLinkedList {
    // 提交时不要提交这个类
    public static class ListNode {
        int val;
        ListNode next;
    }

    public ListNode oddEvenList(ListNode head) {
        // 过滤空链表和只有一个节点的链表
        if (head == null || head.next == null) {
            return head;
        }
        // 奇数编号的链表头尾节点
        ListNode oddHead = head;
        ListNode oddTail = head;
        // 偶数编号的链表头尾节点
        ListNode evenHead = head.next;
        ListNode evenTail = head.next;
        // 当前遍历到的节点
        ListNode cur = head.next.next;
        // 将奇偶链表都和外界断开
        oddTail.next = null;
        evenTail.next = null;
        // 标识当前遍历到的节点编号是多少
        int cnt = 3;
        while (cur != null) {
            // 奇数编号
            if ((cnt & 1) == 1) {
                // 将当前节点挂到奇数链表尾部
                oddTail.next = cur;
                cur = cur.next;
                oddTail = oddTail.next;
                oddTail.next = null;
                // 偶数编号
            } else {
                // 将当前节点挂到奇偶数链表尾部
                evenTail.next = cur;
                cur = cur.next;
                evenTail = evenTail.next;
                evenTail.next = null;
            }
            // 编号+1
            cnt++;
        }

        // 将奇数和偶数链表连在一起，奇数链表在前，偶数链表在后
        oddTail.next = evenHead;
        return oddHead;
    }
}
