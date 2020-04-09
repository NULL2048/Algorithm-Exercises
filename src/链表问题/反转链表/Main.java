package 链表问题.反转链表;



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
    public ListNode reverseList(ListNode head) {
        // 反转链表的头指针
        ListNode pre = null;
        // 记录还未进行翻转的链表的头节点
        ListNode next = null;

        // head在循环中就表示当前处理的结点
        while (head != null) {
            // 记录下一次循环要处理的节点
            next = head.next;

            // 将当前的next指向pro，相当是一个头插法，完成链表反转
            head.next = pre;

            // pre记录已经反转过来的链表的头指针
            pre = head;

            // 将head当前要处理的节点指向next下一次循环要处理的节点，为下次循环做准备
            head = next;

        }
        return pre;
    }
}
