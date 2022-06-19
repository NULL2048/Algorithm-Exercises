package 体系学习班.class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接：https://leetcode.cn/problems/encode-n-ary-tree-to-binary-tree/
public class Code03_EncodeNaryTreeToBinaryTree {
    // N叉树节点类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // 二叉树节点类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 将N叉树的每个节点的所有孩子节点，都转换成二叉树的左子树右边界，即可完成N叉树到二叉树的转换
     */
    class Codec {
        // Encodes an n-ary tree to a binary tree.
        // 将N叉树转换为二叉树
        public TreeNode encode(Node root) {
            // 根节点为空，直接返回null
            if (root == null) {
                return null;
            }
            // 将N叉树根节点转换为二叉树节点
            TreeNode head = new TreeNode(root.val);
            // 将N叉树所有的孩子节点都添加到二叉树的左子树的右边界上，通过递归去实现
            head.left = en(root.children);
            return head;
        }

        // 是一个深度优先递归，先递归到最底，在向上返回时再进行每一层递归栈的操作
        private TreeNode en(List<Node> children) {
            // 创建二叉树节点，表示左子树的根节点
            TreeNode head = null;
            // 遍历时的当前节点
            TreeNode cur = null;
            // 遍历N叉树节点所有的孩子节点，将所有的孩子都往右边界上挂
            for (Node child : children) {
                // 将N叉树节点转换为孩子节点
                TreeNode tNode = new TreeNode(child.val);
                // 如果左子树根节点为空，说明这是第一轮循环，还没有任何N叉树孩子节点添加到左子树上，这里就初始化左子树根节点
                if (head == null) {
                    // 将当前N叉树孩子节点设置为二叉树左子树根节点
                    head = tNode;
                } else {
                    // 这里cur是上一次循环到的节点，我们要把所有的子孩子都加到有边界，所以将本次循环到的子孩子tNode追加到上一次循环到的节点的右子节点处
                    cur.right = tNode;
                }
                // cur指向本次循环到的孩子节点
                cur = tNode;
                // 递归去继续转换本次遍历到的节点的孩子，这里就是深度优先递归，先递归到最底，然后在向上返回的过程中处理余下的孩子节点
                cur.left = en(child.children);
            }
            return head;
        }

        // Decodes your binary tree to an n-ary tree.
        // 将二叉树转换为N叉树
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            // 调用递归函数，将二叉树转换为N叉树
            return new Node(root.val, de(root.left));
        }

        // 是一个深度优先递归
        public List<Node> de(TreeNode root) {
            // 创建存放孩子节点的List<Node>
            List<Node> children = new ArrayList<>();
            // 将每一个节点的左子树上所有右边界的节点全部添加到List中，当遍历到null则说明已经将所有的孩子节点添加进List了
            while (root != null) {
                // 创建N叉树节点，并且调用递归函数去转换本次循环到的节点的左子树
                Node cur = new Node(root.val, de(root.left));
                // 将N叉树节点添加到List中
                children.add(cur);
                // 继续向右边界节点遍历
                root = root.right;
            }
            // 返回本层节点的List
            return children;
        }
    }

}
