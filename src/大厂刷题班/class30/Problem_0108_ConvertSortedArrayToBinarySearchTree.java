package 大厂刷题班.class30;
// 二叉树   平衡搜索二叉树   二分   递归
// https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/
public class Problem_0108_ConvertSortedArrayToBinarySearchTree {
    // 不用提交下面这个内部类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode sortedArrayToBST(int[] nums) {
        return process(0, nums.length - 1, nums);
    }

    public TreeNode process(int l, int r, int[] nums) {
        // 如果l和r错开了，说明这个位置应该是空，直接返回null
        if (l > r) {
            return null;
        }

        // 如果要构造的范围中只剩下一个数了，直接将其返回
        if (l == r) {
            return new TreeNode(nums[l]);
        }

        // 每次选择中间位置的数作为根节点，两边的数分别作为左右子树，这样就能保证构造出来平衡二叉搜索树
        int m = (l + r) >> 1;
        // nums[m]作为根节点
        TreeNode node = new TreeNode(nums[m]);
        // 小于nums[m]的在左树，大于nums[m]的在右树
        node.left = process(l, m - 1, nums);
        node.right = process(m + 1, r, nums);

        // 返回
        return node;
    }
}
