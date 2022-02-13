package 新手班.class04;

// 测试链接：https://leetcode.com/problems/add-two-numbers/

/*
    两个链表相加问题

    给定两个链表的头节点head1和head2，
    认为从左到右是某个数字从低位到高位，返回相加之后的链表
    例子     4 -> 3 -> 6        2 -> 5 -> 3
    返回     6 -> 8 -> 9
    解释     634 + 352 = 986
 */
public class Code05_AddTwoNumbers {
    // 不要提交这个类
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
         这种类型的题需要先找到长链表和锻炼表
         然后可以划分出来三种情况
         1、长链表和短链表都没有遍历完
         2、短链表已经遍历完了，但是长链表还没有遍历完
         3、长链表和短链表都遍历完了
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 计算两个链表的长度
        int l1Len = getLinkedListLength(l1);
        int l2Len = getLinkedListLength(l2);

        // 长链表
        ListNode longList = null;
        // 短链表
        ListNode shortList = null;
        // 长链表中最后一个非空节点
        ListNode lastNotNullNode = null;
        // 记录进位值
        int carry = 0;

        // 比较出长锻炼表，并且记录长链表的头节点，因为我们会将最后的结果全部写入到长链表中返回，所以要返回的头节点就是长链表的头节点
        ListNode ansList = longList = l1Len > l2Len ? l1 : l2;
        shortList = longList == l1 ? l2 : l1;


        // 遍历链表进行加和

        // 长链表和短链表都美誉哦遍历完
        while (shortList != null) {
            // 加和
            int num = longList.val + shortList.val + carry;
            // 更新进位值
            carry = num / 10;
            // 将加和之后当前位的数字写入长链表对应的节点中
            longList.val = num % 10;

            // 更新长链表中最后一个不为空的节点
            lastNotNullNode = longList;

            // 指针后移
            longList = longList.next;
            shortList = shortList.next;
        }

        // 短链表遍历完了，开始遍历长链表
        while(longList != null) {
            int num = longList.val + carry;
            carry = num / 10;
            longList.val = num % 10;

            lastNotNullNode = longList;

            longList = longList.next;
        }

        // 如果长链表遍历完了，仍还有进位值，就需要创建一个新结点存放进位值，并且将长链表的最后一个节点的next指向这个节点
        if (carry != 0) {
            lastNotNullNode.next = new ListNode(carry);
        }

        // 返回长链表头节点
        return ansList;
    }


    /**
     * 计算链表长度
     * @param head 链表头节点
     * @return 链表长度
     */
    public int getLinkedListLength(ListNode head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

}
