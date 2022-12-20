package 大厂刷题班.class28;
// 数组   字符串
// https://leetcode.cn/problems/longest-common-prefix/
public class Problem_0014_LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        // 过滤无效参数
        if (strs == null || strs.length == 0) {
            return "";
        }
        // 过滤特殊情况
        if (strs.length == 1) {
            return strs[0];
        }

        // 当前检查到了什么下标位置
        int index = 0;
        // 当前要判断是否所有的字符串在index位置都是cha字符
        char cha = 0;
        while (true) {
            // 注意要先判断一下是否已经越界了
            if (index >= strs[0].length()) {
                return strs[0].substring(0, index);
            }
            cha = strs[0].charAt(index);

            for (int i = 1; i < strs.length; i++) {
                // 只要是找公共前缀中断了，就返回答案
                if (index >= strs[i].length() || strs[i].charAt(index) != cha) {
                    return strs[0].substring(0, index);
                }
            }
            index++;
        }
    }
}
