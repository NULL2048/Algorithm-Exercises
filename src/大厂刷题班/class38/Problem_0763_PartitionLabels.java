package 大厂刷题班.class38;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
// 贪心   字符串
// https://leetcode.cn/problems/partition-labels/
public class Problem_0763_PartitionLabels {
    // 1、我自己写的代码
    public List<Integer> partitionLabels(String str) {
        // 要返回的答案，返回划分出来的最多子串的长度
        List<Integer> ans = new ArrayList<>();
        // 过滤无效参数
        if (str == null || str.length() == 0) {
            return ans;
        }
        // 转化为字符串数组
        char[] s = str.toCharArray();
        // 记录每一种字符在字符串中最靠右的位置下标
        HashMap<Character, Integer> mostRightIndex = new HashMap<>();
        // 从右往左遍历，找到每一种字符最右的下标
        for (int i = s.length -1; i >= 0; i--) {
            // 只有还从来没有找过的字符，才能将遍历到的下标加入到Map中，这样找到的所有下标就是这个字符在字符串中最右边的下标
            if (!mostRightIndex.containsKey(s[i])) {
                mostRightIndex.put(s[i], i);
            }
        }
        // 记录每一段划分的长度
        int len = 0;
        // 此时这一段中字符最右边的位置
        int r = 0;
        // 从左往右遍历
        for (int i = 0; i < s.length; i++) {
            // 如果此时遍历到的位置已经超过r了，就说明i之前的字符已经在i后面没有相同的字符了，说明i之前子串可以划分出来了
            if (i > r) {
                // 将记录的长度加入到答案中
                ans.add(len);
                // 重置len为1
                len = 1;
                // 将r设置为当前字符s[i]最右边的下标位置
                r = mostRightIndex.get(s[i]);
                // 进行下一轮循环
                continue;
            }
            // 如果此时i没有超过r，就去看s[i]最右边的下标有没有超过r，如果超过r就更新r
            if (mostRightIndex.get(s[i]) > r) {
                r = mostRightIndex.get(s[i]);
            }
            // 当前这一块的长度加1
            len++;
        }
        // 将最后一块的长度加入到ans
        ans.add(len);
        return ans;
    }

    // 2、左神的代码，和我的思路完全一样，只不过它是用数组代替的HashMap，所以效率上比我更高
    public static List<Integer> partitionLabels1(String S) {
        char[] str = S.toCharArray();
        int[] far = new int[26];
        for (int i = 0; i < str.length; i++) {
            far[str[i] - 'a'] = i;
        }
        List<Integer> ans = new ArrayList<>();
        int left = 0;
        int right = far[str[0] - 'a'];
        for (int i = 1; i < str.length; i++) {
            if (i > right) {
                ans.add(right - left + 1);
                left = i;
            }
            right = Math.max(right, far[str[i] - 'a']);
        }
        ans.add(right - left + 1);
        return ans;
    }

}
