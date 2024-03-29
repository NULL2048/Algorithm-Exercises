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


    // 下面这段代码是我后来自己写的代码，我感觉从代码的流程和注释来看更好理解，更符合我自己的思路
    class Solution {
        public int longestConsecutive(int[] nums) {
            // 过滤无效参数
            if (nums == null || nums.length == 0) {
                return 0;
            }
            // 头表
            HashMap<Integer, Integer> headMap = new HashMap<>();
            // 尾表
            HashMap<Integer, Integer> tailMap = new HashMap<>();
            HashSet<Integer> visited = new HashSet<>();

            for (int num : nums) {
                // 不能重复访问相同的数字
                if (!visited.contains(num)) {
                    // 一开始先不管别的，先把当前遍历到的数字加入到两个Map中
                    headMap.put(num, 1);
                    tailMap.put(num, 1);
                    visited.add(num);

                    // 先从尾表开始处理
                    // 如果尾表中存在num - 1这个数，就说明加入了num之后，就会将连续的数再推高一个长度，也就是尾标开头数字从num开始算起，不用从num-1开始算了
                    if (tailMap.containsKey(num - 1)) {
                        // 先查询原先以num-1结尾的尾表连续长度
                        int lastLen = tailMap.get(num - 1);
                        // 加入num后，就能将这段连续的长度再向前推一个，以后就以num作为结尾了
                        tailMap.put(num, lastLen + 1);
                        // num-1的尾表就没用了，直接移除
                        tailMap.remove(num - 1);


                        // 每修改一次尾标，对应连续数字的头表中的信息也要跟着的修改
                        // 找到原先尾表中num-1对应的头表中的开头key  用下标减去长度再加1，就能得到这一段连续的开头数字
                        int headKey = num - 1 - lastLen + 1;
                        // 将头表中这段连续长度加1，这里不用修改headKey，因为数字加在了尾部，不影响开头
                        headMap.put(headKey, lastLen + 1);
                    }

                    // 再去处理头表，这里还要注意一步操作，就是将之前独立出来的两段连续数字，因为加了num之后，就可以练成一大段长的连续数字的情况，要将这种情况进行合并    这个合并操作是一定要做的，因为只要是进到这个分支，就一定需要合并操作，因为至少我们把num加入到Map中了，前一段的连续数字就是带着num的
                    if (headMap.containsKey(num + 1)) {
                        // 先找前面一段的连续数字，肯定能找到，因为至少我们在一开始加入了num了   找前一段连续数字的时候要带着num
                        // 先找前一段的尾表，以num结尾的
                        int preTailKey = num;
                        // 找这一段尾表的长度，至少也能找出来1，因为在最开始向尾表中加入过num了
                        int preTailLen = tailMap.get(preTailKey);
                        // 再找前面一段的连续数字的头表的开头数字
                        int preHeadKey = preTailKey - preTailLen + 1;
                        // 获取前面一段的头表的长度
                        int preHeadLen = headMap.get(preHeadKey);


                        // 下面再去找后面一段的连续数字
                        // 后面一段的连续数字的头表  一定存在，否则就进不来这个分支了
                        int postHeadKey = num + 1;
                        // 后面一段头表的长度
                        int postHeadLen = headMap.get(postHeadKey);
                        // 再去计算后面一段尾表的结尾数字
                        int postTailKey = postHeadKey + postHeadLen - 1;
                        // 后面一段尾表的长度
                        int postTailLen = tailMap.get(postTailKey);


                        // 因为加入了num，就将前后两段连续数字连成一大段了
                        // 用前一段的头表的key来修改成大段的连续数字   也就是整个连续数字的最开头的数
                        headMap.put(preHeadKey, preHeadLen + postHeadLen);
                        // 用后一段的尾表的key来修改成大段地连续数字    也就是整个连续数字的最结尾的数
                        tailMap.put(postTailKey, preHeadLen + postHeadLen);


                        // 前面一段的尾表开头和后面一段的头表开头也就没用了，直接移除
                        tailMap.remove(preTailKey);
                        headMap.remove(postHeadKey);


                    }
                }
            }

            // 这里随便找尾表或者头表都可以，遍历找到最大长度的连续区间并返回
            int ans = -1;
            for (Integer len : headMap.values()) {
                ans = Math.max(ans, len);
            }

            return ans;
        }
    }
}
