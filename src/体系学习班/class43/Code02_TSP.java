package 体系学习班.class43;

import java.util.ArrayList;
import java.util.List;

public class Code02_TSP {
    // 1、没有状态压缩的暴力递归
    public static int t1(int[][] matrix) {
        // 记录有几个城市
        int N = matrix.length; // 0...N-1
        // set：用来记录还剩下哪些城市供我们选择
        // set.get(i) != null i这座城市在集合里
        // set.get(i) == null i这座城市不在集合里
        // 使用List，这样可以直接用下标找对应的城市
        List<Integer> set = new ArrayList<>();
        // 一开始先将所有城市加入到set中
        for (int i = 0; i < N; i++) {
            set.add(1);
        }
        // 暴力递归，我们将源起始点设置为0城市。这里选什么都无所谓，因为本身最终就是要求一个环，从哪个城市开始都不影响最后的结果。
        return func1(matrix, set, 0);
    }
    // 任何两座城市之间的距离，可以在matrix里面拿到
    // set中表示着还剩下哪些可以选择的城市集合，没在set中的城市就是已经选择过了。
    // start这座城一定在set里，
    // 返回值：从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
    public static int func1(int[][] matrix, List<Integer> set, int start) {
        // 统计还剩下多少个城市可供选择
        int cityNum = 0;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i) != null) {
                cityNum++;
            }
        }
        // basecase 如果只剩下start一个城市了，那么肯定就是将这个城市连接源起始点0即可，返回两点的举例
        if (cityNum == 1) {
            return matrix[start][0];
        }
        // cityNum > 1  不只start这一座城
        // 将start选择上，将其在set中置为空
        set.set(start, null);
        int min = Integer.MAX_VALUE;
        // 去尝试所有剩余的可能，选择一个点作为下一个路径上的点去做递归
        for (int i = 0; i < set.size(); i++) {
            // 如果这个点还在set中，说明它还没被选择
            if (set.get(i) != null) {
                // func1(matrix, set, i)：选择了当前i城市后，以i为起始点，从剩余的set中再做选择尝试。注意,i从set中删除是在下一层递归中实现的，返回的是从i开始，选择路程的最短距离
                // 再加上这一层start到i的距离matrix[start][i]
                // cur就是当前这个选择尝试下start -> i，i... -> 0的距离
                int cur = matrix[start][i] + func1(matrix, set, i);
                // 更新当前从start开始所有可能选择中的距离最小的
                min = Math.min(min, cur);
            }
        }
        // 本层尝试完了，还要记得还原set现场，这一类型尝试的题在递归尝试结束时都要还原现场，因为它的上一层递归还要去进行别的尝试，不能让本层尝试的结果影响上一层原本的状态
        set.set(start, 1);
        // 返回最小值
        return min;
    }
    // 2、状态压缩的暴力递归
    public static int t2(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        // 整体的思路还是和暴力递归一样，只不过把上面暴力递归的set转换成了用二进制位表示
        // 上面的set的作用无非也就是表示一个城市存在或者不存在，只需要表示两个状态就可以了，所以直接用二进制位0和1即可代替，这就实现了状态压缩
        // 例如：7座城 1111111   1表示还可以选
        int allCity = (1 << N) - 1;
        return f2(matrix, allCity, 0);
    }
    // 任何两座城市之间的距离，可以在matrix里面拿到
    // set中表示着哪些城市的集合，
    // start这座城一定在set里，
    // 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
    public static int f2(int[][] matrix, int cityStatus, int start) {
        // cityStatus == cityStatux & (~cityStaus + 1)
        // cityStatux & (~cityStaus + 1)：将cityStatux二进制形式最右边的1取出来
        // 如果将cityStatux二进制形式最右边的1取出来得到的数等于cityStatus，那么就说明cityStatus的二进制就只剩下一个1了，也就是此时只剩下一个还没有选择的城市了
        // basecase  直接返回还剩下的这个城市到0的距离
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            return matrix[start][0];
        }
        // 把start位的1去掉，表示将start位置选择进来
        cityStatus &= (~(1 << start));
        int min = Integer.MAX_VALUE;
        // 枚举所有的城市
        for (int move = 0; move < matrix.length; move++) {
            // 将所有还可以选择的城市，进行尝试选择向下递归
            if ((cityStatus & (1 << move)) != 0) {
                int cur = matrix[start][move] + f2(matrix, cityStatus, move);
                // 找到当前这一层所有尝试的最小距离
                min = Math.min(min, cur);
            }
        }
        // 这里需要还原现场，如果我们并不对cityStatus进行修改赋值的话就根本不需要还原现场，直接在上面现计算，现使用即可，就直接用这个式子cityStatus & (~(1 << start))，而不是把这个式子计算的结果赋值给cityStatus
        cityStatus |= (1 << start);
        return min;
    }
    // 3、改为状态压缩版本的记忆化搜索
    public static int t3(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        // 7座城 1111111
        int allCity = (1 << N) - 1;
        // 上面暴力递归中一共有两个可变参数cityStatus和start
        int[][] dp = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                dp[i][j] = -1;
            }
        }
        return f3(matrix, allCity, 0, dp);
    }
    // 任何两座城市之间的距离，可以在matrix里面拿到
    // set中表示着哪些城市的集合，
    // start这座城一定在set里，
    // 从start出发，要把set中所有的城市过一遍，最终回到0这座城市，最小距离是多少
    public static int f3(int[][] matrix, int cityStatus, int start, int[][] dp) {
        // 如果缓存中有答案就直接取
        if (dp[cityStatus][start] != -1) {
            return dp[cityStatus][start];
        }
        // basecase
        if (cityStatus == (cityStatus & (~cityStatus + 1))) {
            dp[cityStatus][start] = matrix[start][0];
        } else {
            // 把start位的1去掉，
            cityStatus &= (~(1 << start));
            int min = Integer.MAX_VALUE;
            // 枚举所有的城市
            for (int move = 0; move < matrix.length; move++) {
                if ((cityStatus & (1 << move)) != 0) {
                    int cur = matrix[start][move] + f3(matrix, cityStatus, move, dp);
                    min = Math.min(min, cur);
                }
            }
            cityStatus |= (1 << start);
            dp[cityStatus][start] = min;
        }
        return dp[cityStatus][start];
    }
    // 4、改为动态规划
    public static int t4(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        int statusNums = 1 << N;
        int[][] dp = new int[statusNums][N];
        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {
                if ((status & (1 << start)) != 0) {
                    if (status == (status & (~status + 1))) {
                        dp[status][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;
                        // start 城市在status里去掉之后，的状态
                        int preStatus = status & (~(1 << start));
                        // start -> i
                        for (int i = 0; i < N; i++) {
                            if ((preStatus & (1 << i)) != 0) {
                                int cur = matrix[start][i] + dp[preStatus][i];
                                min = Math.min(min, cur);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }
        return dp[statusNums - 1][0];
    }

    // for test
    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }
    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        System.out.println("功能测试开始");
        for (int i = 0; i < 20000; i++) {
            int[][] matrix = generateGraph(len, value);
            int origin = (int) (Math.random() * matrix.length);
            int ans1 = t3(matrix);
            int ans2 = t4(matrix);
            //int ans3 = tsp2(matrix, origin);
            if (ans1 != ans2 ) {
                System.out.println("fuck");
            }
        }
        System.out.println("功能测试结束");
        len = 22;
        System.out.println("性能测试开始，数据规模 : " + len);
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * value) + 1;
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        long start;
        long end;
        start = System.currentTimeMillis();
        t4(matrix);
        end = System.currentTimeMillis();
        System.out.println("运行时间 : " + (end - start) + " 毫秒");
        System.out.println("性能测试结束");
    }
}
