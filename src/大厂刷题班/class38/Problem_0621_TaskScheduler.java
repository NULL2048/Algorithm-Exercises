package 大厂刷题班.class38;
// 贪心   耍聪明的题，没有什么具体的算法    阿里面试题
// https://leetcode.cn/problems/task-scheduler/
public class Problem_0621_TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        // 统计每一个字符的词频
        int[] count = new int['Z' + 1];
        // 出现最多次的任务，到底是出现了几次
        int maxCnt = 0;
        for (int i = 0; i < tasks.length; i++) {
            count[tasks[i]]++;
            maxCnt = Math.max(maxCnt, count[tasks[i]]);
        }
        // 有多少种任务，都出现最多次
        int maxNumCnt = 0;
        for (char c = 'A'; c < 'Z'; c++) {
            if (count[c] == maxCnt) {
                maxNumCnt++;
            }
        }

        // 完成全部任务需要的最短时间
        int ans = 0;
        // maxNumCnt : 有多少种任务，都出现最多次
        // maxCnt : 最多次，是几次？
        // 出现最多次的任务占用的时间(maxNumCnt * maxCnt) + 产生的所有空格的时间。
        // maxCnt - 1:产生的间隙数
        // n - maxNumCnt + 1：产生的每一个间隙都有多少个空格
        ans = maxNumCnt * maxCnt + (n - maxNumCnt + 1) * (maxCnt - 1);
        // 如果空格不足以把剩下的任务都填满，就需要在每一部分的最后追加没有被填上的任务
        if (ans < tasks.length) {
            // 累加剩余没有被填进去的任务数
            ans += (tasks.length - ans);
        }

        return ans;
    }
}
