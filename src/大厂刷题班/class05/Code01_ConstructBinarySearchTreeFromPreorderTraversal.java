package 大厂刷题班.class05;

// 二叉树   单调栈
// 本题测试链接 : https://leetcode.cn/problems/construct-binary-search-tree-from-preorder-traversal/
public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {
    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        // 过滤无效参数
        if (preorder == null || preorder.length == 0) {
            return null;
        }

        int n = preorder.length;
        // 记录先序序列中每一个位置右遍最近的比他的的数的位置
        // nearBig[i] = x 表示先序序列i位置中，右遍离它最近的大于大的数的位置为x
        int[] nearBig = new int[n];
        // 先初始化成-1，表示右边没有比自己大的数了
        for (int i = 0; i < n; i++) {
            nearBig[i] = -1;
        }

        // 使用单调栈，找到每一个位置右遍里它最近的并且大于它的数
        // 栈中的数据自底向上，从大到小
        int[] stack = new int[n];
        int top = -1;
        for (int i = 0; i < n; i++) {
            // 把需要弹出的数据都从栈中弹出来，并记录弹出位置的右边最近比它大的数的位置
            while (top != -1 && preorder[i] >  preorder[stack[top]]) {
                nearBig[stack[top]] = i;
                top--;
            }

            stack[++top] = i;
        }

        // 递归构造二叉树
        return process(preorder, 0, n - 1, nearBig);
    }

    // 返回先序序列中l~r范围上的节点构成的二叉树的头节点
    public TreeNode process(int[] preorder, int l, int r, int[] nearBig) {
        // 表示此时已经遍历到不合法位置了，说明此时这个应该是到了最底部的空节点，返回null
        if (l > r) {
            return null;
        }

        // 在先序序列中任意范围上的数，最左边的一定是这些数组成的二叉树的头节点
        // 找到此时l位置作为头节点，它右遍最近的比他大的位置，找到的这个位置及其右边的所有数，就构成了l为头节点的二叉搜索树的右子树
        // 如果此时l右边已经没有比他大的数了，或者比他大的数就不在当前这个树的l~r的范围上，就说明当前l为头节点的这棵树就没有右子树，设置为r+1，在下一层递归的就返回null
        int next = (nearBig[l] == -1 || nearBig[l] > r) ? r + 1 : nearBig[l];

        // 向下递归构造左子树  l + 1 ~ next-1就是l为根节点的左子树的所有节点
        TreeNode left = process(preorder, l + 1, next - 1, nearBig);
        // 向下递归构造右子树  next ~ r就是l为根节点的右子树的所有节点
        TreeNode right = process(preorder, next, r, nearBig);

        // 构造二叉树根节点
        TreeNode head = new TreeNode(preorder[l], left, right);
        // 返回当前这棵树的根节点
        return head;
    }
}
