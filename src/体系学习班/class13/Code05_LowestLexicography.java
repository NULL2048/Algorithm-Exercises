package 体系学习班.class13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class Code05_LowestLexicography {
    // 暴力
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        // 这里TreeSet自动按照字典序由小到大排序，所以直接返回TreeSet中第一个元素就是字典序最小的字符串
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    // 暴力
    // strs中所有字符串全排列，返回所有可能的结果
    public static TreeSet<String> process(String[] strs) {
        // 存放各种组合排序的字符串
        TreeSet<String> ans = new TreeSet<>();
        // 递归出口，如果传入的String[]已经空了，则添加一个空字符串到TreeSet中
        if (strs.length == 0) {
            // 必须要加上这个，不然就返回的是null了，会报错
            ans.add("");
            return ans;
        }
        // 就循环将每一个数组中的字符串都当作排列在第一位置的字符串一次
        for (int i = 0; i < strs.length; i++) {
            // 设置到第一个位置
            String first = strs[i];
            // 将已经当作第一个位置的字符串从字符串数组中移除
            String[] nexts = removeIndexString(strs, i);
            // 通过递归再去排列组合余下的字符串
            TreeSet<String> next = process(nexts);
            // 再将余下的字符串按顺序拼接上上去
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    // {"abc", "cks", "bct"}
    // 0 1 2
    // removeIndexString(arr , 1) -> {"abc", "bct"}
    public static String[] removeIndexString(String[] arr, int index) {
        int N = arr.length;
        String[] ans = new String[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    // 贪心   这个就是我们的贪心策略
    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    // 贪心
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        // 直接去按照我们的贪心策略去排序
        Arrays.sort(strs, new MyComparator());
        // 字符串拼接，如果不考虑线程安全，用StringBuilder最快
        String res = "";
        // 直接将排序结果拼接起来就是最终的答案
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }
        return res;
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            // 用于随机生成ASCII
            int value = (int) (Math.random() * 5);
            // 随机生成大小写字母
            ans[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    // for test
    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    // 通过对数器证明了我们的贪心策略是正确的。
    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString1(arr1).equals(lowestString2(arr2))) {
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
