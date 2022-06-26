package 体系学习班.class14;

import java.util.Comparator;
import java.util.PriorityQueue;

// 测试链接：https://leetcode.cn/problems/ipo/submissions/
public class Code03_IPO {
    // 项目类
    public static class Program {
        public int profit;
        public int capital;

        public Program(int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }
    }

    // 对成本做小根堆
    public static class ProgramComparatorCapital implements Comparator<Program> {
        @Override
        public int compare(Program a, Program b) {
            return a.capital - b.capital;
        }
    }

    // 对利润做大根堆
    public static class ProgramComparatorProfit implements Comparator<Program> {
        @Override
        public int compare(Program a, Program b) {
            return b.profit - a.profit;
        }
    }

    // 使用贪心求解
    // 过程就是先按照成本从小到大排列好，然后将所有当前自己可以做的项目拿出来
    // 将这些项目拿出来之后再按照利润从大到小排序，选利润最大的那个做
    // 按照这个流程直到昨晚k个项目或者资金已经不足以再做项目了即可结束，最后得到最大的利润
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        // 判空直接返回0
        if (profits == null || capital == null || profits.length == 0 || capital.length == 0) {
            return 0;
        }

        // 创建成本的小根堆和利润的大根堆
        PriorityQueue<Program> pqCapital = new PriorityQueue<>(new ProgramComparatorCapital());
        PriorityQueue<Program> pqProfit = new PriorityQueue<>(new ProgramComparatorProfit());

        // 先将所有的项目加入到成本小根堆中
        for (int i = 0; i < profits.length; i++) {
            Program p = new Program(profits[i], capital[i]);
            pqCapital.add(p);
        }

        // 记录已经做了多少个项目了
        int cnt = 0;
        // 当已经做了k个项目则结束循环
        while (cnt < k) {
            // 将成本小根堆中所有在现有资金下可以进行的项目全部弹出放入到利润大根堆中
            // 当小根堆为空或者现有资金下已经没有项目能做，则结束循环
            while (!pqCapital.isEmpty() && pqCapital.peek().capital <= w) {
                Program p = pqCapital.peek();
                // 只要是当前资金超过这个项目的成本就弹出放入到大根堆中
                pqCapital.poll();
                pqProfit.add(p);
            }

            // 将利润大根堆中的堆顶弹出，我们在所有可以做的项目中选出利润最大的项目来做
            Program doProgram = pqProfit.poll();
            // 如果大根堆中弹出项目，则更新现有资金，并且记录上已经做的项目个数
            if (doProgram != null) {
                cnt++;
                w += doProgram.profit;
                // 如果大根堆中已经没有项目弹出了，说明所有的可以做的项目已经做完了，则直接结束循环
            } else {
                break;
            }
        }

        // 返回最大利润
        return w;
    }
}
