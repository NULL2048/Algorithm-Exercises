package 链表问题.合并两个排序的链表;

class ListNode {
    int val;
    ListNode next;
    ListNode(){}
    ListNode(int x) { val = x; }
}

public class Main {
    public static void main(String[] args) {

    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 合并之后得新链表
        // list标识新链表的尾部
        ListNode list = new ListNode();
        // head记录链表头部。用来在最后返回
        ListNode head = list;


        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                // 这里要注意需要单独新创建一个节点
                ListNode node = new ListNode();
                // 尾插法添加
                list.next = node;
                node.val = l1.val;
                node.next = null;

                list = node;
                l1 = l1.next;
            } else if (l1.val > l2.val) {
                ListNode node = new ListNode();
                list.next = node;
                node.val = l2.val;
                node.next = null;
                list = node;
                l2 = l2.next;
            } else {
                ListNode node = new ListNode();
                list.next = node;
                node.val = l1.val;
                node.next = null;
                list = node;
                l1 = l1.next;

                node = new ListNode();
                list.next = node;
                node.val = l2.val;
                node.next = null;
                list = node;
                l2 = l2.next;
            }
        }

        while (l1 != null) {
            ListNode node = new ListNode();
            list.next = node;
            node.val = l1.val;
            node.next = null;
            list = node;
            l1 = l1.next;
        }

        while (l2 != null) {
            ListNode node = new ListNode();
            list.next = node;
            node.val = l2.val;
            node.next = null;
            list = node;
            l2 = l2.next;
        }

        return head.next;
    }
}
