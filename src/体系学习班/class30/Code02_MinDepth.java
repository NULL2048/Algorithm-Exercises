package 体系学习班.class30;

// 本题测试链接 : https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
public class Code02_MinDepth {
    // 不提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }

    // 1、二叉树的递归套路解题
    public static int minDepth1(TreeNode head) {
        // 过滤无效参数
        if (head == null) {
            return 0;
        }
        return p(head);
    }

    // 返回x为头的树，最小深度是多少
    public static int p(TreeNode x) {
        // 递归出口
        if (x.left == null && x.right == null) {
            return 1;
        }
        // 左右子树起码有一个不为空
        int leftH = Integer.MAX_VALUE;
        // 指针不为空，就向下层递归，收集下层的信息
        if (x.left != null) {
            leftH = p(x.left);
        }
        int rightH = Integer.MAX_VALUE;
        if (x.right != null) {
            rightH = p(x.right);
        }
        // 将返回上来的信息取最小值并且加1，得到的就是当前树的最小深度。
        return 1 + Math.min(leftH, rightH);
    }

    // 2、morris遍历解题
    public static int minDepth2(TreeNode head) {
        // 过滤无效参数
        if (head == null) {
            return 0;
        }
        // 当前遍历到的节点
        TreeNode cur = head;
        // 当前遍历到的节点的左树最右节点
        TreeNode mostRight = null;
        // 当前遍历到节点的层数
        int curLevel = 0;
        // 记录最小的深度
        int minHeight = Integer.MAX_VALUE;

        // 开始进行Morris遍历
        while (cur != null) {
            // 整个流程框架就还是Morris遍历，只不过在遍历过程中加了计算每一个节点的层数和判断节点是否为叶子结点的过程。

            // 下面先去找到当前节点的左树最右节点
            mostRight = cur.left;
            // 如果当前节点存在左树，说明当前节点会被遍历两遍
            if (mostRight != null) {
                // 记录左树右边界节点的个数
                int rightBoardSize = 1;

                // 持续向右遍历，知道找到左树的最右节点
                // 当遍历到右指针为空或右指针指向当前节点cur，说明来到了左树右边界。
                while (mostRight.right != null && mostRight.right != cur) {
                    // 遍历过程中将右边界节点个数增加
                    rightBoardSize++;
                    // 向右遍历
                    mostRight = mostRight.right;
                }

                // 第一次到达
                if (mostRight.right == null) {
                    // 直接将当前遍历到的节点cur层数加1
                    curLevel++;
                    // 将当前节点的左树最右节点的右指针指向cur
                    mostRight.right = cur;
                    // 去遍历下一个位置
                    cur = cur.left;
                    continue;
                    // 第二次到达
                } else {
                    // 第二次到达cur时，在这个分支一定会将mostRight.right置为空，所以这里我们只需要判断mostRight.left是否为空就能判断mostRight是否是一个叶子节点了
                    if (mostRight.left == null) {
                        // 当前cur的指向的是从以前的mostRight节点，通过其右指针又指向的上层节点
                        // 但是当前的curLevel还是指的以前在下层的mostRight叶子结点的层数，所以我们就可以先用这个curLevel统计出最小深度
                        // 然后再用curLevel - rightBoardSize更新出当前cur节点的层数
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    // 将curLevel更新为当前cur节点的层数，因为以前curLevel表示的是mostRight节点的层数
                    curLevel -= rightBoardSize;
                    // 将mostRight右指针指向空
                    mostRight.right = null;
                }
                // 如果节点没有左树，那么说明这个节点只会来一次
            } else {
                // 直接将层数增加
                curLevel++;
            }
            // 向右移动
            cur = cur.right;
        }


        // Morris遍历结束后，最后再单独判断一下整棵树的最右节点是不是叶子节点
        // 记录整棵树最右节点的层数
        int finalRight = 1;
        // 找到整棵树的最右节点
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        // 判断这个节点的做右指针是不是指向空，如果为空，就说明这个是一个叶子节点
        if (cur.left == null && cur.right == null) {
            // 找最小深度
            minHeight = Math.min(minHeight, finalRight);
        }
        // 返回最小深度
        return minHeight;
    }

}

