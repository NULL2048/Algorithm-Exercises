package 体系学习班.class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Code03_Coffee {
    // 1、暴力递归
    // 以下为贪心+优良暴力递归
    // 第一部分：求每个人泡完咖啡的最优时间点
    // 用于存入小根堆的咖啡机信息类，记录了咖啡机什么时间点可以用和当前咖啡机泡一杯咖啡需要多长时间
    public static class Machine {
        // 咖啡机可以使用的时间点   是一个变量
        public int timePoint;
        // 咖啡机泡一杯咖啡需要用多长时间    是一个不变的常量
        public int workTime;
        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }
    // 小根堆的比较器，是用 咖啡机可以使用的时间点 + 咖啡机泡完一杯要多久 来进行从小到大的排序
    public static class MachineComparator implements Comparator<Machine> {
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }


    /**
     * 优良一点的暴力尝试的方法
     * arr[]：每一个咖啡机泡一杯咖啡所需要的时间
     * n：有n个人
     * a：洗杯子的时间
     * b：杯子挥发的时间
     */
    public static int minTime1(int[] arr, int n, int a, int b) {
        // 创建小根堆，小根堆中存储的是咖啡机的信息类
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        // 初始化小根堆，将每一个咖啡机的信息都压入小根堆，并且按照指定的排序规则排序。此时每一个咖啡机的可用时间点都是0
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        // drinks[]：所有杯子可以开始洗的时间点
        int[] drinks = new int[n];
        // 进行贪心操作
        // 每次从小根堆中弹出一个对象，说明此时要安排一个人用弹出的这个咖啡机来泡咖啡
        // 因为小根堆堆顶的咖啡机，一定是当前所有的咖啡机中，可以让人在最早泡完咖啡的一台咖啡机。（因为小根堆是按照咖啡机可以使用的时间点 + 咖啡机泡完一杯要多久来排序的）
        // 一共有n个人，也就是有n个咖啡杯，所以需要遍历n此，将计算好的数据复制到drinks数组中
        for (int i = 0; i < n; i++) {
            // 从小根堆中弹出堆顶对象，说明此时要安排一个人用这个咖啡机来泡咖啡
            Machine cur = heap.poll();
            // 将弹出对象的咖啡机可以使用的时间点 + 咖啡机泡完一杯要多久，得到的值就是现在一个人用这个咖啡机跑完咖啡后，这个咖啡机新的可以使用的时间点
            cur.timePoint += cur.workTime;
            // 这个咖啡机新的可以使用的时间点也是当前这个人喝完咖啡，可以去洗咖啡杯的时间点
            drinks[i] = cur.timePoint;
            // 将更新好的咖啡机信息对象再次压回小根堆中
            heap.add(cur);
        }
        // 至此，所有杯子可以开始洗的最优时间就存到了drinks[]数组中，再去使用暴力递归去进行第二部分的求解
        return bestTime(drinks, a, b, 0, 0);
    }
    // 第二部分：现在有了每一个人有序喝完咖啡的最优解数组，怎么洗咖啡杯才能是所有咖啡杯洗干净的时间点最早?

    /**
     * 暴力递归过程，将所有可能的情况都暴力模拟一边，将所有可能的选择分支都尝试一遍，最后取最优解
     *
     * drinks 所有杯子可以开始洗的时间点
     * wash 单杯洗干净的时间（串行）
     * air 挥发干净的时间(并行)
     * index 当前判断到哪个杯子了
     * free 洗的机器什么时候可用
     *
     * drinks[index.....]都变干净，最早的结束时间（返回）
     */
    public static int bestTime(int[] drinks, int wash, int air, int index, int free) {
        // basecase  当index已经遍历完整个杯子的数组了，这个时候就返回。
        // 因为返回0之后，在上一层的选择的时候是取selfClean和resrClean中的最大值，所以并不会影响最后的结果。
        if (index == drinks.length) {
            return 0;
        }
        // 决定一：index号杯子 决定洗
        // 计算如果当前index号杯子决定水洗，那么它的洗完时间点是多少
        // 这里需要先对杯子可以去洗的时间点和洗咖啡杯的机器空闲时间点作比较，选取最大值 去累加wash，这一点很好理解
        // 举个例子：
        // 1、一个人在时间点27的时候要洗咖啡杯，咖啡机在时间点12就可以空闲可用，但人在时间点27喝完，所以应该取比较大的那个值取加wash，结束时间应该是27 + 3
        // 2、一个人在时间点27就喝完了，但是洗杯机的空闲时间free到时间点100才可用，则结束时间点应该选择大的那个数100取累加wash，应该是100+3才可洗完
        int selfClean1 = Math.max(drinks[index], free) + wash;
        // 去归计算剩下的杯子全部变干净的时间点   这里将index+1传入，因为在这一层递归中Index就已经洗完了，然后将selfClean1作为洗杯机新的free时间点去进行计算。思路也是讲大块的问题递归分解成一块块小问题去解决，最后再向上层返回合并结果
        int restClean1 = bestTime(drinks, wash, air, index + 1, selfClean1);
        // selfClean1为index号杯子洗完的时间点
        // restClean1为index号以后的杯子全部都变干净的时间点
        // 决定一的结果就在这两个值中取最小值
        // 举一个例子：
        // 1、当前index杯子，洗完咖啡机在时间点100,剩下的杯子在咖啡机在时间点100才有空,去决策,剩下杯子都洗完的时间是300 ==> 都洗完的时间点是300
        // 2、当前index杯子，洗完咖啡机在时间点1000,剩下的杯子在咖啡机在时间点1000才有空,去决策,剩下杯子都洗完的时间是300,但是如果剩下的杯子都去挥发，在1000之前也全挥发了 ==> 都洗完的时间点是1000
        int p1 = Math.max(selfClean1, restClean1);

        // 决定二：index号杯子 决定挥发
        // 这个决定就比较简单了，因为决定去挥发就不需要用到洗杯机，所以挥发之前free是多少，挥发之后free就是多少
        // 计算如果当前index号杯子决定挥发，那么它的挥发完时间点是多少
        // 直接用被子可以开始洗的时间点 + 挥发的时间，就是最后这个杯子挥发完的时间
        int selfClean2 = drinks[index] + air;
        // 去递归计算剩下的杯子全部变干净的时间点   这里也是将index+1传入，然后free不变。后续的决策就也是可以选择机器洗或者挥发，这里就会给我们返回一个最后的结果
        int restClean2 = bestTime(drinks, wash, air, index + 1, free);
        // selfClean2为index号杯子挥发完的时间点
        // restClean2为index号以后的杯子全部都变干净的时间点
        // 决定二的结果就在这两个值中取最小值
        int p2 = Math.max(selfClean2, restClean2);
        // 我们这个是要求一个最优解，所以在这两种决策结果中取最小值返回
        return Math.min(p1, p2);
    }

    // 2、动态规划
    // 贪心+优良尝试改成动态规划
    // 第一部分：求每个人泡完咖啡的最优时间点
    // 求每个人泡完咖啡的最优时间点的方法是没有变化的，还是用贪心结合小根堆去求解
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int i = 0; i < arr.length; i++) {
            heap.add(new Machine(0, arr[i]));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        // 调用动态规划的方法
        return bestTimeDp(drinks, a, b);
    }

    // 第二部分：现在有了每一个人有序喝完咖啡的最优解数组，怎么洗咖啡杯才能是所有咖啡杯洗干净的时间点最早?
    // 动态规划
    public static int bestTimeDp(int[] drinks, int wash, int air) {
        // 首先通过暴力递归的传参去分析可变参数，可以知道可变参数只有两个index(当前遍历到哪个杯子了)和free(洗杯机空闲可用的时间点)
        // index: 0——N-1
        // free:咖啡机所有可能的空闲的时间点
        // 我们发现index的范围比较好找到，但是free可变参数的变化范围分析不出来
        // 这个时候我们就可以结合题意，利用业务内容逻辑，看看它最夸张的值是多少
        // 根据我们的业务，最夸张的时候就是所有的杯子全都用机器洗，最后的free一定是最大的，下面就根据这个来计算maxFree
        int N = drinks.length;
        int maxFree = 0;
        // 所有的杯子都用机器洗，得到maxFree
        for (int i = 0; i < drinks.length; i++) {
            // 从洗杯机9可用时间点和杯子可以去洗的时间点中选取最大值去加wash
            // 最后将maxFree累加出来
            maxFree = Math.max(maxFree, drinks[i]) + wash;
        }
        // 得到么maxFree之后，就可以去创建dp数组了，注意如果free的值最大能到maxFree，那么数组应该创建到maxFree才行
        int[][] dp = new int[N + 1][maxFree + 1];
        // 通过暴力递归的代码中basecase去给dp赋初值，我们发现当index == drinks.length时，值都是0，这个就是我们的初值。但是创建dp数组后所有的元素默认都是0，所以就不用再赋值了。
        // 最后根据暴力递归中找出的位置依赖关系，来对dp数组进行赋值
        // 通过上面的递归调用，我们发现每一层的数据（index）依赖于上一层的数据（index + 1），在同一层之间的数据没有依赖关系，所以我们就根据这个去进行复制
        // 上面的行依赖下面的行，并且我们已经将最下面的行赋初值了（index == drinks.length），所以我们就从下往上赋值
        // 赋值方向是从下往上，从左往右
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                // 下面的赋值逻辑就是直接赋值暴力递归中的递归调用代码，稍微修改成给dp赋值的代码即可，不用深入分析是为什么。
                // 决定一：当前index号杯子用机器洗
                int selfClean1 = Math.max(drinks[index], free) + wash;
                // 这里需要做一个越界判断，详细已经在笔记里有了
                if (selfClean1 > maxFree) {
                    break; // 因为后面的也都不用填了
                }
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // 决定二：前index号杯子去挥发
                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                // 将两种决定的最小值赋值给当前位置的dp
                dp[index][free] = Math.min(p1, p2);
            }
        }
        // 根据暴力递归的入口，入参为0，0知道应该返回dp[0][0]，这就是最后的结果
        return dp[0][0];
    }


    // for test
    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }
    // for test
    // 每个人暴力尝试用每一个咖啡机给自己做咖啡
    public static int forceMake(int[] arr, int[] times, int kth, int[] drink, int n, int a, int b) {
        if (kth == n) {
            int[] drinkSorted = Arrays.copyOf(drink, kth);
            Arrays.sort(drinkSorted);
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            int work = arr[i];
            int pre = times[i];
            drink[kth] = pre + work;
            times[i] = pre + work;
            time = Math.min(time, forceMake(arr, times, kth + 1, drink, n, a, b));
            drink[kth] = 0;
            times[i] = pre;
        }
        return time;
    }
    // for test
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, wash, Math.max(wash, time));
        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);
    }
    // for test 对数器
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }
    // for test 对数器
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
            int ans2 = minTime1(arr, n, a, b);
            int ans3 = minTime2(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                printArray(arr);
                System.out.println("n : " + n);
                System.out.println("a : " + a);
                System.out.println("b : " + b);
                System.out.println(ans1 + " , " + ans2 + " , " + ans3);
                System.out.println("===============");
                break;
            }
        }
        // 如果没有错误样例信息的输出，就说明代码是正确的。
        System.out.println("测试结束");
    }
}
