package 大厂刷题班.class09;

import java.util.ArrayList;
import java.util.List;

// 递归   这个题要返回全部可能的情况，这就说明需要将所有可能的情况都尝试一遍，那么这种题就可以采用直接暴力递归的方法，把所有情况都枚举出来即可。不需要再做额外的优化了
// 测试链接 : https://leetcode.cn/problems/remove-invalid-parentheses/
public class Code03_RemoveInvalidParentheses {
    // 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, 0, 0, ans, new char[] {'(', ')'});

        return ans;
    }

    // modifyIndex <= checkIndex
    // 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
    // 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
    // 比如：
    // ( ( ) ( ) ) ) ...
    // 0 1 2 3 4 5 6
    // 一开始当然checkIndex = 0，modifyIndex = 0
    // 当查到6的时候，发现不对了，
    // 然后可以去掉2位置、4位置的 )，都可以
    // 如果去掉2位置的 ), 那么下一步就是
    // ( ( ( ) ) ) ...
    // 0 1 2 3 4 5 6
    // checkIndex = 6 ，modifyIndex = 2
    // 如果去掉4位置的 ), 那么下一步就是
    // ( ( ) ( ) ) ...
    // 0 1 2 3 4 5 6
    // checkIndex = 6 ，modifyIndex = 4
    // 也就是说，
    // checkIndex和modifyIndex，分别表示查的开始 和 调的开始，之前的都不用管了  par[0] = '('  par[1] = ')'
    // str不是地址传递，每个递归层不是共用同一个str
    public void remove(String str, int checkIndex, int deleteIndex, List<String> ans, char[] par) {
        int cnt = 0;
        // 检查每一个位置。找到右括号（左括号）多的情况
        for (int i = checkIndex; i < str.length(); i++) {
            if (str.charAt(i) == par[0]) {
                cnt++;
            }

            if (str.charAt(i) == par[1]) {
                cnt--;
            }

            // 一旦出现了cnt小于0的情况，就说明此时与i配对的左括号（右括号）肯定存在多个配对的右括号（左括号），所里我们要开始找可以选择删除的括号
            if (cnt < 0) {
                // 从deleteIndex开始向右边遍历，遍历到i位置，按照我们的规则找到所有可以删除的字符
                for (int j = deleteIndex; j <= i; j++) {
                    // 如果此时是可以删除的字符，就将其删除，并将删除后的字符串继续向下递归
                    if (str.charAt(j) == par[1] && (j == deleteIndex || str.charAt(j - 1) != par[1])) {
                        // 删除玩字符之后的i和j已经指向的是他们原本下一个位置了，所以这里i和j不用加1
                        remove(str.substring(0, j) + str.substring(j + 1, str.length()), i, j, ans, par);
                    }
                }
                // 如果上面循环越界了，说明已经将所有要删除的都删掉了，此时str这个字符。
                // 注意这个返回，当一个字符已经将所有不合法的括号都删掉之后，删掉的同时就是会调用一次递归，就像上面的代码那样，会把删除之后最终的字符串作为参数调用递归
                // 然后新调用的递归的str就是最终已经将所有无效括号都删除的情况，这样在这个递归中就不可能进入到这个if分支，也就不会return，而是直接到最后去收集答案了。
                // 所以还在删除阶段，没有将所有无效括号删除完时，会直接走到这个return返回，不会接着向下执行去收集答案
                return;
            }

        }

        // 一旦执行到这里，一定是右括号多的情况都已经删除了，或者此时字符串已经是删除之后没有无效括号的字符串了

        // 将字符串反转
        String reversed = new StringBuilder(str).reverse().toString();
        // 如果此时par[0] == '('，说明还没有找左括号多的情况，那么我们就将反转的字符串在调用一边递归，并且将new char[] {')', '('}传入，相当于再从反方向找一遍左括号多的情况
        if (par[0] == '(') {
            remove(reversed, 0, 0, ans, new char[] {')', '('});
            // 如果两个方向都已经找完了，那么此时就是答案，将其添加到ans中
        } else {
            ans.add(reversed);
        }

    }

}

