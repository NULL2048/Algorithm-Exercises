package 大厂刷题班.class17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Manacher   回文串
// 测试链接 : https://leetcode.cn/problems/palindrome-pairs/
public class Code05_PalindromePairs {
    public List<List<Integer>> palindromePairs(String[] words) {
        HashMap<String, Integer> wordsMap = new HashMap<>();
        // 将所有的单词都加入到HashMap中，这样可以加快查询速度
        for (int i = 0; i < words.length; i++) {
            wordsMap.put(words[i], i);
        }
        // 记录所有可能组成回文对的下标组合，里面的List存两个下标，拼在前面的下标放到前面，拼在后面的下标放在后面，要注意先后顺序，这是题目规定的
        List<List<Integer>> res = new ArrayList<>();
        // 遍历单词表中的每一个单词，找到所有能和它拼出回文串的回文对
        for (int i = 0; i < words.length; i++) {
            // 将所有的回文对都加入到res中
            res.addAll(findPalindromePairs(wordsMap, words[i], i));
        }

        return res;
    }

    // 找到单词表wordsMap中所有能和word拼成回文串的的字符串，将它们两个的下标组成回文对返回。word在单词表数组中的下标是index
    public List<List<Integer>> findPalindromePairs(HashMap<String, Integer> wordsMap, String word, int index) {
        // 记录能和word组成回文串的所有回文对下标
        List<List<Integer>> res = new ArrayList<>();
        // 先将当前的word反转
        String reverse = reverse(word);
        // 查找单词表中是否有空串，有的话就获取它的下标
        Integer restReverseStrIndex = wordsMap.get("");

        // 如果word本身就是一个回文串，并且单词表中存在空字符串，那么word和空字符串就可以拼出回文串，这就是一个符合条件的回文对
        if (restReverseStrIndex != null && restReverseStrIndex != index && word.equals(reverse)) {
            // 将空串拼到前面，当前字符串拼到后面，这是一组回文对，存入两个字符串的下标
            res.add(addRecord(restReverseStrIndex, index));
            // 将空串拼到后面，当前字符串拼到前面，这是一组回文对，存入两个字符串的下标
            res.add(addRecord(index, restReverseStrIndex));
        }

        // 通过manacher算法计算word字符串中以每个位置为中心的的回文半径是多少
        int[] pr = manacher(word);
        // 取pr数组的中间位置，向下取整。之所以要去中间位置，是因为我们要找的是前缀或后缀是否为回文串，也就是回文串边界要么是贴着0下标的，要么是贴着word.length-1下标的，如果我们选择一个mid右边的位置作为回文中心，然后想去找前缀回文串的话，这是不可能的，因为回文串在回文中心左右两部分肯定是等长的，如果回文中心选择在mid右边的位置，然后又需要左半边的回文范围要到下标0位置，那么回文中心右部分的长度根本就不够了，不可能组成回文串。
        // 找后缀回文串的时候同理。所以在找前缀回文串的时候，回文中心只能选取字符串中心位置及左边的位置，找后缀回文串时回文中心只能选取字符串中心位置及右边的位置
        int mid = pr.length >> 1;
        // 找前缀是回文串的情况
        for (int i = 1; i < mid; i++) {
            // 是一个前缀回文串，此时回文中心是i，i-回文半径如果等于-1，说明回文边界已经到0下标了
            if (i - pr[i] == -1) {
                // // 截取除了前缀回文串以外剩余的字符串
                // String restStr = word.substring(pr[i] - 1);
                // // 将剩余字符串反转
                // String restReverseStr = reverse(restStr);
                // // 去单词表中查找是否有剩余字符串的反转串
                // restReverseStrIndex = wordsMap.get(restReverseStr);

                // 上面注释掉的是正确思路，但是还可以优化成下面这条语句
                // 因为本身就是要截取剩余字符串的反转穿，我们可以直接利用word字符串的反转串reverse，来直接在这上面截取我们想要的即可
                // 就是分析好要截取的范围，本来要截取原字符串的后缀部分，现在就是要截取原字符串的反转穿的前缀部分
                restReverseStrIndex = wordsMap.get(reverse.substring(0, mid - i));

                // 如果单词表中有剩余字符串的反转串，并且这个字符串并不是当前word字符串自己，就说明此时可以和word拼出来回文串
                if (restReverseStrIndex != null  && restReverseStrIndex != index) {
                    // restReverseStr拼在前面，word拼在后面，存下标
                    res.add(addRecord(restReverseStrIndex, index));
                }
            }
        }

        for (int i = mid + 1; i < pr.length; i++) {
            // 是一个后缀回文串，此时回文中心是i，i+回文半径如果等于pr.length，说明回文边界已经到pr的最后一个位置下标了
            if (i + pr[i] == pr.length) {
                // // 截取除了后缀回文串以外剩余的字符串
                // String restStr = word.substring(0, word.length() - (pr[i] - 1));
                // // 将剩余字符串反转
                // String restReverseStr = reverse(restStr);
                // // 去单词表中查找是否有剩余字符串的反转串
                // restReverseStrIndex = wordsMap.get(restReverseStr);

                // 上面注释掉的是正确思路，但是还可以优化成下面这条语句
                // 因为本身就是要截取剩余字符串的反转穿，我们可以直接利用word字符串的反转串reverse，来直接在这上面截取我们想要的即可
                // 就是分析好要截取的范围，本来要截取原字符串的前缀部分，现在就是要截取原字符串的反转穿的后缀部分
                restReverseStrIndex = wordsMap.get(reverse.substring((mid << 1) - i));

                if (restReverseStrIndex != null  && restReverseStrIndex != index) {
                    // word拼在前面，restReverseStr拼在后面，存下标
                    res.add(addRecord(index, restReverseStrIndex));
                }
            }
        }


        return res;
    }

    // 反转word字符串
    public String reverse(String word) {
        char[] s = word.toCharArray();
        int l = 0;
        int r = s.length - 1;
        while (l < r) {
            char temp = s[l];
            s[l++] = s[r];
            s[r--] = temp;
        }

        return String.valueOf(s);
    }

    // 将两个下标记录构造成一个List<Integer>返回，用于添加答案
    public List<Integer> addRecord(Integer index1, Integer index2) {
        List<Integer> res = new ArrayList<>();
        res.add(index1);
        res.add(index2);
        return res;
    }

    // manacher算法
    public int[] manacher(String word) {
        // 构造manacher算法的辅助字符串
        char[] ms = getManacherStr(word);
        // 记录处理串以所有下标位置为回文中心的回文半径是多少
        int[] pr = new int[ms.length];
        // 当前已找到的所有回文半径中能到达的最右边位置的下标
        int r = -1;
        // 和r是成对使用的，记录当前能到达最靠右的回文半径对应的回文中心的下标位置
        int c = -1;

        // 遍历处理串，求以i位置为回文中心，最大的回文半径是多少
        for (int i = 0; i < ms.length; i++) {
            // R > i表示i在R内，这种情况是可以存在优化的，就是分了三种情况，其中①和②两种情况时答案分别为pArr[2 * C - i]和R - i，比如如果pArr[2 * C - i]比R - i小，那么说明此时就是①情况，反之就是情况②。
            // R <= i表示i在R外，这种是没法优化的，这种情况就直接将回文半径先设置为1，后面再去外扩尝试。因为每一个位置的回文串最少也有1个，也就是它本身
            pr[i] = r > i ? Math.min(pr[(c << 1) - i], r - i) : 1;

            // 直接接着我们上面判断得到的结果来处理
            // 首先回文半径要保证回文串不能越界
            while (i - pr[i] >= 0 && i + pr[i] < ms.length) {
                // 如果此时已经找到的回文半径两边的字符还是相等的，那么回文范围就可以继续向两边扩
                if (ms[i - pr[i]] == ms[i + pr[i]]) {
                    // 回文半径加1
                    pr[i]++;
                    // 只要是出现了两边的字符不相等的情况，直接跳出循环
                } else {
                    break;
                }
            }

            // 如果此时新的回文半径可以将下标最靠右的回文边界下标继续向右边推，就更新r和c
            if (i + pr[i] > r) {
                r = i + pr[i];
                // 此时的i就是回文中心
                c = i;
            }
        }

        // 返回word字符串的以所有位置为中心的回文半径
        return pr;
    }

    // 将word字符串进行处理，在字符串的两边和每个字符之间都加上#，这样方便manacher算法的处理，也就不会存在偶数长度的回文串了
    public char[] getManacherStr(String word) {
        // 一定要记住，只要是有位移操作的，一定要加上括号，因为位移运算符优先级低，如果下面不加括号，就是先选1+1，然后再算word.length() << 2了
        char[] s = new char[(word.length() << 1) + 1];
        // 先给开头加上'#'
        s[0] = '#';
        // 向后开始添加一个原始字符，再添加一个'#'，构造处理串
        int index = 1;
        for (int i = 0; i < word.length(); i++) {
            s[index++] = word.charAt(i);
            s[index++] = '#';
        }
        // 返回处理串
        return s;
    }
}
