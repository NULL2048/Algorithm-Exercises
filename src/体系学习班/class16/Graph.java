package 体系学习班.class16;

import java.util.HashMap;
import java.util.HashSet;

// 图结构
public class Graph {
    // 点集：记录图中所有的节点
    public HashMap<Integer, Node> nodes;
    // 边集：记录途中所有的边
    public HashSet<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
