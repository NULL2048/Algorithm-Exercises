package 大厂刷题班.class28;
// 模拟    数学
// https://leetcode.cn/problems/roman-to-integer/
public class Problem_0013_RomanToInteger {
    public static int romanToInt(String s) {
        // C     M     X   C     I   X
        // 100  1000  10   100   1   10
        // 创建一个数组，用来记录每一位罗马数字代表的数是多少
        int nums[] = new int[s.length()];
        // 遍历罗马数字，赋值上每一位对应的数是多少
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
            }
        }
        int sum = 0;
        // 根据前后大小关系判断代表的数是正还是负，做累加
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                sum -= nums[i];
            } else {
                sum += nums[i];
            }
        }

        // 罗马数字最后一位认为一定是正数
        return sum + nums[nums.length - 1];
    }
}
