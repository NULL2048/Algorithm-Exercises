package 体系学习班.class16;

// 将其他类型的图结构描述转换成我们熟悉的图结构描述
public class GraphGenerator {

    // matrix 所有的边
    // N*3 的矩阵
    // [weight, from节点上面的值，to节点上面的值]
    //
    // [ 5 , 0 , 7]
    // [ 3 , 0,  1]
    public static Graph createGraph(int[][] matrix) {
        // 创建图
        Graph graph = new Graph();
        // 遍历数组，构造构造图结构
        for (int i = 0; i < matrix.length; i++) {
            // 拿到每一条边， matrix[i]
            // 权重
            int weight = matrix[i][0];
            // 起始点
            int from = matrix[i][1];
            // 结束点
            int to = matrix[i][2];
            // 看一下结束点和起始点的Node在图结构中是不是已将创建了，如果没有创建则去创建一下对应的Node
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }
            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            // 获取起始点和结束点的Node
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            // 创建新的边
            Edge newEdge = new Edge(weight, fromNode, toNode);
            // 将结束点加入到起始点的点集中
            fromNode.nexts.add(toNode);
            // 增加起始点的出度
            fromNode.out++;
            // 增加结束点的入度
            toNode.in++;
            // 将边加入到起始点的边集中
            fromNode.edges.add(newEdge);
            // 将边加入到图的边集中
            graph.edges.add(newEdge);
        }
        // 返回构建好的图结构
        return graph;
    }

}
