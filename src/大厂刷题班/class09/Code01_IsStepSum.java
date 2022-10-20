package 大厂刷题班.class09;

import java.util.HashMap;
// 二分
public class Code01_IsStepSum {
    public static boolean isStepSum(int stepSum) {
        int L = 0;
        int R = stepSum;
        int M = 0;
        int cur = 0;
        // 如果一个数的步骤和是stepSum，那么这个数一定在1~stepSum之间。
        // 并且还有一个结论，一个数X它的步骤和叫甲，一个数Y它的步骤和叫乙，我首先有一个推论，如果Y>X，它的步骤和乙只可能大于甲。
        // 通过二分去找步骤和
        while (L <= R) {
            M = L + ((R - L) >> 1);
            // 计算中间这个数的步骤和stepSum还是小于，还是等于，来决定下一部分的二分是左半部分还是右半部分，还是说找到了我们要的答案返回true
            cur = stepSum(M);
            // 看这个步骤和是大于
            if (cur == stepSum) {
                return true;
            } else if (cur < stepSum) {
                L = M + 1;
            } else {
                R = M - 1;
            }
        }
        // 如果二分完了也没找到，说明不是任何数的步骤和
        return false;
    }

    // 计算num的步骤和是多少，并返回
    public static int stepSum(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num;
            num /= 10;
        }
        return sum;
    }

    // for test
    public static HashMap<Integer, Integer> generateStepSumNumberMap(int numMax) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= numMax; i++) {
            map.put(stepSum(i), i);
        }
        return map;
    }

    // for test
    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        System.out.println("测试开始");
        for (int i = 0; i <= maxStepSum; i++) {
            if (isStepSum(i) ^ ans.containsKey(i)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }

}
