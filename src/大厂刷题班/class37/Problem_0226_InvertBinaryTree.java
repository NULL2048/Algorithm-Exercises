package 大厂刷题班.class37;
// 二叉树  二叉树遍历  递归
// https://leetcode.cn/problems/invert-binary-tree/
public class Problem_0226_InvertBinaryTree {
    // 不用提交这个类
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    // 1、我自己写的代码
    public TreeNode invertTree(TreeNode root) {
        // 空树
        if (root == null) {
            return root;
        }

        // 后序遍历
        process(root);
        return root;
    }

    // 递归实现二叉树后序遍历
    // 必须要后序遍历，因为要递归到最后一层的时候，从底往上两两交换才不会破坏二叉树结构，不能从上往下交换，这样整个结构就乱了
    public void process(TreeNode node) {
        if (node.left != null) {
            process(node.left);
        }

        if (node.right != null) {
            process(node.right);
        }

        // 交换当前节点的左右子节点
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    // 2、左神的代码
    public static TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        root.left = invertTree1(root.right);
        root.right = invertTree1(left);
        return root;
    }
}
