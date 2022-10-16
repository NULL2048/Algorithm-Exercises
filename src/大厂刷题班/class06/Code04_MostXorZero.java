package 大厂刷题班.class06;

import java.util.ArrayList;
import java.util.HashMap;

// 前缀树  动态规划   数组三连问题
// 一般数组三连答案假设法都是很难的题，这道题是网易笔试最后一题，当时在牛客上考的，1200个人只有12个人过了
public class Code04_MostXorZero {

    // 1、暴力方法
    public static int comparator(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // 记录以i位置结尾的前缀异或和
        int[] eor = new int[N];
        eor[0] = arr[0];
        // 计算所有的前缀异或和
        for (int i = 1; i < N; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }
        return process(eor, 1, new ArrayList<>());
    }

    // index去决定：前一坨部分，结不结束！
    // 如果结束！就把index放入到parts里去
    // 如果不结束，就不把index放入到parts中
    public static int process(int[] eor, int index, ArrayList<Integer> parts) {
        int ans = 0;
        // 当到了结尾的时候
        if (index == eor.length) {
            // 将越界的位置加入到异或和分隔部分，作为最后一个部分的结束位置
            parts.add(eor.length);
            // 计算一下此时有多少个异或和为0的部分
            ans = eorZeroParts(eor, parts);
            // 当前这个向下层递归分支就算是结束了，恢复现场
            parts.remove(parts.size() - 1);
        } else {
            // 第一种可能：前面的部分不结束，当前index位置仍然是前一个部分的数
            int p1 = process(eor, index + 1, parts);
            // 第二种可能：前面的部分结束，将当前位置加入到parts中，作为分割隔断
            parts.add(index);
            int p2 = process(eor, index + 1, parts);
            // 这里也要清理现场
            parts.remove(parts.size() - 1);
            // 求分割出来的子数组异或和为0的个数
            ans = Math.max(p1, p2);
        }
        return ans;
    }

    // 计算一下按照parts分割，有多少个异或和为0的部分。利用前缀和数组快速的到指定子数组范围的异或和
    public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
        int L = 0;
        int ans = 0;
        for (Integer end : parts) {
            // 如果存在异或和为了，ans++
            if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
                ans++;
            }
            // 分割边界
            L = end;
        }
        return ans;
    }

    // 2、从左向右的尝试模型
    // 时间复杂度O(N)的方法
    public static int mostXor(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // arr从0...i能够最多切出多少个异或和为0的部分，不要求子数组必须以i结尾
        int[] dp = new int[N];

        // key 某一个前缀异或和
        // value 这个前缀异或和上次出现的位置(最晚！)
        HashMap<Integer, Integer> map = new HashMap<>();
        // 0前缀异或和最早出现的位置是-1
        map.put(0, -1);
        // 0~i整体的异或和
        int xor = 0;
        for (int i = 0; i < N; i++) {
            // 记录前缀异或和
            xor ^= arr[i];
            // 可能性2：如果当前的前缀异或和已经在之前出现过了
            // 这就意味着前面最后一次出现这个异或和的部分的结尾位置到当前i位置之间的异或和为0。很好理解，只有N ^ 0 = N
            if (map.containsKey(xor)) {
                // 找到当前异或和前面最后一次出现的结尾下标位置
                int pre = map.get(xor);
                // 将0~i范围上能分割出来的异或和为0的子数组个数加1
                dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
            }

            if (i > 0) {
                // 可能性1：如果当前的前缀异或和在之前没有出现过，说明到目前为止0~i范围上最后一个分割部分的异或和不是0，那么当前dp[i]的异或和为0的个数就和dp[i - 1]一致
                // 找两种可能的最大值
                dp[i] = Math.max(dp[i - 1], dp[i]);
            }
            // 更新当前异或和最后一次出现的位置
            map.put(xor, i);
        }
        // 返回答案
        return dp[N - 1];
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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
        int testTime = 150000;
        int maxSize = 12;
        int maxValue = 5;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostXor(arr);
            int comp = comparator(arr);
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

