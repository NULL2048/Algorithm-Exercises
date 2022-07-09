package 体系学习班.class16;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Code05_Prim {
    // 对数器，用于小根堆，按照边去权值的递增顺序排序
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    // 返回最小生成树的边集
    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆，按照权值从小到大排序
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        // 记录被解锁的节点
        HashSet<Node> nodeSet = new HashSet<>();

        // 记录要保留的边
        Set<Edge> result = new HashSet<>();
        // 通过循环随便挑了一个点作为起始点
        for (Node node : graph.nodes.values()) {
            // node 是开始点
            // 判断这个点是不是已经被解锁的节点
            if (!nodeSet.contains(node)) {
                // 将该节点解锁并介入到Set中
                nodeSet.add(node);
                // 由一个点，解锁所有与该点直接相连的边
                for (Edge edge : node.edges) {
                    // 将解锁的边加入到小根堆中
                    priorityQueue.add(edge);
                }
                // 找到已经解锁的边中权值最小的边，如果这条边连接的另一个节点是还没有被解锁的，则将该节点解锁，并保留该边
                // 如果这个边连接的两个节点都是已经被解锁的点，就遗弃掉该边
                while (!priorityQueue.isEmpty()) {
                    // 弹出解锁的边中，最小的边
                    Edge edge = priorityQueue.poll();
                    // 获取该边连接到的节点
                    Node toNode = edge.to;
                    // 如果该节点还没有被解锁，则将该节点解锁，并且保留该边
                    if (!nodeSet.contains(toNode)) {
                        // 解锁该节点
                        nodeSet.add(toNode);
                        // 将该边加入到最小生成树结果集中
                        result.add(edge);
                        // 再将新解锁的节点直接连接的边都去进行解锁，加入到小根堆中
                        // 这个循环有一点需要注意一下，这个写法可能会导致已经被放到小根堆中的边，会被重复的又放进去一边，
                        // 但是这个并不会影响最后的结果，只是可能会让效率稍微变慢一点。因为就算是重复放进去了，在前面判断节点是不是已经被唤醒的时候也会被直接跳过
                        // 要避免重复被放进去也很简单，就再引入一个set结构，专门用来存放解锁的边，这样每次到这里要将边添加到小根堆的之前，先判断一下这个边是不是在以前就已经被解锁了，如果是就不要再加入小根堆了，避免了重复加入
                        for (Edge nextEdge : toNode.edges) {
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
            // 这个break也要讲一下，这里在最外层是一个for循环，在一般情况下，如果这个图就是一整张连通的图，而不是多个独立的图的话，这个最外层的for循环只需要执行一次循环就够了
            // 也就是当如果这个图是一个连通的图，并不是一篇森林时，就可以加上这个break，执行完一次循环就跳出for循环即可，因为此时已经完成了找最小生成树，这个循环的目的就是随便找一个起始点而已
            // 但是如果这个图是多个相互独立的图，也就是一个森林的话，只通过一次循环肯定是不够了，就需要完全走完整个for循环才能将多个图的最小生成树都找出来才行，就不能加这个break了
            // break;
        }
        return result;
    }
}
