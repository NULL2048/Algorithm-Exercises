package 大厂刷题班.class04;

import java.util.*;

// 本题测试链接 : https://leetcode.cn/problems/the-skyline-problem/
public class Code08_TheSkylineProblem {
    // 将题目中输入的每个范围的高度，转化成记录x轴上高度变化的数据Node
    public class Node {
        // 记录当前是在x轴的什么位置
        private int x;
        // 当前位置要变化到的高度
        private int h;
        // 当前位置是增加到h高度还是降低h高度，true表示增加
        boolean isAdd;

        public Node (int x, int h, boolean isAdd) {
            this.x = x;
            this.h = h;
            this.isAdd = isAdd;
        }
    }

    // 比较器，用来将记录x后上高度变化的数组按x轴从左到右排序，如果存在在同一位置的高度变化，将增加高度的排到前面，防止在后续记录变化次数的Map中出现明明之前没有该高度的增加，却要来一次该高度的减少的不合理的情况。
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                return o1.x - o2.x;
            } else {
                return o1.isAdd ? -1 : 1;
            }

        }
    }

    // buildings[i][0]  buildings[i][1]  buildings[i][2]  表示：
    // 在buildings[i][0] ~ buildings[i][1]范围之间，有高度为buildings[i][2]的大楼
    public List<List<Integer>> getSkyline(int[][] buildings) {
        // 将原本题目输入的在每个范围上的高度转换成每个位置的高度变化数组nodes
        // 也就是每一个范围数据会产生两个node，一个在左边界增加高度，一个在右边界减少高度
        Node[] nodes = new Node[buildings.length * 2];
        // 下面这个写法就可以将每一个位置的增加和减少都记录到nodes数组中
        for (int i = 0; i < buildings.length; i++) {
            // 表示在x轴的buildings[i][0]位置增加到buildings[i][2]高度
            nodes[i * 2] = new Node(buildings[i][0], buildings[i][2], true);
            // 表示在x轴的buildings[i][1]位置减少buildings[i][2]高度，变为0高度
            nodes[i * 2 + 1] = new Node(buildings[i][1], buildings[i][2], false);
        }

        // 将nodes数组按照x轴从左到右排序，这是为了模拟记录高度变化的流程，从左向右记录
        Arrays.sort(nodes, new NodeComparator());

        // 记录在整个高度变化过程中，此时每种高度的增加次数，并且按照高度从小到大排序
        // key：增加的高度    value：增加次数
        TreeMap<Integer, Integer> heightCntMap = new TreeMap<>();
        // 记录在整个高度变化过程中，每个位置的最大高度，并且按照位置从左到右排序
        // key：位置    value：最大高度
        TreeMap<Integer, Integer> xMostHeightMap = new TreeMap<>();
        // 开始从左到右遍历全部的高度变化过程，进而统计记录每一个位置的最大高度
        // 必须从左到右统计，这样才能把每一个位置的情况全部统计全，找到最大高度
        for (int i = 0; i < buildings.length * 2; i++) {
            // 如果当前是增加高度
            if (nodes[i].isAdd) {
                // 检查改高度是不是在前面已经增加过了，存在对应的map记录
                if (heightCntMap.containsKey(nodes[i].h)) {
                    // 将该高度的增加次数+1
                    heightCntMap.put(nodes[i].h, heightCntMap.get(nodes[i].h) + 1);
                } else {
                    // 如果是第一次增加到该高度，就put新的数据，次数为1
                    heightCntMap.put(nodes[i].h, 1);
                }
                // 如果当前是减少高度
            } else {
                // 如果该高度就剩下1次了
                if (heightCntMap.get(nodes[i].h) == 1) {
                    // 直接将该高度的记录删除
                    heightCntMap.remove(nodes[i].h);
                } else {
                    // 该高度剩下不止1次，就将次数-1
                    heightCntMap.put(nodes[i].h, heightCntMap.get(nodes[i].h) - 1);
                }
            }

            // 完成上面的流程，就算是找到了此时nodes[i].x位置上的最大高度了，我们只找heightCntMap中的次数不为0的最大高度（同一个位置可能有重叠的大楼，我们只看最高的），只有最大高度才能形成天际线

            // 如果此时记录高度的次数的Map为空，就说明当前位置不存在高度了，直接记为0
            if (heightCntMap.isEmpty()) {
                xMostHeightMap.put(nodes[i].x, 0);
                // 如果此时记录高度的次数的Map不为空，就说明当前位置还存在高度
            } else {
                // heightCntMap.lastKey()取最后一个位置的key，也就是最大的key，也就是最大高度
                xMostHeightMap.put(nodes[i].x, heightCntMap.lastKey());
            }
        }

        // 生成答案数组  记录每一次高度变化的x轴位置和对应的高度
        List<List<Integer>> ans = new ArrayList<>();
        // 遍历每一个x轴的最大高度，形成ans
        for (Map.Entry<Integer, Integer> entry : xMostHeightMap.entrySet()) {
            int curX = entry.getKey();
            int curMaxHeight = entry.getValue();
            // 如果当前ans为空或者上一x位置的高度和当前位置的高度不同，就说明需要将该位置高度记录下来
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
                // 当前位置是一个高度变化点，记录下来
                // asList(curX, curMaxHeight)，将curX, curMaxHeight生成一个List集合
                ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
            }

        }
        return ans;
    }
}
