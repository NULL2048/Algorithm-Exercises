package 大厂刷题班.class32;

import java.util.Arrays;
import java.util.Comparator;

// 数组  贪心  排序
// https://leetcode.cn/problems/largest-number/
public class Problem_0179_LargestNumber {
    class MyComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            // 比较器写法一：
            // Long ab = Long.valueOf(new StringBuilder().append(a).append(b).toString());
            // Long ba = Long.valueOf(new StringBuilder().append(b).append(a).toString());
            // return ab > ba ? -1 : 1;

            // 比较器写法二：
            String ab = new StringBuilder().append(a).append(b).toString();
            String ba = new StringBuilder().append(b).append(a).toString();
            // a拼接b的字典序 > b拼接a的字典序，则a放前，否则b放前
            return ba.compareTo(ab);
        }
    }

    public String largestNumber(int[] nums) {
        // 需要将int数组转化为Integer数组才可以使用对数器
        Integer[] ns = new Integer[nums.length];
        // 标记数组中的数是否都是0，如果都是0就为false，否则为true
        boolean flag = false;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                flag = true;
            }
            ns[i] = nums[i];
        }

        // 如果数组中都是0，直接返回"0"。避免返回"000"这样的字符串
        if (!flag) {
            return "0";
        }

        // 按照比较器规则排序
        Arrays.sort(ns, new MyComparator());

        // 按照比较器规则排序完之后再将所有的字符都拼接起来就是答案。
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ns.length; i++) {
            res.append(ns[i]);
        }
        return res.toString();
    }
}
