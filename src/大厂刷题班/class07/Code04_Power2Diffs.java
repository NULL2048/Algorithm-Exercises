package 大厂刷题班.class07;

import java.util.Arrays;
import java.util.HashSet;

// 数组有单调性，就马上想到可能使用双指针（窗口）
public class Code04_Power2Diffs {
    /*
     * 给定一个有序数组arr，其中值可能为正、负、0。 返回arr中每个数都平方之后不同的结果有多少种？
     *
     * 给定一个数组arr，先递减然后递增，返回arr中有多少个绝对值不同的数字？
     *
     */

    // 1、直接利用set来统计。时间复杂度O(N)，额外空间复杂度O(N)
    public static int diff1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int cur : arr) {
            set.add(cur * cur);
        }
        return set.size();
    }

    // 2、利用双指针。时间复杂度O(N)，额外空间复杂度O(1)
    public static int diff2(int[] arr) {
        int N = arr.length;
        int L = 0;
        int R = N - 1;
        int count = 0;
        int leftAbs = 0;
        int rightAbs = 0;
        while (L <= R) {
            // 统计绝对值不同的数的种类数
            count++;
            leftAbs = Math.abs(arr[L]);
            rightAbs = Math.abs(arr[R]);
            // 让大指针移动
            if (leftAbs < rightAbs) {
                // 碰到相等的，就继续往左滑动
                while (R >= 0 && Math.abs(arr[R]) == rightAbs) {
                    R--;
                }
                // 让大指针移动
            } else if (leftAbs > rightAbs) {
                // 碰到相等的，就继续往右滑动
                while (L < N && Math.abs(arr[L]) == leftAbs) {
                    L++;
                }
                // 如果两个数相等，就一起向中间移动
            } else {
                // 碰到相等的，就继续移动跳过这些数
                while (L < N && Math.abs(arr[L]) == leftAbs) {
                    L++;
                }
                while (R >= 0 && Math.abs(arr[R]) == rightAbs) {
                    R--;
                }
            }
        }
        return count;
    }

    // for test
    public static int[] randomSortedArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        Arrays.sort(ans);
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int cur : arr) {
            System.out.print(cur + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 100;
        int value = 500;
        int testTimes = 200000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomSortedArray(len, value);
            int ans1 = diff1(arr);
            int ans2 = diff2(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("test finish");
    }
}
