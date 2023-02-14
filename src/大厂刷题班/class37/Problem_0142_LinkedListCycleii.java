package 大厂刷题班.class37;
// 链表  双指针  快慢指针   环形链表
// https://leetcode.cn/problems/linked-list-cycle-ii/
public class Problem_0142_LinkedListCycleii {
    // 这个类不用提交
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int value) {
            val = value;
        }
    }

    public ListNode detectCycle(ListNode head) {
        // 过滤空链表、单节点链表和双节点链表，这三种情况一定是无环链表，直接返回null
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        // 设置两个快慢指针，快指针一次走两步，满指针一次走一步
        // 提前将快慢指针的初始位置设置好，这一步很重要，不能将初始位置都设置成head，会导致结果错误
        ListNode slow = head.next; // n1 -> slow
        ListNode fast = head.next.next; // n2 -> fast
        while (fast != slow) {
            // 如果快指针在遍历过程中出现了结尾为空的情况，直接返回null，表示一定没有环
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            // 快指针一次走两步
            fast = fast.next.next;
            // 慢指针一次走一步
            slow = slow.next;
        }
        // 执行到这里说明快慢指针重合了，此时将快指针重新回到头节点
        fast = head;
        // 快慢指针都用相同的步长向后遍历，当再次相遇的时候，相遇节点就是第一个入环节点
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        // 返回入环节点
        return fast;
    }
}
