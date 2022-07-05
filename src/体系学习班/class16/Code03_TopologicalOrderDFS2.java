package 体系学习班.class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderDFS2 {
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的
    public static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode n, int o) {
            node = n;
            deep = o;
        }
    }

    // 按照最大深度降序排列
    public static class MyComparator implements Comparator<Record> {
        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 遍历每一个节点，计算每一个节点的最大深度并存入Map中
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }
        // 将得到的节点的最大深度信息和节点的对应关系信息放入到List中
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }
        // 根据最大神父降序排列
        recordArr.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
        // 排列后的结果，就是拓扑排序结果
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    // 深度优先遍历求每个节点的最大深度
    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        // 如果该节点的最大深度已经求出来了，直接取出来用。
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        // 记录最大深度
        int follow = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            // 遍历当前节点的所有直接邻居，这个节点的最大深度就是这列直接邻居的中深度最大的再加1
            follow = Math.max(follow, f(next, order).deep);
        }
        // 写入记录节点最大深度的缓存Map中
        Record ans = new Record(cur, follow + 1);
        order.put(cur, ans);
        // 返回结果
        return ans;
    }
}
