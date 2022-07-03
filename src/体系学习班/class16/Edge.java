package 体系学习班.class16;

// 边的描述
public class Edge {
    // 边的权重
    public int weight;
    // 边的起始点
    public Node from;
    // 边的结束点
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}