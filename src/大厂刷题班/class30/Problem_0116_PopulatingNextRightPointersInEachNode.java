package 大厂刷题班.class30;
// 116和117题是完全一样的，用相同的代码都可以过
// 二叉树   二叉树遍历    二叉树层序遍历   宽度优先遍历
// https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/
public class Problem_0116_PopulatingNextRightPointersInEachNode {
    // 不要提交这个类
    public static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;
    }

    // 提交下面的代码
    public Node connect(Node root) {
        // 无效参数
        if (root == null) {
            return null;
        }

        // 构造队列
        MyQueue queue = new MyQueue();
        // 将根节点加入队列
        queue.offer(root);
        // 开始层序遍历，在层序遍历过程中将每一层的节点都通过next指针串连起来
        while (!queue.isEmpty()) {
            // 每一次直接将一层的节点遍历
            int size = queue.size();
            // 记录当前遍历到节点的上一个节点，用于将同一层的节点串起来
            // 这里要在循环外每次都创建一次pre，并且初始化为null，因为每一层的节点串起来，但是不同层的不能串
            Node pre = null;

            // 遍历这一层的所有节点
            for (int i = 0; i < size; i++) {
                // 从队列中弹出节点
                Node cur = queue.poll();
                // 将子节点加入到队列中
                if (cur.left != null) {
                    queue.offer(cur.left);
                }

                if (cur.right != null) {
                    queue.offer(cur.right);
                }

                // 将pre和cur节点利用next指针串连起来
                if (pre != null) {
                    pre.next = cur;
                }
                // 更新pre
                pre = cur;
            }
        }
        // 返回根节点
        return root;
    }

    // 利用Node节点中的next指针构造出队列结构
    public class MyQueue {
        public Node head;
        public Node tail;
        public int size;

        public MyQueue() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        // 加入节点
        public void offer(Node cur) {
            if (head == null) {
                head = cur;
                tail = cur;
                size++;
            } else {
                tail.next = cur;
                tail = cur;
                size++;
            }
        }

        // 弹出节点
        public Node poll() {
            Node node = head;
            head = head.next;
            size--;
            node.next = null;
            return node;
        }

        // 队列是否为空
        public boolean isEmpty() {
            return this.size == 0;
        }

        // 返回队列节点数
        public int size() {
            return this.size;
        }
    }
}
