package 大厂刷题班.class01;

import java.util.Arrays;

public class Code01_CordCoverMaxPoint {
    // 1、贪心 + 二分法   O(N*logN)
    // arr[]数组中记录的是所有在x轴上的点，value是点在x轴上的坐标。
    // 比如x轴上一共有3个点，那么arr[]数组大小就是3，记录3个点在x轴上的位置。
    // L是绳子的长度
    public static int maxPoint1(int[] arr, int L) {
        int res = 1;
        // 去遍历x轴上的点
        for (int i = 0; i < arr.length; i++) {
            // 算出绳子从当前点开始，向后前最多能压多少个点，采用二分法
            // arr[i] - L指的绳子能到的左边界
            int nearest = nearestIndex(arr, i, arr[i] - L);
            // 取最大值
            res = Math.max(res, i - nearest + 1);
        }
        return res;
    }

    // R是绳子的右边界  value是绳子的左边界
    // 我们就看R~value范围内绳子能盖住多少个点
    public static int nearestIndex(int[] arr, int R, int value) {
        int L = 0;
        int index = R;
        // 二分法，是在点数组arr上做二分
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return index;
    }

    // 2、滑动窗口，这个方法更加优良    O(N)
    public static int maxPoint2(int[] arr, int L) {
        int left = 0;
        int right = 0;
        int N = arr.length;
        int max = 0;
        // 整个过程中没有出现窗口回退的情况，所以时间复杂度是O(N)
        while (left < N) {
            // 滑动窗口是在arr点数组上运动的，所以要保证窗口右边界不能越界arr点数组(right < N)
            // 如果滑动窗口的在点数组上滑动，窗口涵盖的点距离长度不能超过绳子长度L(arr[right] - arr[left] <= L  涵盖的最左边的点到最右边点的距离不能超过L)
            while (right < N && arr[right] - arr[left] <= L) {
                // 如果满足上面条件，就将滑动窗口向右边扩
                right++;
            }
            // right - (left++)：计算当前窗口涵盖了几个点，因为窗口是在点数组上滑动的，所以窗口的左右边界下标相减就能求出来窗口中有几个点
            // 并且将left右移
            max = Math.max(max, right - (left++));
        }
        // 返回最大值
        return max;
    }

    // for test
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= L) {
                pre--;
            }
            max = Math.max(max, i - pre);
        }
        return max;
    }

    // for test
    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        int len = 100;
        int max = 1000;
        int testTime = 100000;
        System.out.println("测试开始");

        for (int i = 0; i < testTime; i++) {
            int L = (int) (Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, L);
            int ans2 = maxPoint2(arr, L);
            int ans3 = test(arr, L);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("oops!");
                break;
            }
        }

    }

}