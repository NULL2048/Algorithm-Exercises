package 体系学习班.class12;

// 在线测试链接 : https://leetcode.cn/problems/largest-bst-subtree/
public class Code06_MaxSubBSTSize {
    // 提交时不要提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    // 提交如下的代码，可以直接通过
    public static int largestBSTSubtree(TreeNode head) {
        // 如果一棵树为空树，则其最大二叉搜索子树就是0
        if (head == null) {
            return 0;
        }
        // 通过二叉树递归套路求解
        return process(head).maxBSTSubtreeSize;
    }

    // 信息类
    public static class Info {
        // 当前树的最大二叉搜索子树大小
        public int maxBSTSubtreeSize;
        // 当前树的大小
        public int allSize;
        // 当前树的最大值
        public int max;
        // 当前树的最小值
        public int min;

        public Info(int m, int a, int ma, int mi) {
            maxBSTSubtreeSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    // 递归
    public static Info process(TreeNode x) {
        // 这里如果是空的话，我们不好去设置空树的max和min值，因为可以无穷大或无穷小。所以这里我们就直接返回null，让上层去做处理
        if (x == null) {
            return null;
        }
        // 左右子树递归去得到相应的Info
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 设置max、min、allSize初始值。
        int max = x.val;
        int min = x.val;
        int allSize = 1;
        // 在下面的过程中完成对null的处理
        // 如果左子树不为空，则取更新max、min、allSize的值
        if (leftInfo != null) {
            // 与左子树的最大值比较
            max = Math.max(leftInfo.max, max);
            // 与左子树的最小值比较
            min = Math.min(leftInfo.min, min);
            // 节点数累加左子树的节点数
            allSize += leftInfo.allSize;
        }
        // 右子树同理
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
            allSize += rightInfo.allSize;
        }

        // 到这里，就将最大值，最小值，节点数三个数据都求出来了，下面再去求最大搜索二叉子树大小

        // 下面这是考虑三种情况，1、最大二叉搜索树在左子树中，2、最大二叉搜索树在右子树中，3、最大二叉搜索树就是其本身
        // 设置情况1的最大二叉搜索子树大小初始值
        int p1 = -1;
        // 直接获取左子树的最大二叉搜索子树大小
        if (leftInfo != null) {
            p1 = leftInfo.maxBSTSubtreeSize;
        }
        // 设置情况2的最大二叉搜索子树大小初始值
        int p2 = -1;
        // 直接获取右子树的最大二叉搜索子树大小
        if (rightInfo != null) {
            p2 = rightInfo.maxBSTSubtreeSize;
        }
        // 设置情况3的最大二叉搜索子树大小初始值
        int p3 = -1;
        // 这个情况的前提是当前树必须是一棵二叉搜索出才可以。所以先去判断这棵树是否是一颗二叉搜索树
        // 判断左子树是否是二叉搜索树   通过最大二叉搜索树大小是否等于左子树节点数判断
        boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        //判断右子树是否是二叉搜索树
        boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
        // 如果左右子树都是二叉搜索树，就在去判断x是否大于左子树最大值，是否小于右子树最小值，只要这些条件都符合，就说明这是一棵二叉搜索树
        if (leftBST && rightBST) {
            // x是否大于左子树最大值
            boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.val);
            // x是否小于右子树最小值
            boolean rightMinMoreX = rightInfo == null ? true : (x.val < rightInfo.min);
            // 如果上述条件都成立，说明当前树是一颗搜索二叉树
            if (leftMaxLessX && rightMinMoreX) {
                // 下面就去计算当前二叉树的最大二叉搜索子树大小，也就是计算当前二叉树的节点个数
                // 这里仍然要记得判断空树
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;

                // 左右子树节点数加和 + 1
                p3 = leftSize + rightSize + 1;
            }
        }

        // 最后比较p1、p2、p3大小，找出最大的一个就是当前树的最大二叉搜索树的大小，再把其他的相关信息向上返回
        return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
    }
}
