package 体系学习班.class16;

import java.util.*;

public class Code04_Kruskal {
    // 构造并查集，利用并查集来检查保留某条边是否会形成环
    // 每一个集合中的所有节点都是可以相互连通的，如果将一个节点加入一个集合中，但是这个集合已经存在该节点了，那么就会造成环路
    public static class UnionFind {
        // key：某一个节点， value：key节点的父节点
        private HashMap<Node, Node> fatherMap;
        // key：某一个集合的代表节点, value：key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;
        public UnionFind() {
            fatherMap = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        // 初始化并查集，将每一个节点都作为一个独立集合进行初始化
        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }
        // 找到代表节点
        private Node findFather(Node n) {
            Stack<Node> path = new Stack<>();
            // 找到代表界节点
            while(n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }
            // 路径压缩
            while(!path.isEmpty()) {
                fatherMap.put(path.pop(), n);
            }
            return n;
        }
        // 判断两个样本是不是处在同一个集合中
        public boolean isSameSet(Node a, Node b) {
            // 如果两个样本的代表节点一样，就说明两个样本在同一个节点中
            return findFather(a) == findFather(b);
        }
        // 合并操作
        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            // 找到代表节点
            Node aDai = findFather(a);
            Node bDai = findFather(b);
            // 两个节点不在同一个集合中，则对这两个集合进行合并操作
            if (aDai != bDai) {
                // 小集合并入大集合
                int aSetSize = sizeMap.get(aDai);
                int bSetSize = sizeMap.get(bDai);
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aDai, bDai);
                    sizeMap.put(bDai, aSetSize + bSetSize);
                    sizeMap.remove(aDai);
                } else {
                    fatherMap.put(bDai, aDai);
                    sizeMap.put(aDai, aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }
            }
        }
    }

    // 比较器，按边的权值由小到大排序
    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    // Kruskal算法
    public static Set<Edge> kruskalMST(Graph graph) {
        // 创建并查集
        UnionFind unionFind = new UnionFind();
        // 初始化并查集，将图中存储所有节点的HashMap传入，将所有节点都作为一个独立的集合进行初始化
        unionFind.makeSets(graph.nodes.values());
        // 从小的边到大的边，依次弹出，小根堆！
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        // 将图中所有的边加入到小根堆中，按权值从小到大排序
        for (Edge edge : graph.edges) { // M 条边
            priorityQueue.add(edge);  // O(logM)
        }
        // 保存要保留的边
        Set<Edge> result = new HashSet<>();
        // 按照边的权值从小到大的顺序，依次判断当前边要不要保留
        // 判断的原则就是看这个边连通的两个节点是不是在同一个集合中的，如果已经在同一个集合中了，那么保留该边就会造成环路，所以应该遗弃该边
        // 如果不在一个集合中，就不会造成环路，就可以保留该边
        while (!priorityQueue.isEmpty()) { // M 条边
            Edge edge = priorityQueue.poll(); // O(logM)
            // 判断两个点是不是在一个集合中
            if (!unionFind.isSameSet(edge.from, edge.to)) { // O(1)
                // 不在一个集合中就保留该边
                result.add(edge);
                // 并且将这两个节点合并到同一个集合中
                unionFind.union(edge.from, edge.to);
            }
        }
        // 返回要保留的边集
        return result;
    }
}
