package 新手班.class05;

import java.util.HashSet;

public class Code01_BitMap {
    // 这个类的实现是正确的
    public static class BitMap {

        // 创建位图所使用的long型数组  long类型占64位数据
        private long[] bits;

        // 将数字范围的最大值传入，根据这个最大值来申请位图所使用的空间
        public BitMap(int max) {
            // 位图需要申请的数组长度是 max / 64 + 1，之所以需要+1是因为如果数范围的最大值小于64，也需要至少申请一个long数据，不能一个都不申请，一个都不申请就没有地方存放数据了
            // 这里>>6，就相当于除以62，之前讲过， 右移1位就相当于除以2，结果取整
            bits = new long[(max + 64) >> 6];
        }

        // 向位图中添加数据
        public void add(int num) {
            // 1L表示这个1是long类型，占用64位，而不是int类型占用32位，Java默认的整数就是int类型，如果这里不写L会出做，因为这样的话左移就会溢出了。 通向如果是2.1，Java默认它是double类型，必须这样写2.1f，这个2.1才是float类型

            /**
             * 1、先定位这个数要存放在数组的哪一个元素中，通过num >> 6来计算，也就是用num / 64
             * 2、然后定位这个数在这个数组元素具体哪个比特位上表示，就是这个数字除以64的余数，就是这个数字所在的比特位，然后将这一位与1取或，就能将这个位置的数字变成1
             *    这个具体的实现就是先将长整型1 向左移动 num % 64的余数个位，将1正好在代表num这个数字的比特位上，然后进行或操作即可。
             */
            bits[num >> 6] |= (1L << (num & 63));
        }

        // 将num从位图中删除
        public void delete(int num) {
            // 这个原理和上面基本一样，只是这里是将对应的位变成0，所以这里需要将num对应的比特位与0进行与操作，就可以将其置为0了。所以这里相较于添加操作有一个取反运算，将1变成0
            bits[num >> 6] &= ~(1L << (num & 63));
        }

        // 判断num在位图中存不存在
        public boolean contains(int num) {
            // 将代表num的比特位，与1进行与运算，结果是1说明这个比特位是1，证明num存在于位图中，反之则不存在
            return (bits[num >> 6] & (1L << (num & 63))) != 0;
        }

    }

    // 用HashMap做了一个对数器，来验证位图正不正确
    public static void main(String[] args) {
        System.out.println("测试开始！");
        int max = 10000;
        BitMap bitMap = new BitMap(max);
        HashSet<Integer> set = new HashSet<>();
        int testTime = 10000000;
        for (int i = 0; i < testTime; i++) {
            int num = (int) (Math.random() * (max + 1));
            double decide = Math.random();
            if (decide < 0.333) {
                bitMap.add(num);
                set.add(num);
            } else if (decide < 0.666) {
                bitMap.delete(num);
                set.remove(num);
            } else {
                if (bitMap.contains(num) != set.contains(num)) {
                    System.out.println("Oops!");
                    break;
                }
            }
        }
        for (int num = 0; num <= max; num++) {
            if (bitMap.contains(num) != set.contains(num)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }
}
