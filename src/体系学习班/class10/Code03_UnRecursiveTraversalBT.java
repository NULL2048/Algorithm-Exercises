package 体系学习班.class10;

import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    // 先序遍历
    public static void pre(Node cur) {
        System.out.print("pre-order: ");
        // 传入的是二叉树的根节点，先判断是不是为空
        if (cur != null) {
            // 创建栈
            Stack<Node> stack = new Stack<Node>();
            // 先将根节点压入栈顶
            stack.add(cur);
            // 循环入栈出栈，当栈中没有节点了，则先序遍历结束
            while (!stack.isEmpty()) {
                // 1、先将栈顶节点出栈并且输出打印
                cur = stack.pop();
                System.out.print(cur.value + " ");

                // 2、如果该节点有右子树，则将其右子节点入栈
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                // 3、如果该节点有左子树，则将其左子节点入栈
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
        System.out.println();
    }

    // 中序遍历
    public static void in(Node cur) {
        System.out.print("in-order: ");
        // 传入的是二叉树的根节点，先判断是不是为空
        if (cur != null) {
            // 创建栈
            Stack<Node> stack = new Stack<Node>();
            // 当栈空并且树空则中序遍历结束
            while (!stack.isEmpty() || cur != null) {
                // 只要是左边界还没有遍历到底，就将所有的左边界压入栈
                if (cur != null) {
                    stack.push(cur);
                    // cur指向左孩子，遍历所有的左边界
                    cur = cur.left;
                    // 如果cur为空，则说明左边界已经遍历到底了，将栈顶节点弹出并打印，并且将其右孩子设置为cur，从其右孩子开始，再次将这一层的左边界全部压入栈中
                } else {
                    // 弹出栈顶并打印
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    // 再从弹出节点的右孩子为基准，将这一层所有的左边界入栈
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    // 后序遍历
    public static void pos1(Node cur) {
        System.out.print("pos-order: ");
        // 传入的是二叉树的根节点，先判断是不是为空
        if (cur != null) {
            // 栈1
            Stack<Node> s1 = new Stack<Node>();
            // 栈2，用来将栈1中节点逆序
            Stack<Node> s2 = new Stack<Node>();
            // 先将根节点压入栈1
            s1.push(cur);
            // 基本过程和先序遍历一样，只不过调换了压入左右子节点的顺序，并且从栈1弹出的节点不再打印，而是将其放入到栈2，等到最后一并从栈2弹出打印
            while (!s1.isEmpty()) {
                // 头 右 左
                // 1、将栈1的栈顶结点弹出
                cur = s1.pop();
                // 2、将栈1弹出的节点压入栈2
                s2.push(cur);

                // 3、如果该节点有左子树，则将其左子节点入栈
                if (cur.left != null) {
                    s1.push(cur.left);
                }
                // 4、如果该节点有右子树，则将其右子节点入栈
                if (cur.right != null) {
                    s1.push(cur.right);
                }
            }

            // 左 右 头
            // 从栈2中将所有节点弹出并打印，完成后序遍历
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    // 只使用一个栈实现非递归后序遍历，这个非常难，面试考不到这个难度，可以不掌握
    public static void pos2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek();
                if (c.left != null && h != c.left && h != c.right) {
                    stack.push(c.left);
                } else if (c.right != null && h != c.right) {
                    stack.push(c.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    h = c;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }
}
