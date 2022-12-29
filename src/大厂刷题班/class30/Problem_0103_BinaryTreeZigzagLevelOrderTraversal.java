package 大厂刷题班.class30;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// 二叉树   二叉树层序遍历   宽度优先遍历
// https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/
public class Problem_0103_BinaryTreeZigzagLevelOrderTraversal {
    // 提交的时候不用提交这个内部类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        // 存放结果的对象
        List<List<Integer>> ans = new ArrayList<>();
        // 对于边界问题一定要看题干，题干都说了，如果root时空，应该返回一个空数组，而不是返回null
        if (root == null) {
            return ans;
        }

        // 题目要求实现锯齿层序遍历，所以这里队列要用双端链表来实现
        LinkedList<TreeNode> queue = new LinkedList<>();
        // 标记当前层应该是从左向右遍历还是从右向左遍历
        boolean flag = true;
        // 先将根节点加入到队列
        queue.addLast(root);

        // 下面整体代码还是按照经典的层序遍历的思路，只不过每一次是一次性输出一层，并且根据flag来决定是从左向右遍历还是从右向左遍历
        while (!queue.isEmpty()) {
            // 记录当前层的节点数，后面一次性将当前层全部输出
            int size = queue.size();
            // 当前层遍历到的节点
            TreeNode cur = null;
            // 记录当前层的输出答案
            List<Integer> curLevel = new ArrayList<>();
            // 将当前层的节点输出，这里是边遍历边加入，但是因为有size控制输出个数，所以并不会多输出
            for (int i = 0; i < size; i++) {
                // 根据flag判断当前层是从左向右遍历还是从右向左遍历
                cur = flag ? queue.pollFirst() : queue.pollLast();
                // 将cur加入到当前层输出答案中
                curLevel.add(cur.val);
                // 根据flag决定要从那个方向将当前节点的子节点加入到队列
                if (flag) {
                    // 如果当前是从左往右输出，那么我们在加入子节点的时候要从队列右侧加入，避免影响本层的输出
                    // 注意左子节点和右子节点加入的顺序
                    if (cur.left != null) {
                        queue.addLast(cur.left);
                    }

                    if (cur.right != null) {
                        queue.addLast(cur.right);
                    }
                } else {
                    // 如果当前是从右往左输出，那么我们在加入子节点的时候要从队列左侧加入，避免影响本层的输出
                    // 注意左子节点和右子节点加入的顺序
                    if (cur.right != null) {
                        queue.addFirst(cur.right);
                    }

                    if (cur.left != null) {
                        queue.addFirst(cur.left);
                    }
                }
            }
            // 对flag取反
            flag = !flag;
            // 将当前层的结果加入到ans中
            ans.add(curLevel);
        }

        // 返回锯齿层序遍历的结果
        return ans;
    }
}
