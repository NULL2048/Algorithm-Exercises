package 链表问题.链表中倒数第k个节点;




class ListNode {
     int val;
     ListNode next;
     ListNode(int x) { val = x; }
}

class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        // 记录倒数第k个节点
        ListNode p = head;
        // 作为一个标记指针
        ListNode t = head;

        // 先建立好双指针之间的距离
        for (int i = 0; t != null && i < k; i++) {
            t = t.next;
        }

        // 双指针通知向后遍历，直到t到达链表尾部
        while (t != null) {
            p = p.next;
            t = t.next;
        }

        return p;
    }
}