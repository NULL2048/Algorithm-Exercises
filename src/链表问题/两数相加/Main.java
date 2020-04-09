package 链表问题.两数相加;

public class Main {
    public static void main(String[] args) {

    }


}

class ListNode {
      int val;
      ListNode next;
      ListNode(){}

     ListNode(int x) { val = x; }
}

class Solution {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode();
        ListNode tail;
        head.next = null;
        tail = head;

        // 记录后面位数的进位  模拟计算过程
        int t = 0;

        while (l1 != null || l2 != null || t != 0) {
            int a = 0;
            int b = 0;
            if (l1 != null) {
                a = l1.val;
                l1 = l1.next;
            }

            if (l2 != null) {
                b = l2.val;
                l2 = l2.next;
            }

            t = a + b + t;
            ListNode node = new ListNode(t % 10);
            node.next = null;
            tail.next = node;
            tail = node;

            t /= 10;
        }

        return head.next;
    }
}
