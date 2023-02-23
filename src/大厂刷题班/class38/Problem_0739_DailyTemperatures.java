package 大厂刷题班.class38;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
// 单调栈  数组
// https://leetcode.cn/problems/daily-temperatures/
public class Problem_0739_DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        // 存储每一个位置距离自己右边最近的比自己大的数有多远
        // ans[i]：temperatures[i]到自己右边最近的比自己大的数字的距离是ans[i]
        // 默认为0，这样如果没有找到右边有比自己大的就说明温度在以后不会升高了
        int[] ans = new int[n];
        // 过滤无效参数
        if (temperatures == null || temperatures.length == 0) {
            return ans;
        }
        // 单调栈，按照自底向上是从大到小排列，为了找每一个数右边比自己大的最近的数是什么
        // 链表中存储的是下标，同一个链表中的下标指向的值都是一样大的
        Stack<List<Integer>> stack =  new Stack<>();
        // 遍历数组
        for (int i = 0; i < n; i++) {
            // 如果栈顶不为空，并且当前遍历到的数大于栈顶的数，说明如果插入temperatures[i]就会破坏单调栈结构
            // 这里用循环，如果一直会破坏单调栈结构，就一直弹出收集答案
            while (!stack.isEmpty() && temperatures[stack.peek().get(0)] < temperatures[i]) {
                // 弹出栈顶数据，来收集被弹出的位置右边比自己大的且最近的数字的信息
                List<Integer> topList = stack.pop();
                // 链表中存储的是下标，他们指向的值都是一样的
                for (Integer index : topList) {
                    // i就是index右边比自己大且离自己最近的数字的下标
                    // 他们的距离就是i - index
                    ans[index] = i - index;
                }
            }

            // 如果栈不为空，并且与栈顶数字相等
            if (!stack.isEmpty() && temperatures[stack.peek().get(0)] == temperatures[i]) {
                // 有重复值的情况，直接将i加入到栈顶链表中即可
                stack.peek().add(i);
                // 栈为空，或者i没有破坏单调栈结构（自底向上从大到小），就将i作为一个新的节点压入栈中
            } else {
                // 创建新的链表
                List<Integer> nodeList = new ArrayList<>();
                // 将i加入链表
                nodeList.add(i);
                // 将链表压入栈中
                stack.push(nodeList);
            }
        }

        // 返回答案
        return ans;
    }
}
