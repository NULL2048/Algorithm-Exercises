package 大厂刷题班.class28;

// 模拟   题目理解能力    数学
// https://leetcode.cn/problems/integer-to-roman/
public class Problem_0012_IntegerToRoman {
    public static String intToRoman(int num) {
        // 提前打表，准备下面这么多就够了
        String[][] c = {
                { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },
                { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
                { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },
                { "", "M", "MM", "MMM" } };
        StringBuilder roman = new StringBuilder();
        // 因为题目说了给ide给的num不会超过3999，所以下面准备四位数就够了
        roman
                .append(c[3][num / 1000 % 10])  // 如果压根就没有千位，除以1000就会得0，0下标在前面打表的数组对应的都是空字符串，所以就相当于什么都没加入
                .append(c[2][num / 100 % 10])
                .append(c[1][num / 10 % 10])
                .append(c[0][num % 10]);
        return roman.toString();
    }
}
