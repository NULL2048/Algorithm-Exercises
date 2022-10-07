package 大厂刷题班.class03;

import java.util.ArrayList;
import java.util.HashMap;

// 模拟(自然智慧的思路)+动态规划
// 本题测试链接 : https://leetcode.cn/problems/freedom-trail/submissions/
public class Code08_FreedomTrail {
    // 1、暴力递归
    public int findRotateSteps1(String ring, String key) {
        char[] ringC = ring.toCharArray();
        char[] keyC = key.toCharArray();

        HashMap<Character, ArrayList<Integer>> ringMap = new HashMap<>();
        for (int i = 0; i < ringC.length; i++) {
            if (ringMap.containsKey(ringC[i])) {
                ringMap.get(ringC[i]).add(i);
            } else {
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(i);
                ringMap.put(ringC[i], arr);
            }
        }

        return process1(0, keyC, ringMap, 0, ringC.length);

    }


    public int process1(int index, char[] keyC, HashMap<Character, ArrayList<Integer>> ringMap, int ringIndex, int ringLen) {
        if (index == keyC.length) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        ArrayList<Integer> nextPositions = ringMap.get(keyC[index]);
        for (Integer i : nextPositions) {
            min = Math.min(min, minPay(ringIndex, i, ringLen) + process1(index + 1, keyC, ringMap, i, ringLen));
        }

        return min + 1;
    }



    // 2、记忆化搜索
    public int findRotateSteps(String ring, String key) {
        // String转化为字符串数组
        char[] ringC = ring.toCharArray();
        char[] keyC = key.toCharArray();

        // 构造表盘上每一个字符都有哪些位置，存储在Map中
        HashMap<Character, ArrayList<Integer>> ringMap = new HashMap<>();
        for (int i = 0; i < ringC.length; i++) {
            if (ringMap.containsKey(ringC[i])) {
                ringMap.get(ringC[i]).add(i);
            } else {
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(i);
                ringMap.put(ringC[i], arr);
            }
        }

        // 创建缓存数组
        int[][] dp = new int[keyC.length][ringC.length];

        return process(0, keyC, ringMap, 0, ringC.length, dp);

    }

    // 当前检查要拨打的index位置的字符的最小步数，此时电话机上的指针指向圆盘的ringIndex位置
    public int process(int index, char[] keyC, HashMap<Character, ArrayList<Integer>> ringMap, int ringIndex, int ringLen, int[][] dp) {
        // basecase  当遍历完所有的要拨打的字符，当前来到越界位置，返回0步数
        if (index == keyC.length) {
            return 0;
        }

        // 如果缓存中已经有结果了，直接返回
        if (dp[index][ringIndex] != 0) {
            return dp[index][ringIndex];
        }

        // 找到当前要拨打的keyC[index]字符在电话机的圆盘上的全部位置，递归尝试每一个位置，去找到步数最小的转轮方法
        int min = Integer.MAX_VALUE;
        // 获取圆盘上所有keyC[index]字符的位置
        ArrayList<Integer> nextPositions = ringMap.get(keyC[index]);
        // 尝试每一个转盘上的位置
        for (Integer i : nextPositions) {
            // 尝试顺时针转到i位置和逆时针转到i位置(minPay(ringIndex, i, ringLen))，找到最小消耗，并且再加上后续的最小步数(process(index + 1, keyC, ringMap, i, ringLen, dp))，就是我们要求的当前层的结果
            min = Math.min(min, minPay(ringIndex, i, ringLen) + process(index + 1, keyC, ringMap, i, ringLen, dp));
        }

        // 存入缓存  这里要+1，因为每转到一个字符，还需要点一下按钮，这也算是一次步数
        dp[index][ringIndex] = min + 1;
        return dp[index][ringIndex];
    }

    // 将指针从fromIndex位置转到toIndex位置的最小步数
    // 这里会尝试顺时针转到toIndex和逆时针转到toIndex，看哪种转法步数最少
    public int minPay(int fromIndex, int toIndex, int ringLen) {
        // Math.abs(toIndex - fromIndex)   顺时针
        // Math.abs(ringLen - Math.max(toIndex, fromIndex) + Math.min(fromIndex, toIndex)) 逆时针   这个就是个简单的数学关系，很好理解，就是注意把fromIndex < toIndex和fromIndex >= toIndex两种情况都考虑进去即可
        return Math.min(Math.abs(toIndex - fromIndex), Math.abs(ringLen - Math.max(toIndex, fromIndex) + Math.min(fromIndex, toIndex)));
    }



    // 这道题没有必要改动态规划，因为这道题的枚举过程无法进行斜率优化，枚举过程中的相对位置关系不是固定的，依赖的位置数量也是不固定的。
}
