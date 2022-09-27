package 体系学习班.class43;

// https://leetcode.cn/problems/can-i-win/
public class Code01_CanIWin {
    // 0、没有用状态压缩的暴力递归
    // 1~choose 拥有的数字
    // total 一开始的剩余
    // 返回先手会不会赢
    public static boolean canIWin0(int choose, int total) {
        if (total == 0) {
            return true;
        }
        if ((choose * (choose + 1) >> 1) < total) {
            return false;
        }
        // 直接用数组的线性结构来表示每个数的存在状态
        int[] arr = new int[choose];
        for (int i = 0; i < choose; i++) {
            arr[i] = i + 1;
        }
        // arr[i] != -1 表示arr[i]这个数字还没被拿走
        // arr[i] == -1 表示arr[i]这个数字已经被拿走
        // 集合，arr，1~choose
        return process(arr, total);
    }
    // 当前轮到先手拿，
    // 先手只能选择在arr中还存在的数字，
    // 还剩rest这么值，
    // 返回先手会不会赢
    public static boolean process(int[] arr, int rest) {
        if (rest <= 0) {
            return false;
        }
        // 先手去尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                int cur = arr[i];
                arr[i] = -1;
                boolean next = process(arr, rest - cur);
                arr[i] = cur;
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }
    // 1、使用状态压缩来写的暴力递归
    public boolean canIWin1(int maxChoosableInteger, int desiredTotal) {
        // 题目规定，desiredTotal == 0则先手赢
        if (desiredTotal == 0) {
            return true;
        }
        // 如果可选择的数字总加和都小于desiredTotal，每个数只能选一次，那么无论怎么选，desiredTotal都不可能小于0，所以先手一定输
        if ((1 + maxChoosableInteger) * maxChoosableInteger >> 1 < desiredTotal) {
            return false;
        }
        return process((1 << maxChoosableInteger) - 1, desiredTotal, maxChoosableInteger);
    }

    public boolean process(int status, int rest, int n) {
        // basecase 当rest小于0了，那么当前这个先手肯定就是数了
        if (rest <= 0) {
            return false;
        }
        // 遍历尝试所有的可能选择情况
        for (int i = 0; i < n; i++) {
            if ((status & (1 << i)) == (1 << i)) {
                // 下一层的先手数，就是这一层的后手输，这一层的后手输，就是这一层的先手赢
                if (!process(status & (~(1 << i)), rest - (i + 1), n)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 2、使用状态压缩来写的记忆化搜索
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        // 题目规定，desiredTotal == 0则先手赢
        if (desiredTotal == 0) {
            return true;
        }
        // 如果可选择的数字总加和都小于desiredTotal，每个数只能选一次，那么无论怎么选，desiredTotal都不可能小于0，题目规定这种情况先手输
        if ((1 + maxChoosableInteger) * maxChoosableInteger >> 1 < desiredTotal) {
            return false;
        }
        // 创建dp表，记录所有可能的数组存在情况，每一个数有存在或者不存在两种状态，一共有maxChoosableInteger个数，所以一共有2^maxChoosableInteger种状态，这就是需要开辟dp数组的大小
        int[] dp = new int[1 << (maxChoosableInteger)];
        // (1 << maxChoosableInteger) - 1：将代表每一个数状态的二进制位都设置为1，表示初始存在状态
        return process((1 << maxChoosableInteger) - 1, desiredTotal, maxChoosableInteger, dp);
    }

    // status：利用整形的二进制位来表示每一个数的存在状态，1表示存在，0表示已经被拿取
    // rest：在每一轮取数过程中，desiredTotal还剩下多少
    // n：常数参，表示一共有多少个数供玩家选择
    // dp[status]：记录每一种状态下，先手玩家的胜负。0表示还没有记录过该状态先手玩家的胜负，1表示该状态先手胜利，-1表示该状态先手失败
    // 返回值：在先手和后手都绝顶聪明的情况下，先手会不会赢。如果先手会赢返回true，否则返回false。
    public boolean process(int status, int rest, int n, int[] dp) {
        // 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
        // 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
        // 如果dp中已经记录了该状态的结果，直接返回
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }

        // 当rest > 0时
        if (rest > 0) {
            // 遍历每一个数，找还没有被人拿走的数来进行尝试，将所有可能的选择都尝试一遍
            for (int i = 0; i < n; i++) {
                // (status & (1 << i)) == (1 << i)：说明要拿的数对应的二进制位是1，表示还没有被人拿走，可以尝试将其拿走
                if ((status & (1 << i)) == (1 << i)) {
                    // 将i+1这个数拿走以后，去执行后续的递归，如果后续的递归返回false，表示下层的递归的先手输了，而下层递归的先手就是本层递归的后手，本层递归后手输了，那么就表示本层递归先手赢了。则返回ture
                    // status & (~(1 << i)：将i+1这个数对应的二进制位设置为0，表示已经被拿走
                    // rest - (i + 1)：将rest减掉选择的(i + 1)这个数
                    if (!process(status & (~(1 << i)), rest - (i + 1), n, dp)) {
                        // 将结果记录在dp中。不需要还原现场，因为我们实际并没有修改本层的status
                        dp[status] = 1;
                        return true;
                    }
                }
            }
        }

        // 执行到这里有两种情况：
        // 1、rest < 0，那么回直接跳过上面的for循环直接到这里，当rest小于0，那么先手肯定就是输了
        // 2、上面执行完循环，先手尝试了所有可能的拿法，都没找到能让自己胜利的拿法，则说明先手不管怎么拿都会输
        // 将结果记录在dp中
        dp[status] = -1;
        return false;
    }
}
