package 大厂刷题班.class37;
// 二叉树递归套路
// 这道题的思路就是派对快乐值问题，体系学习班13节Code04_MaxHappy
// https://leetcode.cn/problems/house-robber-iii/
public class Problem_0337_HouseRobberIII {
    // 不要提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public class Info {
        // 如果要抢劫当前节点的情况下，以当前节点为根节点的整棵树能获得的最大收益是多少
        public int yes;
        // 如果不抢劫当前节点的情况下，以当前节点为根节点的整棵树能获得的最大收益是多少
        public int no;

        public Info(int y, int n) {
            yes = y;
            no = n;
        }

    }

    public int rob(TreeNode root) {
        // 获取根节点root的Info信息
        Info ans = process(root);
        // 两种情况取最大值就是答案
        return Math.max(ans.no, ans.yes);
    }

    public Info process(TreeNode node) {
        // node节点左子树的信息
        Info leftInfo = null;
        // node节点右子树的信息
        Info rightInfo = null;

        // 递归手机左右子树的Info
        if (node.left != null) {
            leftInfo = process(node.left);
        }

        if (node.right != null) {
            rightInfo = process(node.right);
        }

        // 左子树和右子树不抢根节点情况下整棵树的最大收益
        int leftNo = 0;
        int rightNo = 0;
        // 左子树和右子树抢根节点情况下整棵树的最大收益
        int leftYes = 0;
        int rightYes = 0;
        // 给上面四个变量赋值，如果没有左右子树了，相关的信息就默认为0
        if (leftInfo != null) {
            leftNo = leftInfo.no;
            leftYes = leftInfo.yes;
        }
        if (rightInfo != null) {
            rightNo = rightInfo.no;
            rightYes = rightInfo.yes;
        }

        // 情况一：抢劫node节点的情况下，计算以node节点为根节点的整颗树的最大收益
        // 这种情况下左右子节点都是不能抢劫的，否则会出发警报。所以这个的答案就是leftNo + rightNo + node.val
        int yes = leftNo + rightNo + node.val; // 要记得加上node节点本身的价值，因为这种情况还要抢劫node节点
        // 情况二：不抢劫node节点的情况下，计算以node节点为根节点的整颗树的最大收益
        // 这种情况下左右子树抢劫也可以，不抢也可以，都不会触发警报，因为没有同时抢劫直接相连的屋子
        // 所以这个就是取左子树抢劫和不抢劫两种情况的最大收益的最大值 + 右子树抢劫和不抢劫两种情况的最大收益的最大值  这个就是node节点情况二的最大收益
        int no = Math.max(leftNo, leftYes) + Math.max(rightNo, rightYes);

        // 返回node节点的Info信息
        return new Info(yes, no);
    }
}
