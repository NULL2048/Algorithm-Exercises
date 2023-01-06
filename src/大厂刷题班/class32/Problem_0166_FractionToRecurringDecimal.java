package 大厂刷题班.class32;
import java.util.HashMap;
// 数学 模拟运算  模拟
// https://leetcode.cn/problems/fraction-to-recurring-decimal/
public class Problem_0166_FractionToRecurringDecimal {
    public String fractionToDecimal(int numerator, int denominator) {
        // 被除数为0，直接返回0
        if (numerator == 0) {
            return "0";
        }
        // 要返回的结果字符串
        StringBuilder res = new StringBuilder();
        // 判断一下分子和分母的正负号，相同结果就为正，不相同结果就为负
        res.append((numerator > 0) ^ (denominator > 0) ? "-" : "");
        // 这里要注意。如果取绝对值后的变量类型为long，需要在取绝对值之前将原数也转换为long类型，否则绝对值转化过程会出问题。
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);

        // 整个过程就是模拟我们的出发计算流程
        // 先计算整数部分，默认是取整的
        res.append(num / den);
        // 再去一下余数，将余数赋值给num。这个余数就会参与后面小数部分的除法运算，和我们平时计算的流程是一样的
        num %= den;
        // 如果没有余数了，说明这个数能被整除，直接返回结果
        if (num == 0) {
            return res.toString();
        }
        // 加入小数
        res.append(".");
        // 记录小数部分每一个余数出现在计算哪个下标时，这个用于判断是否出现了循环小数
        // 因为在计算除法的过程中，一旦出现了一个以前出现过的余数，那么余数乘以10后得到的一个数肯定之前就出现过了，那么肯定就会按照以前后面的计算结果出现循环情况了
        HashMap<Long, Integer> map = new HashMap<>();
        // 先将第一个上面求出来的余数加入到结果中
        map.put(num, res.length());
        // 当余数num为0的时候，说明除法计算结束，结束循环
        while (num != 0) {
            // 将余数乘以10，进行后面的除法运算。这个和计算除法流程是一样的，每次都要在后面加一个0再接着进行运算
            num *= 10;
            // 将除法的结果加入到res中
            res.append(num / den);
            // 再求一下余数，整个流程就是在模拟除法运算
            num %= den;

            // 判断一下余数num是否在以前出现过，出现过的话说明出现了循环小数
            if (map.containsKey(num)) {
                // 获取之前余数num出现的下标
                int index = map.get(num);
                // 在之前出现的下标前面插入左括号
                res.insert(index, "(");
                // 在结尾加入右括号
                res.append(")");
                // 返回答案
                return res.toString();
            }
            // 将num加入到map中，并记录上出现的下标
            map.put(num, res.length());
        }
        // 返回答案
        return res.toString();
    }
}
