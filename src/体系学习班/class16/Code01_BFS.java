package 体系学习班.class16;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code01_BFS {
    // 从node出发，进行宽度优先遍历  宽度优先遍历必须要设置上一个起始点
    public static void bfs(Node start) {
        // 为空直接返回
        if (start == null) {
            return;
        }
        // 创建队列和Set
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        // 先将起始点加入队列和Set
        queue.add(start);
        set.add(start);
        // 宽度优先遍历  整体过程和二叉树层序遍历一致，只不过加入了一个Set操作
        while (!queue.isEmpty()) {
            // 弹出队列头
            Node cur = queue.poll();
            // 出队列后打印
            System.out.println(cur.value);
            // 将队列头节点的所有直接邻居尝试加入队列
            for (Node next : cur.nexts) {
                // 在将直接邻居加入队列前，需要先判断set中有没有这个节点，如果有的话说明这个节点已经被遍历过了，不能再重复便利了，就不能加入队列中
                if (!set.contains(next)) {
                    // 如果这个节点还没有在Set中，那么将其加入Set
                    set.add(next);
                    // 将还没有遍历过的直接邻居加入队列
                    queue.add(next);
                }
            }
        }
    }
}

