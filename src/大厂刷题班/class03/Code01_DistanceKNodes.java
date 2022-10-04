package 大厂刷题班.class03;

import java.util.*;

public class Code01_D、istanceKNodes {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    // 找target节点距离为k的所有节点
    public static List<Node> distanceKNodes(Node root, Node target, int K) {
        HashMap<Node, Node> parents = new HashMap<>();
        // 根节点没有父节点
        parents.put(root, null);
        // 创建父节点关联Map
        createParentMap(root, parents);
        // 用来宽度优先遍历的队列
        Queue<Node> queue = new LinkedList<>();
        // 用来标记哪些节点一定加入过队列了，不让其再重复加入
        HashSet<Node> visited = new HashSet<>();
        // 从target节点开始宽度优先遍历
        queue.offer(target);
        // 记录target已经进入过队列
        visited.add(target);
        // 记录当前多少层了
        int curLevel = 0;
        // 记录最后的答案
        List<Node> ans = new ArrayList<>();
        // 宽度优先遍历
        while (!queue.isEmpty()) {
            // 将队列中下一个直接关联的并且没有加入到过队列的所有节点都加入到队列中
            int size = queue.size();
            while (size-- > 0) {
                // 弹出队列头
                Node cur = queue.poll();
                // 如果当前到了k了，弹出的节点就是答案
                if (curLevel == K) {
                    ans.add(cur);
                }
                // 将cur直接相连的节点（下一层节点，包括子节点和父节点）都加入到队列中，前提是不能已经加入到过队列中的节点。
                if (cur.left != null && !visited.contains(cur.left)) {
                    visited.add(cur.left);
                    queue.offer(cur.left);
                }
                if (cur.right != null && !visited.contains(cur.right)) {
                    visited.add(cur.right);
                    queue.offer(cur.right);
                }
                if (parents.get(cur) != null && !visited.contains(parents.get(cur))) {
                    visited.add(parents.get(cur));
                    queue.offer(parents.get(cur));
                }
            }
            // 层数增加
            curLevel++;
            // 遍历结束，跳出循环
            if (curLevel > K) {
                break;
            }
        }
        return ans;
    }

    // 构造父节点map表
    public static void createParentMap(Node cur, HashMap<Node, Node> parents) {
        // 直接用递归遍历，将所有节点的父节点都找到，并且添加map中，建立起关联关系。
        if (cur == null) {
            return;
        }
        if (cur.left != null) {
            parents.put(cur.left, cur);
            createParentMap(cur.left, parents);
        }
        if (cur.right != null) {
            parents.put(cur.right, cur);
            createParentMap(cur.right, parents);
        }
    }

    public static void main(String[] args) {
        Node n0 = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        Node n7 = new Node(7);
        Node n8 = new Node(8);

        n3.left = n5;
        n3.right = n1;
        n5.left = n6;
        n5.right = n2;
        n1.left = n0;
        n1.right = n8;
        n2.left = n7;
        n2.right = n4;

        Node root = n3;
        Node target = n5;
        int K = 2;

        List<Node> ans = distanceKNodes(root, target, K);
        for (Node o1 : ans) {
            System.out.println(o1.value);
        }

    }

}
