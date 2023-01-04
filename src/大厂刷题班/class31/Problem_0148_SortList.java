package 大厂刷题班.class31;
// 归并排序  需要用迭代的写法，因为递归写法会使用递归栈空间，额外空间复杂度就是不是O(1)了    链表
// https://leetcode.cn/problems/sort-list/
public class Problem_0148_SortList {
    public static class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    // 链表的归并排序
    // 时间复杂度O(N*logN), 因为是链表所以空间复杂度O(1)
    public ListNode sortList(ListNode head) {
        // 记录链表有多长
        int n = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            n++;
        }

        // 要返回的排序完的链表头部
        ListNode h = head;
        // 按照步长分组，每次将两个分组两两进行合并操作，leftTeamFirst为当前正在操作的两个组的左边的那一组的头节点
        ListNode leftTeamFirst = h;
        // 每次都是操作两组，将两组合并完之后，就再去操作后面的两组。这个pre记录的就是上一次操作两组合并完之后的链表的尾节点
        ListNode pre = null;
        // 从步长为1开始，每次步长乘2，步长要小于n。通过迭代模拟递归归并过程。
        for (int step = 1; step < n; step <<= 1) {
            // 开始从整个链表的头节点开始，进行以step为步长的合并操作。
            // 整个过程就是将列表以长度step分成一组，然后从左往右再两两一组，从左往右将两两一组的按从小到大进行合并
            // 在合并过程中保持整个链表还是连通的，合并后的前一组链表要和下一组合并后的链表连接好，为了方便后续再以step*2的步长进行合并操作
            // 开始两两一组进行合并，将两个组按照从小到大合并成一个
            while (leftTeamFirst != null) {
                // 从leftTeamFirst开始，向后推step*2个长度，划分出两个组，每一个组的长度就是step。hthtn数组存储两个组的头节点和尾节点，以及两个组后面的下一组的头节点
                ListNode[] hthtn = hthtn(leftTeamFirst, step);
                // 将划分出来的两个组进行合并，合并为一组，组内从小到达排序，返回合并后的链表头尾节点
                ListNode[] mhmt = merge(hthtn[0], hthtn[1], hthtn[2], hthtn[3]);

                // 如果此时h为leftTeamFirst，说明这是第一次合并后的操作，需要设置合并后的头节点
                if (h == leftTeamFirst) {
                    // 设置合并后链表的头节点
                    h = mhmt[0];
                    // 设置合并后链表的尾节点，在下一次合并时，下一次合并后链表的头节点应该挂在pre的后面
                    pre = mhmt[1];
                    // 非首次的合并操作
                } else {
                    // 将合并后的链表挂在pre后边
                    pre.next = mhmt[0];
                    // 更新pre，下一次合并后链表的头节点就可以挂在pre的后面
                    pre = mhmt[1];
                }
                // 合并完当前两组之后，将leftTeamFirst设置为hthtn[4]，即设置为下一组要合并的两个链表的左组头节点，后续进行相同的操作
                leftTeamFirst = hthtn[4];
            }

            // 将leftTeamFirst设置为h，从整个链表的头节点开始，继续以step*2的步长进行合并操作，模拟归并排序
            leftTeamFirst = h;
            // pre设置为空
            pre = null;
        }

        // 返回头节点
        return h;
    }

    // 从leftTeamFirst开始，向后推step*2个长度，划分出两个组，每一个组的长度就是step
    // 返回划分出来的两个组的头尾节点，以及后面的下一组的头节点
    public ListNode[] hthtn(ListNode leftTeamFirst, int step) {
        // 记录划分出来的链表长度
        int cnt = 0;
        // 左组的头尾节点，初始化赋值为leftTeamFirst
        // 左组的头一定是leftTeamFirst
        ListNode lh = leftTeamFirst;
        ListNode lt = leftTeamFirst;
        // 右组的头尾节点，这里先赋值为null，有可能此时节点数量已经不够分给第二组了
        ListNode rh = null;
        ListNode rt = null;
        // 两个组的下一组的头节点，这个是用于在对整个链表进行分组合并的过程中，保证合并后的链表前后还是连通的，不能中断。
        ListNode next = null;

        // 从leftTeamFirst开始向后进行分组
        while (leftTeamFirst != null) {
            cnt++;
            // 当cnt小于等于step时，说明左组还没有划分出来，要继续划分左组，更新lt
            if (cnt <= step) {
                lt = leftTeamFirst;
            }

            // 当nt == step + 1，说明左组刚刚划分完成，需要开始划分右组了，这里初始化右组的头节点
            if (cnt == step + 1) {
                rh = leftTeamFirst;
            }

            // 如果cnt大于step了，说明此时整改划分右组，持续更新rt
            if (cnt > step) {
                rt = leftTeamFirst;
            }

            // 当cnt等于step*2，说明左右组划分完了，左右组的长度都是step，直接跳出循环
            if (cnt == (step << 1)) {
                break;
            }
            // 向后遍历链表
            leftTeamFirst = leftTeamFirst.next;
        }

        // 将划分出来的两组断开，为了方便后续的合并操作
        // 左组最后一个节点next设置为null
        lt.next = null;
        // 如果右组的rt不为空，则将rt.next设置为空，和其他链表断开
        if (rt != null) {
            // 要先记录一下rt.next，记录上两个组的下一组的同节点，用于后续将合并后的链表进行连接操作
            next = rt.next;
            rt.next = null;
        }

        // 返回结果
        return new ListNode[] {lh, lt, rh, rt, next};
    }

    // 将划分出来的两个组进行合并，合并为一组，组内从小到达排序，返回合并后的链表头尾节点
    // 此时lh~lt这个组内是有序的，rh~rt这个组内是有序的
    public ListNode[] merge(ListNode lh, ListNode lt, ListNode rh, ListNode rt) {
        // 如果rh为空，说明没有右组，直接返回左组。一般就是最后两组的划分，剩余的节点已经不够分给右组了
        if (rh == null) {
            return new ListNode[] {lh, lt};
        }

        // 先初始化选择好合并后的头节点，两个链表哪个头小就设置为哪个
        ListNode head = lh.val < rh.val ? lh : rh;
        // 当前合并到的节点
        ListNode cur = head;
        // 用来遍历左组链表的指针
        ListNode li = lh;
        // 用来遍历右组链表的指针
        ListNode ri = rh;
        // 根据head设置的情况，来对li和ri进行向后移动，只有已经选择的节点所在的链表在需要向后移动一个位置
        if (head == lh) {
            li = li.next;
        } else {
            ri = ri.next;
        }

        // 合并两个链表，按照从小到大排列
        while (li != null && ri != null) {
            // 将较小的节点挂在cur节点的后面
            if (li.val < ri.val) {
                cur.next = li;
                cur = li;
                li = li.next;
            } else {
                cur.next = ri;
                cur = ri;
                ri = ri.next;
            }
        }

        while (li != null) {
            cur.next = li;
            cur = li;
            li = li.next;
        }

        while (ri != null) {
            cur.next = ri;
            cur = ri;
            ri = ri.next;
        }
        // 执行到这里，cur指向的就是合并后链表的尾节点
        return new ListNode[] {head, cur};
    }


    // 链表的快速排序
    // 时间复杂度O(N*logN), 空间复杂度O(logN)
    public static ListNode sortList2(ListNode head) {
        int n = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            n++;
        }
        return process(head, n).head;
    }

    public static class HeadAndTail {
        public ListNode head;
        public ListNode tail;

        public HeadAndTail(ListNode h, ListNode t) {
            head = h;
            tail = t;
        }
    }

    public static HeadAndTail process(ListNode head, int n) {
        if (n == 0) {
            return new HeadAndTail(head, head);
        }
        int index = (int) (Math.random() * n);
        ListNode cur = head;
        while (index-- != 0) {
            cur = cur.next;
        }
        Record r = partition(head, cur);
        HeadAndTail lht = process(r.lhead, r.lsize);
        HeadAndTail rht = process(r.rhead, r.rsize);
        if (lht.tail != null) {
            lht.tail.next = r.mhead;
        }
        r.mtail.next = rht.head;
        return new HeadAndTail(lht.head != null ? lht.head : r.mhead, rht.tail != null ? rht.tail : r.mtail);
    }

    public static class Record {
        public ListNode lhead;
        public int lsize;
        public ListNode rhead;
        public int rsize;
        public ListNode mhead;
        public ListNode mtail;

        public Record(ListNode lh, int ls, ListNode rh, int rs, ListNode mh, ListNode mt) {
            lhead = lh;
            lsize = ls;
            rhead = rh;
            rsize = rs;
            mhead = mh;
            mtail = mt;
        }
    }

    public static Record partition(ListNode head, ListNode mid) {
        ListNode lh = null;
        ListNode lt = null;
        int ls = 0;
        ListNode mh = null;
        ListNode mt = null;
        ListNode rh = null;
        ListNode rt = null;
        int rs = 0;
        ListNode tmp = null;
        while (head != null) {
            tmp = head.next;
            head.next = null;
            if (head.val < mid.val) {
                if (lh == null) {
                    lh = head;
                    lt = head;
                } else {
                    lt.next = head;
                    lt = head;
                }
                ls++;
            } else if (head.val > mid.val) {
                if (rh == null) {
                    rh = head;
                    rt = head;
                } else {
                    rt.next = head;
                    rt = head;
                }
                rs++;
            } else {
                if (mh == null) {
                    mh = head;
                    mt = head;
                } else {
                    mt.next = head;
                    mt = head;
                }
            }
            head = tmp;
        }
        return new Record(lh, ls, rh, rs, mh, mt);
    }

}
