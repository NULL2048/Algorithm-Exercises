package 大厂刷题班.class08;

import java.util.LinkedList;

// 模拟    递归   栈
// 本题测试链接 : https://leetcode.cn/problems/basic-calculator-iii/    hard
public class Code01_ExpressionCompute {

    public static int calculate(String str) {
        // 调用递归，求0...结尾位置的计算结果
        return f(str.toCharArray(), 0)[0];
    }

    // 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
    // 返回两个值，长度为2的数组
    // 0) 负责的这一段的结果是多少
    // 1) 负责的这一段计算到了哪个位置
    public static int[] f(char[] str, int i) {
        // 用双向队列来代替栈。这里之所以用双向队列，是因为除了要实现从栈顶弹出，在最后将栈中剩余元素一并取出来计算的时候还需要直接从栈底按顺序取数，所以治理甘村直接用双向队列，这样就可以实现从两头取数的功能了。
        LinkedList<String> que = new LinkedList<String>();
        // 记录收集数字节点收集出来的数字是多少
        int cur = 0;
        int[] bra = null;
        // 从i出发，开始向右遍历。碰到右括号或者字符串终止就结束递归返回
        while (i < str.length && str[i] != ')') {
            // 碰到的如果是数字就收集
            if (str[i] >= '0' && str[i] <= '9') {
                // 计算收集数字节点已经收集上来的数字，将其组合成正常的数字类型的数。大一掌握的能力
                // 并且将指针向后移动一个位置
                cur = cur * 10 + str[i++] - '0';
                // 遇到的是运算符号，就结束数字的收集，将收集到的数字压入栈中
            } else if (str[i] != '(') {
                // 将收集到的数字压入栈中，如果栈顶是乘除号，需要先将栈顶前两个元素弹出，和cur数字计算一下，再将计算结果压入到栈中，这是为了保证先乘除后加减的运算规律
                addNum(que, cur);
                // 将当前的运算符号压入栈中，并将指针向后移动一个位置
                que.addLast(String.valueOf(str[i++]));
                // 将手机的数字重新置为0
                cur = 0;
                // 遇到左括号了，再调用一层递归
            } else {
                // 调用一次递归，在下一层递归中计算遇到这个括号里面的式子，并将结果和计算结束位置返回给bra
                bra = f(str, i + 1);
                // 将括号中的结果赋值给cur
                cur = bra[0];
                // 将指针移动到已经计算的结束位置的下一个位置，也就是跳过了遇到的这个括号的式子。
                i = bra[1] + 1;
            }
        }
        // 将还没有来得及加入到栈中的最后一个数字加入到栈里
        addNum(que, cur);
        // 将栈中剩余的数据一并计算并将结果返回，并且此时计算的结束位置就是i
        return new int[] { getNum(que), i };
    }

    // 将num数字压入到栈中，并且要满足先乘除，后加减的规律
    public static void addNum(LinkedList<String> que, int num) {
        // 整个流程中栈顶一定都是运算符号
        // 如果此时栈不为空
        if (!que.isEmpty()) {
            int cur = 0;
            // 将栈顶元素弹出查看一下
            String top = que.pollLast();
            // 如果栈顶元素是+或-号，则再将其压回到栈中
            if (top.equals("+") || top.equals("-")) {
                que.addLast(top);
                // 如果栈顶元素是*或/号，则再弹此时栈顶上的数字，先用num和这个弹出的数字进行*或/的运算，然后后面将运算的结果压入到栈中
            } else {
                cur = Integer.valueOf(que.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        // 将要添加到栈中的数或者进行完乘除计算后的结果压入到栈中
        que.addLast(String.valueOf(num));
    }

    // 将栈中的数据都拿出来，进行计算，此时栈中只存在+和-
    public static int getNum(LinkedList<String> que) {
        int res = 0;
        // 标记是加号还是减号
        boolean add = true;
        String cur = null;
        int num = 0;
        // 将栈中的数据依次弹出并计算，注意是从栈底开始依次弹出计算
        while (!que.isEmpty()) {
            // 从栈底开始弹出
            cur = que.pollFirst();
            // 弹出的是加号
            if (cur.equals("+")) {
                add = true;
                // 弹出的是减号
            } else if (cur.equals("-")) {
                add = false;
                // 弹出的是数字，就进行运算
            } else {
                num = Integer.valueOf(cur);
                // 将运算结果都记录在res中
                res += add ? num : (-num);
            }
        }
        // 返回结果
        return res;
    }

}

