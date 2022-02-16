package 新手班.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

// 该解法复杂度为O(N)
public class Code01_MergeKSortedLists {
    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static class ListNodeComparator implements Comparator<ListNode> {

        // 构建升序比较器
        @Override
        public int compare(ListNode a, ListNode b) {
            if (a.val < b.val) {
                return -1;
            } else if (a.val > b.val) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     原理就是将所有链表的头节点先压入优先队列，通过优先级队列对压入的数据进行排序

     然后再弹出堆顶节点，这个元素一定是当前所有节点中最小的，因为所有的链表也是升序链表。然后将弹出的这个节点的next节点压入优先级队列中。
     再去执行相同的操作，弹出堆顶，将弹出堆顶的next压入优先级队列。这样就能保证每次都能依次的到剩余节点中最小的节点。
     因为每一次将堆顶节点的next节点压入，就是为了保证在优先级队列中的节点，一定都是所有链表中剩余节点的最小节点，
     这样就能保证每次在堆中弹出的节点一定是剩余节点中最小的节点（在所有链表剩余节点的最小的节点的集合中找到一个最小的节点，肯定就是所有剩余节点的最小节点）
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 判断空情况
        if (lists == null) {
            return null;
        }

        // 将每一个链表的头节点先加入堆中
        PriorityQueue<ListNode> pq  = new PriorityQueue<ListNode>(new ListNodeComparator());
        for (int i = 0; i < lists.length; i++) {
            // 一定要确保这个节点不是null再加入，不然后面操作堆的时候会出现空指针异常。一定要养成这种细化，所有压入集合的元素，在后续可能对其进行操作的时候，都要进行一次判空操作
            if (lists[i] != null) {
                pq.add(lists[i]);
            }
        }

        // [[]、[]] 和 [[]]、[]这种情况，lists并不是Null，但是数组中却什么也没有，如果不判断这种情况在后面就会操作一个空的堆，就会出现空指针错误。
        // 养成好习惯，所有要操作的集合，在操作之前都要判断是下是不是空，避免空指针错误
        if (pq.isEmpty()) {
            return null;
        }


        ListNode head = pq.poll();
        // 不要忘了这一步，取出来一个，还要将取出来的节点的next节点加回去。因为第一次是从外部单独进行的，所以也要单独再加回去
        if (head.next != null) {
            pq.add(head.next);
        }
        ListNode tail = head;
        ListNode temp = null;
        // 依次将堆顶弹出，如果将弹出堆顶的next不是空，就将将弹出堆顶的next压入。将所有弹出的节点一次组合成一个链表
        // 当堆中所有元素都弹出之后，最后形成的链表就是最终结果。
        while (!pq.isEmpty()) {
            temp = pq.poll();
            tail.next = temp;
            tail = temp;

            if (tail.next != null) {
                pq.add(tail.next);
            }
        }

        return head;
    }
}
