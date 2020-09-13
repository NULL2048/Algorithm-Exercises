package 树.层序遍历;

import 树.TreeNode;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void LevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        TreeNode node = root;

        queue.offer(node);

        while (!queue.isEmpty()) {
            TreeNode tempNode = queue.poll();
            out.print(tempNode.getVal() + " ");
            if (tempNode.getLeft() != null) {
                queue.offer(tempNode.getLeft());
            }
            if (tempNode.getRight() != null) {
                queue.offer(tempNode.getRight());
            }
        }
        out.flush();
    }
}
