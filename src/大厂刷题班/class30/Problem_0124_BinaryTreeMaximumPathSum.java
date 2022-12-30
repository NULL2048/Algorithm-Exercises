package 大厂刷题班.class30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

// 112. 路径总和、113. 路径总和 II是之前做过的和这道题是同一个系列的题，可以对比着看
// 二叉树递归套路
// follow up : 如果要求返回整个路径是什么，应该怎么做？
// https://leetcode.cn/problems/binary-tree-maximum-path-sum/
public class Problem_0124_BinaryTreeMaximumPathSum {
    // 不用提交下面这个内部类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    // 提交下面的代码
    // 任何一棵树，必须汇报上来的信息
    public class Info{
        // 从左子树或者右子树延伸出来的连到x节点的最大路径和（注意这个只是从左子树或右子树开始连到x为止的路径，并不是从左子树跨过x再连到右子树的路径）
        public int maxPathSumFromHead;
        // 以当前节点为根节点的整棵树的最大路径和（路径并不一定要过x节点）
        public int maxPathSum;

        public Info(int maxPathSumFromHead, int maxPathSum) {
            this.maxPathSumFromHead = maxPathSumFromHead;
            this.maxPathSum = maxPathSum;
        }
    }

    public int maxPathSum(TreeNode root) {
        // 无效参数
        if (root == null) {
            return 0;
        }

        // 返回root的maxPathSum信息
        return process(root).maxPathSum;
    }

    // 二叉树递归
    public Info process(TreeNode x) {
        // basecase  空的话直接返回null，让上层去做相关的判断
        if (x == null) {
            return null;
        }

        // 通过递归获得左右子树的信息
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 1、先计算maxPathSumFromHead。一共就有三种情况：1)只有x 2）x往左扎 3）x往右扎
        // 先将maxPathSumFromHead初始化为x.val，情况一
        int maxPathSumFromHead = x.val;
        // 每一步操作都要先判断leftInfo和rightInfo是否为空
        // 去比较x往左扎的路径最大值是否是比maxPathSumFromHead大，大则更新maxPathSumFromHead，情况二
        if (leftInfo != null) {
            maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + leftInfo.maxPathSumFromHead);
        }
        // 去比较x往右扎的路径最大值是否是比maxPathSumFromHead大，大则更新maxPathSumFromHead，情况三
        if (rightInfo != null) {
            maxPathSumFromHead = Math.max(maxPathSumFromHead, x.val + rightInfo.maxPathSumFromHead);
        }

        // 2、再计算以x为根节点的整棵树的最大路径和maxPathSum。
        // 一共就有六种情况：1) 只有x  2)左树整体的最大路径和（不过x节点，也就是左树的maxPathSum） 3) 右树整体的最大路径和（不过x节点，也就是右树的maxPathSum）  4)从左树连接到x节点的最大路径和（只有x到左树的路径）   5)从右树连接到x节点的最大路径和（只有x到右树的路径）  6)从左树连接到x节点，再延伸到右树的最大路径和
        // 先将maxPathSum初始化为x.val，情况一
        int maxPathSum = x.val;
        // 去比较左子树的最大路径和是否是比maxPathSum大，大则更新maxPathSum，情况二
        if (leftInfo != null) {
            maxPathSum = Math.max(maxPathSum, leftInfo.maxPathSum);
        }
        // 去比较右子树的最大路径和是否是比maxPathSum大，大则更新maxPathSum，情况三
        if (rightInfo != null) {
            maxPathSum = Math.max(maxPathSum, rightInfo.maxPathSum);
        }

        // 去比较左子树连接到x的最大路径和是否是比maxPathSum大，大则更新maxPathSum，情况四
        if (leftInfo != null) {
            // 这个是利用x.val + leftInfo.maxPathSumFromHead求出来的
            maxPathSum = Math.max(maxPathSum, x.val + leftInfo.maxPathSumFromHead);
        }
        // 去比较右子树连接到x的最大路径和是否是比maxPathSum大，大则更新maxPathSum，情况五
        if (rightInfo != null) {
            maxPathSum = Math.max(maxPathSum, x.val + rightInfo.maxPathSumFromHead);
        }
        // 去比较左子树连接到x，再跨国x连接到右子树的最大路径和是否是比maxPathSum大，大则更新maxPathSum，情况六
        if (leftInfo != null && rightInfo != null) {
            // 这个是利用x.val + leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead求出来的
            maxPathSum = Math.max(maxPathSum, x.val + leftInfo.maxPathSumFromHead + rightInfo.maxPathSumFromHead);
        }

        // 返回以当前x节点为根节点的树的Info信息
        return new Info(maxPathSumFromHead, maxPathSum);
    }





    // follow up : 如果要返回路径的做法
    // 整个思路和上面的是基本一致的，只不过是在记录最大路径和的时候，还要记录一下此时路径的起始和结尾节点，只要是找到了这两个节点，就可以通过找到他们俩的最近公共父节点就能找到整条路经了
    public static List<TreeNode> getMaxSumPath(TreeNode head) {
        List<TreeNode> ans = new ArrayList<>();
        if (head != null) {
            Data data = f(head);
            HashMap<TreeNode, TreeNode> fmap = new HashMap<>();
            fmap.put(head, head);
            fatherMap(head, fmap);
            fillPath(fmap, data.from, data.to, ans);
        }
        return ans;
    }

    public static class Data {
        public int maxAllSum;
        public TreeNode from;
        public TreeNode to;
        public int maxHeadSum;
        public TreeNode end;

        public Data(int a, TreeNode b, TreeNode c, int d, TreeNode e) {
            maxAllSum = a;
            from = b;
            to = c;
            maxHeadSum = d;
            end = e;
        }
    }

    public static Data f(TreeNode x) {
        if (x == null) {
            return null;
        }
        Data l = f(x.left);
        Data r = f(x.right);
        int maxHeadSum = x.val;
        TreeNode end = x;
        if (l != null && l.maxHeadSum > 0 && (r == null || l.maxHeadSum > r.maxHeadSum)) {
            maxHeadSum += l.maxHeadSum;
            end = l.end;
        }
        if (r != null && r.maxHeadSum > 0 && (l == null || r.maxHeadSum > l.maxHeadSum)) {
            maxHeadSum += r.maxHeadSum;
            end = r.end;
        }
        int maxAllSum = Integer.MIN_VALUE;
        TreeNode from = null;
        TreeNode to = null;
        if (l != null) {
            maxAllSum = l.maxAllSum;
            from = l.from;
            to = l.to;
        }
        if (r != null && r.maxAllSum > maxAllSum) {
            maxAllSum = r.maxAllSum;
            from = r.from;
            to = r.to;
        }
        int p3 = x.val + (l != null && l.maxHeadSum > 0 ? l.maxHeadSum : 0)
                + (r != null && r.maxHeadSum > 0 ? r.maxHeadSum : 0);
        if (p3 > maxAllSum) {
            maxAllSum = p3;
            from = (l != null && l.maxHeadSum > 0) ? l.end : x;
            to = (r != null && r.maxHeadSum > 0) ? r.end : x;
        }
        return new Data(maxAllSum, from, to, maxHeadSum, end);
    }

    public static void fatherMap(TreeNode h, HashMap<TreeNode, TreeNode> map) {
        if (h.left == null && h.right == null) {
            return;
        }
        if (h.left != null) {
            map.put(h.left, h);
            fatherMap(h.left, map);
        }
        if (h.right != null) {
            map.put(h.right, h);
            fatherMap(h.right, map);
        }
    }

    public static void fillPath(HashMap<TreeNode, TreeNode> fmap, TreeNode a, TreeNode b, List<TreeNode> ans) {
        if (a == b) {
            ans.add(a);
        } else {
            HashSet<TreeNode> ap = new HashSet<>();
            TreeNode cur = a;
            while (cur != fmap.get(cur)) {
                ap.add(cur);
                cur = fmap.get(cur);
            }
            ap.add(cur);
            cur = b;
            TreeNode lca = null;
            while (lca == null) {
                if (ap.contains(cur)) {
                    lca = cur;
                } else {
                    cur = fmap.get(cur);
                }
            }
            while (a != lca) {
                ans.add(a);
                a = fmap.get(a);
            }
            ans.add(lca);
            ArrayList<TreeNode> right = new ArrayList<>();
            while (b != lca) {
                right.add(b);
                b = fmap.get(b);
            }
            for (int i = right.size() - 1; i >= 0; i--) {
                ans.add(right.get(i));
            }
        }
    }

    public static void main(String[] args) {
        TreeNode head = new TreeNode(4);
        head.left = new TreeNode(-7);
        head.right = new TreeNode(-5);
        head.left.left = new TreeNode(9);
        head.left.right = new TreeNode(9);
        head.right.left = new TreeNode(4);
        head.right.right = new TreeNode(3);

        List<TreeNode> maxPath = getMaxSumPath(head);

        for (TreeNode n : maxPath) {
            System.out.println(n.val);
        }
    }
}
