package 体系学习班.class12;

import java.util.ArrayList;

public class Code03_IsBST {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 使用中序遍历，只要中序遍历的结果是递增的，就说明这个二叉树为搜索二叉树
    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
    }

    // 中序遍历
    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // 判断是否为二叉搜索树
    public static boolean isBST2(Node head) {
        // 空树也是二叉搜索树
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    // 信息类，记录一棵树是不是搜索二叉树，并且记录这棵树的最大值和最小值
    // 因为我们只是左树需要最大值，右树需要最小值，像这种左右树需要不同值的情况，我们干脆就直接取全值，把一棵树的最大值和最小值都取到
    public static class Info {
        public boolean isBST;
        public int max;
        public int min;

        public Info(boolean i, int ma, int mi) {
            isBST = i;
            max = ma;
            min = mi;
        }

    }

    // 递归判断二叉树是否为搜索二叉树
    public static Info process(Node x) {
        // 如果遍历到空节点，就返回空
        // 当我们不知道如何处理返回null时，就一律返回null就行，让上层取处理这个null
        if (x == null) {
            return null;
        }

        // 左右子树递归，返回左右子树的info。 向下递归的位置
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 设置当前树的最大值
        int max = x.value;
        // 用左右树最大值和当前树根节点比较，找到当前树的最大值
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
        }
        // 设置当树的最小值
        int min = x.value;
        // 用左右树最小值和当前树根节点比较，找到当前树的最小值
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
        }
        // 设置当前树是否为搜索二叉树
        boolean isBST = true;
        // 只要左右树为搜索二叉树，并且左树最大值小于当前树根，右树最小值大于当前树根，则当前树为搜索二叉树
        // 这里也都对null做了处理，如果一个数没有左子树或右子树，则就不去做最大最小值的判断了
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= x.value) {
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= x.value) {
            isBST = false;
        }
        // 返回当前树的info
        return new Info(isBST, max, min);
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
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
