package 体系学习班.class39;

import java.util.HashSet;
import java.util.TreeSet;

// 给定一个非负数组arr，和一个正数m。 返回arr的所有子序列中累加和%m之后的最大值。
public class Code01_SubsquenceMaxModM {

    // 1、暴力递归，将所有可能的选择情况的累加和全部都暴力递归出来，然后存入HashSet中。然后从HashSet中取最大的一个。
    // 但是这个写法如果数组的数量太多，就有可能超过10^8超时
    public static int max1(int[] arr, int m) {
        // 存储全部情况的累加和
        HashSet<Integer> set = new HashSet<>();
        // 从第0个数，累加和为0开始暴力递归
        process(arr, 0, 0, set);
        // 统计出所有累加和中最大的那个
        int max = 0;
        for (Integer sum : set) {
            max = Math.max(max, sum % m);
        }
        return max;
    }

    // 递归过程中把结果都存到了HashSet中，所以不需要返回值
    // index：表示递归到第几个数了。当前index有两个选择，要它或者不要它
    // sum：记录当前已经选择到的累加和
    public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
        // basecase   当递归选择完最后一个数时，说明这一个分支选择结束了，就将这个分支求出来的累加和记录到Set中
        if (index == arr.length) {
            set.add(sum);
        } else {
            // 两个选择：1、要当前index   sum = sum + arr[index]。2、不要当前index   sum = sum
            // 不管选择什么，下一层递归都要向后一个位置，index+1
            process(arr, index + 1, sum, set);
            process(arr, index + 1, sum + arr[index], set);
        }
    }

    // 2、动态规划，求arr从0 ~i自由选择这些数字，能不能把某一个累加和j搞出来。能搞出来就是true。
    // 如果这个题目数组中值过大，就会导致累加和很大，j就会非常堵，构建出来的dp表可能大小超过10^8，那么这个方法就会超时了
    public static int max2(int[] arr, int m) {
        // 统计整个数组的累加和。不管怎么选择，子序列的累加和都不会超过sum
        int sum = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }
        // 构造dp数组，表示arr从0 ~i自由选择这些数字，能不能把某一个累加和j搞出来。能搞出来就是true。
        // 这里dp数组大小就取决于arr数组大小和数组的整体累加和sum，也相当于arr数组的值的大小。
        boolean[][] dp = new boolean[N][sum + 1];
        // 给第一列赋值，当j=0的时候，所有的0~i区间都可以凑出累加和为0的数，也就是啥都不选。
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 第0个数，也能凑出arr[0]累加和，也就是只选它一个
        dp[0][arr[0]] = true;
        // 给dp普遍位置赋值
        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                /**
                 * 1、第一种选择是不要i位置的数，这个问题就变成了你是否能用之前0到i-1的数字，能不能把j搞出来。
                 * 2、第二种选择是我想使用i位置的数字，这个问题就变成了你利用之前的0到i- 1的所有数字，你能不能把j - i位置的值给去掉，把这个累加和搞出来，
                 * 	  也就是谁就是之前0到i-1的结果帮你搞定j-arr[i]的值，你再自己凑个arr[i]，就正好是j。
                 *
                 * 这个题肯定是先写暴力递归，再改dp
                 */
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }

        // 将所有可能凑出来的累加和，找到模M结果最大的那一个
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[N - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }


    // 3、动态规划，arr 0..i所有的数字自由选择，所搞出来的所有累加和再模m之后有没有余数j，能搞出余数j就是true。
    public static int max3(int[] arr, int m) {
        int N = arr.length;
        // 0...m-1
        // 表示arr 0..i所有的数字自由选择，所搞出来的所有累加和再模m之后有没有余数j，能搞出余数j就是true。
        // 这里dp数组的大小就取决于arr数组大小和要模的m的大小，因为取模的结果不会超过m
        boolean[][] dp = new boolean[N][m];
        // // 给第一列赋值，当j=0的时候，所有的0~i区间都可以凑出累加和模m为0的数，也就是啥都不选。
        for (int i = 0; i < N; i++) {
            dp[i][0] = true;
        }
        // 第0个数，也能凑出arr[0] % m，也就是只选它一个
        dp[0][arr[0] % m] = true;
        // 给普遍位置赋值
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < m; j++) {
                // dp[i][j] T or F
                /**
                 * 1）完全不使用i位置数字
                 * 	  就只靠dp[i-1][j]：arr从0~i-1所有的数字搞出来的累加和能不能模完M之后出j
                 * 2）使用i位置的数字
                 *    假设i位置得值和M取模是a，如果我之前的数字能够帮我凑出余数j-a，此时算上我的余数之后就正好凑到了。
                 *    第二个分支如果我模M之后余数a是大于j的，那么我们就需要一个M+j -a，绕一个m回来，才能凑出模为j
                 */
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                // 判断arr[i] % m是否小于等于j
                if (cur <= j) {
                    dp[i][j] |= dp[i - 1][j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][m + j - cur];
                }
            }
        }
        // 取最大值返回
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[N - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }

    // 4、分治
    // 如果arr的累加和很大，m也很大
    // 但是arr的长度相对不大，这种情况上面的所有方法都不合适了，因为上面的所有执行次数都取决于arr数组的值、累加和、m。
    // 这个题目，我们可以采用第一个方法的思路，那个递归次数只取决于数组数量。
    public static int max4(int[] arr, int m) {
        // 过滤特殊参数
        if (arr.length == 1) {
            return arr[0] % m;
        }
        // 将数组划分成两半
        int mid = (arr.length - 1) / 2;
        // 记录左部分的累加和
        TreeSet<Integer> sortSet1 = new TreeSet<>();
        // 只从0~mid范围内任意选择数来组成累加和，将所有可能的情况的累加和取模的结果加入到sortSet1中
        process4(arr, 0, 0, mid, m, sortSet1);
        // 记录右部分的累加和
        TreeSet<Integer> sortSet2 = new TreeSet<>();
        // 只从mid+1~arr.length - 1范围内任意选择数来组成累加和，将所有可能的情况的累加和取模的结果加入到sortSet2中
        process4(arr, mid + 1, 0, arr.length - 1, m, sortSet2);

        // 注意，上面的sortSet1和sortSet2都包括了什么都不选的情况，也就是sum=0。
        // 基于这个，我们就可以将完全选左边的数和完全选右边的数都整合到左右两边合并的过程中了，将左边最大值和右边最大值都会参与到整个的比较过程中。


        // 将左右两部分的结果进行整合
        int ans = 0;
        // 以左部分的结果为基础，遍历
        for (Integer leftMod : sortSet1) {
            // 将左边的结果和右边的最有结果去匹配，找到最大值。
            // sortSet2.floor(m - 1 - leftMod)：找到sortSet2中<=m-1-eleftMod的数
            // 这里之所以还要减1，是因为不能让左右两部分的模相加得m，这样最后模的结果就是0了
            // 我们尽量凑出来leftMod + 一个右部分的结果，能尽可能地接近m-1，这样的就是最优解。是一个像最简单的左右不分整合逻辑
            ans = Math.max(ans, leftMod + sortSet2.floor(m - 1 - leftMod));
        }
        // 经过上面的流程，我们就得到了最后的结果。注意上面的过程中也讨论了左侧最大值和右侧最大值的情况，都会参与到比较流程中，如果他们确实值时最大的，也就会被记录到ans结果中
        // 这是因为sortSet1和sortSet2都包括了什么都不选的情况，在递归中会将这个什么都不选的情况写入到set中，所以也就实现了上面的整合流程可以凑出来只有左侧或只有右侧的情况
        return ans;
    }

    // 从index出发，最后有边界是end+1，arr[index...end]
    public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> sortSet) {
        // basecase  当遍历完了所有的数
        if (index == end + 1) {
            // 将当前这个分支中所有选择情况下的累加和对m取模的结果加入到set中
            sortSet.add(sum % m);
        } else {
            // 两种选择，要index或者不要index
            process4(arr, index + 1, sum, end, m, sortSet);
            process4(arr, index + 1, sum + arr[index], end, m, sortSet);
        }
    }

    // for test
    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int len = 10;
        int value = 100;
        int m = 76;
        int testTime = 500000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int ans1 = max1(arr, m);
            int ans2 = max2(arr, m);
            int ans3 = max3(arr, m);
            int ans4 = max4(arr, m);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

    }

}

