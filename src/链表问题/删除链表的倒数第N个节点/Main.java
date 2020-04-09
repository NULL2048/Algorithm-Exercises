package 链表问题.删除链表的倒数第N个节点;

class ListNode {
    int val;
    ListNode next;
    ListNode(){}

    ListNode(int x) { val = x; }
}

public class Main {
    public static void main(String[] args) {

    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return null;
        }

        // 一定要回这种思路，自己加一个指向头节点的指针
        // 添加一个指向头节点的指针
        ListNode pre = new ListNode();
        pre.next = head;
        // 用来获取倒数第N个节点的前驱节点
        ListNode first = pre;
        ListNode second = pre;

        // 要多移动一位，因为first是要删除节点的前驱节点
        for (int i = 0; second != null && i <= n; i++) {
            second = second.next;
        }

        while (second != null) {
            first = first.next;
            second = second.next;
        }

        // 删除节点
        first.next = first.next.next;
        return pre.next;
    }
}
