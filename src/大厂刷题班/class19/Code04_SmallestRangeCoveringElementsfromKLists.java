package 大厂刷题班.class19;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

// 贪心   辅助数据结构：有序表
// 谷歌二面第二题
// 本题测试链接 : https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/
public class Code04_SmallestRangeCoveringElementsfromKLists {
    class Node {
        // 值大小
        public int value;
        // 该数来自哪个数组，记录数组编号
        public int arrid;
        // 该数来自数组的哪个下标位置
        public int index;

        public Node(int value, int arrid, int index) {
            this.value = value;
            this.arrid = arrid;
            this.index = index;
        }
    }

    // 有序表按照值得递增顺序排序，如果两个值相等，谁的数组编号小，谁就放在前面
    class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
        }
    }

    public int[] smallestRange(List<List<Integer>> nums) {
        // 创建有序表
        TreeSet<Node> ansSet = new TreeSet<>(new NodeComparator());

        // 一开始先将所有数组的第一个数加入到有序表中
        for (int i = 0; i < nums.size(); i++) {
            ansSet.add(new Node(nums.get(i).get(0), i, 0));
        }

        // 记录有序表最小的节点和最大的节点
        Node start = ansSet.first();
        Node end = ansSet.last();
        // 记录当前找到的符合条件的区间最窄的区间左右边界
        int min = start.value;
        int max = end.value;

        // 当我们想要删掉一个数据时，发现这个数据所在的数组已经没有别的数可以用来补充到有序表了，那么整个流程就不用再继续了，以为后面就无法满足每一个数组至少有一个数在有序表了
        while (start.index != nums.get(start.arrid).size() - 1) {
            // 弹出有序表中最小的数
            ansSet.pollFirst();
            // 将弹出的数所在数组的下一个位置的数加入到有序表中，保证这个数组有一个数在有序表中
            ansSet.add(new Node(nums.get(start.arrid).get(start.index + 1), start.arrid, start.index + 1));

            // 记录此时有序表的区间范围
            start = ansSet.first();
            end = ansSet.last();

            // 如果此时的区间比之前记录的更窄，就更新答案
            if (end.value - start.value < max - min) {
                max = end.value;
                min = start.value;
                // 如果此时的区间等于之前记录的区间，就看是否当前的区间起始数是不是比以前的起始数小，如果小就更新答案
            } else if (end.value - start.value == max - min) {
                if (start.value < min) {
                    max = end.value;
                    min = start.value;
                }
            }
        }

        // 返回符合条件的最窄区间
        return new int[] {min, max};
    }
}
