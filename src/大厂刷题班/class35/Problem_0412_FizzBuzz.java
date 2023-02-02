package 大厂刷题班.class35;

import java.util.ArrayList;
import java.util.List;
// 模拟
// https://leetcode.cn/problems/fizz-buzz/
public class Problem_0412_FizzBuzz {
    public List<String> fizzBuzz(int n) {
        List<String> ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                ans.add("FizzBuzz");
            } else if (i % 3 == 0) {
                ans.add("Fizz");
            } else if (i % 5 == 0) {
                ans.add("Buzz");
            } else {
                ans.add(String.valueOf(i));
            }
        }

        return ans;
    }
}
