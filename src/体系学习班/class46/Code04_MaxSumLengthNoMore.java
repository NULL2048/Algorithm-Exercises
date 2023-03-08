package 体系学习班.class46;

import java.util.LinkedList;
// 给定一个数组arr，和一个正数M
// 返回在子数组长度不大于M的情况下，最大的子数组累加和
public class Code04_MaxSumLengthNoMore {
    // O(N^2)的解法，暴力解，用作对数器
    public static int test(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }
        int N = arr.length;
        int max = Integer.MIN_VALUE;
        for (int L = 0; L < N; L++) {
            int sum = 0;
            for (int R = L; R < N; R++) {
                if (R - L + 1 > M) {
                    break;
                }
                sum += arr[R];
                max = Math.max(max, sum);
            }
        }
        return max;
    }

    // O(N)的解法，最优解
    // 用窗口内最大值更新结构，尝试以每一个位置的数为窗口左边界，求符合要求的最大子数组累加和，就让窗口不断地右移更新最大值即可。
    public static int maxSum(int[] arr, int M) {
        if (arr == null || arr.length == 0 || M < 1) {
            return 0;
        }
        int N = arr.length;
        int[] sum = new int[N];
        // 生成前缀和数组，用来快速求任意范围的子数组累加和
        sum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        // 窗口内子数组累加和最大值更新结构的双端队列   这个里面存储的是累加和最大值子数组的结尾位置下标
        LinkedList<Integer> qmax = new LinkedList<>();
        int i = 0;
        // 首先初始化窗口，取N和M的最小值来形成窗口以及累加和最大值更新结构
        int end = Math.min(N, M);
        for (; i < end; i++) {
            // 更新流程和经典的结构完全一样，只不过这里比较的是累加和大小
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
        }
        // 记录当前长度符合条件的最大累加和
        int max = sum[qmax.peekFirst()];
        // 尝试的窗口左边界   这里回尝试以每一个位置为开始位置的长度不超过M的子数组累加和是多少
        int L = 0;
        // 开始窗口内累加和最大值更新结构的维护
        // 因为每一次左边界都会右移一个位置，而且在前面已经固定好窗口长度不超过M了，所以在后续的更新过程中一定能保证子数组长度不超过M
        for (; i < N; L++, i++) {
            // 弹出左边界的数   此时最大累加和的下标已经等于L了，说明需要尝试新的左边界了，弹出此时的累加和最大值下标L
            if (qmax.peekFirst() == L) {
                qmax.pollFirst();
            }
            // 更新累加和最大值
            while (!qmax.isEmpty() && sum[qmax.peekLast()] <= sum[i]) {
                qmax.pollLast();
            }
            qmax.add(i);
            // 更新max，注意因为弹出了左边界的数，所以这里要利用前缀和定律减去sum[L]，才能求出来以此时新的窗口左边界为开始位置的最大累加和
            max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
        }

        // 有可能上面循环结束后，L还没有尝试完所有的位置，所以再继续尝试
        // 后续的子数组长度一定是不超过M的，因为只会缩左边界了，不会扩右边界了
        for (; L < N - 1; L++) {
            if (qmax.peekFirst() == L) {
                qmax.pollFirst();
            }
            // 尝试以每个L开头的子数组长度不超过M情况下的最大累加和
            max = Math.max(max, sum[qmax.peekFirst()] - sum[L]);
        }
        return max;
    }

    // 用作测试
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }
    // 用作测试
    public static void main(String[] args) {
        int maxN = 50;
        int maxValue = 100;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN);
            int M = (int) (Math.random() * maxN);
            int[] arr = randomArray(N, maxValue);
            int ans1 = test(arr, M);
            int ans2 = maxSum(arr, M);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束");
    }
}
