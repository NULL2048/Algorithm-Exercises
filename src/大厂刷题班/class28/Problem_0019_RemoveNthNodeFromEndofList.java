package 大厂刷题班.class28;
// 双指针   链表
// https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
public class Problem_0019_RemoveNthNodeFromEndofList {
    // 链表节点类
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode cur = head;
        ListNode pre = head;

        // 先移动pre指针，向后移动n位，这里每一步让n--，用来标记pre是否真的向后移动了n次，如果还没有移动n次就到了链表结尾了，就说明链表的节点数并不大于n个
        while (n > 0 && pre.next != null) {
            pre = pre.next;
            n--;
        }

        // 同步移动cur和pre，当pre移动到了最后一个节点时，cur指向的就是要删除节点的上一个节点
        while (pre.next != null) {
            cur = cur.next;
            pre = pre.next;
        }

        // 如果pre在一开始向后跳了n次，就说明链表的节点数是大于n的
        if (n == 0) {
            // 此时cur就是要删除节点的上一个节点，下面执行经典的删除节点操作
            ListNode temp = cur.next;
            cur.next = cur.next.next;
            temp.next = null;
            // 如果n==1，说明链表节点数等于n，head节点就是我们要删除的节点
        } else if (n == 1) {
            // 移除头节点，设立下一个节点为新的头节点
            ListNode temp = head;
            head = head.next;
            temp.next = null;
        }// 其余情况就说明链表节点数是小于n，这种情况下是没有节点要删除的


        return head;
    }
}
