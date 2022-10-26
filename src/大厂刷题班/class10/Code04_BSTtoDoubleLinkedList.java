package 大厂刷题班.class10;

// 二叉树递归套路
// 本题测试链接 : https://leetcode.cn/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/   中等难度的力扣[M]
// 这个看代码更好理解
public class Code04_BSTtoDoubleLinkedList {

    // 提交时不要提交这个类
    public static class Node {
        // 当前节点的值
        public int value;
        // 左子节点
        public Node left;
        // 右子节点
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 提交下面的代码
    public static Node treeToDoublyList(Node head) {
        if (head == null) {
            return null;
        }
        Info allInfo = process(head);
        allInfo.end.right = allInfo.start;
        allInfo.start.left = allInfo.end;
        return allInfo.start;
    }

    public static class Info {
        // 转换完之后的链表头指针
        public Node start;
        // 转换完之后的链表尾指针
        public Node end;

        public Info(Node start, Node end) {
            this.start = start;
            this.end = end;
        }
    }

    // 递归手机左右子树的信息，然后利用左右子树返回上来的信息整合出自己这颗节点的信息，然后继续向上返回
    public static Info process(Node X) {
        // basecase   当递归到空叶子节点的时候，这个空节点我们就认为他们的左右子树也都是空，那么形成的双向链表的头尾指针自然也都是空
        if (X == null) {
            return new Info(null, null);
        }
        // info信息：转完之后的链表的头指针,和转完之后链表的尾指针而且只返回两个变量，但是我认为中间全部串好了。
        // 向左子节点递归收集信息
        Info lInfo = process(X.left);
        // 向右子节点递归收集信息
        Info rInfo = process(X.right);
        // 这道题也有点类似于找规律，就是先想一个具体的例子，看这个例子进行转化后的结果，观察每个节点都是怎么连接的，然后再根据这个总结出连接规则，在抽象出规则模型即可。
        // 左子树转换后的链表尾节点如果不为空的话，它的右指针应该指向当前节点x
        if (lInfo.end != null) {
            lInfo.end.right = X;
        }
        // x的左指针也要指向左子树转换后链表的尾节点
        X.left = lInfo.end;
        // x节点的右指针要指向右子树转换后链表的头节点
        X.right = rInfo.start;
        // 如果右子树转换后的链表头节点不为空，就将右子树转换后的链表头节点的左指针指向x
        if (rInfo.start != null) {
            rInfo.start.left = X;
        }
        // 这个时候我认为leftInfo和rightInfo内部的节点都已经串好了，这里我们就把x节点插到leftInfo和rightInfo中间，来形成一个x节点在中间的新串好的一组数
        // 整体链表的头    lInfo.start != null ? lInfo.start : X   如果左节点的info.start为null，那么就说明x的左边没有节点，直接让xInfo.start = x
        // 整体链表的尾    rInfo.end != null ? rInfo.end : X     如果右节点的info.start为null，那么就说明x的右边没有节点，直接让xInfo.end = x
        return new Info(lInfo.start != null ? lInfo.start : X, rInfo.end != null ? rInfo.end : X);
    }

}
