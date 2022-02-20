package 新手班.class07;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// 测试链接：https://leetcode.com/problems/path-sum-ii
public class Code05_PathSumII {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        // 用来存放最终结果
        List<List<Integer>> ans = new ArrayList<>();
        // 如果为空树，直接返回空结果
        if (root == null) {
            return ans;
        }

        // 存储路径
        LinkedList<Integer> path = new LinkedList<>();
        process(root, targetSum, 0, path, ans);

        // 返回结果
        return ans;
    }

    /**
     * 递归方法
     * @param root 当前节点
     * @param targetSum 路径和目标
     * @param sum 当前已经累加到的路径和
     * @param path 存储当前路径
     * @param ans 存储最终结果
     */
    public void process(TreeNode root, int targetSum, int sum, LinkedList<Integer> path, List<List<Integer>> ans) {
        // 递归出口。当递归到叶子节点时
        if (root.left == null && root.right == null) {
            // 如果当前叶子节点 + 已经累加的路径和 = targetSum，说明这个是符合要求的路径
            if (targetSum == sum + root.val) {
                // 将该节点添加到当前路径中
                path.add(root.val);
                // 将该路径复制一份存进结果ans中，注意，这里一定要复制一份新的，因为如果直接把path传进去，那么内存是共享的，我们后面再对path进行操作就会影响ans中的结果
                ans.add(copy(path));
                // 恢复现场。这里一定要注意。这个path对象是递归方法以外的对象，它的存储空间并不存储在递归栈中，也就是说递归返回上一层之后，path的数据是不会还原成上一层的样子的
                // 所以这里我们要手动还原path，将本层递归添加进去的节点删除，这样返回上一层的时候，path就还原成了上一层时的状态
                path.pollLast();
            }
            // 返回
            return ;
        }

        // 将当前这一层的节点添加进路径。记住，传入的sum都是在每一层累加本层的节点，而不是累加下一层的节点。即应该是sum + root.val，而不是sum + root.left.val
        path.add(root.val);
        // sum是按值传递的，所以sum和path是不一样的，sum不需要还原现场，而path是按地址传递，并且不是递归栈中的对象，所以需要还原现场
        if (root.left != null) {
            // 递归调用
            process(root.left, targetSum, sum + root.val, path, ans);
        }

        if (root.right != null) {
            // 递归调用
            process(root.right, targetSum, sum + root.val, path, ans);
        }

        // 恢复现场，在所有要返回上一层的地方，都要手动还原Path
        path.pollLast();
    }

    // 拷贝方法
    public static LinkedList<Integer> copy(LinkedList<Integer> path) {
        LinkedList<Integer> ans = new LinkedList<>();
        for (Integer num : path) {
            ans.add(num);
        }
        return ans;
    }

}
