package 大厂刷题班.class04;
// 贪心  预处理结构
// 测试链接 : https://leetcode.cn/problems/candy/
public class Code05_CandyProblem {
    // 这个是我自己写的，O(N)时间复杂度  O(N)空间复杂度
    public static int candy(int[] ratings) {
        if (ratings == null || ratings.length == 0) {
            return 0;
        }

        // 构造预处理数组
        int[] left = new int[ratings.length];
        int[] right = new int[ratings.length];

        // 从左向右构造
        left[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i - 1] < ratings[i]) {
                left[i] = left[i - 1] + 1;
                // 我们不管相邻两个数相等的情况，题目要求最少准备多少个，所以碰到相等或小于相邻人的情况下，直接赋值为1，这样就能取得最少准备的个数。
            } else {
                left[i] = 1;
            }
        }

        // 从右向左构造
        right[ratings.length - 1] = 1;
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i + 1] < ratings[i]) {
                right[i] = right[i + 1] + 1;
            } else {
                right[i] = 1;
            }
        }

        // 取每个位置的最大值，就能保证题目的要求
        int sum = 0;
        for (int i = 0; i < ratings.length; i++) {
            sum += Math.max(left[i], right[i]);
        }

        return sum;
    }


    // 这是原问题的优良解
    // 时间复杂度O(N)，额外空间复杂度O(N)
    public static int candy1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[] left = new int[N];
        for (int i = 1; i < N; i++) {
            if (arr[i - 1] < arr[i]) {
                left[i] = left[i - 1] + 1;
            }
        }
        int[] right = new int[N];
        for (int i = N - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                right[i] = right[i + 1] + 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < N; i++) {
            ans += Math.max(left[i], right[i]);
        }
        return ans + N;
    }

    // 这是原问题空间优化后的解
    // 时间复杂度O(N)，额外空间复杂度O(1)
    public static int candy2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex2(arr, 0);
        int res = rightCands(arr, 0, index++);
        int lbase = 1;
        int next = 0;
        int rcands = 0;
        int rbase = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex2(arr, index - 1);
                rcands = rightCands(arr, index - 1, next++);
                rbase = next - index + 1;
                res += rcands + (rbase > lbase ? -lbase : -rbase);
                lbase = 1;
                index = next;
            } else {
                res += 1;
                lbase = 1;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex2(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] <= arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int rightCands(int[] arr, int left, int right) {
        int n = right - left + 1;
        return n + n * (n - 1) / 2;
    }

    // 这是进阶问题的最优解，不要提交这个
    // 时间复杂度O(N), 额外空间复杂度O(1)
    public static int candy3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int index = nextMinIndex3(arr, 0);
        int[] data = rightCandsAndBase(arr, 0, index++);
        int res = data[0];
        int lbase = 1;
        int same = 1;
        int next = 0;
        while (index != arr.length) {
            if (arr[index] > arr[index - 1]) {
                res += ++lbase;
                same = 1;
                index++;
            } else if (arr[index] < arr[index - 1]) {
                next = nextMinIndex3(arr, index - 1);
                data = rightCandsAndBase(arr, index - 1, next++);
                if (data[1] <= lbase) {
                    res += data[0] - data[1];
                } else {
                    res += -lbase * same + data[0] - data[1] + data[1] * same;
                }
                index = next;
                lbase = 1;
                same = 1;
            } else {
                res += lbase;
                same++;
                index++;
            }
        }
        return res;
    }

    public static int nextMinIndex3(int[] arr, int start) {
        for (int i = start; i != arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                return i;
            }
        }
        return arr.length - 1;
    }

    public static int[] rightCandsAndBase(int[] arr, int left, int right) {
        int base = 1;
        int cands = 1;
        for (int i = right - 1; i >= left; i--) {
            if (arr[i] == arr[i + 1]) {
                cands += base;
            } else {
                cands += ++base;
            }
        }
        return new int[] { cands, base };
    }

    public static void main(String[] args) {
        int[] test1 = { 3, 0, 5, 5, 4, 4, 0 };
        System.out.println(candy2(test1));

        int[] test2 = { 3, 0, 5, 5, 4, 4, 0 };
        System.out.println(candy3(test2));
    }
}
