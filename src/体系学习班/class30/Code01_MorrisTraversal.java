package 体系学习班.class30;

public class Code01_MorrisTraversal {
    // 二叉树节点类
    public static class Node {
        public int value;
        Node left;
        Node right;
        public Node(int data) {
            this.value = data;
        }
    }
    // 普通的二叉树递归遍历  可以用递归实现先序、中序、后序遍历
    public static void process(Node root) {
        if (root == null) {
            return;
        }
        // 1
        process(root.left);
        // 2
        process(root.right);
        // 3
    }
    // morris遍历
    public static void morris(Node head) {
        // 过滤无效参数
        if (head == null) {
            return;
        }
        // 当前遍历到的节点
        Node cur = head;
        // mostRight是cur左树上的最右节点
        Node mostRight = null;
        // 开始进行morris遍历
        while (cur != null) {
            // mostRight先去指向cur的左树，然后在后面去循环找到左树的最右节点
            mostRight = cur.left;
            // 判断cur是否存在左树
            // 情况一：如果cur没有左孩子，则cur向右移动
            // 情况二：如果cur有左孩子，则进入到该if分支，去找左树的最右节点
            if (mostRight != null) {
                // 进入到该分支，就说明此时是情况二
                // 遍历找到左树的最右节点
                // 这里循环结束条件是如果当向右遍历到一个节点的右指针是空了，说明就遍历到了左树的最右节点了。
                // 或者向右遍历到一个节点的右指针指向cur，说明这是第二次来到这个节点了，这个节点就是左树最右节点，只不过第一次遍历到它的时候将它的右指针指向了cur
                while (mostRight.right != null && mostRight.right != cur) {
                    // 找到cur左树的最右节点
                    mostRight = mostRight.right;
                }
                // 情况a：如果此时cur左树的最右节点的右指针指向空，说明是第一次遍历到cur这个节点
                if (mostRight.right == null) {
                    // 将其右指针指向cur
                    mostRight.right = cur;
                    // cur向左移
                    cur = cur.left;
                    // 跳过本轮循环
                    continue;
                // 情况b：如果此时cur左树的最右节点的右指针指向cur，说明是第二次遍历到cur这个节点，cur的左子树肯定都已经遍历过了，就不用再遍历它的左子树了，直接cur = cur.right遍历右子树
                } else {
                    // 将其右指针重新指向Null，还原回以前的样子，然后cur右移
                    mostRight.right = null;
                }
            }
            // 情况一和情况二的b，都会执行这一步，将cur右移
            cur = cur.right;
        }
    }
    // morris改先序遍历
    public static void morrisPre(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 判断cur是否存在左树
            // 如果cur有左孩子，则进入到该if分支，去找左树的最右节点
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    // 如果左树最右节点的右指针指向空，说明是第一次来到cur这个节点，直接打印
                    System.out.print(cur.value + " ");
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
                // 如果cur没有左孩子，则cur向右移动
            } else {
                // 没有左孩子的节点在morris序中只会遍历一次，所以就直接打印
                System.out.print(cur.value + " ");
            }
            cur = cur.right;
        }
        System.out.println();
    }

    // morris改中序遍历
    // 测试链接：https://leetcode.cn/problems/binary-tree-inorder-traversal/
    // 需要稍微修改一下下面的代码提交，因为力扣要求的是返回中序序列的List，而不是原样输出
    public static void morrisIn(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
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
            // 只会遍历一次的节点，一定会执行到这里。会遍历到两次的节点，在遍历到第二次时也一定会执行到这里，所以就直接在这里打印cur节点
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        System.out.println();
    }
    // morris改后序遍历
    public static void morrisPos(Node head) {
        if (head == null) {
            return;
        }
        Node cur = head;
        Node mostRight = null;
        /**
         * 打印处理时机：
         * 能遍历某个节点两次，且第二次回到这个节点的时候，逆序打印它的左树右边界
         * Morris跑完以后，再去逆序打印整棵树的右边界。
         */
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
                    // 如果找到左树的最右节点，发现它的右指针指向的是cur，就说明这是第二次遍历到cur
                } else {
                    mostRight.right = null;
                    // 第二次遍历到cur时，逆序打印cur的左树右边界
                    printEdge(cur.left);
                }
            }
            cur = cur.right;
        }
        // Morris跑完以后，再去逆序打印整棵树的右边界
        printEdge(head);
        System.out.println();
    }
    // 逆序打印以head为根节点的树的右边界
    public static void printEdge(Node head) {
        // 反转右边界，返回是反转后的头节点，也就是反转之前的尾节点
        Node tail = reverseEdge(head);
        // 当前遍历到的节点
        Node cur = tail;
        // 遍历打印右边界
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        // 打印完再将链表反转回去即可
        reverseEdge(tail);
    }
    // 反转链表，注意这里二叉树节点的right，就相当于链表的next，因为我们这里就是要打印树的右边界，不涉及到左指针的问题
    // 返回反转后链表的头节点
    public static Node reverseEdge(Node from) {
        Node pre = null;
        Node next = null;
        // from为当前遍历到的节点
        while (from != null) {
            // next为下一个要遍历道德节点
            next = from.right;
            // 将其指向上一个节点
            from.right = pre;
            // 然后将上一个节点设置为当前节点，供遍历到下一个节点时使用
            pre = from;
            // 向后遍历下一个节点
            from = next;
        }
        // 整个流程走下来，就将链表反转了，并且pre指向的是反转前链表的尾节点，反转后链表的头节点
        return pre;
    }
    // for test -- print tree
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }
    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
    // 判断某棵树是搜索二叉树   只要中序遍历的结果是一个升序序列，那么这棵树就是一个搜索二叉树
    public static boolean isBST(Node head) {
        if (head == null) {
            return true;
        }
        Node cur = head;
        Node mostRight = null;
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
                    // 第二次遍历到cur的时候，需要将其左树的最右节点的右指针恢复成指向Null，不能破坏原有的二叉树结构
                    mostRight.right = null;
                }
            }
            // 在中序遍历过程中，去判断当前遍历到的节点是不是比上一个节点大，如果不是，就说明中序序列不是升序的，那么这个树就不是搜索二叉树
            if (pre != null && pre >= cur.value) {
                // 注意，如果发现不是搜索二叉树了，也需要执行完morris遍历流程，因为需要把叶子节点的右指针都重新还原为指向null，不然整个二叉树结构就给破坏了
                ans = false;
            }
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }
    public static void main(String[] args) {
        Node head = new Node(4);
        head.left = new Node(2);
        head.right = new Node(6);
        head.left.left = new Node(1);
        head.left.right = new Node(3);
        head.right.left = new Node(5);
        head.right.right = new Node(7);
        printTree(head);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);
        printTree(head);
    }
}

