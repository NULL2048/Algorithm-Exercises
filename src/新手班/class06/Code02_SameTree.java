package 新手班.class06;

// 测试链接：https://leetcode.com/problems/same-tree
public class Code02_SameTree {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        // 一个为空，一个不为空，这两个数的结构肯定不同
        if (p == null ^ q == null) {
            return false;
        }
        // 两个都是空，说明这一条分支判断结束，在进入这个判断分支之前从来没有任何一层调用返回过false，证明这一条分支的结构全都是一样的。
        if (p == null && q == null) {
            return true;
        }
        // 两个都不为空，继续向下递归，来进行判断
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

}

