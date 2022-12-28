package 大厂刷题班.class30;

import java.util.ArrayList;
import java.util.List;

// Morris遍历   二叉树遍历   中序遍历
// https://leetcode.cn/problems/binary-tree-inorder-traversal/
public class Problem_0094_BinaryTreeInorderTraversal {
    // 提交代码时不用提交这个内部类
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

    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> ans = new ArrayList<>();
            // 过滤无效参数
            if (root == null) {
                return ans;
            }
            // 当前遍历到的节点
            TreeNode cur = root;
            // mostRight是cur左树上的最右节点
            TreeNode mostRight = null;
            // 开始进行morris遍历
            while (cur != null) {
                // mostRight先去指向cur的左树，然后在后面去循环找到左树的最右节点
                mostRight = cur.left;
                // 判断cur是否存在左树
                // 情况一：如果cur没有左孩子，则cur向右移动
                // 情况二：如果cur有左孩子，则进入到该if分支，去找左树的最右节点
                if (mostRight != null) {
                    // 进入到该分支，就说明此时是情况二
                    // 遍历找到左树的最右节点
                    // 这里循环结束条件是如果当向右遍历到一个节点的右指针是空了，说明就遍历到了左树的最右节点了。
                    // 或者向右遍历到一个节点的右指针指向cur，说明这是第二次来到这个节点了，这个节点就是左树最右节点，只不过第一次遍历到它的时候将它的右指针指向了cur
                    while (mostRight.right != null && mostRight.right != cur) {
                        // 找到cur左树的最右节点
                        mostRight = mostRight.right;
                    }
                    // 情况a：如果此时cur左树的最右节点的右指针指向空，说明是第一次遍历到cur这个节点
                    if (mostRight.right == null) {
                        // 将其右指针指向cur
                        mostRight.right = cur;
                        // cur向左移
                        cur = cur.left;
                        // 跳过本轮循环
                        continue;
                        // 情况b：如果此时cur左树的最右节点的右指针指向cur，说明是第二次遍历到cur这个节点，cur的左子树肯定都已经遍历过了，就不用再遍历它的左子树了，直接cur = cur.right遍历右子树
                    } else {
                        // 将其右指针重新指向Null，还原回以前的样子，然后cur右移
                        mostRight.right = null;
                    }
                }
                // 只会遍历一次的节点，一定会执行到这里。会遍历到两次的节点，在遍历到第二次时也一定会执行到这里，所以就直接在这里打印cur节点
                ans.add(cur.val);
                // 情况一和情况二的b，都会执行这一步，将cur右移
                cur = cur.right;
            }

            return ans;
        }
    }
}
