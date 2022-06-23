package 体系学习班.class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Code03_lowestAncestor {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node lowestAncestor1(Node head, Node o1, Node o2) {
        if (head == null) {
            return null;
        }
        // key的父节点是value
        HashMap<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
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
        return cur;
    }

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

    public static Node lowestAncestor2(Node head, Node a, Node b) {
        return process(head, a, b).ans;
    }

    // 信息类
    public static class Info {
        // 当前树有没有发现a节点
        public boolean findA;
        // 当前树有没有发现b节点
        public boolean findB;
        // 当前树找到的a和b的最低公共祖先是谁，如果没有在这棵树找到则设置为null
        public Node ans;

        public Info(boolean fA, boolean fB, Node an) {
            findA = fA;
            findB = fB;
            ans = an;
        }
    }

    // 二叉树递归套路
    public static Info process(Node x, Node a, Node b) {
        // 1、处理空节点
        // 如果为空节点，则说明该节点没有a和b，也没有找到二者的最低公共祖先，返回相应的info
        if (x == null) {
            return new Info(false, false, null);
        }

        // 2、通过二叉树递归去获取左右子树的info
        Info leftInfo = process(x.left, a, b);
        Info rightInfo = process(x.right, a, b);

        // 3、通过左右子树的info来计算当前树的info所需要的信息
        // 根据所有的可能性求解
        // 如果当前x就是a,或者左子树发现了a或者右子树发现了a，就说明当前树发现了a
        boolean findA = (x == a) || leftInfo.findA || rightInfo.findA;
        // 同理
        boolean findB = (x == b) || leftInfo.findB || rightInfo.findB;
        // 先将ans设置为null
        Node ans = null;
        // 如果左子树已经找到答案了，则当前树的答案就是左子树的答案
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
            // 如果右子树已经找到答案了，则当前树的答案就是右子树的答案
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
            // 除了上面两种情况，然后发现a和b节点都在x树上，那么说明a和b的最低公共祖先一定是x
        } else {
            if (findA && findB) {
                ans = x;
            }
        }

        // 4、返回当前树的info
        return new Info(findA, findB, ans);
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
    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node> arr = new ArrayList<>();
        fillPrelist(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
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

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node o1 = pickRandomOne(head);
            Node o2 = pickRandomOne(head);
            if (lowestAncestor1(head, o1, o2) != lowestAncestor2(head, o1, o2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
