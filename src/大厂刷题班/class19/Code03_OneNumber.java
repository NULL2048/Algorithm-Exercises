package 大厂刷题班.class19;

// 一种以前没讲过的动态规划模型：数位DP    面试的时候很少出这种题，一般都是在比赛中会出
// 还有一种动态规划模型:轮廓线DP，这种也是在面试中很少出现的，之前讲过的那个贴瓷砖问题就可以用轮廓线DP解。轮廓线DP中还有一种叫作插头DP，2008年被一个长沙的姑娘发明的，现在是斯坦福博士
// 数位DP虽然很冷门，但是这道题是比较高频的题，所以我们就只针对这个题讲一下数位DP，把这道题会写了就可以了。
// 测试链接：https://leetcode.cn/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof/
// https://leetcode.cn/problems/number-of-digit-one/   这两个链接是同一道题
public class Code03_OneNumber {
    public int countDigitOne(int n) {
        // 如果n小于1，直接返回0
        if (n < 1) {
            return 0;
        }
        // 得到n的位数，例如n -> 13625，numLen = 5位数
        int numLen = getNumLength(n);
        // 如果n只有一位，那么直接返回1
        if (numLen == 1) {
            return 1;
        }

        // 例子
        // n    13625
        // temp 10000
        //
        // n    7872328738273
        // temp 1000000000000
        int temp = (int) Math.pow(10, numLen - 1);
        // n的最高位是多少
        int first = n / temp;
        // n除了最高位以外，剩下数是多少
        int restNum = n % temp;


        // 计算restNum ~ n 范围上出现1的总次数

        // 最高位能提供多少个1
        int firstCnt = first == 1 ? restNum + 1 : temp;
        // 除了最高位的其他位一共能提供多少个1
        int restCnt = first == 1 ? (numLen - 1) * (temp / 10) : first * (numLen - 1) *  (temp / 10);

        // 返回1出现的总次数，1 ~ restNum范围继续向下递归
        return firstCnt + restCnt + countDigitOne(restNum);
    }

    public int getNumLength(int num) {
        int len = 0;
        while (num != 0) {
            num /= 10;
            len++;
        }

        return len;
    }
}
