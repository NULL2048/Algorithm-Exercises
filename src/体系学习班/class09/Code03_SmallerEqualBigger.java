package 体系学习班.class09;

public class Code03_SmallerEqualBigger {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 使用额外空间，做partition操作
    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        Node[] nodeArr = new Node[i];
        i = 0;
        cur = head;
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    public static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].value < pivot) {
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    public static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    // 不使用额外空间
    public static Node listPartition2(Node head, int pivot) {
        // 六个指针最开始都指向null
        Node sH = null; // small head
        Node sT = null; // small tail
        Node eH = null; // equal head
        Node eT = null; // equal tail
        Node mH = null; // big head
        Node mT = null; // big tail
        Node next = null; // save next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                // 如果指针还指向空，则将头尾节点都指向该区发现的第一个节点
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    // 将尾节点next指向新加入的节点
                    sT.next = head;
                    // 并且更新尾指针
                    sT = head;
                }
                // 下面都是相同的操作
            } else if (head.value == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (mH == null) {
                    mH = head;
                    mT = head;
                } else {
                    mT.next = head;
                    mT = head;
                }
            }
            // 依次遍历链表上的节点
            head = next;
        }
        // 小于区域的尾巴连等于区域的头，等于区域的尾巴连大于区域的头
        // 下面就是扣边界了，这个也是链表题目的考察重点

        // 如果有小于区域
        if (sT != null) {
            // 小于区域的尾巴连等于区域的头，这里不管等于区域有没有，就算是没有，也是指向一个null值
            sT.next = eH;
            // 下一步，谁去连大于区域的头，谁就变成eT
            // 如果等于区域不为空，则等于区域尾连大于区域头；如果等于区域为空，则直接让小于区域位连大于区域头
            eT = eT == null ? sT : eT;
        }

        // 下一步，一定是需要用eT去连大于区域的头，此时et可能是等于区域尾，也有可能是小于区域尾
        // 有等于区域，eT 为 等于区域的尾结点
        // 无等于区域，eT 为 小于区域的尾结点

        // 如果即没有等区，也没有小于区，那么et仍然有可能为空
        // 这里判断是否小于区域和等于区域都为空
        if (eT != null) {
            // 如果不为空，则用eT区连大区的头
            eT.next = mH;
        }
        // 如果小于区域不为空，则链表头节点就是小头
        // 如果小于区域为空，等于区域不为空，则链表头节点就是等头
        // 否则，链表头节点就直接用大头
        return sH != null ? sH : (eH != null ? eH : mH);
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }

}
