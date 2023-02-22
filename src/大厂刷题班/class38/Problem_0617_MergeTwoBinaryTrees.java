package 大厂刷题班.class38;
// 二叉树递归套路
// https://leetcode.cn/problems/merge-two-binary-trees/
public class Problem_0617_MergeTwoBinaryTrees {
    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    // 1、我自己写的代码
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        TreeNode newNode = null;
        // 标记当前左右子节点的情况
        // 0表示左右都不为空
        // 1表示左不为空，右为空
        // 2表示左为空，右不为空
        // 3表示左右都为空
        int flag = 0;
        if (root1 != null && root2 != null) {
            // 新节点是左右节点的加和
            newNode = new TreeNode(root1.val + root2.val);
        } else if (root1 != null) {
            // 新节点就只有左节点
            newNode = new TreeNode(root1.val);
            flag = 1;
        } else if (root2 != null) {
            // 新节点就只有右节点
            newNode = new TreeNode(root2.val);
            flag = 2;
        } else {
            // 新节点到了叶子节点了，所以是空
            newNode = null;
            flag = 3;
        }

        // 递归去合并左右子树
        if (flag == 0) {
            newNode.left = mergeTrees(root1.left, root2.left);
            newNode.right = mergeTrees(root1.right, root2.right);
        } else if (flag == 1) {
            newNode.left = mergeTrees(root1.left, null);
            newNode.right = mergeTrees(root1.right, null);
        } else if (flag == 2) {
            newNode.left = mergeTrees(null, root2.left);
            newNode.right = mergeTrees(null, root2.right);
        }
        // 返回新节点
        return newNode;
    }

    // 2、左神的代码，最优解
    // 当前，一棵树的头是t1，另一颗树的头是t2
    // 请返回，整体merge之后的头
    public static TreeNode mergeTrees1(TreeNode t1, TreeNode t2) {
        // 左神的代码是直接如果只剩下一个节点后，直接复用原树的节点，没有像我一样所有的情况都新创建节点，所以左神的代码要快一点点，没有创建对象的消耗
        // 如果t1为空了，那么合并后的新节点要么是t2这个节点，如果t2也是空，那么新的节点就是空
        // 所以这个情况直接返回tw
        if (t1 == null) {
            return t2;
        }
        // t2为空，t1不为空，那么合并后的节点自然就是t1
        if (t2 == null) {
            return t1;
        }
        // t1和t2都不是空，这种情况就需要创建新节点了，将两个节点的值加和
        TreeNode merge = new TreeNode(t1.val + t2.val);
        // 递归去合并左右子树
        merge.left = mergeTrees1(t1.left, t2.left);
        merge.right = mergeTrees1(t1.right, t2.right);
        // 返回新节点
        return merge;
    }
}
