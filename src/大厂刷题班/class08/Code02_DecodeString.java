package 大厂刷题班.class08;

// 括号嵌套递归
// 测试连接：https://leetcode.cn/problems/decode-string/
public class Code02_DecodeString {
    // 1、我自己写的代码
    public String decodeString(String s) {
        StringBuilder sb = new StringBuilder();
        sb = process(s, 0).str;
        return sb.toString();
    }

    // 信息类
    public class Info {
        // 当前递归层收集到的字符串
        private StringBuilder str;
        // 当前层的结束位置
        private int end;

        public Info(StringBuilder str, int end) {
            this.str = str;
            this.end = end;
        }
    }

    // 从i...，解码出当前括号内的字符串
    // 每一个括号用一个递归层来处理，如果在里面发现了一个新的括号就再开一个递归栈来处理。递归返回的数据一定是将自己对应的括号内的字符串全部解码出来的结果，返回给上一层，上一层再用这个结果来解码自己这一层的结果
    public Info process(String s, int i) {
        // 当前递归层的括号内已经收集的数字
        int cur = 0;
        // 当前递归层的括号内已经解码出的字符串
        StringBuilder sb = new StringBuilder();
        // 当遍历到字符串的结尾或者碰到']'，就说明当前递归层的括号内的数据已经全部都遍历到了，结束本层递归
        while (i < s.length() && s.charAt(i) != ']') {
            // 碰到非'['的，就一定是数字或者字符，就去收集数字或者字符
            if (s.charAt(i) != '[') {
                // 收集数字
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    cur = (cur * 10) + (s.charAt(i) - '0');
                    // 收集字符串
                } else {
                    sb.append(String.valueOf(s.charAt(i)));
                }
                i++;
                // 遇到'['，说明当前递归层的括号内还存在别的括号，我们再开一个递归层，让下一层递归去解码这个括号内的字符串
            } else {
                // 向下层递归
                Info nextInfo = process(s, i + 1);
                // 更新当前层解码到的位置，这个位置是下一层递归返回上来的，下一层递归已经帮我们完成了这个位置之前的解码
                i = nextInfo.end;
                // 下一层递归解码的出来的字符串
                StringBuilder sbNext = nextInfo.str;
                // 将下一层递归解码出来的字符串和当前层已经取到的数字再进行解码
                sbNext = printString(cur, sbNext);
                // 将解码出来的字符串再拼接到当前层递归已经解码出来的字符串后面
                sb.append(sbNext);

                // 将收集的数字重新置零
                cur = 0;
            }
        }

        // 返回本层递归的信息，解码出来的字符串和解码到的位置
        return new Info(sb, i + 1);
    }

    // 将str循环num次构造出新字符串
    public StringBuilder printString(int num, StringBuilder str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append(str);
        }
        return sb;
    }


    // 2、左神的代码
    public static String decodeString1(String s) {
        char[] str = s.toCharArray();
        return process(str, 0).ans;
    }

    public static class Info1 {
        public String ans;
        public int stop;

        public Info1(String a, int e) {
            ans = a;
            stop = e;
        }
    }

    // s[i....]  何时停？遇到   ']'  或者遇到 s的终止位置，停止
    // 返回Info
    // 0) 串
    // 1) 算到了哪
    public static Info1 process(char[] s, int i) {
        StringBuilder ans = new StringBuilder();
        int count = 0;
        while (i < s.length && s[i] != ']') {
            if ((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z')) {
                ans.append(s[i++]);
            } else if (s[i] >= '0' && s[i] <= '9') {
                count = count * 10 + s[i++] - '0';
            } else { // str[index] = '['
                Info1 next = process(s, i + 1);
                ans.append(timesString(count, next.ans));
                count = 0;
                i = next.stop + 1;
            }
        }
        return new Info1(ans.toString(), i);
    }

    public static String timesString(int times, String str) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < times; i++) {
            ans.append(str);
        }
        return ans.toString();
    }

}
