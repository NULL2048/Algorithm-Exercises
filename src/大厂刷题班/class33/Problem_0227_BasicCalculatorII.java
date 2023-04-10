package 大厂刷题班.class33;

import java.util.ArrayDeque;
import java.util.Deque;

// 栈  数学  模拟计算
// https://leetcode.cn/problems/basic-calculator-ii/
public class Problem_0227_BasicCalculatorII {
    // 1、这个代码是我自己写的版本
    public int calculate(String str) {
        // 字符串转换为字符数组
        char[] s = str.toCharArray();
        // 数组长度
        int n = s.length;
        // 栈
        String[] stack = new String[n];
        int top = -1;
        // 用来收集数字字符串
        StringBuilder numStr = new StringBuilder();
        // 遍历字符数组，先将所有的乘除运算计算完，只在栈中保留加减法
        for (int i = 0; i < n; i++) {
            // 只收集非空格字符
            if (s[i] != ' ') {
                // 如果遍历到的是数字字符，就收集到numStr中
                if (s[i] >= '0' && s[i] <= '9') {
                    numStr.append(s[i]);
                    // 如果遍历到的是运算符
                } else {
                    // 首先说明数字收集结束了，生成前面收集的数字字符串
                    String num = numStr.toString();
                    // 清空StringBuilder
                    numStr.setLength(0);
                    // 此时收集到了数字num和运算符s[i]，还没有将其加入到栈中，需要先进行一下运算优先级判断

                    // 如果此时栈中是非空，就说明里面至少有一个数字和一个字符串
                    // 如果栈是空，说明此时还没有向栈中加入过数据，就直接将收集到的一个数和运算符加入进去
                    if (top != -1) {
                        // 如果此时栈顶是乘除，就先从栈中弹出一个运算符和一个数，与当前刚收集到的数先进行运算，然后再将运算结果和当前收集到的运算符s[i]加入栈中
                        // 先将所有的乘除都计算完
                        if ("*".equals(stack[top]) || "/".equals(stack[top])) {
                            // 弹出一个数和一个运算符
                            int num2 = Integer.valueOf(num);
                            String operator = stack[top--];
                            int num1 = Integer.valueOf(stack[top--]);
                            // 计算乘除法
                            int res = compute(num1, num2, operator);
                            // 将计算结果和新收集的运算符加入栈中
                            stack[++top] = String.valueOf(res);
                            stack[++top] = String.valueOf(s[i]);
                            // 进行下一轮循环
                            continue;
                        }
                    }

                    // 如果不是栈顶不是乘除法，就直接将收集的到数和运算符加入栈中
                    stack[++top] = num;
                    stack[++top] = String.valueOf(s[i]);
                }
            }
        }
        // 上面循环中最后一次收集到的数据还没来得及加入栈就结束循环了，所以需要将这个数单独加入栈中
        String num = numStr.toString();
        // 如果栈不为空，说明还有数字和运算符
        if (top != -1) {
            // 仍然要做优先级判断
            if ("*".equals(stack[top]) || "/".equals(stack[top])) {
                int num2 = Integer.valueOf(num);
                String operator = stack[top--];
                int num1 = Integer.valueOf(stack[top--]);

                int res = compute(num1, num2, operator);
                // 只需要加结果数字即可
                stack[++top] = String.valueOf(res);
                // 如果栈顶不是乘除，直接将最后的数字加入到栈中即可
            } else {
                stack[++top] = num;
            }
            // 如果栈中没有数据，说明给的原始字符串只有一个数字，即num，直接将其加入栈中
        } else {
            stack[++top] = num;
        }

        // 至此栈中不会再有乘除号了

        // 如果栈中有超过1个数据，就说明栈中还存在加减的式子，需要计算
        if (top > 0) {
            // 将栈中的加减法计算出来，结果返回
            // 栈中数据一定是一个数，一个运算符交替排列的
            // 上一轮的计算结果
            int num1 = Integer.valueOf(stack[0]);
            String operator = stack[1];
            // 循环只遍历数字
            for (int i = 2; i <= top; i += 2) {
                int num2 = Integer.valueOf(stack[i]);
                int res = compute(num1, num2, operator);

                num1 = res;
                // 最后一个数字就不用再加其后面的运算符了，因为后面已经没有数据了
                if (i + 1 <= top) {
                    operator = stack[i + 1];
                }
            }
            // 返回结果
            return num1;
        }

        // 执行到这里说明栈中只有一个数了，两种情况：
        // 1、原式子只有乘除法，在一开始的流程中已经将结果算完了放在栈中了，直接返回即可
        // 2、给的字符串只有一个数字，这个数字加入到栈中，直接返回
        return Integer.valueOf(stack[top]);
    }

    // 运算方法
    public int compute(int num1, int num2, String operator) {
        if ("*".equals(operator)) {
            return num1 * num2;
        } else if ("/".equals(operator)) {
            return num1 / num2;
        } else if ("+".equals(operator)) {
            return num1 + num2;
        } else {
            return num1 - num2;
        }
    }


    // 2、网上的版本，和我自己写的代码思路本质一样，时间复杂度也是一样的，但是常数更优
    // 我们是要学习人家的代码写法，真的比我自己的简化好多，我觉得学这个思路是最好的
    public int calculate1(String s) {
        // Deque就是双端队列
        Deque<Integer> stack = new ArrayDeque<>();
        // 记录当前遍历到的数字的上一个运算符是什么
        char preSign = '+';
        int num = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            // 收集数字，不使用内置函数生成
            if (Character.isDigit(ch)) {
                num = num * 10 + ch - '0';
            }
            // 只有运算符会进入分支
            if (!Character.isDigit(ch) && ch != ' ' || i == n - 1) {
                // 栈中不放运算符，只放数字
                // 也是先把乘除都算完，然后最后直接把加减算完即可
                // 加号就将数字直接压栈
                // 加号就将数字取反再压栈
                // 这样计算完乘除后，直接将栈里的数字都相加就是最后结果
                // 乘除就先算出结果（弹出一个数，与当前收集到的数num进行计算），然后把结果压栈
                switch (preSign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);
                }
                // 清空收集的数字
                num = 0;
                preSign = ch;
            }
        }
        // 将栈中所有数相加就是结果
        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.pop();
        }
        return ans;
    }
}
