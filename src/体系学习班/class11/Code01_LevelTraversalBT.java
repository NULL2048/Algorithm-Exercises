package 体系学习班.class11;

import java.util.LinkedList;

public class Code01_LevelTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void level(Node head) {
        if (head == null) {
            return;
        }
        // 准备一个队列
        Queue<Node> queue = new LinkedList<>();
        // 先将根节点入队
        queue.add(head);
        // 当队列中的节点全部弹出时，中序遍历完成
        while (!queue.isEmpty()) {
            // 弹出队头节点并打印
            Node cur = queue.poll();
            System.out.println(cur.value);
            // 将弹出节点左孩子入队
            if (cur.left != null) {
                queue.add(cur.left);
            }
            // 将弹出节点右孩子入队
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        level(head);
        System.out.println("========");
    }
}
