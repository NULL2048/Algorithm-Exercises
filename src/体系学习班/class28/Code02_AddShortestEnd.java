package 体系学习班.class28;

public class Code02_AddShortestEnd {

    // 整个代码就是Manacher算法流程
    public static String shortestEnd(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] str = manacherString(s);
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;
        // 用来记录第一次包住字符串最右侧位置的回文半径
        int maxContainsEnd = -1;
        for (int i = 0; i != str.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            // 从左到右尝试，第一次找到能包住字符串最后一个位置的字符时，就结束循环
            if (R == str.length) {
                // 记录回文半径，通过回文半径就可以推出字符串前部分不是回文串的范围，然后将该范围的字符逆序添加到字符串后面就能形成一整个回文字符串，并且添加的字符数最少
                maxContainsEnd = pArr[i];
                break;
            }
        }
        char[] res = new char[s.length() - maxContainsEnd + 1];
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = str[i * 2 + 1];
        }
        return String.valueOf(res);
    }

    // 将原始串进行处理，加入特殊字符
    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }

}

