package 新手班.class07;

// 测试地址：https://leetcode.cn/problems/validate-binary-search-tree/
public class Code03_IsBinarySearchTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    // 因为需要返回三个数据，所以要设置一个用来存储返回数据的类。这样就能通过然会一个对象，携带三个数据
    public static class Info {
        // 以当前节点为根节点的树的最大值
        public int max;
        // 以当前节点为根节点的树的最小值
        public int min;
        // 以当前节点为根节点的树是不是搜索二叉树
        public boolean isSearch;

        public Info(int max, int min, boolean isSearch) {
            this.max = max;
            this.min = min;
            this.isSearch = isSearch;
        }
    }

    // 方法一：二叉树递归套路
    public boolean isValidBST(TreeNode root) {
        Info info = process(root);
        return info == null ? true : info.isSearch;
    }

    /**
     判断一个树是不是搜索二叉树有两种方法：
     1、判断该树的中序遍历是不是升序，搜索二叉树的中序遍历是升序的。
     2、通过递归来判断

     该方法就是递归判断搜索二叉树

     递归的思路就是保证左右子树都是搜索二叉树，并且当前节点比其左子树的最大值大，比其右子树的最小值小，只要满足这个条件，该树就是搜索二叉树
     递归过程中还要去获取当前树的最大值和最小值，就用当前根节点与其左右子树的最大值和最小值分别比较即可，这样就能得到结果
     */
    public Info process(TreeNode root) {
        // 递归出口，当递归到为空的叶子节点时，递归结束，开始向上返回
        if (root == null) {
            return null;
        }

        // 递归调用。将左右子树进行递归，来判断他们是否为二叉搜索树
        Info infoLeft = process(root.left);
        Info infoRight = process(root.right);

        // 准备返回给上一层的结果数据
        // 以该层root节点为根节点的树是否为搜索二叉树
        boolean isSearch = false;
        // 以该层root节点为根节点的树最大值节点为多少（包括根节点）
        int max = root.val;
        // 以该层root节点为根节点的树最小值节点为多少（包括根节点）
        int min = root.val;

        // 下面需要获取以该层root节点为根节点的树的最大值和最小值
        // 这里需要判断两次，分别将当前节点的val与左右子树的最大值和最小值进行比较，才能得到最后的结果

        // 与左子树比较
        if (infoLeft != null) {
            // 当前节点的值与其左子树最大值进行比较，获得最大值
            max = Math.max(max, infoLeft.max);
            // 当前节点的值与其左子树最小值进行比较，获得最小值
            min = Math.min(min, infoLeft.min);
        }

        // 与右子树比较
        if (infoRight != null) {
            max = Math.max(max, infoRight.max);
            min = Math.min(min, infoRight.min);
        }

        // 下面需要根据左右子树的情况来判断以当前节点为根节点的树是不是搜索二叉树。
        // 如果左右节点都不为空，说明存在左右子树
        if (infoLeft != null && infoRight != null) {
            // 当左右子树都是搜索二叉树，并且左子树的最大值小于当前节点的值，右子树的最小值大于当前结点的值，说明以该节点为根节点的树是搜索二叉树
            isSearch = infoLeft.isSearch && infoRight.isSearch && root.val > infoLeft.max && root.val < infoRight.min;
            // 如果只存在左子树
        } else if (infoLeft != null) {
            // 仅判断左子树即可
            isSearch = infoLeft.isSearch && root.val > infoLeft.max;
            // 如果只存在右子树
        } else if (infoRight != null) {
            // 进判断右子树即可
            isSearch = infoRight.isSearch && root.val < infoRight.min;
            // 如果无孩子节点
        } else {
            // 直接默认该树为搜索二叉树
            isSearch = true;
        }

        // 递归接口，将该层结果数据返回给上一层
        return new Info(max, min, isSearch);
    }



    // 方法二：利用Morris遍历改中序遍历，来检查中序遍历是否为升序
    public boolean isValidBST2(TreeNode root) {
        if (root == null) {
            return true;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            // 判断中序遍历是否为升序
            if (pre != null && pre >= cur.val) {
                // 不要return，完整跑完Morris遍历后再return，因为Morris会修改二叉树，需要让Morris遍历都执行完，把二叉树再还原回去
                ans = false;
            }
            // 遍历过程中要记录中序遍历的上一个节点
            pre = cur.val;
            cur = cur.right;
        }
        return ans;
    }

}
