package 体系学习班.class24;

import java.util.LinkedList;

public class Code03_GasStation {
    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        // 这里使用的方法能将所有的加油站是否为良好出发点求出来，但是leetCode这道题只需要返回一个即可
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            // 只返回一个良好出发点即可
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }
    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        // 将累加和数组的长度扩大一倍，这样就可以保证每一个加油站都能作为起始点向后做一个完整的累加
        int M = N << 1;
        int[] arr = new int[M];
        // 计算得到纯能数组arr，就是用启用减里程，这里直接对arr两倍长度的数组进行复制，两部分的赋值都是一样的，用于后面求累计和
        for (int i = 0; i < N; i++) {
            arr[i] = g[i] - c[i];
            arr[i + N] = g[i] - c[i];
        }
        // 对长数组求一个累加和，每一个位置都去累加前面的数
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        // 双端队列，用于构建窗口内最小值更新结构
        LinkedList<Integer> w = new LinkedList<>();
        // 构建大小为N的窗口，同时维护窗口内最小值更新结构，这个过程右边界就一直右移，直到窗口大小为N
        for (int i = 0; i < N; i++) {
            // 判断新加入窗口的数据是否比队列尾部的数据大，如果不大，就需要就需要将双端队列尾部的数弹出，直到能保证双端队列从头部到尾部是从小到大的，就将新加入窗口的数加入到队列中
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        // 记录每个加油站是否为良好出发点
        boolean[] ans = new boolean[N];
        // 这个就是一个固定大小的窗口在长数组向右滑动的过程，在滑动过程中去找每一个窗口的最小值，判断最小值是否小于0，如果小于0就说明无法走完一圈
        // offset表示的是当前窗口左边界的前一个数的下标，用于窗口内累加和减去这个数得到原始累加和，最一开始窗口在长数组最左端的时候，不需要减掉前面的数，所以offset初始是0
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            // 在双端队列中拿到当前窗口内最小值，然后剪掉哪个offset还原成原始累加和，判断是否不小于0，如果不小于0，说明能跑完一圈，是良好出发点
            if (arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            // 后面就是在滑动窗口的过程中维护最小值更新结构
            // 左边界是i，左边界右移后，原来下标为i的数就移出了窗口，这时就判断一下双端队列头部数的下标是不是i，如果是说明这个数也跟着过期了，需要在头部弹出
            // 这里窗口左边界每次只会右移一个位置，所以只需要判断一次队列头部的下标是不是离开窗口的下标即可
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            // 窗口右边界右移，去判断是否符合由队列头到尾满足由小到大的情况，如果不满足就将队列尾部的数据弹出
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            // 将新加入窗口的数据尾插到双端队列
            w.addLast(j);
        }
        return ans;
    }
}
