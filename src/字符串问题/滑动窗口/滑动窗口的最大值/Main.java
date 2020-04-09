package 字符串问题.滑动窗口.滑动窗口的最大值;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println(maxSlidingWindow(nums, 3));
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
         /*
            思路：用双端队列实现
        */

         // 存储结果
        int[] res = new int[nums.length - k + 1];
        // 结果数组指针
        int index = 0;
        if (nums == null || nums.length < 1 || k <= 0 || k > nums.length) {
            return new int[0];
        }

        // 在队列头部的表示的就是当前滑动窗口的最大值
        // 滑动窗口的思路一般就是要用一个数据结构来作为滑动窗口，这里就用的一个双端链表作为滑动窗口
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            //超出范围的去掉 把所有超过滑动窗口范围的删除  实现这个功能就需要我们在队列中存储的不是真实的值，而是这个值在nums数组中的下标，通过下标来判断是不是超出了范围
            while (!queue.isEmpty() && queue.peek() < i - k + 1) {
                queue.poll();
            }

            //当前值大于之前的值，之前的不可能是最大值，可以删掉   用循环就是要把所有比当前值小的数删掉，如果当前值比队列头的数还要大，那么肯定可以能循环到将队列头删除
            while (!queue.isEmpty() && nums[i] >= nums[queue.getLast()]) {
                queue.removeLast();
            }

            // 将所有可能称为滑动窗口最大值的追加到队列中
            queue.add(i);

            // 此时开始是第一个滑动窗口，将最大值加入到结果数组中
            if (i >= k - 1) {
                res[index++] = nums[queue.peek()];
            }
        }
        return res;
    }
}
