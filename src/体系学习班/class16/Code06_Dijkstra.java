package 体系学习班.class16;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;


public class Code06_Dijkstra {
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        // 创建Map用来记录起始点from到途中所有点的距离
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        // 在一开始先记录起始点from到from的距离为0，存入Map中
        distanceMap.put(from, 0);
        // 该结构用来存储打过对号的点
        HashSet<Node> selectedNodes = new HashSet<>();
        // 找到除了打对勾的点以外，起始点到其距离最短的Node    这个点效率很低，因为就是一个暴力遍历，所以后续我们要针对这个方法使用加强堆进行优化
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        // 循环，直到已经取不出来最小的Node，也就是所有的点都打对号为止
        while (minNode != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = distanceMap.get(minNode);
            // 遍历与当前取出来的节点minNode直接相连的边
            for (Edge edge : minNode.edges) {
                // 找到直接相连的节点
                Node toNode = edge.to;
                // 如果toNode节点在distanceMap没有，说明此时记录中还是正无穷，直接将当前取出来的边的权值写入Map
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                    // 如果当前记录用已经有到toNode的距离了，那么就看一下如果起始点经过当前跳转点minNode到达toNode的权值和是不是小于之前记录的权值和，如果小于则更新
                } else { // toNode
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            // 将minNode打勾
            selectedNodes.add(minNode);
            // 再从剩余的节点中找到最小权值的点
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        // 返回结果
        return distanceMap;
    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
        // 记录权值最小节点
        Node minNode = null;
        // 记录最小权值
        int minDistance = Integer.MAX_VALUE;
        // 找到distanceMap所有节点中，不在touchedNodes中的节点，并且起始点到其权值最小的节点
        // 就是一个简单的暴力
        for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            // 找到最小权值节点
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }
}

