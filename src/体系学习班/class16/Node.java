package 体系学习班.class16;

import java.util.ArrayList;

// 点结构的描述
public class Node {
    // 代表节点的值
    public int value;
    // 入度：有多少个边（节点）指向该节点
    public int in;
    // 出度：该节点指向多少个节点（有多少个指向外部的边）
    public int out;
    // 记录这个节点所有指向的节点（直接邻居）
    public ArrayList<Node> nexts;
    // 记录这个节点所有向外指向的边
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}

