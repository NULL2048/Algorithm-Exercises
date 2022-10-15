package 大厂刷题班.class06;

// 前缀树   子数组    贪心
public class Code01_MaxXOR {
    // 1、暴力
    // O(N^2)
    public static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 准备一个前缀异或和数组eor
        // eor[i] = arr[0...i]的异或和结果
        int[] eor = new int[arr.length];
        eor[0] = arr[0];
        // 生成eor数组，eor[i]代表arr[0..i]的异或和。这样就以每一个位置为结尾的异或和全部求出了
        for (int i = 1; i < arr.length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        int max = Integer.MIN_VALUE;
        // 如果 a ^ b = c，那么a = c ^ b，b = c ^ a
        for (int j = 0; j < arr.length; j++) {
            // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]，求出所有区间范围的异或和，取最大值
            for (int i = 0; i <= j; i++) {
                max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
            }
        }
        return max;
    }

    // 2、利用前缀树结构进行优化
    // 前缀树的Node结构
    // nexts[0] -> 0方向的路
    // nexts[1] -> 1方向的路
    // nexts[0] == null 0方向上没路！
    // nexts[0] != null 0方向有路，可以跳下一个节点
    // nexts[1] == null 1方向上没路！
    // nexts[1] != null 1方向有路，可以跳下一个节点
    public static class Node {
        public Node[] nexts = new Node[2];
    }

    // 基于本题，定制前缀树的实现
    public static class NumTrie {
        // 头节点
        public Node head = new Node();

        // 添加操作，构造前缀树
        public void add(int newNum) {
            Node cur = head;
            // 将newNum每一位二进制数按照从高位到地位的顺序添加到前缀树中
            for (int move = 31; move >= 0; move--) {
                int path = ((newNum >> move) & 1);
                // 已经有相应的路径就复用，没有就新建
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        // 该结构之前收集了一票数字，并且建好了前缀树
        // num和 谁 ^ 最大的结果（把结果返回）
        public int maxXor(int num) {
            Node cur = head;
            int ans = 0;
            for (int move = 31; move >= 0; move--) {
                // 取出num中第move位的状态，path只有两种值0就1，整数
                int path = (num >> move) & 1;
                // 期待遇到的东西   期待遇到和自己不相同的数
                int best = move == 31 ? path : (path ^ 1);
                // 实际遇到的东西
                best = cur.nexts[best] != null ? best : (best ^ 1);
                // (path ^ best) 当前位位异或完的结果
                ans |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return ans;
        }
    }

    // O(N)
    public static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        // 0~i整体异或和
        int xor = 0;
        // 前缀树中存储的是所有以i位置为结尾的前缀异或和结果，用这些前缀和相互异或就能求出所有区间范围上的异或和结果
        NumTrie numTrie = new NumTrie();
        // 先加入0，用于后面求前缀异或和
        numTrie.add(0);
        // 遍历一遍数组，将数组的前缀异或和全部加入到前缀树中
        for (int i = 0; i < arr.length; i++) {
            // 不断计算以每一个位置为结尾的前缀和，加入到前缀树中
            xor ^= arr[i]; // 0 ~ i
            // 找到前缀树中能和当前以i位置结尾的异或和xor 进行异或运算的结果最大的数
            max = Math.max(max, numTrie.maxXor(xor));
            // 将当前前缀异或和添加到前缀树中
            numTrie.add(xor);
        }
        return max;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
