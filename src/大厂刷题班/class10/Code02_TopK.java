package 大厂刷题班.class10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

// 加强堆
// 本题测试链接：https://www.lintcode.com/problem/top-k-frequent-words-ii/
// 以上的代码不要粘贴, 把以下的代码粘贴进java环境编辑器
// 把类名和构造方法名改成TopK, 可以直接通过
public class Code02_TopK {

    // 小根堆数组
    private Node[] heap;
    // 堆中的节点数
    private int heapSize;
    // 词频表   key  abc   value  (abc,7)
    private HashMap<String, Node> strNodeMap;
    // 反向索引表
    private HashMap<Node, Integer> nodeIndexMap;
    // 堆比较器，按照词频比较，如果词频一样，就按照字典序比较
    private NodeHeapComp comp;
    // 小优化，用来实时记录堆中的单词，并且会根据词频进行自动排序。当遍历完所有单词之后，这个TreeSet中存储的就是最终在小根堆中的单子，并且会根据词频来进行排序
    private TreeSet<Node> treeSet;

    public Code02_TopK(int K) {
        heap = new Node[K];
        heapSize = 0;
        strNodeMap = new HashMap<String, Node>();
        nodeIndexMap = new HashMap<Node, Integer>();
        comp = new NodeHeapComp();
        treeSet = new TreeSet<>(new NodeTreeSetComp());
    }

    // 单词信息类
    public static class Node {
        // 单词
        public String str;
        // 词频
        public int times;

        public Node(String s, int t) {
            str = s;
            times = t;
        }
    }

    // 堆比较器
    public static class NodeHeapComp implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.times != o2.times ? (o1.times - o2.times) : (o2.str.compareTo(o1.str));
        }

    }

    // TreeSet比较器
    public static class NodeTreeSetComp implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.times != o2.times ? (o2.times - o1.times) : (o1.str.compareTo(o2.str));
        }

    }

    // 向堆中增加节点
    public void add(String str) {
        if (heap.length == 0) {
            return;
        }
        // str   找到对应节点  curNode
        Node curNode = null;
        // 对应节点  curNode  在堆上的位置
        int preIndex = -1;

        // 如果该单词没有在堆上
        if (!strNodeMap.containsKey(str)) {
            // 为该单词创建Node节点
            curNode = new Node(str, 1);
            // 加入词频表
            strNodeMap.put(str, curNode);
            // 初始化其反向索引表，先将下标赋值为-1，因为有可能这个节点不能加入堆中，只是先暂时在反向索引表中给他占一个位置
            nodeIndexMap.put(curNode, -1);
            // 如果该单词已经在堆上了
        } else {
            // 获取该单词在堆上的节点
            curNode = strNodeMap.get(str);
            // 要在time++之前，先在treeSet中删掉
            // 原因是因为一但times++，curNode在treeSet中的排序就失效了
            // 这种失效会导致整棵treeSet出现问题，TreeSet不能实现动态排序，必须先将其删除，再加入才行。
            if (treeSet.contains(curNode)) {
                treeSet.remove(curNode);
            }
            // 增加词频
            curNode.times++;
            // 通过反向索引表获取该单词在堆上的下标
            preIndex = nodeIndexMap.get(curNode);
        }

        // 该字符串不在堆上
        if (preIndex == -1) {
            // 堆满了
            if (heapSize == heap.length) {
                // 只有当该节点词频超过小根堆堆顶（如果词频一样就比较字典序）才将其加入堆，同时删除堆顶节点。
                if (comp.compare(heap[0], curNode) < 0) {
                    // 从TreeSet中移出堆顶
                    treeSet.remove(heap[0]);
                    // 将该节点加入TreeSet
                    treeSet.add(curNode);
                    // 将原堆顶节点在反向索引表中的下标设置为-1
                    nodeIndexMap.put(heap[0], -1);
                    // 先暂时将该节点放入堆顶位置，设置其反向索引表
                    nodeIndexMap.put(curNode, 0);
                    // 放入堆顶位置
                    heap[0] = curNode;
                    // 自顶向下做heapify调整，维护小根堆结构
                    heapify(0, heapSize);
                }
                // 对没有满
            } else {
                // 将其加入TreeSet
                treeSet.add(curNode);
                // 先暂时将该节点放入堆的最后一个位置，设置其反向索引表
                nodeIndexMap.put(curNode, heapSize);
                // 放入堆的最后一个位置
                heap[heapSize] = curNode;
                // 自底向上做调整，维护小根堆结构
                heapInsert(heapSize++);
            }
            // 该单词在堆上
        } else {
            // 将其加入TreeSet
            treeSet.add(curNode);
            // 从该字符串所在下标preIndex自顶向下做heapify调整，维护小根堆结构
            heapify(preIndex, heapSize);
        }
    }

    // 返回topK的单词。直接从TreeSet中获取
    public List<String> topk() {
        ArrayList<String> ans = new ArrayList<>();
        // 直接从TreeSet中顺序取数，放入List就是答案
        for (Node node : treeSet) {
            ans.add(node.str);
        }
        return ans;
    }

    // 从堆下标index位置向上做heapInsert操作。这是从底部加入新数据后的调整操作
    private void heapInsert(int index) {
        // 将当前节点与他的父结点比较，如果大于父结点就与父结点交换，并且更新index游标的指向
        // 如果index已经交换到树根了，也就是index=0，就会结束循环，完成调整。
        while (index != 0) {
            // 获取index节点的父节点下标
            int parent = (index - 1) / 2;
            // 如果大于父结点就与父结点交换
            if (comp.compare(heap[index], heap[parent]) < 0) {
                swap(parent, index);
                index = parent;
                // 否则说明完成调整，结束循环
            } else {
                break;
            }
        }
    }

    // 从堆下标index位置向下做heapify调整
    private void heapify(int index, int heapSize) {
        // 获取index的左右孩子下标
        int l = index * 2 + 1;
        int r = index * 2 + 2;
        // 记录最小的节点下标
        int smallest = index;
        // 开始进行向下交换  当左节点的下标已经越界了，说明已经交换完成
        while (l < heapSize) {
            // 如果左孩子比index小
            if (comp.compare(heap[l], heap[index]) < 0) {
                smallest = l;
            }
            // 如果右孩子比当前的最小节点小
            if (r < heapSize && comp.compare(heap[r], heap[smallest]) < 0) {
                smallest = r;
            }
            // 如果此时最小节点不是index，则交换这两个位置的节点
            if (smallest != index) {
                swap(smallest, index);
                // 如果index==smallest，说明完成了调整
            } else {
                break;
            }
            // 更新下标
            index = smallest;
            l = index * 2 + 1;
            r = index * 2 + 2;
        }
    }

    // 堆中的节点交换位置
    private void swap(int index1, int index2) {
        // 修改堆数组和反向索引表下标
        nodeIndexMap.put(heap[index1], index2);
        nodeIndexMap.put(heap[index2], index1);
        Node tmp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = tmp;
    }

}
