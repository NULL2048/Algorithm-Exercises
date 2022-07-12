package 体系学习班.class16;

import java.util.HashMap;

public class Code06_Dijkstra_Pro {
    // 节点和其权值的对应关系表，这个是用来在弹出栈顶元素时，能供直接通过一个对象弹出节点和其权值两个信息
    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    // 专门为优化迪杰斯特拉算法特制的加强堆
    public static class NodeHeap {
        // 实际的堆结构
        private Node[] nodes;
        // key：某一个node， value：上面堆中的位置   也就的加强堆上的反向索引表
        private HashMap<Node, Integer> heapIndexMap;
        // key：某一个节点， value：从源节点出发到该节点的目前最小距离    这里如果一个节点从堆中弹出了，就将其distanceMap对应的权值设置为-1，表示该节点已经完成了计算，用来区分还从来没有进过堆的节点
        private HashMap<Node, Integer> distanceMap;
        // 堆上有多少个点
        private int size;

        // 初始化堆
        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        // 当前堆是否为空
        public boolean isEmpty() {
            return size == 0;
        }

        // 传入一个节点node，现在发现了一个从源节点出发到达node的距离为distance
        // 判断该距离是不是比堆中存储的距离更小，小则更新，
        // 不小则不更新，直接忽略
        // 如果堆中还没有加入过这个节点，则直接将这个节点和其权值加入堆中
        public void addOrUpdateOrIgnore(Node node, int distance) {
            // 如果这个节点在堆中
            if (inHeap(node)) {
                // 比较传入权值和堆中权值大小，如果传入的权值更小，则更新堆中记录的权值
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                // 记录完之后，向上维护堆结构。因为是小根堆，并且值肯定只会往小里修改，所以向上做维护即可。
                insertHeapify(heapIndexMap.get(node));
            }
            // 如果这个节点从来没进入过堆，则直接将其加入到最终
            if (!isEntered(node)) {
                // 将该节点和该节点的权值加入到堆中
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                // 也是向上做堆结构维护，并增加堆中节点数
                insertHeapify(size++);
            }
        }

        // 弹出堆顶节点
        public NodeRecord pop() {
            // 为弹出堆顶的节点创建NodeRecord，用来建立节点和其最终权值的对应关系
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            // 弹出之后需要重新维护堆结构
            // 将堆最后一个位置的节点替换到堆顶
            swap(0, size - 1);
            // 将弹出节点在heapIndexMap记录的权值设置为-1，表示其已经计算完成
            heapIndexMap.put(nodes[size - 1], -1);
            // 将弹出节点移除
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            // 将堆中最后一个位置的节点置为空，因为最后一个位置的节点已经替换到堆顶了
            nodes[size - 1] = null;
            // 向下进行结构维护
            heapify(0, --size);
            // 弹出
            return nodeRecord;
        }

        // 向上进行堆结构维护，这个方法和下面的方法都在加强堆中讲过了
        private void insertHeapify(int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 向下进行堆结构维护
        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        // 判断节点是在堆中存在过
        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        // 判断节点当前是否在堆中
        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        // 交换数组中两个位置的元素
        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
        }
    }

    // 改进后的dijkstra算法
    // 从head出发，所有head能到达的节点，生成到达每个节点的最小路径记录并返回
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        // 创建小根堆
        NodeHeap nodeHeap = new NodeHeap(size);
        // 将起始点的信息加入到小根堆中，起始点到起始点的距离就是0
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        // 用来记录最后每个节点的计算结果
        HashMap<Node, Integer> result = new HashMap<>();
        // 循环计算每一个节点，直到小根堆为空，说明所有的节点都计算结束了
        while (!nodeHeap.isEmpty()) {
            // 弹出堆顶节点，该节点是当前剩余可见节点的权值最小节点   弹出的是记录节点和其权值的的对象
            NodeRecord record = nodeHeap.pop();
            // 获取弹出节点
            Node cur = record.node;
            // 获取弹出节点的权值
            int distance = record.distance;
            // 遍历该节点的所有直接连通的边
            // 尝试去添加/更新边直接连通的节点在小根堆中记录的权值，这里传入的edge.to就是边指向的节点
            // 传入的edge.weight + distance就是起始点利用堆中弹出节点作为过渡节点，然后再去连上edge.to这个节点所有的总权值
            // 该方法中会将这个总权值和小根堆中存储的权值比较，如果比小根堆中的权值小，则更新小根堆中的权值，并且重新维护小根堆结构
            // 如果没有比小根堆中的权值小，则不更新，直接忽略
            // 如果小根堆中还没有这个节点的权值，并且该节点还没有被计算完成，则直接将该值添加进小根堆中，并且维护小根堆结构
            for (Edge edge : cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            // 将弹出节点的权值加入到结果对象中，完成对该节点的计算。
            result.put(cur, distance);
        }
        // 返回结果
        return result;
    }

}
