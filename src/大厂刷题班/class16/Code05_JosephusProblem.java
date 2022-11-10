package 大厂刷题班.class16;

// 数学   递归
// 本题测试链接 : https://leetcode.cn/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
public class Code05_JosephusProblem {
    // 1、最优解，迭代版本
    public int lastRemaining(int n, int m) {
        // 当只剩下一个幸存者的时候，整个数组只剩下他一个人，所以他的下标一定是0
        int ans = 0;
        // 就从最后幸存者的0下标开始向上反推，推出在最开始数组中还有n个人的时候，这个幸存者在当时所在的位置下标是什么
        // 最后一轮剩下2个人，所以从2开始反推
        for (int i = 2; i <= n; i++) {
            // 根据笔记的图中可以看出，每次反推幸村位置都会向右移动m个位置，防止溢出，所以最后还要模一下当前轮数组中还剩下的人数i
            ans = (ans + m) % i;
        }
        return ans;
    }

    // 2、递归版本
    public int lastRemaining1(int n, int m) {
        return JosephusProblem(n, m);
    }
    public int JosephusProblem(int n, int m) {
        if (n == 1) {
            return 0;
        }

        return (JosephusProblem(n - 1, m) + m) % n;
    }

}
