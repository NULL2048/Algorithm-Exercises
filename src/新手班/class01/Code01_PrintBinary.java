package 新手班.class01;

import java.io.*;

public class Code01_PrintBinary {
    /**
     * 一、Java中的类型及底层二进制原理
     * 在Java中int都默认的是有符号类型，C++有无符号整型，但是Java没有无符号整型数
     * int类型在底层就是一个32位的二进制数
     * long类型在底层就是一个64位的二进制数
     * 以Java的int类型为例：
     *      int在底层是占用着32位二进制，按道理说，它的最大值应该是2^32-1，也就是42亿多
     *      但实际上并不是这样的，因为Java中的int是一个有符号整型数，所以需要拿出最高位来代表正负号
     *      所以Java中int的范围是 [-2^31, 2^31-1] 最大值是21亿多，少了一半。一半给正数，一半给复数
     *      如果是C++的无符号整型数，那么最大值就是42亿多了，也就是2^32-1（减一是因为最小值是从0开始算起的，而不是从1开始算起的，总共有2^32个数，但是第一个数是0，所以最大的数应该是2^32-1）
     *
     * 二、二进制有符号数和无符号数如何来进行计算
     * 如果是一个有符号类型，最高位是0的话，说明这个数一定是非负的（正数和0）。最高位是1，这个数为负数。
     * 计算一个符号位为1的二进制数代表的是什么数，符号位不用管，然后取剩余位数的补码（也就是取反码然后加1）
     * 比如-1的二进制形式如果输出出来发现是111111111（为了方便，省略其他位数，其实所有位都是1）
     * 然后最高位符号位不用管他，其余为取反码，也就是00000000，然后加1，得00000001，这也就是1，再由最高位可知这是一个负数，所以最后的结果就是-1
     *
     * 再举一个例子，我们将int类型得最小值以二进制输出发现，是10000000（省略其他位，其他位都是0），按照上面的方法，符号位不管，其余为取反，得1111111，然后加1得10000000（最高位是1，剩下31位都是0）
     * 我们之前读取了最高位是1，所以知道这个数是负数，然后经过计算得到了最小值是-2^31。我们会发现如果不看正负号得话，负数能到的最大值比正数更大，负数是2^31，正数是2^31-1
     * 这个很好理解，因为计算正数的时候，最高位符号位是不可以用的，只能用余下得31位，但是计算负数得时候，最高位在最开始记录下来之后就可以使用了，也就是计算负数可以用到32位。（换个角度来理解，正数范围除了要表示正数以外，还要表示0，但是负数只需要表示小于0得数就可以了，所以正数需要把一位留给0，最后能表示的最大正数也就会比负数的最小负数的绝对值少1）
     *
     * 左移只有不带符号的左移，右移有带符号的右移
     *
     * 三、位移
     * 一个数左移一位，就相当于乘2。用位移来替代乘2效率会更高
     * 十进制偶数右移一位相当于除以2；但是如果不是偶数，就相当于除以2的结果然后取整
     */
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static StreamTokenizer st =new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

    /**
     * 将十进制转化为二进制数，并输出
     * @param num
     */
    public static void print(int num) {
        for (int i = 31; i >= 0; i--) {
            // 很好理解，就是按位取与，一位一位的取出二进制数
            out.print((num & (1 << i)) == 0 ? 0 : 1);
        }
        out.println();
    }

    public static void main(String[] args) {
        int num = -1;
        print(num);

        int test = 1123123;
		print(test);
		print(test<<1);
		print(test<<2);
		print(test<<8);

        int a = 12345;
        // ~这个符号就是取反
        int b = ~a;
        print(a);
        print(b);


        System.out.println(Integer.MIN_VALUE);
		System.out.println(Integer.MAX_VALUE);

		int c = 12319283;
		int d = 3819283;
		print(c);
		print(d);
		System.out.println("=============");
		print(c | d);
		print(c & d);
		print(c ^ d);


        out.flush();
        out.close();
    }
}
