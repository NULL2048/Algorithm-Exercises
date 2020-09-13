package 树.树的子结构;

import 树.TreeNode;

import java.io.IOException;
import java.util.Scanner;

public class Solution {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        TreeNode root1 = create();
        TreeNode root2 = create();

        System.out.println(HasSubtree(root1, root2));
    }

    public static TreeNode create() throws IOException {
        TreeNode node;

        String str = scan.next();

        if ("#".equals(str)) {
            node = null;
        } else {
            node = new TreeNode();
            node.setVal(str);
            node.setLeft(create());
            node.setRight(create());
        }

        return node;
    }

    public static boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if (root2 != null && root1 != null) {
            //   if (dfs(root1, root2) == true) {
            //     return true;
            // }
            //  HasSubtree(root1.left, root2);
            //  HasSubtree(root1.right, root2);

            return dfs(root1, root2) || HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
        }
        return false;
    }


    public static boolean dfs(TreeNode node1, TreeNode node2) {
        if (node1.val != node2.val) {
            return false;
        }

        if (node2 == null) {
            return true;
        }

        if (node1 == null) {
            return false;
        }

        //if (node1 != null && node2 != null) {
        // 必须都返回true才能说明B是A的子树
        return dfs(node1.left, node2.left) && dfs(node1.right, node2.right);
        // }
    }
}
