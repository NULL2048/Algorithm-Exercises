package 体系学习班.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code05_TreeMaxWidth {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 使用Map容器来解题，这个方法在笔试的时候直接用就行
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        // 用来实现层序遍历的队列
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 用Map容器存储每一个节点在二叉树的哪一层，每一个节点的层数就是其父节点层数+1
        // key：节点在哪一层，value：节点
        HashMap<Node, Integer> levelMap = new HashMap<>();
        // 先将根节点加入Map，赋值为1层
        levelMap.put(head, 1);
        int curLevel = 1; // 当前你正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
        // 二叉树的最大宽度
        int max = 0;

        // 进行层序遍历
        while (!queue.isEmpty()) {
            // 整个逻辑就是层序遍历，只不过在遍历的过程中将每一个节点以及其层数存入到Map中
            // 就是通过父节点的层数+1，得到本节点的层数，思路有点像斐波那契数列
            // 并且在这个过程中记录当前层节点数，更新最大值
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        // 找到最大个数
        max = Math.max(max, curLevelNodes);
        return max;
    }

    // 不使用Map容器，只使用有限的几个变量来解题，这个就是在面试的时候用
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }

        // 当前层，最右节点是谁
        Node curLevelEndNode = null;
        // 下一层，最右节点是谁
        Node nextLevelEndNode = null;
        // 当前层的节点数
        int curLevelCnt = 0;
        // 最大宽度
        int maxLevelCnt = 0;

        // 首先将根节点设置为第一层最后一个节点，这个是确定的，因为第一层之一定只有一个节点
        curLevelEndNode = head;

        // 后面就是层序遍历的过程
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(head);

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            // 每弹出一个节点，本层个数就++
            curLevelCnt++;

            // 在将弹出节点的子节点加入到队列的过程中，同时也更新下一层最右侧节点
            if (cur.left != null) {
                queue.add(cur.left);
                nextLevelEndNode = cur.left;
            }

            if (cur.right != null) {
                queue.add(cur.right);
                nextLevelEndNode = cur.right;
            }

            // 如果当前弹出的节点是本层的最后一个节点，说明本层已经遍历结束了
            if (cur == curLevelEndNode) {
                // 尝试去更新最大宽度
                maxLevelCnt = Math.max(curLevelCnt, maxLevelCnt);
                // 将本层个数清零，供下一轮开始遍历下一层节点时使用
                curLevelCnt = 0;
                // 将下一层最右侧节点赋值给本层最右侧节点，供下一轮开始遍历下一层节点时使用
                curLevelEndNode = nextLevelEndNode;
            }
        }

        // 返回二叉树最大宽度
        return maxLevelCnt;
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

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");

    }
}
