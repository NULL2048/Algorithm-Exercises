package 大厂刷题班.class38;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// 滑动窗口    欠债表   欠帐表
public class Problem_0438_FindAllAnagramsInAString {
    public List<Integer> findAnagrams(String sStr, String pStr) {
        // 要返回的答案   记录每一个异位词开始位置的下标
        List<Integer> ans = new ArrayList<>();
        // 过滤掉无效情况   如果p的字符串长度大于s，那么就不可能在s中找到异位词
        if (sStr == null || pStr == null || sStr.length() < pStr.length()) {
            return ans;
        }
        // 这道题的欠帐表还是得用Map来做，不能用数组，因为我们无法区分这个位置是根本就没有存过数据（每一个数组位置默认都是0），还是说只是个数被减到0了
        // 欠帐表中只存储p字符串上每个字符的欠帐情况，其他的字符都不记录
        HashMap<Character, Integer> countMap = new HashMap<>();
        // 欠帐表中的欠帐个数
        int all = 0;
        // 转化为字符串数组
        char[] s = sStr.toCharArray();
        char[] p = pStr.toCharArray();

        // 首先构造欠帐表
        for (int i = 0; i < p.length; i++) {
            // 将p字符串中的所有字符都加入到欠帐表中
            if (countMap.containsKey(p[i])) {
                countMap.put(p[i], countMap.get(p[i]) + 1);
            } else {
                countMap.put(p[i], 1);
            }
            all++;
        }

        // 窗口左右边界
        int l = 0;
        int r = 0;
        // 一开始先将窗口扩充到p.length - 2的长度，然后再固定到p.length - 1长度向后滑动收集所有符合条件的异位词
        // 之所以这里先扩充到p.length - 2的长度，是因为在后面开始滑动窗口的时候一开始会先右扩窗口，这样窗口的长度就是p.length - 1了
        for (; r < p.length - 1; r++) {
            // 只有当右边界遍历到欠帐表中的字符时，采取修改欠帐表信息
            if (countMap.containsKey(s[r])) {
                // s[r]字符欠了多少
                int count = countMap.get(s[r]);
                // 只有此时欠的数量大于0，才是一次有效还款，才能all--
                if (count > 0) {
                    all--;
                }
                // 修改欠帐表中这个字符的欠帐数
                countMap.put(s[r], count - 1);
            }
        }

        // 开始滑动窗口，将窗口的长度固定在p.length - 1
        for (; r < s.length; l++, r++) {
            // 还是只有碰到欠帐表里的字符，才对欠帐表进行操作
            if (countMap.containsKey(s[r])) {
                int count = countMap.get(s[r]);
                // 是否是有效还款  只有保证减完了不小于0，才是有效还款
                if (count > 0) {
                    all--;
                }
                // 将欠帐减1
                countMap.put(s[r], count - 1);
            }

            // 因为固定了窗口的长度，并且只有在遍历到p中的字符时才对all进行操作
            // 所以一旦all==0，那么一定说明窗口中的字符种类和数量和p完全一样
            if (all == 0) {
                // 将左边界加入ans
                ans.add(l);
            }

            // 窗口右滑动，所以左边界也要右移一位
            // 只有弹出的字符是欠帐字符，才对欠帐表进行操作
            if (countMap.containsKey(s[l])) {
                int count = countMap.get(s[l]);
                // 只有保证欠帐加完了是大于0的数字，才是有效欠帐
                // 所以判断count是否大于等于0
                if (count >= 0) {
                    all++;
                }
                // 欠帐加1
                countMap.put(s[l], count + 1);
            }
        }
        // 返回答案
        return ans;
    }
}
