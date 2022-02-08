package 新手班.class04;

import java.util.ArrayList;
import java.util.List;

public class Code01_ReverseList {
    // 定义单链表内部类
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    // 定义双链表内部类
    public static class DoubleNode {
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }
    }

    /**
     * 反转单链表
     * @return
     */
    public static Node reverseLinkedList(Node head) {
        // 当前要反转节点的下一个节点
        Node next = null;
        // 当前要反转节点反转后应该指向的节点
        Node pre = null;
        // head最开始表示链表的头节点，但是在进入到循环之后就表示当前要进行翻转的节点
        while (head != null) {
            // 设置当前要反转节点的下一个节点，即记录head.next的指向
            next = head.next;
            // 将当前节点进行翻转，它应该指向pre节点
            head.next = pre;
            // 更新下一个要反转的节点反转后要指向的节点，即向后移动一位。这三者的位置关系时 pre-head-next（前中后）
            pre = head;
            // 更新当前节点，即向后移动一位
            head = next;
        }
        // 返回新的头节点
        return pre;
    }

    /**
     * 反转双链表
     * @param head
     * @return
     */
    public static DoubleNode reverseDoubleList(DoubleNode head) {
        // 当前要反转节点的下一个节点
        DoubleNode next = null;
        // 当前要反转节点的前一个结点（注意，链表每个节点的相对位置在反转前后都是不变的，只是将各个节点的指向改变了，但是节点的相对位置是没变的）
        DoubleNode pre = null;
        // head最开始表示链表的头节点，但是在进入到循环之后就表示当前要进行翻转的节点
        while (head != null) {
            // 记录当前节点的下一个节点
            next = head.last;
            // 双链表反转就是将next和last指向的节点进行互换
            // 将当前节点的next指向pre，实现了next指向的反转
            head.next = pre;
            // 将当前节点的last指向next，实现了last指向的反转
            head.last = next;
            // 更新pre节点，即向后移动一位
            pre = head;
            // 更新当前节点，即向后移动一位
            head = next;
        }
        // 返回头节点
        return pre;
    }

    /**
     * 测试单链表反转的方法，保证返回的一定是正确的反转链表的结果，用来和我们的编写的方法返回的结果进行对照
     * @param head
     * @return
     */
    public static Node testReverseLinkedList(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int N = list.size();
        for (int i = 1; i < N; i++) {
            list.get(i).next = list.get(i - 1);
        }
        return list.get(N - 1);
    }

    /**
     * 测试双链表反转的方法，保证返回的一定是正确的反转双链表的结果，用来和我们的编写的方法返回的结果进行对照
     * @param head
     * @return
     */
    public static DoubleNode testReverseDoubleList(DoubleNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<DoubleNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        DoubleNode pre = list.get(0);
        int N = list.size();
        for (int i = 1; i < N; i++) {
            DoubleNode cur = list.get(i);
            cur.last = null;
            cur.next = pre;
            pre.last = cur;
            pre = cur;
        }
        return list.get(N - 1);
    }

    /**
     * 随机生成单链表
     * @param len
     * @param value
     * @return
     */
    public static Node generateRandomLinkedList(int len, int value) {
        // 生成随机的链表节点个数
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        // 生成头节点
        Node head = new Node((int) (Math.random() * (value + 1)));
        Node pre = head;
        // 循环随机生成节点，生成单链表
        while (size != 0) {
            Node cur = new Node((int) (Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    /**
     * 随机生成双链表
     * @param len
     * @param value
     * @return
     */
    public static DoubleNode generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        DoubleNode head = new DoubleNode((int) (Math.random() * (value + 1)));
        DoubleNode pre = head;
        while (size != 0) {
            DoubleNode cur = new DoubleNode((int) (Math.random() * (value + 1)));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            size--;
        }
        return head;
    }

    /**
     * 记录单链表最初的顺序
     * @param head
     * @return
     */
    public static List<Integer> getLinkedListOriginOrder(Node head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    /**
     * 对数器，比对单链表反转结果是否正确
     * @param origin
     * @param head
     * @return
     */
    public static boolean checkLinkedListReverse(List<Integer> origin, Node head) {
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 记录双链表最初的顺序
     * @param head
     * @return
     */
    public static List<Integer> getDoubleListOriginOrder(DoubleNode head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    /**
     * 对数器，比对双链表反转结果是否正确
     * @param origin
     * @param head
     * @return
     */
    public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode head) {
        DoubleNode end = null;
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            end = head;
            head = head.next;
        }
        for (int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.value)) {
                return false;
            }
            end = end.last;
        }
        return true;
    }


    public static void f(Node head) {
        head = head.next;
    }

    // for test
    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        System.out.println("test begin!");
        for (int i = 0; i < testTime; i++) {
            Node node1 = generateRandomLinkedList(len, value);
            List<Integer> list1 = getLinkedListOriginOrder(node1);
            node1 = reverseLinkedList(node1);
            if (!checkLinkedListReverse(list1, node1)) {
                System.out.println("Oops1!");
            }

            Node node2 = generateRandomLinkedList(len, value);
            List<Integer> list2 = getLinkedListOriginOrder(node2);
            node2 = testReverseLinkedList(node2);
            if (!checkLinkedListReverse(list2, node2)) {
                System.out.println("Oops2!");
            }

            DoubleNode node3 = generateRandomDoubleList(len, value);
            List<Integer> list3 = getDoubleListOriginOrder(node3);
            node3 = reverseDoubleList(node3);
            if (!checkDoubleListReverse(list3, node3)) {
                System.out.println("Oops3!");
            }

            DoubleNode node4 = generateRandomDoubleList(len, value);
            List<Integer> list4 = getDoubleListOriginOrder(node4);
            node4 = reverseDoubleList(node4);
            if (!checkDoubleListReverse(list4, node4)) {
                System.out.println("Oops4!");
            }

        }
        System.out.println("test finish!");

    }

}
