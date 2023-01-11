package 大厂刷题班.class33;

import java.util.ArrayList;
import java.util.List;

// 二叉树  搜索二叉树 二叉树遍历 中序遍历
// https://leetcode.cn/problems/kth-smallest-element-in-a-bst/
public class Problem_0230_KthSmallestElementInABst {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> inRes = new ArrayList<>();
        // 搜索二叉树的中序遍历就是递增的，所以找第k小的数，就直接取中序遍历的第k个即可
        in(root, inRes);
        return inRes.get(k - 1);
    }

    // 递归中序遍历
    public int in(TreeNode node, List<Integer> inRes) {
        if (node.left != null) {
            in(node.left, inRes);
        }
        inRes.add(node.val);
        if (node.right != null) {
            in(node.right, inRes);
        }

        return 0;
    }
}
