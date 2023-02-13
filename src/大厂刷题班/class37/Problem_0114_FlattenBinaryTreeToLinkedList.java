package 大厂刷题班.class37;
// 注意，我们课上讲了一个别的题，并不是leetcode 114
// 我们课上讲的是，把一棵搜索二叉树变成有序链表，怎么做
// 而leetcode 114是，把一棵树先序遍历的结果串成链表
// 所以我更新了代码，这个代码是leetcode 114的实现
// 利用morris遍历 | 二叉树遍历   这两个方法都可以，morris遍历效率在常数上更高一些，额外空间复杂度更低   可以在两个章节的笔记都加上这道题

// 二叉树  Morris遍历  先序遍历
// https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/
public class Problem_0114_FlattenBinaryTreeToLinkedList {
    // 这个类不用提交
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    // 1、我自己写的代码，最优解，空间复杂度O(1)，时间复杂度O(1)
    public void flatten(TreeNode root) {
        // 当前遍历到的节点
        TreeNode cur = root;
        // 当前遍历到的cur节点的上一个遍历到的节点(按先序遍历顺序)
        TreeNode pre = null;
        // 当前节点左子树的最右子节点
        TreeNode mostRight = null;
        // 经典的Morris先序遍历
        while (cur != null) {
            // 来到当前节点的左子树
            mostRight = cur.left;
            // 如果有左子树
            if (mostRight != null) {
                // 找当前节点左子树的最优解点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 说明第一次遍历到cur节点
                if (mostRight.right == null) {
                    mostRight.right = cur;

                    // 将上一个遍历的节点的左指针指向cur，这里之所以用左指针，是因为右指针在后面还有用，但是左指针没用了，所以暂时用左指针指向链表的下一个节点
                    if (pre != null) {
                        pre.left = cur;
                    }
                    // 更新pre
                    pre = cur;

                    cur = cur.left;
                    continue;
                    // 说明第二次遍历到cur节点
                } else {
                    mostRight.right = null;
                }
                // 当前节点没有左子树，那么这个节点只会遍历一次
            } else {
                // 同样将这个节点加到链表结构中
                if (pre != null) {
                    // 暂时使用节点左指针连接下一个节点
                    pre.left = cur;
                }
                pre = cur;
            }
            cur = cur.right;
        }

        // 遍历链表结构，用右指针取代左指针指向下一个节点，并将左指针指向null
        cur = root;
        TreeNode next = null;
        while (cur != null) {
            next = cur.left;
            cur.right = next;
            cur.left = null;
            cur = next;
        }
    }


    // 下面是左神的代码
    // 2、二叉树递归套路
    public static void flatten1(TreeNode root) {
        process(root);
    }

    public static class Info {
        public TreeNode head;
        public TreeNode tail;

        public Info(TreeNode h, TreeNode t) {
            head = h;
            tail = t;
        }
    }

    public static Info process(TreeNode head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        head.left = null;
        head.right = leftInfo == null ? null : leftInfo.head;
        TreeNode tail = leftInfo == null ? head : leftInfo.tail;
        tail.right = rightInfo == null ? null : rightInfo.head;
        tail = rightInfo == null ? tail : rightInfo.tail;
        return new Info(head, tail);
    }

    // 3、Morris遍历的解   左神写的最优解，和我自己写的一样
    public static void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }
        TreeNode pre = null;
        TreeNode cur = root;
        TreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    if (pre != null) {
                        pre.left = cur;
                    }
                    pre = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            } else {
                if (pre != null) {
                    pre.left = cur;
                }
                pre = cur;
            }
            cur = cur.right;
        }
        cur = root;
        TreeNode next = null;
        while (cur != null) {
            next = cur.left;
            cur.left = null;
            cur.right = next;
            cur = next;
        }
    }

}
