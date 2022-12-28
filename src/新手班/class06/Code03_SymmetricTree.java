package 新手班.class06;
// 对称二叉树
// 测试链接：https://leetcode.cn/problems/symmetric-tree/
public class Code03_SymmetricTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSymmetric(TreeNode root) {
        // 根节点肯定是一样的，因为二叉树的根节点本身就在镜子上。所以这里我们直接将root节点当成两个节点传进去进行递归，在进行第一轮递归的时候让root自己和自己比较，肯定就是相同的了
        return isMirror(root, root);
    }

    public static boolean isMirror(TreeNode h1, TreeNode h2) {
        // 下面两个if分支是递归出口，递归的关键点就是找递归出口

        // 一个为空，一个不为空，这两个数的结构肯定不同
        if (h1 == null ^ h2 == null) {
            return false;
        }

        // 两个都是空，说明这一条分支判断结束，在进入这个判断分支之前从来没有任何一层调用返回过false，证明这一条分支的结构全都是一样的。
        if (h1 == null && h2 == null) {
            return true;
        }

        // 两个都不为空，继续向下递归，来进行判断。镜面书就是左节点要和右节点相同，或者说左子树要和右子树相同
        return h1.val == h2.val && isMirror(h1.left, h2.right) && isMirror(h1.right, h2.left);
    }

}