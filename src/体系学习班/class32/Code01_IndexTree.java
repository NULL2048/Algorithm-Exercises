package 体系学习班.class32;

public class Code01_IndexTree {
    // 下标从1开始！
    public static class IndexTree {
        // IndexTree的辅助数组
        private int[] tree;
        // 辅助数组中有多少个用于存放数的位置
        private int N;

        // 0位置弃而不用！
        public IndexTree(int size) {
            N = size;
            // 初始化辅助数组
            tree = new int[N + 1];
        }

        // 1~index 累加和是多少？
        public int sum(int index) {
            // 记录累加和
            int ret = 0;
            // 利用规律二来求累加和
            while (index > 0) {
                // 累加上所有涉及到的位置
                ret += tree[index];
                // index向左边移动，移动规则：把index二进制的最后一个1抹去
                index -= index & -index;
            }
            return ret;
        }

        // 当原始数组index位置的数增加了d，就对辅助数组tree中受影响的数也去增加d
        // index & -index : 提取出index最右侧的1出来
        // index :           0011001000
        // index & -index :  0000001000
        public void add(int index, int d) {
            // 利用规律三来实现更新辅助数组trre
            while (index <= N) {
                // 每一个受影响的tree都加d
                tree[index] += d;
                // index向右移动，移动规则把index二进制最后一个1再加一，也就是把最右边的1向前再进一位
                index += index & -index;
            }
        }
    }

    // for test
    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }

    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(N);
        Right test = new Right(N);
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * V);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
        System.out.println("test finish");
    }

}
