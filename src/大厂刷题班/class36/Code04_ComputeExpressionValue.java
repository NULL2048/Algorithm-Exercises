package 大厂刷题班.class36;

// 递归   括号嵌套求解的递归模型

// 来自美团
// () 分值为2
// (()) 分值为3
// ((())) 分值为4
// 也就是说，每包裹一层，分数就是里面的分值+1
// ()() 分值为2 * 2
// (())() 分值为3 * 2
// 也就是说，每连接一段，分数就是各部分相乘，以下是一个结合起来的例子
// (()())()(()) -> (2 * 2 + 1) * 2 * 3 -> 30
// 给定一个括号字符串str，已知str一定是正确的括号结合，不会有违规嵌套
// 返回分数
public class Code04_ComputeExpressionValue {

    public static int sores(String s) {
        return compute(s.toCharArray(), 0)[0];
    }

    // s[i.....] 遇到 ')' 或者 终止位置  停！
    // 返回值：int[]  长度就是2
    // 0 ：分数是多少
    // 1 : 来到了什么位置停的！
    public static int[] compute(char[] s, int i) {
        // 递归出口，遇到右括号就返回1得分以及右括号的下标位置i
        if (s[i] == ')') {
            return new int[] { 1, i };
        }
        int ans = 1;
        // 只要不是右括号或者遍历到最后，就继续遍历
        while (i < s.length && s[i] != ')') {
            // 返回的是所有同级别的括号及其对应的分数   递归就i+1，到下一个位置遍历
            int[] info = compute(s, i + 1);
            // 将每一个同级别的括号分数加1（因为递归出口的时候只返回了1）
            // 再将每一个同级别的括号分数累乘
            ans *= info[0] + 1;
            // 来到这一批括号的最后一个位置的下一个位置，继续遍历递归
            i = info[1] + 1;
        }
        // 返回结果
        return new int[] { ans, i };
    }

    public static void main(String[] args) {

        String str1 = "(()())()(())";
        System.out.println(sores(str1));

        // (()()) + (((()))) + ((())())
        // (()()) -> 2 * 2 + 1 -> 5
        // (((()))) -> 5
        // ((())()) -> ((2 + 1) * 2) + 1 -> 7
        // 所以下面的结果应该是175
        String str2 = "(()())(((())))((())())";
        System.out.println(sores(str2));

        // (()()()) + (()(()))
        // (()()()) -> 2 * 2 * 2 + 1 -> 9
        // (()(())) -> 2 * 3 + 1 -> 7
        // 所以下面的结果应该是63
        String str3 = "(()()())(()(()))";
        System.out.println(sores(str3));
    }

}
