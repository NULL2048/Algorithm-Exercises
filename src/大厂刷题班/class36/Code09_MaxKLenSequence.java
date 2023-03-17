package 大厂刷题班.class36;

import java.util.TreeSet;

// 单调栈   子序列   字符串

// 来自腾讯
// 给定一个字符串str，和一个正数k
// 返回长度为k的所有子序列中，字典序最大的子序列
public class Code09_MaxKLenSequence {
    public static String maxString(String s, int k) {
        if (k <= 0 || s.length() < k) {
            return "";
        }
        char[] str = s.toCharArray();
        int n = str.length;
        // 单调栈结构 从底往上按照字符的字典序由大到小排列
        char[] stack = new char[n];
        int size = 0;
        // 整个流程就是遍历字符串，将每一个字符尝试加入到单调栈中
        // 题目要求的是字典序最大的子序列，所以组成子序列的字符并一定要挨着
        // 我们的单调栈需要满足自底向上按照字典序从大到小排列
        // 所以再加入过程中，只要是按照我们规定好的单调栈规则来弹出加入字符的话，一定能保证在单调栈中保留的字符组成的子序列一定是当时字典序最大的组合
        // 在整个过程中会出现两种可能：
        // 	1、完成了对字符串的遍历，然后最后仍保存在单调栈中的字符数大于k，那么我们直接取单调栈中自底向上的前k个字符组成的子序列就是答案。
        //	   这是因为我们的单调栈规则保证了一旦发现了一个比栈顶字典序更大的字符，那么就会把栈顶弹出，用这个新字符代替原来的位置，这也就保证了单调栈中的字符构成的子序列字典序一定都是最大的
        //	2、还有一种可能就是在遍历字符将其尝试加入单调栈的过程中，如果在弹出栈顶过程中明明按照规则是还需要弹出的，但是发现如果再弹出的话，字符串剩余还没有遍历的字符数量和在单调栈中的字符数量加起来都凑不够k个了
        // 	   这种情况下就不能再弹出了，而是将新遍历到的更大的字符直接加进去，然后将单调栈的全部字符返回即可，这个就是字典序最大的子序列
        for (int i = 0; i < n; i++) {
            // 如果新遍历到的字符字典序不比栈顶大，就停止弹出。
            // 或者如果弹出过程中虽然新遍历到的字符字典序还是比栈顶大，但是此时如果再弹出的话，字符串剩余还没有遍历的字符数量和在单调栈中的字符数量加起来都凑不够k个了，那么这种情况也需要停止弹出
            while (size > 0 && stack[size - 1] < str[i] && size + (n - i) > k) {
                size--;
            }
            // 情况二：将单调栈中的字符和新遍历到的字符i组合成字符串返回，就是答案
            // 这种情况下必须要返回了，因为这个时候如果继续弹出的话，单调栈里面的字符在后面永远也凑不够k个了
            // 并且在现有的单调栈+i字符就已经是能找到的字典序最大的组合情况了，不会有比这个更大了。字典序是越高位的字典序越大，那么整体的字典序就越大，前缀字符相同的情况下，后面有字符的就比后面没有字符的字典序大
            if (size + (n - i) == k) {
                return String.valueOf(stack, 0, size) + s.substring(i);
            }
            // 将新遍历到的字符加入到单调栈中
            stack[size++] = str[i];
        }
        // 情况一：如果执行到这里，单调栈中的字符一定多余k个，返回前k个就是答案
        return String.valueOf(stack, 0, k);
    }

    // 为了测试
    public static String test(String str, int k) {
        if (k <= 0 || str.length() < k) {
            return "";
        }
        TreeSet<String> ans = new TreeSet<>();
        process(0, 0, str.toCharArray(), new char[k], ans);
        return ans.last();
    }

    // 为了测试
    public static void process(int si, int pi, char[] str, char[] path, TreeSet<String> ans) {
        if (si == str.length) {
            if (pi == path.length) {
                ans.add(String.valueOf(path));
            }
        } else {
            process(si + 1, pi, str, path, ans);
            if (pi < path.length) {
                path[pi] = str[si];
                process(si + 1, pi + 1, str, path, ans);
            }
        }
    }

    // 为了测试
    public static String randomString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int n = 12;
        int r = 5;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * (n + 1));
            String str = randomString(len, r);
            int k = (int) (Math.random() * (str.length() + 1));
            String ans1 = maxString(str, k);
            String ans2 = test(str, k);
            if (!ans1.equals(ans2)) {
                System.out.println("出错了！");
                System.out.println(str);
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}

