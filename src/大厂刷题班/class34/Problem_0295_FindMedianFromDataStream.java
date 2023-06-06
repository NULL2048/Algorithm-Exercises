package 大厂刷题班.class34;

import java.util.PriorityQueue;
// 数据结构设计   堆
// https://leetcode.cn/problems/find-median-from-data-stream/
public class Problem_0295_FindMedianFromDataStream {
    class MedianFinder {
        // 大根堆和小根堆
        public PriorityQueue<Integer> maxHeap;
        public PriorityQueue<Integer> minHeap;

        public MedianFinder() {
            this.maxHeap = new PriorityQueue<>((a, b) -> b - a);
            this.minHeap = new PriorityQueue<>((a, b) -> a - b);
        }

        // 通过特定的添加策略，做到了大根堆堆顶和小根堆堆顶正好是所有数中靠中间的两个数，即所有数中较小的一半在大根堆，较大的一半在小根堆。
        public void addNum(int num) {
            // 这里要记得先判断一下大根堆是不是空，如果是空的话说明此时两个堆一定都是空，将这个数直接加入到大根堆中
            // 如果num小于等于大根堆的最大值，就将其加入到大根堆中。添加策略就是要先保证小根堆中的数全都要大于大根堆中的数，也就是要将大于大根堆堆顶的num加入到小根堆中去
            if (maxHeap.isEmpty() || maxHeap.peek() >= num) {
                maxHeap.add(num);
                // 否则加入小根堆
            } else {
                minHeap.add(num);
            }
            // 上面添加完之后，再调平两个堆容量   大根堆数量比小根堆多2，就将大根堆堆顶弹出到小根堆，反之则将小根堆堆顶加入到大根堆中
            // 下面的操作为了调平堆容量，同时仍然保证了小根堆中的数都大于大根堆的数因为会保证小根堆堆顶（小根堆的最小值）大于大根堆堆顶（大根堆的最大值）。每次都是把大根堆最大值加入到小根堆中，或者小根堆的最小值加入到最大值中，所以仍能保证小根堆的数都大于大根堆的数
            if (maxHeap.size() - minHeap.size() >= 2) {
                minHeap.add(maxHeap.poll());
            } else if (minHeap.size() - maxHeap.size() >= 2) {
                maxHeap.add(minHeap.poll());
            }
        }

        public double findMedian() {
            // 两个堆一样多，堆顶相加除以2就是中位数
            if (maxHeap.size() == minHeap.size()) {
                return  (maxHeap.peek() + minHeap.peek()) / 2D;
                // 两个堆不一样多，说明此时数据个数为奇数，那种size较大的那个堆的堆顶就是中位数
            } else {
                return maxHeap.size() > minHeap.size() ? maxHeap.peek() : minHeap.peek();
            }
        }
    }
}
