package 体系学习班.class27;

public class Code03_IsRotation {
    public static boolean isRotation(String a, String b) {
        // 过滤无效参数
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }
        // 将原字符串进行拼接
        String b2 = b + b;
        // 利用KMP算法，在拼接好的字符串b2中找是否存在子串a。如果存在说明是旋转字符串
        return getIndexOf(b2, a) != -1;
    }
    // KMP Algorithm
    public static int getIndexOf(String s, String m) {
        if (s.length() < m.length()) {
            return -1;
        }
        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int si = 0;
        int mi = 0;
        int[] next = getNextArray(ms);
        while (si < ss.length && mi < ms.length) {
            if (ss[si] == ms[mi]) {
                si++;
                mi++;
            } else if (next[mi] == -1) {
                si++;
            } else {
                mi = next[mi];
            }
        }
        return mi == ms.length ? si - mi : -1;
    }
    // 快速得到next数组
    public static int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[] { -1 };
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < next.length) {
            if (ms[pos - 1] == ms[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }
    public static void main(String[] args) {
        String str1 = "yunzuocheng";
        String str2 = "zuochengyun";
        System.out.println(isRotation(str1, str2));
    }
}

