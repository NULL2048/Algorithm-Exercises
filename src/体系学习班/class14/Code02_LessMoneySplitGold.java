package 体系学习班.class14;

import java.util.PriorityQueue;

public class Code02_LessMoneySplitGold {

    // 纯暴力！
    public static int lessMoney1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0);
    }

    // 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
    // arr中只剩一个数字的时候，停止合并，返回最小的总代价
    public static int process(int[] arr, int pre) {
        // 数组中只剩下一个数的时候，说明所有的数都已经合并完了，返回
        if (arr.length == 1) {
            return pre;
        }
        // 先将代价初始化为最大值
        int ans = Integer.MAX_VALUE;
        // 通过两层循环去将本层递归中还没有合并的数(arr数组中的数)，两两组合，将所有的情况都组合一遍
        //arr[0] [1] [2] [3] [4] [5] [6]   每两组都合并尝试
        //0 1
        //0 2
        //0 3
        //0 6
        //1 2
        //1 3
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                // ans 已经存储过的代价
                // copyAndMergeTwo(arr, i, j) 将i和j位置的两个数合并成一个新数后，形成新的arr数组返回，并且将原i位置和j位置的数移除
                // process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]) 调用递归，再去计算按照当前一次循环的合并情况下，后续的合并情况的最小开销
                // pre + arr[i] + arr[j]本轮合并行为产生的代价
                // 比较清除最小的代价
                ans = Math.min(ans, process(copyAndMergeTwo(arr, i, j), pre + arr[i] + arr[j]));
            }
        }
        // 返回最小的代价
        return ans;
    }

    // 将i和j位置的两个数合并成一个新数后，形成新的arr数组返回，并且将原i位置和j位置的数移除
    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            // 将除了i位置和j位置的数都复制到新数组中
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        // 将合并的数添加到新的arr数组中
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    // 贪心
    public static int lessMoney2(int[] arr) {
        // 默认是小根堆，小根堆的性质就是我们的贪心策略
        PriorityQueue<Integer> pQ = new PriorityQueue<>();
        // 将所有数据加入到小根堆中
        for (int i = 0; i < arr.length; i++) {
            pQ.add(arr[i]);
        }
        // 记录总代价
        int sum = 0;
        // 标记当前的数
        int cur = 0;
        // 进行合并，直到堆中只剩下一个数
        while (pQ.size() > 1) {
            // 弹出堆中前两个数进行合并
            cur = pQ.poll() + pQ.poll();
            // 将代价累加
            sum += cur;
            // 将合并之后的数加入到堆中
            pQ.add(cur);
        }
        // 返回答案
        return sum;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}
