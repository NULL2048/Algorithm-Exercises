package 大厂刷题班.class28;
// 模拟   防溢出
// https://leetcode.cn/problems/string-to-integer-atoi/
public class Problem_0008_StringToInteger {
    // 1、我自己写的代码
    public int myAtoi2(String str) {
        // 字符串转换为字符数组
        char[] s = str.toCharArray();
        // 字符串长度
        int n = s.length;
        // 标记是否已经完成了去除前缀空格的流程
        boolean flag1 = false;
        // 标记是否已经完成了读取符号位的流程
        boolean flag2 = false;
        // 记录符号位
        int signed = 1;
        // 记录答案
        // 这种字符串转数字类型的题，需要在过程中用负数统计，因为系统最小值绝对值比最大值大一个（负数的范围比正数的范围多1个），拿它做底更安全。
        // 比如转换字符串"-2147483648"为一个int数，用正数处理就不够了，只能用负数处理才行。
        // 如果不是负数的，也先将其转化为负数，处理完了之后再转回正数。
        int ans = 0;

        // 下面就是计算两个不能越界的范围，因为在统计过程中我们使用的是负数，所以这里就用负数的最小值来判断溢出边界
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;

        // 开始遍历字符串
        for (int i = 0; i < n; i++) {
            // 如果去除前缀空格的流程，并且当前的字符仍然是空格，则跳过该字符继续下一个
            if (!flag1 && s[i] == ' ') {
            } else {
                // 当第一次找到了一个不是空格的字符时，说明第一个阶段已经结束了，将flag设置为true
                flag1 = true;
                // 第一次进入到该分支，flag2一定是false，我们要先去判断第一个非空格的字符是否是符号位
                if (!flag2) {
                    // 先将flag2设置为true，因为该流程只会执行一次
                    flag2 = true;
                    // 如果符号位是负，则将signed设置为-1
                    if (s[i] == '-') {
                        signed = -1;
                        // 该位置是非数字，直接跳过
                        continue;
                    } else if (s[i] == '+') {
                        // 如果有正号符号位，也需要跳过后续的流程
                        continue;
                    }
                }

                // 当完成了第一个流程和第二个流程后，后面就开始读取数字字符了，当第一次读取到非数字字符时，就结束直接跳出循环，后面的字符串就不管了
                if (s[i] >= '0' && s[i] <= '9') {
                    // 【这里有一个技巧点，上面说了我们要按照负数去做处理，这样更安全，所以这里要用'0' - s[i]，当所有流程结束后我们再将符号位转回去即可】
                    int num = '0' - s[i];

                    // 【第二个技巧——防溢出】
                    // 因为我们使用负数来收集的，所以循环中的ans一定是一个负数
                    // 	1) ans结果是负数，如果比系统最小/10还小，那么在后面的乘10操作之后必溢出，这种情况就可以直接返回系统最大或系统最小值
                    //  2) 如果ans == 系统最小值，则乘10之后不会溢出，此时res*10=-2147483640，相当于系统最小值把最后一位变成0，也就是说只要是在个位上加一个小于-8的数（即小于系统最小值个位的那个数minr），就一定低于系统最小值，溢出了。在后面的操作中，ans要加num，所以如果num小于minr，则加上这部分必溢出。这种情况就可以直接返回系统最大或系统最小值
                    if (ans < minq || (ans == minq && num  < minr)) {
                        // 根据符号位来决定返回最大值还是最小值
                        return signed == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    }
                    // 收集数字
                    ans = ans * 10 + num;
                } else {
                    // 在收集数字的过程中第一次遍历到非数字字符时就结束循环
                    break;
                }
            }
        }

        // 如果此时ans此时正好是系统最小值，但是符号位是正数，我们知道最大值的绝对值比最小值的绝对值小1，所以此时答案应该是已经超过系统最大值了，算溢出了，直接返回系统最大
        if (signed == 1 && ans == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }

        // 因为之前我们是按照负数来收集，执行到这里就说明最终的答案没有溢出（大于等于系统最小，小于等于系统最大），所以我们直接将ans取相反数，消除之前按负数收集的影响
        ans = -ans;
        // 返回答案  (符号位 * ans)
        return  (signed * ans);
    }


    // 2、左神的代码思路
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
