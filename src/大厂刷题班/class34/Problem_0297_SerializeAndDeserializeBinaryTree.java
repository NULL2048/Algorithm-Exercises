package 大厂刷题班.class34;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 二叉树  二叉树序列化  二叉树反序列化  先序序列
// https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
public class Problem_0297_SerializeAndDeserializeBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            // 这里我们用字符串去存储序列化之后的结果，这样就不需要用分隔符分割了，因为容器天然具有分割结构
            StringBuilder ans = new StringBuilder();
            // 调用先序遍历序列化方法，传入二叉树根节点
            pres(root, ans);
            // 返回序列化结果
            return ans.toString();
        }

        // 先序遍历序列化
        public void pres(TreeNode head, StringBuilder ans) {
            // 如果当前节点为空，则将"None,"加入到字符串中，因为空节点也不能忽略
            if (head == null) {
                // 注意要用逗号分隔
                ans.append("None,");
            } else {
                // 就按照先序遍历的顺序进行遍历，遍历到一个节点，就将该节点转换成字符类型添加到队列中
                ans.append(String.valueOf(head.val))
                        .append(",");
                pres(head.left, ans);
                pres(head.right, ans);
            }
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            // 如果序列化字符串队列为空，则直接返回null
            if (data == null || data.length() == 0) {
                return null;
            }
            // 将字符串按逗号都分割出来
            String[] dataArray = data.split(",");
            Queue<String> dataList = new LinkedList<String>(Arrays.asList(dataArray));
            // 调用反序列化方法，并且返回反序列化后构建好的二叉树根节点
            return preb(dataList);
        }

        // 先序遍历反序列化
        public TreeNode preb(Queue<String> prelist) {
            // 弹出队列头节点
            String value = prelist.poll();
            // 如果是空节点则返回空，就不再往下创建了，这一条遍历顺序就算是结束了
            if ("None".equals(value)) {
                return null;
            }
            // 按照 头 左 右 的顺序去构建二叉树
            // 将弹出的节点创建为Node节点
            TreeNode head = new TreeNode(Integer.valueOf(value));
            head.left = preb(prelist);
            head.right = preb(prelist);
            // 返回构建好的节点
            return head;
        }
    }
}
