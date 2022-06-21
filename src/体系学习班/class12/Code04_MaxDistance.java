package 体系学习班.class12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code04_MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // for test   暴力的正确解法
    public static int maxDistance1(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = getPrelist(head);
        HashMap<Node, Node> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    // for test
    public static ArrayList<Node> getPrelist(Node head) {
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    // for test
    public static void fillPrelist(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    // for test
    public static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    // for test
    public static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    // for test
    public static int distance(HashMap<Node, Node> parentMap, Node o1, Node o2) {
        HashSet<Node> o1Set = new HashSet<>();
        Node cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    // 主函数调用
    public static int maxDistance2(Node head) {
        return process(head).maxDistance;
    }

    // 信息类
    public static class Info {
        // 当前树的最大距离
        public int maxDistance;
        // 当前树的高度
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }

    }

    // 二叉树递归套路求解
    public static Info process(Node x) {
        // 如果是一个空树，那么这棵树的最大距离就是0，高度也是0      递归出口
        if (x == null) {
            // 这个就属于空置比较好处理的，所以就不向上返回null让上层去处理了，而是直接在本层创建好对应的info返回
            return new Info(0, 0);
        }
        // 左右子树向下递归   向下递归的位置
        // 递归返回左子树的info
        Info leftInfo = process(x.left);
        // 递归返回右子树的info
        Info rightInfo = process(x.right);
        // 当前树的告诉就是左右子树最大高度 + 1
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 左树最大距离
        int p1 = leftInfo.maxDistance;
        // 右树最大距离
        int p2 = rightInfo.maxDistance;
        // 左树最大高度 + 右树最大高度 + 1
        int p3 = leftInfo.height + rightInfo.height + 1;
        // 当前树的最大距离就是p1、p2、p3中最大值
        int maxDistance = Math.max(Math.max(p1, p2), p3);

        // 创建当前树的info并返回       连接每一层递归的接口
        return new Info(maxDistance, height);
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
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
