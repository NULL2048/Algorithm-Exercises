package 大厂刷题班.class32;
// 链表
// https://leetcode.cn/problems/intersection-of-two-linked-lists/
public class Problem_0160_IntersectionOfTwoLinkedLists {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 两个链表的长度
        int n1 = 1;
        int n2 = 1;
        ListNode ia = headA;
        ListNode ib = headB;
        // 计算两个链表的长度和得到尾节点
        while (ia.next != null) {
            n1++;
            ia = ia.next;
        }

        while (ib.next != null) {
            n2++;
            ib = ib.next;
        }

        // 如果两个链表尾节点不同，那么这样了链表不可能相交。因为一个节点只有一个next，不可能两个链表相交到一起，然后再去分叉开。
        if (ia != ib) {
            return null;
        }

        // 重新标记链表头
        // ia:长链表的链表头
        // ib:短链表的链表头
        if (n1 > n2) {
            ia = headA;
            ib = headB;
        } else {
            ia = headB;
            ib = headA;
        }

        // 先让长链表走差值的步数
        for (int i = 0; i < Math.abs(n1 - n2); i++) {
            ia = ia.next;
        }
        // 然后两个链表在同时向后遍历，当两个链表遍历的节点相遇时，这个相遇节点就是两条链表的相交节点
        while (ia != ib) {
            ia = ia.next;
            ib = ib.next;
        }

        return ib;
    }
}
