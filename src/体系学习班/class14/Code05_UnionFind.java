package 体系学习班.class14;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code05_UnionFind {
    // 样本类，这里就将每一个集合简化成一个样本value，方便我们在代码中演示并查集的功能
    public static class Node<V> {
        V value;

        public Node(V v) {
            value = v;
        }
    }


    public static class UnionFind<V> {
        // nodes：样本和样本节点对应表，每一个样本V都对应着它的样本Node节点，他们的关系一一对应存储在HashMap中。在初始化建好以后，这个关系永远不会有改动
        public HashMap<V, Node<V>> nodes;
        // parents：一个节点它的父亲节点是谁作为一组记录，用于往上找的行为。在经典的写法中这个HashMap就是记录的每一个节点的直接父节点，但是优化之后，这个HashMap中也可以直接存储节点的最总代表节点，这样减少了分支长度，减少遍历时间复杂度，提高效率
        public HashMap<Node<V>, Node<V>> parents;
        // sizeMap：记录每一个代表节点所代表的所有集合的个数，注意，只有当前存在的代表节点在会在这个HashMap中有记录
        public HashMap<Node<V>, Integer> sizeMap;

        // 并查集初始化
        public UnionFind(List<V> values) {
            // 初始化三个HashMap
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            // 将所有的样本都按顺序添加到HashMap中
            for (V cur : values) {
                // 为每一个样本创建其对应的Node
                Node<V> node = new Node<>(cur);
                // 将样本和其对应的Node存入到样本和样本节点对应表
                nodes.put(cur, node);
                // 将每个节点的直接父节点（最开始就是其代表节点）存入直接父节点表，最开始每一个节点的直接父节点还是指向自己
                parents.put(node, node);
                // 将每一个代表节点和其代表的所有样本集合数量存入HashMap中，最开始每个节点都是一个代表节点，都自己代表自己，所以size都是1
                sizeMap.put(node, 1);
            }
        }

        // 给你一个节点，请你往上导，直到不能再往上，这个节点就是代表节点，将其返回
        // 这里在传统写法的基础上，做了相关优化，将每一条父节点的关系线路做扁平优化，让HashMap中直接记录着每个节点的代表节点，这样就能以O(1)的时间复杂度直接取出代表节点，就不用再沿着链表向上遍历到结尾来找到其代表节点了
        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            // 从点cur开始，一直往上找，找到不能再往上的代表点，并且在这个过程中把沿途的所有节点都放入栈中
            // 这个往上找的过程，就是并查集最大的损耗，也就决定了整个并查集的效率
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            // 当结束了上面的循环，cur就是当前找到的代表节点，而遍历沿途经过的所有结点就都被放到了栈中
            // 下面就是扁平化优化，直接将每一个节点的代表节点存入HashMap中，将结构扁平化，减少遍历带来的损耗
            while (!path.isEmpty()) {
                // 从栈中弹出每一个节点，并且将这个节点的代表节点直接建立起对应关系
                parents.put(path.pop(), cur);
            }
            // 返回找到的代表节点
            return cur;
        }

        // 判断两个样本集合是不是属于同一个集合
        // a的点往上不能再往上的代表点的内存地址等于b的点往上不能再往上的代表点的内存地址，那么a和b就属于一个集合
        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        // 将两个样本集合合并成同一个集合
        public void union(V a, V b) {
            // 找到两个样本的代表节点。
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            // 如果两个样本不属于同一个集合，那么我们去做合并
            if (aHead != bHead) {
                // 获得每一个代表节点代表的样本数量
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                // 找到大的代表节点和小的代表节点
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                // 将小的代表节点挂到大的代表节点下面，也就是将小的代表节点的代表节点指向大的代表节点
                // 这么做是为了降低在向上便利查找的时候，路径遍地更短，因为小的代表节点意味着它下面的节点更少，路径也就更多，这样就提高了并查集的效率
                parents.put(small, big);
                // 更新大的代表节点所代表的样本数量
                sizeMap.put(big, aSetSize + bSetSize);
                // 因为小的代表节点已经被并入到大代表节点中了，它不再是一个代表节点了，所以我们在存储集合大小的HashMap中将其删掉。
                sizeMap.remove(small);
            }
        }

        // 返回所有独立集合的个数
        // 这里就能看出来为什么在两个集合合并之后，要将小集合的size在HashMap中remove
        // 之所以要remove，是为了可以扩充功能，比如返回当前还有多少个集合，就可以直接返回这个Map的size即可。因为有多少个代表节点，就表明有多少个独立集合
        public int sets() {
            return sizeMap.size();
        }

    }
}
