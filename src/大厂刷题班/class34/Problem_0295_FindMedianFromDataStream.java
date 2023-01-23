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

        public void addNum(int num) {
            // 这里要记得先判断一下大根堆是不是空，如果是空的话说明此时两个堆一定都是空，将这个数直接加入到大根堆中
            if (maxHeap.isEmpty() || maxHeap.peek() >= num) {
                maxHeap.add(num);
            } else {
                minHeap.add(num);
            }

            // 调平两个堆容量
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
