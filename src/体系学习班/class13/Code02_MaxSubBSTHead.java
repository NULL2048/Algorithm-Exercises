package 体系学习班.class13;

import java.util.ArrayList;

public class Code02_MaxSubBSTHead {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftAns = maxSubBSTHead1(head.left);
        Node rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }


    public static Node maxSubBSTHead2(Node head) {
        // 空树直接返回空
        if (head == null) {
            return null;
        }
        return process(head).maxBSTHead;
    }

    // 每一棵子树
    public static class Info {
        // 当前树的大小
        public int treeSize;
        // 当前树最大搜索子树大小
        public int maxBSTSize;
        // 当前树的最大值
        public int max;
        // 当前树的最小值
        public int min;
        // 当前树的最大搜索子树头节点
        public Node maxBSTHead;

        public Info(int treeSize, int maxBSTSize, int max, int min, Node head) {
            this.treeSize = treeSize;
            this.maxBSTSize = maxBSTSize;
            this.max = max;
            this.min = min;
            this.maxBSTHead = head;
        }
    }

    public static Info process(Node x) {
        // 空值我们不好去设置max和min，所以这里返回null，让上层去做处理
        if (x == null) {
            return null;
        }

        // 递归得到左右两个子树的info
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 初始化当前树的info信息
        int max = x.value;
        int min = x.value;
        int treeSize = 1;
        int maxBSTSize = 0;
        Node maxBSTHead = null;

        // 下面就是三种可能：1、最大搜索子树在左子树上   2、最大搜索子树在右子树上  3、最大搜索子树就是本身
        // 在这一层去处理null
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
            if (maxBSTSize < leftInfo.maxBSTSize) {
                maxBSTSize = leftInfo.maxBSTSize;
                maxBSTHead = leftInfo.maxBSTHead;
            }

            treeSize += leftInfo.treeSize;
        }

        // 在这一层去处理null
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            if (maxBSTSize < rightInfo.maxBSTSize) {
                maxBSTSize = rightInfo.maxBSTSize;
                maxBSTHead = rightInfo.maxBSTHead;
            }

            treeSize += rightInfo.treeSize;
        }

        // 判断当前树是否为搜索二叉树，如果是，则最大搜索子树就是本身。注意这里也要处理null
        if ((leftInfo == null || leftInfo.maxBSTSize == leftInfo.treeSize) && (rightInfo == null || rightInfo.maxBSTSize == rightInfo.treeSize)) {
            if ((leftInfo == null || x.value > leftInfo.max) && (rightInfo == null || x.value < rightInfo.min)) {
                maxBSTSize = treeSize;
                maxBSTHead = x;
            }
        }

        // 返回当前树的info
        return new Info(treeSize, maxBSTSize, max, min, maxBSTHead);
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
