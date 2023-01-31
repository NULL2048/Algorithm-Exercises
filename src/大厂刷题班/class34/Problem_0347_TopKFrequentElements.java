package 大厂刷题班.class34;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
// 堆   门槛堆（这个在资源限制类题目第六题讲过）   数组  排序  计数
// https://leetcode.cn/problems/top-k-frequent-elements/
public class Problem_0347_TopKFrequentElements {
    // 词频类
    class Node {
        // 数字
        public int num;
        // 该数字在数组中的出现次数
        public int cnt;

        public Node(int num) {
            this.num = num;
            this.cnt = 1;
        }
    }

    // 比较器，用于构建小根堆
    public class CountComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.cnt - o2.cnt;
        }
    }

    public int[] topKFrequent(int[] nums, int k) {
        // 词频表  key:存放数组中的数字    value:存放数组中数字对应的Node
        HashMap<Integer, Node> countMap = new HashMap<>();
        // 遍历数字，构建词频表
        for (int i = 0; i < nums.length; i++) {
            if (countMap.containsKey(nums[i])) {
                countMap.get(nums[i]).cnt++;
            } else {
                countMap.put(nums[i], new Node(nums[i]));
            }
        }

        // 创建小根堆，根据每个数的出现次数排序，出现次数小的在堆上面
        PriorityQueue<Node> heap = new PriorityQueue<>(new CountComparator());
        // 遍历词频表，将每一个数字的Node加入到小根堆中，维持小根堆的大小不能超过k
        // 当小根堆大小为k，如果遍历到的数字的出现次数比堆顶还要大，那么就是找到了一个更好的数字，我们将小根堆堆顶数字弹出，将这个新的数字加入到堆中
        // 这样遍历完之后我们就可以找到数组中词频数在前K名的所有num了
        for (Node node : countMap.values()) {
            // 堆还没有满或者node.cnt小于此时的堆顶cnt，就将该数的node加入到heap中
            if (heap.size() < k || heap.size() == k && node.cnt > heap.peek().cnt) {
                heap.add(node);
            }
            // 如果上一步是加入了一个比堆顶词频大的Node，这里就要将这个最小的堆顶弹出，维持堆的大小不能超过k
            if (heap.size() > k) {
                heap.poll();
            }
        }
        // 将堆中的数据都加入到答案数组中
        int[] ans = new int[k];
        int index = 0;
        while (!heap.isEmpty()) {
            ans[index++] = heap.poll().num;
        }
        // 返回答案
        return ans;
    }
}
