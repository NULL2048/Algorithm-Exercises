package 体系学习班.class02;

public class Code02_EvenTimesOddTimes {

    // arr中，只有一种数，出现奇数次
    public static void printOddTimesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    // arr中，有两种数，出现奇数次
    public static void printOddTimesNum2(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        // a 和 b是两种数，所以a一定不等于b
        // 所以最后的结果一定eor != 0
        // 将eor最右侧的1，提取出来
        // eor :     00110010110111000
        // rightOne :00000000000001000
        int rightOne = eor & (-eor); // 提取出最右的1

        // 再定义一个eor'
        int onlyOne = 0;

        for (int i = 0 ; i < arr.length;i++) {
            // arr[1] =  111100011110000
            // rightOne=  000000000010000
            // 一个属与rightOne取与，如果结果不等于0，说明这个数在rightOne为1的那一位，也是1
            // 只有这样的数会异或进onlyOne中
            if ((arr[i] & rightOne) != 0) {
                onlyOne ^= arr[i];
            }
        }
        // 输出两个数，如果a ^ b = c，那么 a = c ^ b
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }


    public static int bit1counts(int N) {
        int count = 0;

        //   011011010000
        //   000000010000     1

        //   011011000000
        //



        while(N != 0) {
            int rightOne = N & ((~N) + 1);
            count++;
            N ^= rightOne;
            // N -= rightOne
        }


        return count;

    }


    public static void main(String[] args) {
        int a = 5;
        int b = 7;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

    }

}
