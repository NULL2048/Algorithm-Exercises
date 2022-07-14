package 体系学习班.class17;

public class Code01_Hanoi {
    // 最直观的启发式方法
    public static void hanoi1(int n) {
        // 总的目的就是将所有的圆盘按照小的放上，大的放下，从最左边的柱子转移到最右边的柱子
        // 这是总的方法，这个方法中再将总流程拆分成子流程
        leftToRight(n);
    }

    // 请把1~N层圆盘 从左 -> 右
    // 这个将盘子从左放到右的大过程中，其实是由两个子过程完成了，由中柱子作为中转
    // 1、先将左柱子上除了最下面一层的盘子以外的其他所有盘子转移到中柱子，作为中转
    // 2、然后当左柱子只剩下最下面的盘子之后，将这个盘子（也就是最大的盘子）直接放到右柱子上
    // 3、最后再将中柱子上的盘子转移到右柱子上
    public static void leftToRight(int n) {
        // 递归出口，当左柱子上只剩下一个盘子的时候，那么说明它上面的所有比它大的盘子已经转移到了右柱子，这个时候直接将左柱子的盘子移到右柱子即可
        if (n == 1) { // base case
            System.out.println("Move 1 from left to right");
            // 递归结束
            return;
        }
        // 先将左柱子上除了最下面一层的盘子以外的其他所有盘子转移到中柱子，作为中转
        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        // 将中柱子上的盘子移动到右柱子上
        midToRight(n - 1);
    }

    // 请把1~N层圆盘 从左 -> 中
    // 将左柱子移动到中柱子的过程中，也是需要两个子过程完成，由右柱子作为中转
    // 1、先将左柱子上除了最下面一层的盘子以外的其他所有盘子转移到右柱子，作为中转
    // 2、然后当左柱子只剩下最下面的盘子之后，将这个盘子（也就是最大的盘子）直接放到中柱子上
    // 3、最后再将右柱子上的盘子转移到中柱子上
    public static void leftToMid(int n) {
        // 递归出口，当左柱子上只剩下一个盘子的时候，那么说明它上面的所有比它大的盘子已经转移到了中柱子，这个时候直接将左柱子的盘子移到中柱子即可
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        // 先将左柱子上除了最下面一层的盘子以外的其他所有盘子转移到右柱子，作为中转
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        // 将右柱子上的盘子移动到左柱子上
        rightToMid(n - 1);
    }

    // 后面的流程就都是一样的了，总的来说就是将每一个流程都拆分成两个子流程
    // 比如由from位置到to位置，那么这个大的操作其中的两个子流程就是：
    // 1、先将from上除了最下面一层的盘子以外的其他所有盘子从from 到 other，作为中转
    // 2、再将from上的最下面的盘子转移到to上（递归出口）
    // 3、最后将other上的盘子转移到to上

    // 也就是将涉及到的每一个方法都相互嵌套两个子流程，不用去管微观的东西，重点关注宏观的过程，这就是递归的本质意义
    // 本质就是将所有涉及到的行为全都写一遍，左到中，左到右，中到左，中到右，右到左，右到中。
    // 这个就是最直观的启发性算法，在这个思路的基础上，我们还可以继续做优化
    public static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }

    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);
    }

    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n - 1);
    }


    // 汉诺塔优化——在上面方法的思路上，将所有的过程抽象出来，简化成一个方法
    public static void hanoi2(int n) {
        if (n > 0) {
            // 总方法，将左柱子上的盘子，转移到右柱子上，以中柱子作为中转
            func(n, "left", "right", "mid");
        }
    }

    // 流程方法  将盘子从from柱子上，转移到to柱子上，以other为中转柱子
    // 通过这个方法，就将上面所有的过程都给抽象了出来，仅仅通过递归传参的不同，就组合出了不同的操作流程
    public static void func(int N, String from, String to, String other) {
        // 递归出口
        if (N == 1) {
            // 当左柱子只剩下最下面的盘子之后，将这个盘子（也就是最大的盘子）直接放到中柱子上
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            // 先将from上除了最下面一层的盘子以外的其他所有盘子从from 到 other，作为中转
            func(N - 1, from, other, to);
            System.out.println("Move " + N + " from " + from + " to " + to);
            // 最后将other上的盘子转移到to上
            func(N - 1, other, to, from);
        }
    }

}
