package 体系学习班.class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting
public class Code03_TopologicalOrderDFS1 {
    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的代码

    // 记录点次的类
    public static class Record {
        // 记录图节点
        private DirectedGraphNode node;
        // 记录该节点的的点次：点次就是从该节点开始，按拓扑序向后遍历，能够遍历到几次节点，包括自己
        private long cnt;

        public Record (DirectedGraphNode node, long cnt){
            this.node = node;
            this.cnt = cnt;
        }
    }


    // 比较器，按照点次降序排列
    public static class MyComparator implements Comparator<Record> {
        @Override
        public int compare(Record r1, Record r2) {
            return r1.cnt == r2.cnt ? 0 : (r1.cnt > r2.cnt ? -1 : 1);
        }
    }

    /**
     * @param graph: A list of Directed graph node   传入的参数其实就是一个图的邻接表
     * @return: Any topological order for the given graph.
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // 创建点次结果类
        HashMap<DirectedGraphNode, Record> cntMap = new HashMap<>();
        // 遍历图的邻接表，计算每一个节点的点次，并且存入到点次结果类中
        for (DirectedGraphNode node : graph) {
            Record ans = count(node, cntMap);
            cntMap.put(node, ans);
        }

        // 创建点次List，将节点按照点次降序排序。这样就能得到节点按照点次降序的排序关系了，这里也能看出来创建Record类的用处，可以建立起点次和节点的对应关系，方便我们后续处理
        ArrayList<Record> recordList = new ArrayList<>();
        for (Record r : cntMap.values()) {
            recordList.add(r);
        }
        recordList.sort(new MyComparator());

        // 这里将按点次降序排列后的节点顺序加入到最终的拓扑序List中。
        ArrayList<DirectedGraphNode> topSortList = new ArrayList<>();
        for (Record r : recordList) {
            topSortList.add(r.node);
        }

        // 返回拓扑排序结果
        return topSortList;
    }

    // 返回传入节点的点次。这里就用到了动态规划的思想，记忆化搜索，不重复计算，已经有结果的话就直接拿现成的结果
    // 其实这个流程就是一个深度优先搜索的思想
    public Record count(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> cntMap) {
        // 如果在cntMap中已经存有该节点的点次了，直接返回，而不是重复计算
        if (cntMap.containsKey(cur)) {
            return cntMap.get(cur);
        }
        // 如果还没有该节点的点次，则计算一遍
        long cnt = 1; // 自己也要算一次点次，这是为了能够在后续累加的时候保证数据准确性
        // 将该节点的所有直接邻居遍历，然后去获取每一个直接邻居的点次（这个点次已经包括直接邻居本身了），累加起来就是当前节点的点次
        for (DirectedGraphNode neighbor : cur.neighbors) {
            // 递归调用count()方法得到直接邻居的点次
            cnt += count(neighbor, cntMap).cnt;
        }

        // 创建点次类
        Record ans = new Record(cur, cnt);
        // 将点次对象加入到结果缓存中，这样以后如果再用到该节点的点次，直接取就行了
        cntMap.put(cur, ans);

        // 返回点次结果
        return ans;
    }
}
