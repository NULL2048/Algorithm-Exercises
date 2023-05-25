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

        public Node(int x, int h, boolean isAdd) {
            this.x = x;
            this.h = h;
            this.isAdd = isAdd;
        }
    }

    // 比较器，用来将记录x轴上高度变化的数组按x轴从左到右排序，如果存在在同一位置的高度变化，将增加高度的排到前面，防止在后续记录变化次数的Map中出现明明之前没有该高度的增加，却要来一次该高度的减少的不合理的情况。
    public class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            if (o1.x != o2.x) {
                // 按位置从左往右排列大楼高度
                return o1.x - o2.x;
            } else {
                // 相同位置的情况下，增加的放在前面
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
            // 注意Node中标记增长还是减少的标识是有用的，每一个楼都要将其左边界设置为增高该楼层的高度，右边界设置为减少该楼层的高度，因为我们就假设滑过这栋楼的轮廓，从左往右滑动，就是先增高，在降低，因为先滑到大楼，再滑离大楼
            // 增加高度和减少高度的标识，在后面的模拟流程中很重要，因为很多楼是重叠在一起的，我们又需要把所有的楼都划过取最高值，为了不遗漏情况，就需要利用这个增加和减少的标识来把所有的楼都扫描一遍
            // 表示在x轴的buildings[i][0]位置增加到buildings[i][2]高度
            nodes[i * 2] = new Node(buildings[i][0], buildings[i][2], true);
            // 表示在x轴的buildings[i][1]位置减少buildings[i][2]高度，变为0高度
            nodes[i * 2 + 1] = new Node(buildings[i][1], buildings[i][2], false);
        }
        // 将nodes数组按照x轴从左到右排序，这是为了模拟记录高度变化的流程，从左向右记录
        Arrays.sort(nodes, new NodeComparator());
        // 记录在整个高度变化过程中，此时每种高度的增加次数，并且按照高度从小到大排序    【什么要把高度设计成次数的，因为有可能有多个高度并存，删掉一个高度的楼，可能还存在其他的楼也是这个高度，所以要设计为次数的形式】
        // key：增加的高度    value：增加次数       【假设一栋楼的高度为h，那么划过这栋楼就会先带来一次增加h高度，再来一次减少h高度】
        TreeMap<Integer, Integer> heightCntMap = new TreeMap<>();
        // 记录在整个高度变化过程中，每个位置的最大高度，并且按照位置从左到右排序
        // key：位置    value：最大高度        【因为每划过一栋楼都会增加和减少一次该楼层的高度，所以我们每划过一个位置后，还在heightCntMap中剩下的还没有减为0的最大高度，就是划过位置最终的最大高度。还剩下的高度就是在这个位置还存在的楼层的高度】
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
                // 不用判断有没有该高度，此时一定存在该高度，因为相同位置增加的排在减少的前面，在前面一定已经存在一个增加该高度了
                // 如果该高度就剩下1次了
                if (heightCntMap.get(nodes[i].h) == 1) {
                    // 直接将该高度的记录删除，必须要将已经减为0的高度remove掉，否则会影响后面取heightCntMap.lastKey()数据时的准确性，会把已经不存在的高度取出来
                    heightCntMap.remove(nodes[i].h);
                } else {
                    // 该高度剩下不止1次，就将次数-1
                    heightCntMap.put(nodes[i].h, heightCntMap.get(nodes[i].h) - 1);
                }
            }


            // 完成上面的流程，就算是找到了此时nodes[i].x位置上的最大高度了，我们只找heightCntMap中的次数不为0的最大高度（同一个位置可能有重叠的大楼，我们只看最高的），只有最大高度才能形成天际线
            // 每完成一轮处理后，此时heightCntMap中还存在的次数大于0的高度就是当前nodes[i].x位置最终存在的大楼的高度（有的大楼可能到了nodes[i].x位置已经不在），我们取最大值就是能形成的天际线高度
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
            // 当前位置
            int curX = entry.getKey();
            // 当前位置的最大高度
            int curMaxHeight = entry.getValue();
            // 如果当前ans为空（也就是记录第一个位置时会出现这种情况）或者上一x位置的高度和当前位置的高度不同，就说明需要将该位置高度记录下来
            if (ans.isEmpty() || ans.get(ans.size() - 1).get(1) != curMaxHeight) {
                // 当前位置是一个高度变化点，则记录下来
                // asList(curX, curMaxHeight)，将curX, curMaxHeight生成一个List集合
                ans.add(new ArrayList<>(Arrays.asList(curX, curMaxHeight)));
            }

        }
        return ans;
    }
}
