package 体系学习班.class24;

import java.util.LinkedList;

public class Code02_AllLessNumSubArray {

    // 暴力的对数器方法
    public static int right(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }
        int N = arr.length;
        int count = 0;
        // 暴力枚举出所有的情况，统计符合达标的子数组数量
        for (int L = 0; L < N; L++) {
            for (int R = L; R < N; R++) {
                int max = arr[L];
                int min = arr[L];
                for (int i = L + 1; i <= R; i++) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    count++;
                }
            }
        }
        return count;
    }

    // 使用窗口内最大值和最小值更新结构来求解
    public static int num(int[] arr, int sum) {
        // 过滤无效参数
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int N = arr.length;
        // 记录达标子数组数量
        int count = 0;
        // 窗口内最大值更新结构
        LinkedList<Integer> maxWindow = new LinkedList<>();
        // 窗口内最小值更新结构
        LinkedList<Integer> minWindow = new LinkedList<>();
        // 窗口右边界
        int R = 0;
        // 左边界从下标0开始，将右边界右移，直到当前窗口不达标就停止
        for (int L = 0; L < N; L++) {
            // 窗口右边界右移
            while (R < N) {
                // 窗口右边界右移过程中，将新加入窗口的数据添加进双端队列中，按照最大值更新结构规则维护
                while (!maxWindow.isEmpty() && arr[maxWindow.peekLast()] <= arr[R]) {
                    maxWindow.pollLast();
                }
                maxWindow.addLast(R);
                // 窗口右边界右移过程中，将新加入窗口的数据添加进双端队列中，按照最小值更新结构规则维护
                while (!minWindow.isEmpty() && arr[minWindow.peekLast()] >= arr[R]) {
                    minWindow.pollLast();
                }
                minWindow.addLast(R);
                // 如果右边界右移到当前窗口已经不达标了，跳出循环
                if (arr[maxWindow.peekFirst()] - arr[minWindow.peekFirst()] > sum) {
                    break;
                    // 如果一直达标，就继续右移
                } else {
                    R++;
                }
            }
            // 记录以L为左边界的，在L..R范围内所有达标的子数组数量
            count += R - L;
            // 当R右移到不达标之后，就会将左边界L右移，这个时候也需要对最大值和最小值更新结构进行维护，查看队列头部的数据是否过期，如果过期就将其从头部弹出
            // 因为这里每次左边界只右移一个位置，后面会讲L右移，也就是当前的L就是马上要移出窗口的数，这里就直接判断队列中头部数的下标是否等于L即可，如果两个相等，说明就是已过期的。这里就不用使用循环和小于等L来判断了，直接用一个if判断是否等于L即可
            // 最小值结构和最大值结构判断是否过期的方法是一致的
            if (maxWindow.peekFirst() == L) {
                maxWindow.pollFirst();
            }
            if (minWindow.peekFirst() == L) {
                minWindow.pollFirst();
            }
        }
        return count;
    }

    // for test
    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1)) - (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int sum = (int) (Math.random() * (maxValue + 1));
            int ans1 = right(arr, sum);
            int ans2 = num(arr, sum);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(sum);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }

}
