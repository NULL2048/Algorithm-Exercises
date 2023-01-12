package 大厂刷题班.class33;
// 链表
// https://leetcode.cn/problems/delete-node-in-a-linked-list/
public class Problem_0237_DeleteNodeInLinkedList {
    public static class ListNode {
        int val;
        ListNode next;
    }
    // 左神的方法，我觉得非常的巧妙，可以直接学他的方法，甚至都不用写循环
    public void deleteNode1(ListNode node) {
        // 将node.next的val赋值给node，这样就相当于抹掉了node的val，完成了删除
        node.val = node.next.val;
        // 再将原本的node.next这个节点删除掉，就相当于完成了删除操作
        node.next = node.next.next;
    }

    // 我自己的方法
    public void deleteNode2(ListNode node) {
        ListNode nextNode = node.next;
        // 将node节点后面的节点的val依次向前移动一个位置，也就相当于将node的val抹掉了
        while (nextNode.next != null) {
            node.val = nextNode.val;
            node = nextNode;
            nextNode = nextNode.next;
        }
        // 最后不要忘了还有最后一个节点还没有完成val的向前移动
        node.val = nextNode.val;
        // 最后将倒数第二节点的next指向null，就完成了删除操作
        node.next = null;
    }
}
