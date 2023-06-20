package 体系学习班.class18;

public class Code02_CardsInLine {
    // 1、暴力递归
    // 根据规则，返回获胜者的分数
    public static int win1(int[] arr) {
        // 传入数组为空，直接返回0
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 在arr数组已经确定的情况下，先手能获得的最大分数
        int first = f1(arr, 0, arr.length - 1);
        // 在arr数组已经确定的情况下，每次都是先手先用最优解去抽纸牌，后手能获得的最大分数
        int second = g1(arr, 0, arr.length - 1);
        // 返回两者最大的分数，也就是最终获胜者的分数
        return Math.max(first, second);
    }

    // arr[L..R]，先手获得的最好分数返回
    // 说一下这个游戏先手抽一张牌，后手抽一张牌，也就是两个人各抽一次记为一轮
    // 而下面f和g这两个函数是以每一轮为一个时间单位来描述行为的，也就是说这两个函数表示的是在一轮游戏（两次抽取，此时谁先拿谁就是先手，先手是谁并不固定）最开始，根据此时的纸牌情况返回两人各自能抽取的最大值
    public static int f1(int[] arr, int L, int R) {
        // 当这一轮开始，当只剩下一张牌后，那么这张牌一定就被先手抽走了，所以将最后这张盘直接返回给先手
        if (L == R) {
            return arr[L];
        }
        // 注意，当前方法指的是每一轮游戏的开始，也就是此时先手和后手都没有抽牌，而这个f方法就是用来表示先手抽牌的情况
        // 先手总共就有两种情况，一种是抽取左边的牌，一种是抽取右边的牌，所以就将两种情况都记录一遍

        // 情况一：本轮先手A抽取最左边的牌，然后后面的游戏这个先手A就变成后手了，因为后面就是在剩下的arr[L+1...R]纸牌中，让B先抽，A再抽
        // 所以，再将后续A作为后手能获取到的最大分数返回，加到arr[L]上，就是将g1函数先看做一个黑盒，我们这里只关注当A先手抽取最左边获得的分手，
        // 假定A后续的总得分会被g1函数都返回回来。其实在g1中会继续递归，将A所有的抽取情况都暴力的找出来进行计算。
        int p1 = arr[L] + g1(arr, L + 1, R);
        // 情况二：这个和上面的一样，只不过这里假定先手A取得是右边的牌
        int p2 = arr[R] + g1(arr, L, R - 1);

        // 上面的两个递归，就会分别以本层递归可能的两种情况，选左边的牌和右边的牌为起点，将后续全部有可能的情况都递归列出来，然后判断最大值返回出来
        // 所以整个过程本质就是暴力模拟递归

        // 返回两种情况中最大的一个
        return Math.max(p1, p2);
    }

    // arr[L..R]，后手获得的最好分数返回
    public static int g1(int[] arr, int L, int R) {
        // 当这一轮开始时，只剩下一张牌了，那么这张牌一定是被先手拿走，后手就获取不到牌了，所以这里返回0
        if (L == R) {
            return 0;
        }

        // 这里注意一下f方法的含义，就是以全力以赴的拿到指定范围纸牌中最好的一个
        // 所以下面这个f1其实作用的是后手，就是说当先手拿了L位置牌之后，后手通过调用f1方法，来假设后手全力以赴拿到最好的纸牌
        // 所以就假设先手拿了L和R时两种情况，对应后手能拿到的最好的纸牌
        // 找到了p1和p2之后，我们就要取最小值，为什么呢？
        // 这是因为先手也是聪明绝顶的，先手肯定会选一张排，然后让后手拿到的牌的大小最小
        // 所以后手能拿到的两种情况p1和p2中，一定拿到的是最小的那个，因为先手会让后手拿到最小的

        // 情况一：对手（先手）拿走了L位置的数，这里之所以调取f1，因为当先手A拿走了L位置后，纸牌就执行下L+1...R了，然后后手B在剩下的这些牌中取，其实此时B就变成了先手了，所以这里调取f1函数就是表示B在剩下的牌中作为先手能获取到的最大分数
        int p1 = f1(arr, L + 1, R);
        // 情况二：对手（先手）拿走了R位置的数，这里也是同理，调取f1函数就是表示B在剩下的牌中作为先手能获取到的最大分数
        int p2 = f1(arr, L, R - 1);
        // 但是题目已经说了，两个人都是绝顶聪明的，所以先手A一定会想办法让后手B拿到最小的数值，所以先手A的取法一定会让后手B能获取到最小的数
        // 所以这里就返回最小的那个数
        return Math.min(p1, p2);
    }

    // 2、加缓存法
    public static int win2(int[] arr) {
        // 判空
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 创建先手和后手的dp数组
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        // 先给数组进行初始化，都设置成-1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        // 后面就是和暴力递归的流程一样，只不过入参带着两个dp数组
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        // 返回最后胜利者的分数
        return Math.max(first, second);
    }

    // arr[L..R]，先手获得的最好分数返回
    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        // 先去从先手的dp数组中找有没有现成的答案，有的话直接返回
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        // 没有现成答案就继续按照暴力递归的方式进行求解
        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        // 将计算的值存入dp数组中
        fmap[L][R] = ans;
        return ans;
    }

    // arr[L..R]，后手获得的最好分数返回
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        // 先去从后手的dp数组中找有没有现成的答案，有的话直接返回
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = 0;
        if (L != R) {
            int p1 = f2(arr, L + 1, R, fmap, gmap); // 对手拿走了L位置的数
            int p2 = f2(arr, L, R - 1, fmap, gmap); // 对手拿走了R位置的数
            ans = Math.min(p1, p2);
        }
        // 将计算的值存入dp数组中
        gmap[L][R] = ans;
        return ans;
    }

    // 3、经典动态规划
    // 在加缓存写法的基础上进行动态规划修改
    public static int win3(int[] arr) {
        // 判空
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 创建先手和后手的dp数组
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        // 先对对角线赋初值
        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }

        // 再去沿着斜对角线的方向来对dp数组进行赋值，将所有的情况的值都提前计算出来存入数组中
        // 第一层循环是设置对角线的最开始位置，第一条对角线是从第一列开始的
        for (int startCol = 1; startCol < N; startCol++) {
            // 所有的对角线都是从第0行开始
            int L = 0;
            // 设置每一次对角线开始的列
            int R = startCol;
            // 从设定的最开始位置开始沿着对角线进行赋值
            while (R < N) {
                // 下面就是我们找到的状态转移方程
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]); // 取最大值
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]); // 取最小值
                // 对坐标++
                L++;
                R++;
            }
        }
        // 根据暴力递归中的写法，我们知道了这道题想要哪一个位置的结果[0][N - 1]
        // 所以返回先手和后手对应位置的最大值
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

    }

}
