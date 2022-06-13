package 体系学习班.class09;

import java.util.HashMap;

// 测试链接：https://leetcode.cn/problems/copy-list-with-random-pointer/submissions/
public class Code04_CopyListWithRandom {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        // 创建额外容器，map
        // key 老节点
        // value 新节点
        HashMap<Node, Node> nodeMap = new HashMap<>();
        // 先遍历一边链表，将新老节点存入map中，建立快速索引
        Node cur = head;
        while (cur != null) {
            nodeMap.put(cur, new Node(cur.val));
            cur = cur.next;
        }
        // 在遍历一边链表，利用map快速索引将新节点的next和random指向补全
        cur = head;
        while (cur != null) {
            nodeMap.get(cur).next = nodeMap.get(cur.next);
            nodeMap.get(cur).random = nodeMap.get(cur.random);
            cur = cur.next;
        }
        // 返回新头节点
        return nodeMap.get(head);
    }

    public static Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node node = null;
        // 第一次遍历链表：为每一个节点都复制创建一个新节点，然后将新节点挂到老节点的后面
        // 这样就实现了在新老节点之间建立快速索引关系，就可以通过老节点快速找到与之对应的新节点
        while (cur != null) {
            // 创建新节点
            node = new Node(cur.val);
            // 将新节点插入到老节点和老节点的next节点之间
            node.next = cur.next;
            cur.next = node;
            // 向后遍历节点
            cur = node.next;
        }
        cur = head;
        node = null;
        // 第二次遍历链表：这里是利用已经建立好的新老节点索引，为每一个新节点赋值对应的random
        while (cur != null) {
            // 获取当前老节点对应的新节点
            node = cur.next;
            // 为新节点的random赋值，这里要判断老节点random是否为空，不为空则通过每一个老节点的next节点都是其对应的新节点这个索引规则，快速找到新节点对应的新random节点；如果random为空，则直接赋值null
            node.random = cur.random != null ?  cur.random.next : null;
            // 这里注意，每次遍历一对节点
            cur = node.next;
        }
        cur = head;
        node = null;
        // 保存好新链表头节点，用来返回
        Node h = head.next;
        // 第三次遍历链表：将老节点链表和新节点链表分离
        while (cur != null) {
            // 获取当前老节点对应的新节点
            node = cur.next;
            // 将当前老节点的next指向其对应新节点的next节点
            // 需要判断node是否为空，如果node为空，说明cur为空，则直接赋值null；如果node不为空则用node.next赋值。
            cur.next = node != null ?node.next : null;
            // 将当前老节点对应新节点的next指向当前老节点的next的next
            // 需要判断当前老节点的next是否为空，如果为空，直接返回null
            node.next = cur.next != null ? cur.next.next : null;
            // 向后遍历
            cur = cur.next;
        }
        // 返回新链表头节点
        return h;

    }
}
