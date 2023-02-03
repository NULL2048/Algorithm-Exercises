package 大厂刷题班.class35;
// https://leetcode.cn/problems/longest-univalue-path/
// 二叉树递归套路
public class Problem_0687_LongestUnivaluePath {
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    // 建设以x节点为头的树，返回两个信息
    public class Info {
        // 在一条路径上：要求每个节点通过且只通过一遍
        public int max; // 路径必须从x出发且只能往下走的情况下，路径的最大距离
        public int len; // 路径不要求必须从x出发的情况下，整棵树的合法路径最大距离

        public Info(int max, int len) {
            this.max = max;
            this.len = len;
        }
    }

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return process(root).max - 1;
    }

    public Info process(TreeNode x) {
        // 如果递归到空姐点，直接返回info(0,0)
        if (x == null) {
            return new Info(0, 0);
        }

        TreeNode left = x.left;
        TreeNode right = x.right;
        // 左树上，不要求从左孩子出发，最大路径
        // 左树上，必须从左孩子出发，往下的最大路径
        Info leftInfo = process(left);
        // 右树上，不要求从右孩子出发，最大路径
        // 右树上，必须从右孩子出发，往下的最大路径
        Info rightInfo = process(right);

        // 1、必须从x出发的情况下，往下的最大路径
        // 初始路径只有x一个节点，len初始化为1
        int len = 1;
        // 先去看x的左子树的根节点，val是不是和x一样，如果一样就可以连成一条路径，更新len
        if (left != null && left.val == x.val) {
            len = leftInfo.len + 1;
        }
        // 再去用相同的标准判断右子树，更新len
        if (right != null && right.val == x.val) {
            len = Math.max(len, rightInfo.len + 1);
        }

        // 2、不要求必须从x出发（路径可以路过x，也可以不路过x），最大路径
        // len：从x出发，只往一侧向下延伸的最大同值路径长度
        // Math.max(leftInfo.max, rightInfo.max)：以x为根的树上，不路过x节点的最大同值路径长度
        // 上面两者取最大值
        int max = Math.max(len, Math.max(leftInfo.max, rightInfo.max));
        // 然后再去看是不是可以左右两个子树的路径都可以连接到x，这样整个路径就是从左子树到x再到右子树，计算这种情况的路径长度
        if (left != null && right != null && left.val == x.val && right.val == x.val) {
            // 与max比较，尝试推高max
            max = Math.max(max, leftInfo.len + rightInfo.len + 1);
        }

        // 返回当前以x为根的这棵树的info信息
        return new Info(max, len);
    }
}
