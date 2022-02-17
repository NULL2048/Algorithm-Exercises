package 新手班.class06;

// 测试链接：https://leetcode.com/problems/maximum-depth-of-binary-tree
public class Code04_MaximumDepthOfBinaryTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    // 以root为头的树，最大高度是多少返回！
    public static int maxDepth(TreeNode root) {
        // 递归出口，到了最后一层的时候，它的深度一定是0
        if (root == null) {
            return 0;
        }

        // 向上返回的时候，每一层的深度，都是它下一层深度+1，取左右子树深度最大的那一个
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

}