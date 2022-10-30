package 大厂刷题班.class12;

import java.util.HashMap;
import java.util.HashSet;

// 这道题基本思路就是之前讲过的一道题：【一种消息接收并打印的结构设计】
// 数据结构设计  根据数据量猜解法  模拟
// 看这道题的数据量是0 <= nums.length <= 10^5，很明显这道题要求O(N)，因为首先O(N^2)肯定超时，如果是O(logN)那就没必要把数据量设置的10^5这么小，还可以再设置大一点，这样设置有点浪费，所以这道题的正确解法应该是O(N)
// 本题测试链接 : https://leetcode.cn/problems/longest-consecutive-sequence/
public class Code03_LongestConsecutive {
    // 补充一个两张表：头表、尾表。非常好理解的方法
    // 不是最优解，但是好理解
    public int longestConsecutive(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // key记录的是区间开头    value记录的是这个连续区间中有多少个数
        // 连续区间头表
        HashMap<Integer, Integer> headMap = new HashMap<>();
        // 连续区间尾表
        HashMap<Integer, Integer> tailMap = new HashMap<>();
        // 记录已经遍历到哪些数了，相同的数就不同再加入headMap和tailMap了
        HashSet<Integer> visited = new HashSet<>();
        // 开始遍历  先向连续区间尾表加入新的数，然后再去想连续区间头标加新的数，最后如果这个数同时都找到了自己要加入的头表和尾表，说明这个头表和尾表也可以合并，在对两个连续区间进行合并
        for (int i = 0; i < nums.length; i++) {
            // 只取操作第一次遍历到的数，避免重复操作
            if (!visited.contains(nums[i])) {
                // 初始先将这个数加入到头表和尾表
                headMap.put(nums[i], 1);
                tailMap.put(nums[i], 1);
                visited.add(nums[i]);

                // 当前数的前一个数如果已经存在在尾表了，那么就可以将当前数加入到这个连续区间中
                if (tailMap.containsKey(nums[i] - 1)) {
                    // 已存在于尾表中的尾节点key
                    int tailKey = nums[i] - 1;
                    // 已存在于尾表中的尾节点key代表的连续区间长度
                    int tailLen = tailMap.get(tailKey);
                    // 计算tailKey代表的尾表连续区间对应的头表连续区间的头节点key
                    int headKey = tailKey - tailLen + 1;
                    // 该连续区间在头表和为表中长度都是一样的
                    int headLen = tailLen;

                    // 将当前数nums[i]加入到尾表连续区间，长度扩充了1，key也向后移动了一位
                    tailMap.put(nums[i], tailLen + 1);
                    // 并将原有的尾表连续区间对应的key都删除
                    tailMap.remove(tailKey);
                    // 将原尾表连续区间对应的头表连续区间的长度也扩充1，但是头表key不变
                    headMap.put(headKey, headLen + 1);
                    // 移除掉在一开始加入到头表nums[i]这个键值对
                    headMap.remove(nums[i]);
                }

                // 当前数的后一个数如果已经存在在头表了，那么就可以将当前数加入到这个连续区间中
                // 这一步操作还可能涉及到加入到nums[i]后，会将前面的一段连续区间和后面的一段连续区间连起来，就需要将原本两个独立的连续区间合并成一个大的连续区间
                if (headMap.containsKey(nums[i] + 1)) {
                    // 当前nums[i]已经作为尾表中的key了，代表一段连续区间，这段连续区间在前面
                    int tailKey = nums[i];
                    // 计算前面这段连续区间的长度
                    int preLen = tailMap.get(tailKey);
                    // 计算前面这段连续区间对应的头表的key，用来后面的合并操作
                    int preHead = tailKey - preLen + 1;
                    // 记录当前这个数后一个位置，这个位置作为原有头表中连续区间的头节点
                    int headKey = nums[i] + 1;
                    // 计算原有头表连续区间的长度
                    int headLen = headMap.get(headKey);
                    // 记录原头表连续区间对应的尾表的尾节点，用来后面的合并操作
                    int postTail = headKey + headLen - 1;

                    // 最终就是将preHead和postTail代表的两端连续区间合并成一个大的
                    // 将前后两端连续区间合并，这两段连续区间是因为nums[i]而连接起来的
                    headMap.put(preHead, preLen + headLen);
                    tailMap.put(postTail, preLen + headLen);
                    // 移除原头表的连续区间头节点
                    headMap.remove(headKey);
                    // 移除掉在一开始加入到尾表nums[i]这个键值对
                    tailMap.remove(nums[i]);
                }
            }
        }

        // 这里随便找尾表或者头表都可以，遍历找到最大长度的连续区间并返回
        int max = -1;
        for (Integer len : headMap.values()) {
            max = Math.max(max, len);
        }
        return max;
    }


    // 优化以后的写法，只是常熟优化，时间复杂度和上面一样
    // 只不过这个写法只需要用一个HashMap实现，其实就是记录连续区间的头节点，至于连续区间的尾节点直接就用头节点和长度计算出来，不再单独用一个Map记录了
    public static int longestConsecutiv2(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                int all = preLen + posLen + 1;
                map.put(num - preLen, all);
                map.put(num + posLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }
}
