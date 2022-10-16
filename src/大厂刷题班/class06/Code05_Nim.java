package 大厂刷题班.class06;

// 异或  nim博弈
public class Code05_Nim {

    // 保证arr是正数数组
    public static void printWinner(int[] arr) {
        int eor = 0;
        // 计算数组的异或和
        for (int num : arr) {
            eor ^= num;
        }

        // 如果数组初始的异或和就是0，那么一定是后手赢，因为后手也是绝顶聪明。否则就是先手赢
        if (eor == 0) {
            System.out.println("后手赢");
        } else {
            System.out.println("先手赢");
        }
    }

}
