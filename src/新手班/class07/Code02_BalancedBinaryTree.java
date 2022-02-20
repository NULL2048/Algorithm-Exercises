package 新手班.class07;

// 测试链接：https://leetcode.com/problems/balanced-binary-tree
public class Code02_BalancedBinaryTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 因为这里递归需要传递两个数据，然而return只能将一个对象返回，所以我们创建一个对象，然后在对象内部携带两个属性参数，这样就能实现返回两个数据了。
    public static class Info {
        // 当前节点的高度
        public int height;
        // 以当前节点为根节点的数是不是满足平衡二叉树
        public boolean isBalanced;

        public Info(int height, boolean isBalanced) {
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }

    public boolean isBalanced(TreeNode root) {
        return process(root).isBalanced;
    }

    /**
     递归判断是否为平衡二叉树
     */
    public Info process(TreeNode root) {
        // 递归出口，当向下递归到最后一层，也就是为null的叶子节点时，递归结束，开始向上返回。一般在递归调用之前
        if (root == null) {
            // 为空的叶子节点一定是平衡的，而且高度为0
            return new Info(0, true);
        }

        // 递归调用，分别对左子树和右子树进行递归
        Info infoLeft = process(root.left);
        Info infoRight = process(root.right);

        // 准备本层递归要返回给上层数据的结果
        // 这里首先计算当前节点的高度，然后再去判断当前节点是不是满足平衡条件，即左右子树都满足平衡，并且左右子树的高度差不超过1
        Info rootInfo = new Info(Math.max(infoLeft.height, infoRight.height) + 1,
                infoLeft.isBalanced && infoRight.isBalanced &&
                        (Math.abs(infoLeft.height - infoRight.height) < 2));

        // 每一层递归的接口，用来将本层数据结果返回给上一层。一般在递归调用之后
        return rootInfo;
    }
}
