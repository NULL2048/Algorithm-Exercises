package 大厂刷题班.class05;

import 体系学习班.class33.Hash;

// 二叉树递归套路   用Hash函数优化
// 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
// 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
public class Code02_LeftRightSameTreeNumber {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    // 1、未使用哈希函数
    // 时间复杂度O(N * logN)
    // 检查以head为根节点的的数有多少颗相等子树
    public static int sameNumber1(Node head) {
        if (head == null) {
            return 0;
        }

        // 左子树的相等子树数量 + 右子树的相等子树数量 + （当前树是否是相等子树，是就+1）
        return sameNumber1(head.left) + sameNumber1(head.right) + (same(head.left, head.right) ? 1 : 0);
    }

    // 判断以h1和h2为根的两棵树是否结构相同
    public static boolean same(Node h1, Node h2) {
        // 表示h1和h2存在一个为空一个不为空，这种情况表示两个结构不同，返回false
        if (h1 == null ^ h2 == null) {
            return false;
        }
        // 两个头节点都是null，认为是结构相同，返回true
        if (h1 == null && h2 == null) {
            return true;
        }
        // 两个都不为空
        // 只有当前两个头结点的值一样，并且他们的左子树都结构一样，右子树结构一样，才能说明以h1和h2为根节点的树是结构相等的，返回true，否则就返回false
        return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
    }

    // 2、使用哈希函数，对先序序列进行序列化。使用这个方法就可以直接用一个固定长度的字符串来唯一标识一棵二叉树，就可以少写一个递归
    // 时间复杂度O(N)
    public static int sameNumber2(Node head) {
        // 构造哈希函数
        String algorithm = "SHA-256";
        Hash hash = new Hash(algorithm);
        // 二叉树递归套路，以x节点为头，来讨论情况收集数据
        return process(head, hash).ans;
    }

    // 记录每一个先序序列代表的二叉树对应的答案是什么
    public static class Info {
        // 该先序序列代表的二叉树有多少颗相等子树
        public int ans;
        // 先序序列
        public String str;

        public Info(int a, String s) {
            ans = a;
            str = s;
        }
    }

    // 递归求以head为头的二叉树有多少棵相等子树，返回该二叉树的序列化字符串和相等子树数量
    public static Info process(Node head, Hash hash) {
        // 空节点，默认为#，然后将#用哈希函数处理
        if (head == null) {
            return new Info(0, hash.hashCode("#,"));
        }
        // 向左树递归
        Info l = process(head.left, hash);
        // 向右树递归
        Info r = process(head.right, hash);
        // 如果左树的序列化字符串和右树的序列化字符串相等，说明这两棵树结构相同，也就说明以head为头节点的二叉树是相等子树
        // 然后再加上左树的相等子树数量，右树的相等子树数量，就是以head为头节点的二叉树所有的相等子树数量
        int ans = (l.str.equals(r.str) ? 1 : 0) + l.ans + r.ans;
        // 将左树的序列化字符串和右树的序列化字符串拼接起来，用哈希函数处理，生成以Head为头节点的二叉树的序列化字符串，用来构造Info对象
        String str = hash.hashCode(String.valueOf(head.value) + "," + l.str + r.str);
        return new Info(ans, str);
    }

    // for test
    public static Node randomBinaryTree(int restLevel, int maxValue) {
        if (restLevel == 0) {
            return null;
        }
        Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
        if (head != null) {
            head.left = randomBinaryTree(restLevel - 1, maxValue);
            head.right = randomBinaryTree(restLevel - 1, maxValue);
        }
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 8;
        int maxValue = 4;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Node head = randomBinaryTree(maxLevel, maxValue);
            int ans1 = sameNumber1(head);
            int ans2 = sameNumber2(head);
            if (ans1 != ans2) {
                System.out.println("出错了！");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("测试结束");

    }
}
