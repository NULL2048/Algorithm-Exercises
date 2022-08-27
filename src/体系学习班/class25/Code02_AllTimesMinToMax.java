package 体系学习班.class25;

import java.util.Stack;

public class Code02_AllTimesMinToMax {
    // 暴力方法，就是将所有的情况都遍历一遍，然后去最大值
    public static int max1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int minNum = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                    minNum = Math.min(minNum, arr[k]);
                }
                max = Math.max(max, minNum * sum);
            }
        }
        return max;
    }

    // 使用单调栈和前缀和数组来解题
    public static int max2(int[] arr) {
        int size = arr.length;
        // 创建前缀和数组
        int[] sums = new int[size];
        // 计算第一个位置的前缀和
        sums[0] = arr[0];
        // 去计算以每个位置为结束点的前缀和
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        // 创建单调栈
        Stack<Integer> stack = new Stack<Integer>();
        // 遍历数组，执行单调栈的流程，找到每一个位置的数左边最近的小于它的数和右边最近的小于它的数，进而求出以这个值为最小值的累加和最大的子数组（因为这个是正数数组，所以子数组包含的数越多，肯定累加和就越大）
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                // 这里是通过前缀和的规律来计算指定子数组的累加和的，并且比较取最大值
                max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
            }
            stack.push(i);
        }
        // 当数组遍历完，如果单调栈不为空，则继续单独计算剩余的数
        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
        }
        // 返回最大值
        return max;
    }

    // 对数器
    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (max1(arr) != max2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
    }

    // 本题可以在leetcode上找到原题
    // 测试链接 : https://leetcode.com/problems/maximum-subarray-min-product/
    // 注意测试题目数量大，要取模，但是思路和课上讲的是完全一样的
    // 注意溢出的处理即可，也就是用long类型来表示累加和
    // 还有优化就是，你可以用自己手写的数组栈，来替代系统实现的栈，也会快很多
    public static int maxSumMinProduct(int[] nums) {
        int n = nums.length;
        // 创建前缀和数组
        long[] preSum = new long[n];
        // 计算每一个位置的前缀和
        preSum[0] = nums[0];
        for (int i = 1; i < n; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }

        // 这里用long类型，避免数据类型溢出
        long max = Long.MIN_VALUE;
        // 创建单调栈，里面存储的是数组下标，注意数组下标只能是int型，所以这里stack数组为int类型
        int[] stack = new int[n];
        // 栈顶指针
        int top = -1;
        // 遍历数组，执行单调栈的流程，找到单调栈弹出的这个数左边和右边距离它最近并且小于它的数
        // 找到的这两个数中间部分就是我们要的，这一部分子数组的最小值就是弹出单调栈的这个数
        for (int i = 0; i < n; i++) {
            // 完全是按照单调栈流程来执行   新加入的数小于栈顶，就需要将栈顶数弹出
            while (top != -1 && nums[stack[top]] >= nums[i]) {
                // 弹出栈顶数，注意ans是数组下标
                int ans = stack[top--];
                // 如果 top == -1，说明弹出数的下面没有压着任何数，说明在数组中这个数的左边全都比它大
                //      这种情况下要求的子数组就是下标0~i-1范围的数组，这个范围内是以nums[ans]为最小值的，并且累加和最大（包含的数最多）的子数组
                // 如果 top != -1，说明弹出数的下面还压着别的数，说明在数组中这个数左边到它下面压着的这个数之间的数都是比这个数大的，右边到使这个数弹出，即将压入栈中的这个数之前的数都是比这个数大的，所以这个子数组下标范围就是i-1~stack[top]+1，然后用下标利用前缀和规则去求指定区间的累加和
                // 前缀和公式：sum(arr, i, j) = arr[i] + arr[i + 1] + …. arr[j - 1] + arr[j] = sum(arr, 0, j) - sum(arr, 0, i - 1)
                max = Math.max(max, top == -1 ? preSum[i - 1] * nums[ans] : (preSum[i - 1] - preSum[stack[top]]) * nums[ans]);
            }

            // 将数据压入栈
            stack[++top] = i;
        }

        // 如果将数组遍历完，单调栈还没空，就继续依次弹出
        while (top != -1) {
            int ans = stack[top--];
            // 此时弹出的所有数，数组中在这这个数右边的所有数一定都比它大
            // 所以就只需要考虑它左边有没有比它小的数即可
            // 如果 top == -1，说明弹出数的下面没有压着任何数，说明在数组中这个数的左边全都比它大
            //      这种情况这个子数组就是从0~n-1，因为这个数左边和右边所有的数都比它大，那么整个数组的最小值就是这个数
            // 如果 top != -1，说明弹出数的下面还压着别的数，说明在数组中这个数左边到它下面压着的这个数之间的数都是比这个数大的，所以这个子数组下标范围就是当前这个数的下标压着的这个树的下标stack[top]+1~n-1
            max = Math.max(max, top == -1 ? preSum[n - 1] * nums[ans] : (preSum[n - 1] - preSum[stack[top]]) * nums[ans]);
        }

        return (int) (max % 1000000007);
    }

}