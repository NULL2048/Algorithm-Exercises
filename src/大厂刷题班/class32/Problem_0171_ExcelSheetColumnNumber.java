package 大厂刷题班.class32;

// 数学
public class Problem_0171_ExcelSheetColumnNumber {
    // https://leetcode.cn/problems/excel-sheet-column-number/
    // 这道题反过来也要会写
    public static int titleToNumber(String s) {
        char[] str = s.toCharArray();
        int ans = 0;
        // 从低位开始计算
        for (int i = 0; i < str.length; i++) {
            // 进一位就是*26，就和普通的数进位一样
            ans = ans * 26 + (str[i] - 'A') + 1;
        }
        return ans;
    }


    // https://leetcode.cn/problems/excel-sheet-column-title/
    // 正常26进制是有0的，比如(低位0~25，高位1低位0即10代表一个26)
    // 本题每位1~26，待转换数字num从1开始~N
    // 当num属于[1~26] % 26 = 1...25， 0(当前位值为26时为0)
    // 即高位的1代表低位的26，而低位为26时，相当于低位有一个高位的1
    // 正常26进制，26在高一位，伪26进制，当mod值 为0时，代表当前为有26
    // 计算完后num - 26，相当于从高一位减1
    // _(个26)_ (1~26)， 低位有一个高位的26的1
    public static String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        while (columnNumber > 0) {
            int rem = columnNumber % 26;
            // 如果余数是0，代表当前位为26，修正余数为26
            if (rem == 0) {
                rem = 26;
                // 低位有一个高位的26的1,减去
                // 相当于/26后得到的高位要-1
                columnNumber -= 26;
            }
            // 插到第一个位置
            sb.insert(0, (char) (rem - 1 + 'A'));
            columnNumber /= 26;
        }
        return sb.toString();
    }
}
