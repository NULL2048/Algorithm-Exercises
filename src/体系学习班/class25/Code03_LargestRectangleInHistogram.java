package 体系学习班.class25;

import java.util.Stack;

// 测试链接：https://leetcode.cn/problems/largest-rectangle-in-histogram
public class Code03_LargestRectangleInHistogram {
    // 使用系统提供的栈结构
    public static int largestRectangleArea1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int maxArea = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }

    // 自己构建栈结构，速度最快
    public static int largestRectangleArea2(int[] heights) {
        // 过滤无效参数
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int n = heights.length;
        // 创建单调栈
        int[] stack = new int [n];
        int top = -1;
        int max = Integer.MIN_VALUE;
        // 遍历数组，将每个数组添加进单调栈中
        // 找到每个位置左右能扩大的最大范围，就是距离当前位置的长方形最近的并且高度小于它的长方形，在这两个高度小于它的长方形之间的全部长方形，都是可以扩张到形成大长方形的
        for (int i = 0; i < n; i++) {
            while (top != -1 && heights[stack[top]] >= heights[i]) {
                int index = stack[top--];
                // 形成的长方形高度都是固定的，都是heights[index]，也就是从栈中弹出的高度
                max = Math.max(max, top == -1 ? heights[index] * i : heights[index] * (i - stack[top] - 1));
            }
            stack[++top] = i;
        }
        // 单独处理单调栈中剩下的长方形
        while (top != -1) {
            int index = stack[top--];
            max = Math.max(max, top == -1 ? heights[index] * n : heights[index] * (n - stack[top] - 1));
        }

        return max;
    }

}

