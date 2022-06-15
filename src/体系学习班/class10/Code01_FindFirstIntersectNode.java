package 体系学习班.class10;

public class Code01_FindFirstIntersectNode {
    // 节点内部类
    public static class Node {
        public int value;
        public Node next;
        public Node(int data) {
            this.value = data;
        }
    }

    // 得到两个链表的第一次相交节点，如果没有相交则返回null
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        // 先去获得两个链表各自的入环第一个节点
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        /*
         * 获取了两个节链表的入环节点后，就可以分成三种情况：
         * 1、两个链表都有入环节点，即都是有环链表
         * 2、一个链表有入环节点，一个链表没有入环节点，即一个是有环链表，一个是无环链表
         * 3、两个链表都没有入环节点，即两个链表都没有环
         *
         * 先说情况二，这种情况下两个链表不可能有交点。因为有环单链表不可能有指向null的尾节点，但是无环的单链表一定有指向null的尾节点
         * 如果两个链表有相交的话，那么相交位置以后，两个链表实际上就合并成一个了，但是两个链表一个是有指向null的尾节点的，一个是没有指向null的尾节点的，这就矛盾了，所以这种情况不可能存在尾节点
         *
         * 情况一和情况三是有可能有交点的。
         *
         * 如果情况三有交点，就很好理解，就是两个最普通的无环单链表在某个位置相交，合并成了一条链表一直到null结尾
         *
         * 如果情况一有交点，我们可以在分出两种情况：
         * 1、两个链表的交点在入环节点之前，也就是在进入环形之前两个链表就相交合并成一条链表了
         * 2、两个链表的交点在入环节点之后，也就是两个链表的相交节点是在环上，此时我们认为这个相交节点既可以是链表1的入环节点，也可以是链表2的入环节点，按那个算都行，反正他们是在环上相交的
         *
         */
        // 如果两个节点都是无环节点，就是情况三
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        // 如果两个都是有环节点，就是情况一
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 如果执行到这里，说明是一个有环，一个无环，不可能存在交点，直接返回null
        return null;
    }

    /*
     * 找到链表第一个入环节点，如果无环，返回null
     *
     * 设置两个快慢指针，快指针一次走两步，满指针一次走一步
     * 然后将快慢指针从链表的head位置同时向后遍历，直到快指针和慢指针相交（肯定可以相交，相交时快指针已经比满指针夺走了一圈了）
     * 此时讲快指针再次回到链表头节点位置，慢指针保持当前位置不变。两个指针再次同时向后遍历，并且两个指针都一次只走一步
     * 当快慢指针再次相交的时候，相交的位置就是该链表第一个入环节点
     *
     * 这个就当作是一个结论，不用深究是为什么
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        // 设置两个快慢指针，快指针一次走两步，满指针一次走一步
        Node slow = head.next; // n1 -> slow
        Node fast = head.next.next; // n2 -> fast
        // 快慢指针从头节点同时开始向后遍历，直到两个指针相交为止
        while (slow != fast) {
            // 如果快指针遍历到了null位置了，说明该节点是没有环的。有环的单链表不可能存在null结束位置
            if (fast.next == null || fast.next.next == null) {
                // 直接返回null
                return null;
            }
            // 快指针一次走两步，满指针一次走一步
            fast = fast.next.next;
            slow = slow.next;
        }
        // 当跳出循环之后说明快慢指针相遇了，这也就是说明该链表一定有环，否则快指针是不可能转一圈又追上慢指针的（环形跑道和直线跑道的区别）
        // 快指针再次回到head链表头位置，慢指针保持在相交位置
        fast = head; // n2 -> walk again from head
        // 快慢指针再次同时向后遍历，当两个指针再次相交的时候，相交位置就是入环的第一个节点
        while (slow != fast) {
            // 注意这一次快慢指针都一次只走一步
            slow = slow.next;
            fast = fast.next;
        }
        // 结束循环后，两指针的相交位置即为入环的第一个节点
        return slow;
    }

    /*
     * 情况三：如果两个链表都无环，返回第一个相交节点，如果不相交，返回null。
     *
     * 如果两个无环单链表相交的话，那么他们一定会在相交节点位置合并为同一个链表，也就是说两个链表的尾节点一定是同一个。
     *
     * 这个比较简单，就是先遍历链表1，一直到结尾节点，并且得到链表1的长度
     * 再遍历一遍链表2，一直到结尾节点，得到链表2的长度，进而就能得到两个链表长度的差值
     *
     * 判断一下两个链表的尾节点是不是同一个，如果不是同一个，说明两个链表不相交，直接返回null
     * 如果有相同的结尾节点，则说明两个链表相交。再去找两链表相交节点的位置。
     *
     * 让长度更长的链表，先从头节点向后遍历长度差值个节点。
     * 然后长度更长的链表从当前节点开始，长度更短的链表从头节点开始，同时向后开始遍历，
     * 当遍历到相同节点时，该节点就是两链表的交点节点。
     *
     * 这个原理就是两个链表如果有交点的话，交点以后的肯定是同一个链表，长度也肯定是相同的，这一部分我们就不去做考虑。只考虑相交之前的部分，
     * 也就是说两个链表长度的差值就是插在相交之前部分的链表中，然后我们让长的链表先遍历差值个节点，
     * 这样长链表此时的节点离相交节点的距离和短链表头节点离相交节点的距离就一样了，这样就能保证他们同时遍历就能同时到达交点。
     * 这个很好理解，比如两个不等长的链表，如果让讲长链表减去长度差值个节点，这样两个链表长度也就一致了。这里其实就是相同的道理。
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        // 记录两个链表的长度差值
        int n = 0;
        // 遍历链表1，直到cur1指向尾节点，并且n记录上链表1的长度
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        // 遍历链表2，直到cur2指向尾节点，并且用n链表1的长度自减，这样到遍历结束n就是链表1和链表2的长度差值
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        // 如果两个无环单链表的尾节点是同一个的话（这里是引用比较，不是值比较），说明两个单链表在前面某个位置合并成了同一个链表，说明两个链表一定有交点
        // 如果两个无环单链表的尾节点不是同一个节点，就说明两个单链表是独立的，没有合并成同一个链表，也就没有交点
        if (cur1 != cur2) {
            // 无交点，返回null
            return null;
        }
        // n  :  链表1长度减去链表2长度的值
        // 如果n大于0，说明链表1长于链表2。这里将cur1指向长链表，cur2指向短链表，方便后续的操作
        cur1 = n > 0 ? head1 : head2; // 谁长，谁的头变成cur1
        cur2 = cur1 == head1 ? head2 : head1; // 谁短，谁的头变成cur2
        // 得到长度差值的绝对值
        n = Math.abs(n);
        // 先让长链表向后走差值个位置，这样就能让cur1到交点节点的距离和cur2到交点节点的距离相同的
        // 因为交点之后合并为一个链表可以直接忽略，之前交点之前部分，交点之前部分的长度差值就是n，然后让长链表先走n步，也就相当于先截去n长度的链表，这样长链表剩下的部分和短链表的长度也就一样的。
        // 这样两个链表再同时向后遍历，就能同时到达相交节点
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        // 两个链表同时继续分别从cur1和cur2开始向后遍历，就能同时到达相交节点，当遍历到的同一个节点时，就说明该节点是链表的相交节点。
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        // 返回相交节点
        return cur1;
    }

    /**
     情况一：两个有环链表，返回第一个相交节点，如果不相交返回null
     这种两个都有环的链表，如果有交点的话，可以分成两种情况：
     1、两个链表的交点在入环节点之前，也就是在进入环形之前两个链表就相交合并成一条链表了
     2、两个链表的交点在入环节点之后，也就是两个链表的相交节点是在环上，此时我们认为这个相交节点既可以是链表1的入环节点，也可以是链表2的入环节点，按那个算都行，反正他们是在环上相交的

     这里我们就根据这两种情况分类讨论。
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        // 如果两个链表的入环节点是同一个，说明两个链表在入环之前就已经相交合并成一个链表了。也就是说相交节点在入环节点之前。
        // 这种情况我们就可以忽略掉后面的环了，因为相交之后反正也都合并成了同一条链表，忽略掉合并之后的部分即可。这样我们就可以看成普通的两个无环单链表相交了，可直接用情况三的方法求解
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            // 记录两个链表的长度插值
            int n = 0;
            // 向后遍历链表1，注意，这里只遍历到入环节点即可，环以后的不去考虑了，反正环都是合并成同一条链表了，可忽略。
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            // 向后遍历链表2，也是遍历到入环节点即可，同时计算两个链表遍历部分的长度差值
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            // 将cur1指向长链表，将cur2指向短链表
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            // 计算长度差值
            n = Math.abs(n);
            // 先让长链表向后走差值个节点
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            // 两个链表同时向后走，当遍历到同一个节点的时候，说明两个链表相交，该节点就是相交节点
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            // 返回相交节点
            return cur1;
            // 如果入环节点不是同一个，说明这两个链表就算是相交，相交节点也肯定在入环节点之后，交点在环上
        } else {
            // 从链表1的入环节点开始遍历环，如果碰到了链表2的入环节点，说明两个链表相交，则返回loop1或loop2均可
            cur1 = loop1.next;
            // 注意，只要进入到了环，那么就出不去了，遍历的话就会在环里转圈，因为一个节点只能由一个next，所以如果形成了环的话就不可能再有节点的next指向null
            // 当cur1再次等于loop1时，说明已经遍历完一圈了，则跳出循环
            while (cur1 != loop1) {
                // 当环中发现了链表2的入环节点是，说明两个链表在环内相交
                if (cur1 == loop2) {
                    // 返回loop1或loop2均可
                    return loop1;
                }
                cur1 = cur1.next;
            }
            // 如果已经遍历了一圈了，还是没有碰到loop2，说明两个链表没有交点，返回null
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);
        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4
        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);
        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);
    }
}
