package 大厂刷题班.class28;
// 递归   栈
// https://leetcode.cn/problems/valid-parentheses/
public class Problem_0020_ValidParentheses {
    public boolean isValid(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        char[] s = str.toCharArray();
        char[] stack = new char[s.length];
        int top = -1;

        stack[++top] = s[0];
        for (int i = 1; i < s.length; i++) {
            char cha = s[i];

            // 碰到左括号就压栈
            if (cha == '(' || cha == '{' || cha =='[') {
                stack[++top] = cha;
                // 碰到右括号就弹出栈顶，看弹出的栈顶是否能和当前的右括号配对
            } else {
                // 如果此时栈为空，直接返回false
                if (top == -1) {
                    return false;
                }
                char last = stack[top--];
                // 如果弹出的栈顶无法和当前遍历到的右括号配对，则返回false
                if ((cha == ')' && last != '(') ||
                        (cha == ']' && last != '[') ||
                        (cha == '}' && last != '{') ) {
                    return false;
                }
            }
        }
        // 如果完成流程之后，栈为空，说明是都是有效的括号，返回true，否则返回false
        return top == -1;
    }
}
