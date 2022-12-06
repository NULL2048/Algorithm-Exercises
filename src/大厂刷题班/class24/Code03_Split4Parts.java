package 大厂刷题班.class24;

import java.util.HashMap;
import java.util.HashSet;
// 数组能不能分成4个相等的部分
// 前缀和   数组   一看到这道题涉及到需要知道任意范围的子数组的累加和，马上意识到可以用前缀和数组来进行优化
public class Code03_Split4Parts {
    // 1、双指针方法
    public static boolean canSplits1(int[] arr) {
        if (arr == null || arr.length < 7) {
            return false;
        }
        HashSet<String> set = new HashSet<String>();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        int leftSum = arr[0];
        for (int i = 1; i < arr.length - 1; i++) {
            set.add(String.valueOf(leftSum) + "_" + String.valueOf(sum - leftSum - arr[i]));
            leftSum += arr[i];
        }
        int l = 1;
        int lsum = arr[0];
        int r = arr.length - 2;
        int rsum = arr[arr.length - 1];
        while (l < r - 3) {
            if (lsum == rsum) {
                String lkey = String.valueOf(lsum * 2 + arr[l]);
                String rkey = String.valueOf(rsum * 2 + arr[r]);
                if (set.contains(lkey + "_" + rkey)) {
                    return true;
                }
                lsum += arr[l++];
            } else if (lsum < rsum) {
                lsum += arr[l++];
            } else {
                rsum += arr[r--];
            }
        }
        return false;
    }

    // 2、这个是上课讲的方法
    public static boolean canSplits2(int[] arr) {
        // 过滤无效参数
        if (arr == null || arr.length < 7) {
            return false;
        }
        // key 某一个累加和， value出现的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 构造前缀和数组
        int sum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            map.put(sum, i);
            sum += arr[i];
        }
        // 因为每一部分都要有数，所以第一刀至少要从下标1位置试，arr[0]一定是在第一部分的
        int lsum = arr[0]; // 第一刀左侧的累加和
        // 尝试以每个位置作为第一刀，确定了每一部分应该的累加和后，看看能不能分出四部分
        for (int s1 = 1; s1 < arr.length - 5; s1++) { // s1是第一刀的位置
            // 应该要找的下一个累加和位置，来确定第二刀
            int checkSum = lsum * 2 + arr[s1]; // 100 x 100   100*2 + x
            // 查看是否有checkSum这个累加和
            if (map.containsKey(checkSum)) {
                int s2 = map.get(checkSum); // j -> y
                // 应该要找的下一个累加和位置，来确定第三刀
                checkSum += lsum + arr[s2];
                // 查看是否有checkSum这个累加和
                if (map.containsKey(checkSum)) { // 100 * 3 + x + y
                    int s3 = map.get(checkSum); // k -> z
                    if (checkSum + arr[s3] + lsum == sum) {
                        // 只要是能找到合法的三刀，直接返回true
                        return true;
                    }
                }
            }
            // 扩大第一刀左侧累加和，第一刀尝试位置右移
            lsum += arr[s1];
        }
        // 如果尝试完了也没有找到合法的切法，就返回false
        return false;
    }

    // for test
    public static int[] generateRondomArray() {
        int[] res = new int[(int) (Math.random() * 10) + 7];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * 10) + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        int testTime = 3000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRondomArray();
            if (canSplits1(arr) ^ canSplits2(arr)) {
                System.out.println("Error");
            }
        }
    }
}
