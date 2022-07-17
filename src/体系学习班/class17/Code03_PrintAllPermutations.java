package 体系学习班.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Code03_PrintAllPermutations {
    public static List<String> permutation1(String s) {
        // 记录每一轮判断的结果
        List<String> ans = new ArrayList<>();
        // 如果传入的是空字符串，直接返回空结果
        if (s == null || s.length() == 0) {
            return ans;
        }
        // 将字符串转换成字符数组
        char[] str = s.toCharArray();
        // 用来记录剩下的可供挑选的字符
        ArrayList<Character> rest = new ArrayList<Character>();
        // 初始的时候将所有的字符都加入到rest
        for (char cha : str) {
            rest.add(cha);
        }
        // 记录本轮找到的字符串
        String path = "";
        // 深度递归求解
        f(rest, path, ans);
        return ans;
    }

    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        // 当rest为空，说明已经没有可选择的字符了，所有的字符都已经被选择过了，递归结束，返回
        // 递归出口
        if (rest.isEmpty()) {
            // 将本轮找到的结果path加入到ans中
            ans.add(path);
        } else {
            // 获取当前剩余可选择的字符数量
            int N = rest.size();
            // 遍历所有可选择的字符，选择一个进行递归
            for (int i = 0; i < N; i++) {
                // 选择一个字符
                char cur = rest.get(i);
                // 将已选择的字符移出rest
                rest.remove(i);
                // 向下递归，将本轮找到的字符串更新为path+cur
                f(rest, path + cur, ans);
                // 上一个递归结束之后，恢复现场
                rest.add(i, cur);
            }
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    public static void g1(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            // 这里整体思路和之前还是一样的，但是并不是从剩余字符集合中移除已选择的字符的方法，而是通过和不同位置的字符交换来实现将每一种情况都选择一遍
            // 每次从index以后的字符中选择，因为index之前的都已经选择过了
            for (int i = index; i < str.length; i++) {
                // 交换位置
                swap(str, index, i);
                g1(str, index + 1, ans);
                // 回复现场
                swap(str, index, i);
            }
        }
    }

    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    // 剔除重复的结果很简单可以和以前的方法一样，用Set来作为最终结果ans的存储容器，这样每次就可以将最后的结果去重
    // 也可以用下面的方法，在交换的时候就不去交换已经选择过的字符，这样从最根源的地方就避免了重复递归，相对来说效率更好一些
    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            // 用来记录本轮层递归已经选择了哪些字符，下标存储的是ASCII码
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                // 已经重复选择的字符不再进行交换，直接跳过
                if (!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }


    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "acc";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }
}
