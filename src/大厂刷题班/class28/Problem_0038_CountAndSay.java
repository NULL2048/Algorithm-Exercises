package 大厂刷题班.class28;
// 递归   模拟    考验题目理解能力
// https://leetcode.cn/problems/count-and-say/
public class Problem_0038_CountAndSay {
    public String countAndSay(int n) {
        // 过滤无效参数
        if (n == 0) {
            return "";
        }
        // 单独处理
        if (n == 1) {
            return "1";
        }

        // 开始递归
        return process("1", 2, n);
    }

    // pre：上一轮的字符串结果
    // index：当前到第几轮了
    // n：要求第几轮的答案
    public String process(String pre, int index, int n) {
        // basecase
        if (index > n) {
            // 上一轮的结果就是最终的答案
            return pre;
        }

        // 记录当前轮的答案
        StringBuilder ans = new StringBuilder();
        char[] p = pre.toCharArray();
        // 统计当前要统计的数的个数
        int cnt = 1;
        for (int i = 1; i < p.length; i++) {
            // 分段统计，同一段相同的数统计出他们的个数，到了下一段的时候将cnt置为1，重新开始统计新的数的个数
            if (p[i] == p[i - 1]) {
                cnt++;
            } else {
                // 将上一段的的答案加入到ans中
                // 这里要转换为字符串加入到ans，不要以字符型加入，会导致输出错误
                ans.append(String.valueOf(cnt))
                        .append(String.valueOf(p[i - 1]));
                cnt = 1;
            }
        }

        // 将结尾还没来得及加入的结果加入ans
        ans.append(String.valueOf(cnt))
                .append(String.valueOf(p[p.length - 1]));

        // 继续向下层递归
        return process(ans.toString(), index + 1, n);
    }
}
