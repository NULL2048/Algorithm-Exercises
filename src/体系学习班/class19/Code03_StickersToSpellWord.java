package 体系学习班.class19;

import java.util.HashMap;

// 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word
public class Code03_StickersToSpellWord {

    // 1、暴力递归
    // 第一种尝试方法
    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        // 如果返回系统最大值，说明无法组成目标字符串
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 所有贴纸stickers，每一种贴纸都有无穷张
    // target  目标字符串
    // 返回使用贴纸的最少张数
    public static int process1(String[] stickers, String target) {
        // 当目标字符串中已经没有字符时，此时肯定是不用贴纸就能组合出空字符串了，直接返回0张贴纸
        if (target.length() == 0) {
            return 0;
        }
        // 先将最小的贴纸数量赋值为系统最大值
        int min = Integer.MAX_VALUE;
        // 遍历所有的贴纸
        for (String first : stickers) {
            // 这个方法是用当前遍历到的贴纸中的字符去冲减目标字符串中的字符，然后返回冲减完成后的剩余的目标字符串字符
            String rest = minus(target, first);
            // 如果冲减完成后剩余目标字符串的字符数量和冲减之前字符数量不一致，说明本次的贴纸中有可以冲减的字符，是一次有效的分支情况，那么继续向下暴力递归
            // 如果冲减完成后并没有变化，说明这一张贴纸中没有可以用来冲减的字符，那么这个分支就是无效的，直接跳过递归。
            if (rest.length() != target.length()) {
                // 用剩余的目标字符串作为下一层递归的目标字符串，去进行递归。返回使用贴纸的数量，取最小值。
                min = Math.min(min, process1(stickers, rest));
            }
        }
        // 返回当前这一条分支的最小贴纸数。如果上一层向上返回的是系统最大值，说明无法组成目标字符，将min赋值为0
        // 如果上一层返回的不是系统最大值，说明可以组成目标字符，此时就将上一层返回的结果 + 1，然后再返回
        // 之所以要加1，是因为在上面的循环中，每一个分支的头部的第一个贴纸，并没有把这一张计入到使用贴纸数中（不是第一次循环的第一张，而是循环形成的每一条分支的头部第一张贴纸，每一条分支都需要加1），所以需要在最后再给加上这一张。
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    // 冲减方法，就是一个暴力模拟
    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        // 用来记录目标字符串中每一种字符的数量
        int[] count = new int[26];
        // 将目标字符串每一个词频都记录一下
        for (char cha : str1) {
            count[cha - 'a']++;
        }
        // 再去遍历贴纸字符，对目标字符的词频进行冲减
        for (char cha : str2) {
            count[cha - 'a']--;
        }
        // 最后再根据词频生成字符串返回，这道题目字符排列顺序并不重要。
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    // 第二种尝试方法
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        // 关键优化(用词频表替代贴纸数组)
        int[][] counts = new int[N][26];
        // 每一个贴纸的词频统计数组
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // stickers[i] 数组，i号贴纸的字符统计 int[][] stickers -> 所有的贴纸
    // 每一种贴纸都有无穷张
    // 返回搞定target的最少张数
    public static int process2(int[][] stickers, String t) {
        // 递归出口 basecase
        if (t.length() == 0) {
            return 0;
        }
        // 对target做、词频统计
        // target  aabbc  2 2 1..
        //                0 1 2..
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        // 先将最小的贴纸数量赋值为系统最大值
        int min = Integer.MAX_VALUE;
        // 遍历所有的贴纸
        for (int i = 0; i < N; i++) {
            // 尝试第一张贴纸是谁
            int[] sticker = stickers[i];
            // 最关键的优化(重要的剪枝!这一步也是贪心!)
            // 只有当前贴纸存在当前剩余目标字符串中第一个位置的字符时，才会进入到递归分支的流程
            // target[0] : 目标字符串的第一个字符
            if (sticker[target[0] - 'a'] > 0) {
                // 根据目标字符串和贴纸的词频去做冲减，然后形成新的目标字符串
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                // 继续递归
                min = Math.min(min, process2(stickers, rest));
            }
        }
        // 返回当前一条分支的最小值，如果分支有效，需要再加1，是因为在上面的循环中，每一个分支的头部的第一个贴纸，并没有把这一张计入到使用贴纸数中，所以需要在最后再给加上这一张。
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    // 2、动态规划
    // 这里的动态规划没有严格的位置依赖，所以就是用加缓存的方法，实现记忆化搜索来进行动态规划求解
    // 依旧是在暴力递归的代码基础上，去加缓存，实现记忆化搜索，避免相同的值重复计算
    public static int minStickers3(String[] stickers, String target) {
        // 构造贴纸的词频
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        // 缓存，用HashMap容器进行存储
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        // 如果已经存存在当前剩余目标字符串的结果，则直接取出来用即可。】
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        // 统计目标字符串的词频
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        // 进行循环暴力递归
        for (int i = 0; i < N; i++) {
            // 整个过程和上面的第二个尝试方法是一样的，只不过要把最终结果存到缓存中
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        // 这个题目要跳出循环之后，才能有当前分支的最终结果
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        // 存入缓存
        dp.put(t, ans);
        return ans;
    }

}
