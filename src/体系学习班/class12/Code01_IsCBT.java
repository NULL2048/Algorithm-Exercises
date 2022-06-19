package 体系学习班.class12;

import jdk.internal.dynalink.linker.LinkerServices;

import java.util.LinkedList;
import java.util.Queue;

public class Code01_IsCBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 根据完全二叉树的定义，我们就可以通过层序遍历来判断是不是完全二叉树
     *
     * 有两个判断原则：
     * 1、如果层序遍历过程中发现了有节点存在右子节点但是不存在左子节点，那么这棵树肯定不是完全二叉树
     * 2、如果层序遍历到了不是左右子节点双全的节点（无子节点或只有一个左子节点），
     *    那么后面层序遍历到的节点必须都得是叶子节点（无子节点的节点），否则该树也不是完全二叉树
     */
    public static boolean isCBT1(Node head) {
        // 空二叉树也是完全二叉树
        if (head == null) {
            return true;
        }

        // 后面就是经典的层序遍历过程
        Queue<Node> queue = new LinkedList();
        queue.add(head);
        // 标记是不是遍历到了左右子节点非双全的节点
        boolean flag = false;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            // 1、如果节点无左子节点但是有右子节点，该树一定不是完全二叉树
            if (cur.left == null && cur.right != null) {
                return false;
            }

            // 如果还没有遍历到过左右子节点非双全节点，则正常的进行层序遍历，将子节点加入到队列中
            if (!flag) {
                if (cur.left != null) {
                    queue.add(cur.left);
                }

                if (cur.right != null) {
                    queue.add(cur.right);
                }
            // 如果已经遇到左右子节点非双全节点了，那么后续遍历到的节点必须都是无子节点的叶子节点，否则该树就不是完全二叉树
            } else {
                // 如果不是叶子节点，则返回false
                if (cur.left != null || cur.right != null) {
                    return false;
                }
            }

            // 在循环的最后去判断该节点是不是左右子节点非双全节点
            if (cur.left == null || cur.right == null) {
                flag = true;
            }
        }
        return true;
    }


    public static boolean isCBT2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isCBT;
    }

    // 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

    public static Info process(Node X) {
        if (X == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(X.left);
        Info rightInfo = process(X.right);



        int height = Math.max(leftInfo.height, rightInfo.height) + 1;


        boolean isFull = leftInfo.isFull
                &&
                rightInfo.isFull
                && leftInfo.height == rightInfo.height;


        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else { // 以x为头整棵树，不满
            if (leftInfo.isCBT && rightInfo.isCBT) {


                if (leftInfo.isCBT
                        && rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        &&
                        rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull
                        && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }


            }
        }
        return new Info(isFull, isCBT, height);
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
