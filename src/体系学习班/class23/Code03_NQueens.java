package 体系学习班.class23;

public class Code03_NQueens {
    // 1、方法1
    public static int num1(int n) {
        // 判空
        if (n < 1) {
            return 0;
        }
        // 用来记录每一层放值皇后的坐标  数组下标：表示横轴坐标   数组值：表示纵轴坐标
        int[] record = new int[n];
        // 递归入口
        return process1(0, record, n);
    }
    // i：当前来到i行，一共是0~N-1行。在i行上放皇后，所有列都尝试，每一层递归就是对某一行所有位置进行尝试，必须要保证跟之前所有的皇后不打架
    // record：记录已经防止皇后的位置，int[] record record[x] = y 之前的第x行的皇后，放在了y列上
    // n：有n个皇后，棋盘是n*n
    // 返回：不关心i以上发生了什么，i.... 后续有多少合法的方法数
    public static int process1(int i, int[] record, int n) {
        // basecase
        if (i == n) {
            // 当遍历完了所有层，这个过程中没有中断，就说明当前这个分支的决策方法是有效的，这就是一个符合要求的摆放方法，返回0
            // 之所以没有为无效情况设置basecase，是因为在递归调用位置，已经去做了无效判断了，如果发现是无效情况的话，直接在递归过程中就给中断掉了，不会再一直递归到底了，所以就没有设置无效情况的basecase
            return 1;
        }
        // 当前这一层递归的有效决策数
        int res = 0;
        // i行的皇后，放哪一列呢？尝试放到j列
        for (int j = 0; j < n; j++) {
            // 这里就是尝试过程中的无效检测，如果尝试的这个位置本身就是一个无效位置，那么直接就不会进入向下的递归，也就中断了。
            if (isValid(record, i, j)) {
                // 如果当前尝试的这个位置有效，那么就将这个位置设置成为皇后放置位置，然后以这个位置为基准，继续向下层递归，去下一行i+1尝试
                record[i] = j;
                // 将本行所有有效的方法数进行累加
                res += process1(i + 1, record, n);
            }
        }
        // 返回方法数
        return res;
    }
    // 判断当前选择的这个摆放位置是不是有效的，会不会和已有的皇后起冲突
    public static boolean isValid(int[] record, int i, int j) {
        // 0..i-1
        // 传入的i和j就是要尝试位置的坐标，这个方法就是看这个坐标是不是有效的
        // 遍历尝试的这一层以上的所有放置的皇后位置，然后去看尝试位置是不是与这些皇后位置冲突
        for (int k = 0; k < i; k++) {
            // 不用考虑行冲突，因为已经限定死了，每一行有且只能放一个皇后
            // 只要纵坐标不相等，这两个位置就没有列冲突
            // 去检查两个位置的横纵坐标相减的绝对值是不是一样，如果一样说明两个位置在同一条斜线上，就算是起冲突了。其实这个本质就是算两个位置与远点连线的斜率是否一致
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }
    // 方法2：使用位运算进行优化，优化了常数时间，但是时间复杂度和方法1是一样的
    // 请不要超过32皇后问题
    public static int num2(int n) {
        // 判空
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit 最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        // 递归入口
        return process2(limit, 0, 0, 0);
    }
    // 7皇后问题
    // limit : 0....0 1 1 1 1 1 1 1
    // 之前皇后的列影响：colLim
    // 之前皇后的左下对角线影响：leftDiaLim
    // 之前皇后的右下对角线影响：rightDiaLim
    public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        // basecase，如果colLim和limit相等，说明所有的行都已经放置了皇后且不冲突，就算是找到了一种放置方法，返回方法数1
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，就是你可以去尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        // 找到二进制位最右边的1
        int mostRightOne = 0;
        // 当前这一行的所有可行的方法数
        int res = 0;
        // 尝试这一行的所有列  二进制位1是可以尝试的位置  0是不可以尝试的位置
        while (pos != 0) {
            // 去找最右边的1，也就是最右边可以尝试的位置
            mostRightOne = pos & (~pos + 1);
            // 将这个1减掉，也就是说尝试在这个位置放置，那么这个位置以后就不能放置了，这个减操作将这个位置变为0了
            pos = pos - mostRightOne;
            // 继续向下递归   将列、左下和右下影响进行或操作，得到的就是对之后皇后总的影响位置
            // mostRightOne也就相当于当前尝试的新的放置皇后的位置，将mostRightOne和leftDiaLim取或，然后左移一位，就是更新新的左下影响，最新位置mostRightOne的左下影响肯定也是其正下方位置向左移动一位，所以这里就将他和leftDiaLim取或，然后一起左移了
            // 右下影响同理，右下影响是无符号位右移。之所以需要位移，是因为斜线的影响是沿着一定角度向下延伸的，随着行向下它的影响位置也在左移或右移
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        // 返回结果
        return res;
    }
    public static void main(String[] args) {
        int n = 15;
        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }
}
