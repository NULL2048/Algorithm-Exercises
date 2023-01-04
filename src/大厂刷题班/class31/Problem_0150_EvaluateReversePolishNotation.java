package 大厂刷题班.class31;

// 栈    模拟运算    数学
// https://leetcode.cn/problems/evaluate-reverse-polish-notation/
public class Problem_0150_EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        int n = tokens.length;
        // 自己构造栈
        String[] stack = new String[n];
        int top = -1;
        // 遍历逆波兰表达式
        for (int i = 0; i < n; i++) {
            String str = tokens[i];
            // 遇到数字压栈,遇到运算符弹出两个数计算,算完再压栈回去
            if ("+".equals(str) || "-".equals(str) || "*".equals(str) || "/".equals(str)) {
                int num1 = Integer.valueOf(stack[top--]);
                int num2 = Integer.valueOf(stack[top--]);
                // 弹出两个数进行运算，注意num2在前，num1在后
                int ans = compute(num1, num2, str);
                // 将运算结果压入栈中
                stack[++top] = String.valueOf(ans);
            } else {
                // 碰到数字直接压栈
                stack[++top] = str;
            }
        }

        return Integer.valueOf(stack[top]);
    }

    // 将两个数进行计算，返回运算结果
    public int compute(int num1, int num2, String operator) {
        if ("+".equals(operator)) {
            return num2 + num1;
        } else if ("-".equals(operator)) {
            return num2 - num1;
        } else if ("*".equals(operator)) {
            return num2 * num1;
        } else  {
            return num2 / num1;
        }
    }
}
