package 体系学习班.class13;

import jdk.nashorn.internal.ir.IfNode;

import java.util.LinkedList;

public class Code01_IsCBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isCBT1(Node head) {
        if (head == null) {
            return true;
        }
        LinkedList<Node> queue = new LinkedList<>();
        // 是否遇到过左右两个孩子不双全的节点
        boolean leaf = false;
        Node l = null;
        Node r = null;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            l = head.left;
            r = head.right;
            if (
                // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
                    (leaf && (l != null || r != null)) || (l == null && r != null)

            ) {
                return false;
            }
            if (l != null) {
                queue.add(l);
            }
            if (r != null) {
                queue.add(r);
            }
            if (l == null || r == null) {
                leaf = true;
            }
        }
        return true;
    }

    public static boolean isCBT2(Node head) {
        // 空树是完全二叉树
        if (head == null) {
            return true;
        }

        // 通过二叉树递归套路求解
        return process(head).isCBT;
    }

    // 信息类
    public static class Info {
        // 当前树是不是满二叉树
        public boolean isFBT;
        // 当前树是不是完全二叉树
        public boolean isCBT;
        // 当前树的高度
        public int height;

        public Info(boolean isFBT, boolean isCBT, int height) {
            this.isFBT = isFBT;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    // 递归
    public static Info process(Node x) {
        // 如果是空树，我们认为它是满二叉树     递归出口
        if (x == null) {
            // 返回info
            return new Info(true, true, 0);
        }

        // 递归获得左右子树的info
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 得到左右子树的info之后，利用左右子树的info来计算当前树的信息

        // 通过左右子树计算当前树的告诉
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;


        boolean isFBT = false;
        boolean isCBT = false;

        // 根据分类的情况，判断当前是是否为满二叉树，是否为完全二叉树
        if (leftInfo.isFBT && rightInfo.isFBT && leftInfo.height == rightInfo.height) {
            isFBT = true;
            isCBT = true;
        } else if (leftInfo.isFBT && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            isFBT = false;
            isCBT = true;
        } else if (leftInfo.isCBT && rightInfo.isFBT && leftInfo.height == rightInfo.height + 1) {
            isFBT = false;
            isCBT = true;
        }

        // 返回当前树info
        return new Info(isFBT, isCBT, height);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
