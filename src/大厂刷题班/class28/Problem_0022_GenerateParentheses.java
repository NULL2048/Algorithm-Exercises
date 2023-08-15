package 大厂刷题班.class28;

import java.util.ArrayList;
import java.util.List;

// 动态规划   暴力递归   剪枝    递归
// https://leetcode.cn/problems/generate-parentheses/
public class Problem_0022_GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        // 记录递归路径
        char[] path = new char[n << 1];
        // 记录所有可能的答案
        List<String> ans = new ArrayList<>();

        // 一开始还没有放左括号和右括号，所以差值为0，还能放的左括号数为n
        process(0, path, ans, 0, n);

        return ans;
    }

    // path 做的决定  path[0....index-1]做完决定的！
    // path[index.....] 还没做决定，当前轮到index位置做决定！
    // 合法的括号组合，在任意的前缀上，左括号数量不能少于右括号数量。
    // leftMinusRight：左括号数量减右括号数量的差值
    // leftRest：记录还能放几个左括号，给的数字是n，那么一定是有n个左括号和n个右括号才行
    public void process(int index, char[] path, List<String> ans, int leftMinusRight, int leftRest) {
        // basecase  当path已经放完了，说明已经收集好答案了。整个递归过程中会保证只有有效的括号组合才能递归到最后
        if (index == path.length) {
            ans.add(String.valueOf(path));
        } else {
            // 下面的两个if分支就做了剪枝，保证只有是有效的括号组合才可以继续递归

            // 如果此时还可以放的左括号数大于0，则就尝试放左括号
            if (leftRest > 0) {
                path[index] = '(';
                // leftMinusRight + 1   因为加了一个左括号，所以向下传递的差值要加1
                // 还能放的左括号数减1
                process(index + 1, path, ans, leftMinusRight + 1, leftRest - 1);
            }

            // 如果此时左括号数量减右括号数量的差值大于0，说明左括号还是多与右括号的，可以继续放右括号
            if (leftMinusRight > 0) {
                path[index] = ')';
                // leftMinusRight - 1   因为加了一个右括号，所以向下传递的差值要减1
                // 还能放的左括号数不变
                process(index + 1, path, ans, leftMinusRight - 1, leftRest);
            }
        }
    }


    // 不剪枝的做法
    public static List<String> generateParenthesis2(int n) {
        char[] path = new char[n << 1];
        List<String> ans = new ArrayList<>();
        process2(path, 0, ans);
        return ans;
    }

    public static void process2(char[] path, int index, List<String> ans) {
        if (index == path.length) {
            if (isValid(path)) {
                ans.add(String.valueOf(path));
            }
        } else {
            path[index] = '(';
            process2(path, index + 1, ans);
            path[index] = ')';
            process2(path, index + 1, ans);
        }
    }

    public static boolean isValid(char[] path) {
        int count = 0;
        for (char cha : path) {
            if (cha == '(') {
                count++;
            } else {
                count--;
            }
            if (count < 0) {
                return false;
            }
        }
        return count == 0;
    }
}
