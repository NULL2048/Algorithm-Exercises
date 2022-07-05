package 体系学习班.class16;

import java.util.*;

// 利用入度进行拓扑排序
public class Code03_TopologySort {
    // directed graph and no loop
    public static List<Node> sortedTopology(Graph graph) {
        // key 某个节点   value 剩余的入度   记录每个节点此时的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        // 遍历图中所有节点，将每个节点的入度添加到inMap中，并且找到入度为0的节点，加入到队列中
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        // 拓扑排序结果序列
        List<Node> result = new ArrayList<>();
        // 执行拓扑排序流程，直到零入度队列为空
        while (!zeroInQueue.isEmpty()) {
            // 弹出零入度队列头
            Node cur = zeroInQueue.poll();
            // 将其加入到拓扑排序结果序列
            result.add(cur);
            // 遍历这个节点的所有直接邻居，消除该节点的影响，也就是将所有直接邻居的入度减1
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                // 入度减1之后，将入度为0的节点加入到零入读队列中
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        // 返回拓扑排序结果
        return result;
    }
}
