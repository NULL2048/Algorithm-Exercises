package 新手班.class07;

// 测试链接：https://leetcode.com/problems/path-sum
public class Code04_PathSum {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        // 当树为空，直接返回false
        if (root == null) {
            return false;
        }

        return process(root, targetSum);
    }

    /**
     递归方法
     这里的思路很有意思，就是从最顶层一层层的用targetSum减去当前层节点的val
     当递归到最后一层叶子结点的时候，如果叶子结点的val和targetSum减完还剩下的数字相比较是一样的，说明这一条递归路径就是符合要求的。

     因为有很多分叉，所以这个思路用递归很正确。

     root 为当前子树的根节点
     sum 为递归到这一层，targetSum还剩下多少数没有减完
     */
    public boolean process(TreeNode root, int sum) {
        // 递归出口，当已经遍历到叶子节点（没有子节点的节点），就可以返回了。
        if (root.left == null && root.right == null) {
            // 如果此时叶子节点的val和还剩下的sum一样，说明这一条分支路径加和等于targetSum，符合要求，返回true
            return root.val == sum;
        }

        // 递归调用 这里要注意判断left和right是否为空，因为有的节点可能只有一个子节点
        // 向下层递归，并且将sum减去节点的val
        // 如果某一条子节点为空，直接将这个子节点所在的路径返回false，因为执行到这里说明还不符合条件
        boolean ans = root.left != null ? process(root.left, sum - root.val) : false;
        // 这里用的或，就是说只要有一个子分支返回的是true，就说明存在符合条件的分支
        ans |= root.right != null ? process(root.right, sum - root.val) : false;

        // 递归接口，向上一层返回结果
        return ans;
    }
}
