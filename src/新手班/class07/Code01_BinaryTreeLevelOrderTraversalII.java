package 新手班.class07;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 测试链接：https://leetcode.com/problems/binary-tree-level-order-traversal-ii
public class Code01_BinaryTreeLevelOrderTraversalII {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }


    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // 存放结果的对象
        LinkedList<List<Integer>> ans = new LinkedList<List<Integer>>();
        // 对于边界问题一定要看题干，体感都说了，如果root时空，应该返回一个空数组，而不是返回null
        if (root == null) {
            return ans;
        }

        // 用来实现层序遍历的队列
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        // 队列中弹出的节点
        TreeNode cur = null;
        // 先将头节点放入队列
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 存放其中一层的节点
            List<Integer> list = new LinkedList<Integer>();
            // 这里一定要注意，我们在进行循环操作的时候，是要弹出队列中的元素的，也就是每一轮循环，队列的长度都是会变的。所以一定要操作之间，保存好队列的长度
            int size  = queue.size();
            for (int i = 0; i < size; i++) {
                // 弹出队列头节点，然后存入list。这样就能把这一层的节点都存进去了
                cur = queue.poll();
                list.add(cur.val);

                // 同时把弹出节点的左右节点依次加入队列，方便进行后续的层序遍历
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }

            // 因为题目要求是从底层到上层输出，所以利用双向队列，将每一层的结果头插到链表中
            ans.addFirst(list);
        }

        return ans;

    }
}
