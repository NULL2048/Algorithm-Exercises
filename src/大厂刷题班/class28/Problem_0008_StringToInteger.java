package 大厂刷题班.class28;
// 模拟   防溢出
// https://leetcode.cn/problems/string-to-integer-atoi/
public class Problem_0008_StringToInteger {
    public static int myAtoi(String s) {
        // 过滤无效参数
        if (s == null || s.equals("")) {
            return 0;
        }
        // 先将字符串中所有的空格字符去掉，然后再去掉前缀0
        // s.trim(str)：去掉s字符串中的str
        s = removeHeadZero(s.trim());
        // 再一次判空
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        if (!isValid(str)) {
            return 0;
        }
        // str 是符合日常书写的，正经整数形式
        boolean posi = str[0] == '-' ? false : true;
        // 下面就是计算两个不能越界的范围
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;
        int res = 0;
        int cur = 0;
        // 开始转换，要以负数为基础转换
        for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
            // 3  cur = -3   '5'  cur = -5    '0' cur = 0
            cur = '0' - str[i];
            // 溢出返回系统最大或最小
            if ((res < minq) || (res == minq && cur < minr)) {
                return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + cur;
        }
        // res 负
        if (posi && res == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        return posi ? -res : res;
    }

    // 去掉前缀0
    public static String removeHeadZero(String str) {
        // startsWith() 方法用于检测字符串是否以指定的前缀开始。
        boolean r = (str.startsWith("+") || str.startsWith("-"));
        int s = r ? 1 : 0;
        // 找到第一个不为0的位置
        for (; s < str.length(); s++) {
            if (str.charAt(s) != '0') {
                break;
            }
        }
        // s 到了第一个不是'0'字符的位置
        int e = -1;
        // 左<-右
        for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                e = i;
            }
        }
        // e 到了最左的 不是数字字符的位置
        // 我们要截取的就是s~e这个范围的数字，还要带着正负号返回
        return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
    }

    // 判断有效性  不能存在数字和正负号以外的字符
    public static boolean isValid(char[] chas) {
        if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
            return false;
        }
        if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
            return false;
        }
        // 0 +... -... num
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }
}
