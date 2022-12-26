package 大厂刷题班.class29;
import java.util.Arrays;
// 数组   排序   贪心
// https://leetcode.cn/problems/merge-intervals/
public class Problem_0056_MergeIntervals {
    public int[][] merge(int[][] intervals) {
        // 如果为空，直接返回（0，0）区间
        if (intervals == null || intervals.length == 0) {
            return new int[][] {{0, 0}};
        }

        // 按照区间的开始位置从小到大排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        // 记录当前正在统计合并区间的开始位置
        int s = intervals[0][0];
        // 记录当前正在统计合并区间的结束位置
        int e = intervals[0][1];
        // 当前要返回的答案已经填到什么位置了
        int size = 0;

        // 遍历所有区间，将答案复用填到intervals中
        for (int i = 1; i < intervals.length; i++) {
            // 如果当前遍历到的区间的开始位置大于当前正在统计合并区间的结束位置，说明我们遍历的区间出现了断档，无法和前面的区间连在一起
            // 所以记录一个独立区间的答案，然后重新开始统计一段新的区间
            if (intervals[i][0] > e) {
                // 将当前已经合并的区间记录在要返回的答案中
                intervals[size][0] = s;
                intervals[size++][1] = e;
                // 以当前新遍历到的区间来继续合并统计
                s = intervals[i][0];
                e = intervals[i][1];
                // 如果当前遍历到的区间的开始位置小于等于当前正在统计合并区间的结束位置，说明这两个区间还是能合并成一个的，他们是连在一起的
            } else {
                // 去看哪一个区间的结束位置能推高e，就将哪个赋值给e，完成两段区间的合并
                e = Math.max(e, intervals[i][1]);
            }
        }

        // 将最后一个还没来得及加入到结果中的区间加入进去
        intervals[size][0] = s;
        intervals[size++][1] = e;
        // 将要返回的答案进行数组创建，并返回，只需要返回intervals数组中长度为size的部分即可
        return Arrays.copyOf(intervals, size);
    }
}
