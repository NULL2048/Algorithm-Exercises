package 新手班.class05;

// 测试链接：https://leetcode.com/problems/divide-two-integers
// 位运算实现加减乘除
public class Code02_BitAddMinusMultiDiv {
    /**
     * 加法运算
     * @param a
     * @param b
     * @return 返回a + b的结果
     *
     * a + b = a与b的无进位相加结果 + 进位信息
     */
    public static int add(int a, int b) {
        int sum = a;
        // 当相加的进位信息为0了，说明已经没有进位了，加和已经结束了，这一次无进位相加的结果就是最终结果，跳出循环
        while (b != 0) {
            // 将a和b进行无进位相加
            sum = a ^ b;
            // 获得a+b的进位信息
            b = (a & b) << 1;
            // 将无进位相加的结果赋值给a，在下一轮循环，将这一轮无进位相加结果与进位信息再次进行无进位相加，但是会先判断当前进位信息是不是0，如果是的话直接结束循环
            a = sum;
        }
        return sum;
    }

    // 计算n的相反数 -n = ~n + 1
    public static int negNum(int n) {
        return add(~n, 1);
    }

    /**
     * 减法运算
     * @param a
     * @param b
     * @return 返回a - b的结果
     *
     * a - b = a + (-b) = a + (~b + 1)
     */
    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    /**
     * 乘法运算
     * @param a
     * @param b
     * @return 返回a * b的结果
     */
    public static int multi(int a, int b) {
        int res = 0;
        // 当b的每一位都参与了运算之后，乘法运算就结束了
        while (b != 0) {
            // 取b的最右边一位，判断它是不是0，如果是0就不用管了，如果不是0，需要把a加进结果里
            if ((b & 1) != 0) {
                // 将a加进去，原因见上面的流程就懂了
                res = add(res, a);
            }

            // 这个是因为在加和的过程中，我们发现每一轮抄的一排数都向前提一位，为了保证最后的加和结果正确，我们就将a向左移动一位
            a <<= 1;
            // 将b向右移动1位，因为已经完成了最右边一位的运算，可以将这一位去掉了。注意，这里是无符号位右移，因为如果b是一个复数的话，符号位就为1，右移的话会把符号位1带进运算，运算结果就会出现错误了
            b >>>= 1;
        }
        return res;
    }

    // 判断n是不是为负数
    public static boolean isNeg(int n) {
        return n < 0;
    }

    /**
     * 除法运算(此时a和b都不是其数据类型的最小值)
     * @param a
     * @param b
     * @return 返回 a / b的结果
     */
    public static int div(int a, int b) {
        // 在进行除法运算前，需要把a和b转换成整数，然后最后结果再根据a、b两个的符号进行符号位赋值
        //
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;

        int res = 0;

        // 代码中我们是将x右移，将x的最大项对上y的最大项（x刚好大于y，也就是x是大于y的最小值），但是在我们笔记的例子中，是y左移，将y最大项对上x的最大项。
        // 实际上x右移对y，和y左移对x效果是一样的，只不过因为如果将y向左移动，在每一轮尝试的过程中，有可能将1移动到符号位上去（因为左移过程中，只有左移超位了，我们才知道这次左移前一步才是我们要的位置），如果这个数原来是正数，就有可能出现问题，为了避免这个问题，我们换做让x右移
        for (int i = 30; i >= 0; i = minus(i, 1)) {
            // 将x一位一位地向右移，直到x正好大于y地时候，说明上一次的右移的位置是我们需要的数。注意，这里我们是从右移30位开始的，然后逐步对i--。之所以从30开始位移，是因为int有32位，然后去掉符号位，然后我们本身的数就占一位，所以最多我们就能移动30位
            if ((x >> i) >= y) {
                // 直接根据这个位移数i，将二进制位的第i位设置成1，这个就是最后我们要求的结果的一部分
                res |= (1 << i);
                // 将x与y左移i位的数相减，将结果赋值给x，在进行后续的操作
                x = minus(x, y << i);
            }
        }

        // 根据a和b的符号进行符号位赋值，通过异或来实现，相同为1，不同为0
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    /**
     * 除法运算
     * @param a
     * @param b
     * @return 返回 a / b的结果
     *
     * 真正的除法，还是要分几个情况，因为上面那个方法只适用于非最小值进行运算
     *
     * 因为我们在计算的时候必须要将a和b转换为正数，所以就需要取绝对值操作，但是我们知道一个数据类型的最小值吗，没有办法取相反数，所以这种情况我们就没办法计算，需要分情况讨论
     */
    public static int divide(int a, int b) {
        // 当a和b都是最小值，也就是两个数一样，所以相除肯定得1
        if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
            return 1;
            // 只有除数是最小值，注意这里最小值肯定是一个绝对值很大得负数，所以一个数除以一个很大的书，肯定趋近于0，然后就算是负数我们也进行取整，最后就是0
        } else if (b == Integer.MIN_VALUE) {
            return 0;
            // 只有被除数是最小值
        } else if (a == Integer.MIN_VALUE) {
            // 如果除数是-1，直接返回int最大值，这是leetCode原题的约定
            if (b == negNum(1)) {
                return Integer.MAX_VALUE;
            } else {
                // 我们的最小值是没有办法取相反数的，但是我们可以给最小值+1，就可以求相反数了，举一个例子
                // 假定一个数据类型得范围是 -15 ~ 14
                // 我们想要计算 -15 / 3
                // 被除数是-15，我们就对-15加1，变成了-14 / 3 = -4
                // 我们将 -4 * 3 = -12，发现和原来的-15还差-3
                // 那么我们就把结果再稍微的修正补偿一下，将还差的-3除以除数3，得到 -1
                // 将结果-4 + （-1） = -5，这个修正后的结果就是-15 / 3的值

                // 抽象一下：
                // a / b
                // (a + 1) / b =c
                // a - (b *  c) = d
                // d / b = e
                // c + e

                // （a + 1） / b
                int c = div(add(a, 1), b);
                // (a - (b *  c)) / b + c   这个就是最终结果
                return add(c, div(minus(a, multi(c, b)), b));
            }
            // a和b都不是最小值，直接调用div()
        } else {
            return div(a, b);
        }
    }
}
