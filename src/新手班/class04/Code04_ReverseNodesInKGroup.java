package 新手班.class04;

// 测试链接：https://leetcode.com/problems/reverse-nodes-in-k-group/

/*
    K个节点的组内逆序调整问题

    给定一个单链表的头节点head，和一个正数k
    实现k个节点的小组内部逆序，如果最后一组不够k个就不调整
    例子:
    调整前：1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8，k = 3
    调整后：3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8
 */
public class Code04_ReverseNodesInKGroup {

    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    /*
       这个题的接替流程如下：
       1、先根据K的值，从链表中划分出来K个节点的子链表段，用start和end来标记子链表的头尾节点
       2、将子链表进行链表反转
       3、子链表头尾节点互换后，重新更新start和end的指向，将反转后的子链表与还未反转的链表进行连接（这一步比较关键）
       4、根据相同的做法，再从余下的链表中再次划分出来K个节点的子链表段，进行相同的操作
    */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 先设置第一段子链表的起始位置
        ListNode start = head;
        // 找到子链表的末尾位置
        ListNode end = getKGroupEnd(start, k);

        // 判断end是不是空，如果是空的话，说明划分出来的子链表不够k的元素，这样的话根据题意，就不需要进行反转了，直接返回链表头节点
        if (end == null) {
            return head;
        }

        // 这里记录一下反转后整个链表新的头节点，也就是第一段被划分的子链表的末尾位置的节点，end在反转之后就会变成新的头节点
        ListNode ansHead = end;

        // 进行链表反转
        reverse(start, end);

        // 上一段链表的尾节点，这是为了进行后续的链表连接操作。上面进行了反转操作之后，start就成了子链表的尾节点
        ListNode lastEnd = start;

        // 如果上一段链表的尾节点的next指向null，说明已经完成了所有链表的反转了，结束循环
        while (lastEnd.next != null) {
            // 更新新的子链表起始位置，也就是上一段反转后链表尾节点的下一个位置
            start = lastEnd.next;
            // 跟新新的子链表的末尾位置
            end = getKGroupEnd(start, k);
            // 如果end为null，说明最后一段划分不足k个节点，就不需要在进行翻转了，直接返回
            if (end == null) {
                return ansHead;
            }

            // 反转子链表
            reverse(start, end);
            /*
                这里也很关键，我们在上一段划分出来的子链表进行reverse()操作时，在方法中已经更新了start.next = end
                将上一段划分出来的子链表与后面还没进行划分反转的链表进行了重新连接
                但是现在我们对后面划分出来的子链表进行了反转，就导致这个子链表的头尾节点调换了一下
                上一段子链表的尾节点指向的节点，已经向从头节点变成了尾节点
                所以在子链表进行反转之后，还需要重新更新上一段子链表与刚刚完成反转操作的子链表之间的连接关系
             */
            lastEnd.next = end; // 反转之后，end才是当前子链表的头节点，而以前的头节点start已经变成了尾节点，之前lastEnd.next指向的是start，所以需要将上一段子节点的尾节点重新指向end，
            // 更新上一段子节点的尾节点，为下一轮循环做准备
            lastEnd = start;
        }

        // 返回整个链表的头节点
        return ansHead;
    }

    /**
     * 找到划分出来的子链表的末尾位置
     * @param start 子链表的起始位置
     * @param k 子链表要划分出来的个数
     * @return 返回子链表末尾位置end，这里的end指向的就是子链表的最后一个节点
     */
    public ListNode getKGroupEnd(ListNode start, int k) {
        ListNode end = start;
        // 向后遍历链表，直到划分出了k个元素，或者遍历到链表尾部
        for (int i = 1; end != null && i < k; i++) {
            end = end.next;
        }

        return end;
    }

    /**
     * 反转链表
     * @param start 链表送头节点
     * @param end 链表尾节点
     */
    public void reverse(ListNode start, ListNode end) {
        // 这里把end向后移动一格，方便我们来判断是不是已经反转结束
        end = end.next;

        // 当前要反转的节点
        ListNode cur = start;
        // 当前要反转节点的上一个节点，也就是反转之后cur.next要指向的节点
        ListNode pre = null;
        // 当前要反转节点的下一个节点
        ListNode next = null;

        // 当前节点指向了链表尾节点的下一个节点后，说明所有的节点都已经反转完成了。这样也是为了保证cur不是null，否则可能会导致后面next = cur.next报空指针错误
        while (cur != end) {
            // 设置当前节点的下一个节点
            next = cur.next;
            // 更新cur.next的指向，进行反转
            cur.next = pre;
            // 将pre和cur都向后移动一位
            pre = cur;
            cur = next;
        }

        /*
            这一步就是将当前已经反转后的链表与后面还没有进行划分反转的链表进行重新连接
            此时end表示的是后面还没有划分反转的链表的头节点
            此时start是已经完成反转的子链表的尾节点
            将尾节点的next指向后面还没进行划分反转的链表的头节点，即可完成两段链表的重新连接
         */
        start.next = end;
    }

}
