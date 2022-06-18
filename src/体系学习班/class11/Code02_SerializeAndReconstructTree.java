package 体系学习班.class11;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
    /*
     * 二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样。
     * 比如如下两棵树
     *         __2
     *        /
     *       1
     *       和
     *       1__
     *          \
     *           2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     *
     * */
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 先序遍历序列化
    public static Queue<String> preSerial(Node head) {
        // 这里我们用队列去存储序列化之后的结果，这样就不需要用分隔符分割了，因为容器天然具有分割结构
        Queue<String> ans = new LinkedList<>();
        // 调用先序遍历序列化方法，传入二叉树根节点
        pres(head, ans);
        // 返回序列化结果
        return ans;
    }

    // 先序遍历序列化
    public static void pres(Node head, Queue<String> ans) {
        // 如果当前节点为空，则将null加入到队列中，因为空姐点也不能忽略
        if (head == null) {
            ans.add(null);
        } else {
            // 就按照先序遍历的顺序进行遍历，遍历到一个节点，就将该节点转换成字符类型添加到队列中
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    // 先序遍历反序列化：这里是将队列中的序列化字符串进行反序列化，所以不用考虑分隔符
    public static Node buildByPreQueue(Queue<String> prelist) {
        // 如果序列化字符串队列为空，则直接返回null
        if (prelist == null || prelist.size() == 0) {
            return null;
        }
        // 调用反序列化方法，并且返回反序列化后构建好的二叉树根节点
        return preb(prelist);
    }

    // 先序遍历反序列化
    public static Node preb(Queue<String> prelist) {
        // 弹出队列头节点
        String value = prelist.poll();
        // 如果是空节点则返回空，就不再往下创建了，这一条遍历顺序就算是结束了
        if (value == null) {
            return null;
        }
        // 按照 头 左 右 的顺序去构建二叉树
        // 将弹出的节点创建为Node节点
        Node head = new Node(Integer.valueOf(value));
        head.left = preb(prelist);
        head.right = preb(prelist);
        // 返回构建好的节点
        return head;
    }

    // 中序无法实现序列化和反序列化
    public static Queue<String> inSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        ins(head, ans);
        return ans;
    }

    public static void ins(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ins(head.left, ans);
            ans.add(String.valueOf(head.value));
            ins(head.right, ans);
        }
    }

    // 后续遍历序列化
    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    // 后序遍历序列化
    public static void poss(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            // 和先序遍历序列化唯一的区别就是这里是按照后序遍历的顺序
            poss(head.left, ans);
            poss(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    // 后序遍历反序列化
    public static Node buildByPosQueue(Queue<String> poslist) {
        // 如果序列化字符串队列为空，则直接返回null
        if (poslist == null || poslist.size() == 0) {
            return null;
        }

        // 这里将队列中的序列化字符转入到栈中，这样从栈弹出顺序就从 左右头 ——> 头右左
        Stack<String> stack = new Stack<>();
        while (!poslist.isEmpty()) {
            stack.push(poslist.poll());
        }
        return posb(stack);
    }

    // 后序遍历反序列化：使用栈
    public static Node posb(Stack<String> posstack) {
        String value = posstack.pop();
        if (value == null) {
            return null;
        }
        // 这里构建二叉树是按照头右左的顺序
        Node head = new Node(Integer.valueOf(value));
        head.right = posb(posstack);
        head.left = posb(posstack);
        return head;
    }

    // 层序遍历序列化
    public static Queue<String> levelSerial(Node head) {
        // ans队列用来存储序列化结果
        Queue<String> ans = new LinkedList<>();
        // 如果根节点为空，则直接将null加入到序列化队列中，并且返回ans
        if (head == null) {
            ans.add(null);
        } else {
            // 先将根节点加入到队列中
            ans.add(String.valueOf(head.value));
            // queue队列是用来完成层序遍历的
            Queue<Node> queue = new LinkedList<Node>();
            // 将头节点加入到queue中，准备开始层序遍历
            queue.add(head);
            // 当queue为空时，层序遍历结束
            while (!queue.isEmpty()) {
                // 下面就是很经典的层序遍历过程
                // 将队列头弹出
                head = queue.poll(); // head 父   子
                // 将弹出节点的左孩子加入到ans和queue队列中
                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                    // 如果左孩子为空，则不将null加入到queue，但是仍要加入到ans中，因为序列化不能忽略null
                } else {
                    ans.add(null);
                }
                // 右节点也是相同的流程
                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    ans.add(null);
                }
            }
        }
        // 返回序列化结果
        return ans;
    }

    // 层序遍历反序列化
    public static Node buildByLevelQueue(Queue<String> levelList) {
        // 如果序列化结果为空，则直接返回null
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        // 创建头节点，并且序列化队列中的头节点弹出
        Node head = generateNode(levelList.poll());
        // 创建层序遍历需要用到的队列
        Queue<Node> queue = new LinkedList<Node>();
        // 如果头节点不为空，则将其加入到层序遍历队列中
        if (head != null) {
            queue.add(head);
        }
        Node node = null;

        // 当层序遍历队列中为空，则反成了反序列化
        while (!queue.isEmpty()) {
            // 同队列中弹出头节点
            node = queue.poll();
            // 创建弹出节点的左右子孩子（左右子孩子就是依次从序列化队列中弹出两个节点）
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            // 弹出来的左右子孩子如果不为空，则依次加入到层序遍历队列中
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }

        // 返回头节点
        return head;
    }

    // 根据value生成Node节点
    public static Node generateNode(String val) {
        if (val == null) {
            return null;
        }
        return new Node(Integer.valueOf(val));
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static boolean isSameValueStructure(Node head1, Node head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (head1.value != head2.value) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // for test
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

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            Node preBuild = buildByPreQueue(pre);
            Node posBuild = buildByPosQueue(pos);
            Node levelBuild = buildByLevelQueue(level);
            if (!isSameValueStructure(preBuild, posBuild) || !isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }
}

