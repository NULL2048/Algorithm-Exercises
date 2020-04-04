package 打印沙漏;

import java.io.*;

/*
本题要求你写个程序把给定的符号打印成沙漏的形状。例如给定17个“*”，要求按下列格式打印

*****
 ***
  *
 ***
*****

所谓“沙漏形状”，是指每行输出奇数个符号；各行符号中心对齐；相邻两行符号数差2；符号数先从大到小顺序递减到1，再从小到大顺序递增；首尾符号数相等。

给定任意N个符号，不一定能正好组成一个沙漏。要求打印出的沙漏能用掉尽可能多的符号。

输入格式:
输入在一行给出1个正整数N（≤1000）和一个符号，中间以空格分隔。

输出格式:
首先打印出由给定符号组成的最大的沙漏形状，最后在一行中输出剩下没用掉的符号数。

输入样例:
19 *


输出样例:
*****
 ***
  *
 ***
*****
2

 */

import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        StreamTokenizer scan = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        scan.wordChars('!', '@');

        scan.nextToken();
        int n = (int) scan.nval;

        scan.nextToken();
        char c = scan.sval.charAt(0);

        int l = (int) Math.sqrt((n + 1) / 2);

        for (int i = l; i > 0; i--) {
            for (int j = 0; j < l - i; j++) {
                out.print(" ");
            }

            for (int j = 0; j < 2 * i - 1; j++) {
                out.print(c);
            }
            out.println();
        }

        for (int i = 2; i <= l; i++) {
            for (int j = 0; j < l - i; j++) {
                out.print(" ");
            }

            for (int j = 0; j < 2 * i - 1; j++) {
                out.print(c);
            }
            out.println();
        }

        out.println(n - (2 * l * l - 1));

        out.flush();
    }

}
