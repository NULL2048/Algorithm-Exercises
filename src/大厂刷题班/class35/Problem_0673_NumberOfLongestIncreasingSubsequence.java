package 大厂刷题班.class35;
import java.util.ArrayList;
import java.util.TreeMap;
// 前置知识   最长递增子序列  在大厂刷题班第九节课讲的
// 动态规划   辅助数组  二分
// 这道题确实非常难，实在不行就再听一遍课
// https://leetcode.cn/problems/number-of-longest-increasing-subsequence/
public class Problem_0673_NumberOfLongestIncreasingSubsequence {
    // 好理解的方法，时间复杂度O(N^2)
    public static int findNumberOfLIS1(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        // lens[i]：以nums[i]结尾的最长递增子序列长度
        int[] lens = new int[n];
        // cnts[i]：以nums[i]结尾的最长递增子序列的总个数
        int[] cnts = new int[n];
        // 初始化，一开始遍历到nums[0]，以nums[0]结尾的最长递增子序列至少长度就是1，也就是只有nums[0]本身
        lens[0] = 1;
        // 初始化，以nums[0]结尾的最长递增子序列的个数最少有1个
        cnts[0] = 1;
        // 当前的最大递增子序列长度，初始化为1
        int maxLen = 1;
        // 当前的最大递增子序列的总个数，初始化为1
        int allCnt = 1;
        // 开始从1下标遍历nums数组
        for (int i = 1; i < n; i++) {
            // 先来解释一下preLen和preCnt这两个变量的含义
            // 假设nums[i]追加到以nums[j]结尾的递增子序列后面后，能够形成长度最大的以nums[i]结尾的递增子序列
            // 那么以nums[j]结尾的最长递增子序列长度就是preLen，以及以nums[j]结尾的最长递增子序列的个数是preCnt
            // 所以preLen表示的是能和nums[i]组成递增子序列的最长递增子序列的长度，初始化为0
            int preLen = 0;
            // preCnt表示的是能和nums[i]组成递增子序列的最长递增子序列的总数量，初始化为1
            int preCnt = 1;
            // 当前已经遍历完nums数组的0~i-1下标了，即已经收集完当前的lens[0~i-1]和cnts[0~i-1]的数据了
            // 从已经遍历完的数据中找能够与新遍历到的nums[i]这个数组成递增子序列的数据
            // 我们的目的就是找到以nums[i]结尾的最大递增子序列长度和个数，即构造lens[i]和cnts[i]
            for (int j = 0; j < i; j++) {
                // 如果nums[i]都不大于nums[j]，那么它就不可能与以nums[j]结尾的递增子序列组成一个新的递增子序列，这种情况直接跳过
                // 如果之前已经找到的能够和nums[i]组成递增子序列的最长递增子序列长度preLen都大于以nums[j]结尾的最长递增子序列长度，
                // 那么nums[i]与以nums[j]结尾的递增子序列组合出来的递增子序列已经也不会超过之前找到的preLen+1这个长度，也就没有必要去看这个情况了
                if (nums[j] >= nums[i] || preLen > lens[j]) {
                    continue;
                }
                // 执行到这里，就能保证nums[i] > nums[j]，所以nums[i]一定可以追加到以nums[j]结尾的递增子序列的后面组成新的递增子序列，
                // 并且因为此时一定是preLen <= lens[j]，所以也一定能保证组合出来的递增子序列已经是当前找到的最长的
                // 如果此时lens[j]大于preLen，那么说明和以nums[j]结尾的递增子序列组合出来的新子序列长度已经超过了之前找到的，所以更新preLen和preCnt
                if (preLen < lens[j]) {
                    // 当前nums[i]追加到以nums[j]结尾的最长递增子序列后面能组合出一个新的以nums[i]结尾的最长的递增子序列
                    // 记录以nums[j]为结尾的最长递增子序列长度
                    preLen = lens[j];
                    // 记录以nums[j]为结尾的最长递增子序列的个数
                    preCnt = cnts[j];
                    // 如果preLen == lens[j]，就说明找到了一种新的子序列与nums[i]能组合出preLen+1长度的递增子序列
                } else {
                    // 直接将cnts[j]累加到preCnt上
                    preCnt += cnts[j];
                }
            }
            // 填写以nums[i]结尾的信息
            // 目前以nums[i]结尾的递增子序列最长为preLen+1，nums[j]结尾的最长公共子序列长度加1
            lens[i] = preLen + 1;
            // 目前以nums[i]结尾的最长递增子序列有preCnt个，直接复用nums[j]结尾的最长公共子序列个数
            cnts[i] = preCnt;
            // 更新maxLen和allCnt
            if (maxLen < lens[i]) {
                // 用新的统计数据
                maxLen = lens[i];
                allCnt = cnts[i];
                // 如果最长长度相等，就累加个数
            } else if (maxLen == lens[i]) {
                allCnt += cnts[i];
            }
        }
        // 返回答案
        return allCnt;
    }



    // 优化后的最优解，时间复杂度O(N*logN)
    public int findNumberOfLIS(int[] nums) {
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 设置dp，里面存储了所有长度的递增子序列中以每一个nums[i]为结尾的子序列个数分别有多少个
        // dp.get(i)：存储长度为i的递增子序列信息
        // dp.get(i).(key, value)：表示以大于等于key结尾的长度为i的递增子序列的个数为value个
        ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
        // 遍历nums
        for (int num : nums) {
            // 记录以num结尾的递增子序列的最大长度
            int len = 0;
            // 记录以num结尾的最大递增子序列的个数
            int cnt = 0;

            // 1、找到以num结尾的最长递增子序列的长度
            // 用二分法找到dp中找到一个长度最小的无法和num构成递增子序列的长度，即找到一种长度的最小结尾数是大于等于num的，并且这个长度是满足这个条件中最小的长度
            len = search(dp, num);

            // 2、计算以num结尾的最长递增子序列的个数
            // 如果len == 0，说明此时num比所有的数都小，无法和他们组成递增子序列，只能自己组成一个子序列，个数为1个
            if (len == 0) {
                cnt = 1;
                // num可以和以前的子序列组成更长的递增子序列
            } else {
                // 获取这个可以和num组成递增子序列的最长递增子序列信息
                TreeMap<Integer, Integer> treeMap = dp.get(len - 1);
                // 以num结尾的最长递增子序列个数就是长度为len-1的递增子序列中所有结尾数小于num的个数总和
                // treeMap.firstEntry().getValue()：长度为len-1的递增子序列总数
                // treeMap.ceilingEntry(num).getValue()：长度为len-1的递增子序列中结尾值大于num的子序列总数
                // 上面两个数相减得到的就是长度为len-1的递增子序列中所有结尾数小于num的个数总和
                cnt = treeMap.firstEntry().getValue() - (treeMap.ceilingEntry(num) == null ? 0 : treeMap.ceilingEntry(num).getValue());
            }

            // 3、将计算得到的len和cnt加入到dp中
            // 如果len == dp.size()，说明以num结尾的最长递增子序列已经推高了当前找到的最长递增子序列长度，需要新开一个TreeMap来存储
            if (len == dp.size()) {
                dp.add(new TreeMap<Integer, Integer>());
                // 将以num结尾的最长递增子序列数据加入到dp中
                dp.get(len).put(num, cnt);
                // 服用以前已有的TreeMap来存储以num结尾的最长递增子序列数据
            } else {
                // 这里要注意，len长度的递增子序列中，此时num一定是最小结尾
                // 因为前面通过二分法找到的len长度是长度最小的无法和num构成递增子序列的长度，即len长度的递增子序列原本的最小结尾数是大于num的，
                // 所以num的加入一定会作为len长度的递增子序列中新的最小结尾数
                // 所以在计算长度等于len的递增子序列中结尾数大于等于num的个数时，就直接用dp.get(len).firstEntry().getValue() + cnt
                // dp.get(len).firstEntry().getValue():原本就在TreeMap中结尾数大于等于num的总数，它也是原来的长度为len的递增子序列的总个数
                dp.get(len).put(num, dp.get(len).firstEntry().getValue() + cnt);
            }
        }
        // 直接返回dp中最大长度的递增子序列总个数即可
        // 最大长度：dp.size() - 1
        // 递增子序列总个数：dp.get(dp.size() - 1).firstEntry().getValue()
        return dp.get(dp.size() - 1).firstEntry().getValue();
    }

    // 二分查找，返回TreeMap.firstEntry().getKey()>=num最左的位置
    public int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
        int l = 0;
        int r = dp.size() - 1;
        // 如果最后返回dp.size()就说明此时dp中没有比num大的
        int ans = dp.size();
        while (l <= r) {
            int m = (l + r) >> 1;
            // TreeMap.firstEntry()就是当前这个长度的递增子序列中结尾数最小的子序列
            if (dp.get(m).firstEntry().getKey() >= num) {
                // 记录大于等于num的答案
                ans = m;
                r = m - 1;
            } else {
                // 如果一直执行这个分支就会返回0，说明num比当前所有的数都小
                l = m + 1;
            }
        }
        return ans;
    }
}
