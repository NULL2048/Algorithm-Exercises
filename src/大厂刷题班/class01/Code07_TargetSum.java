package 大厂刷题班.class01;

import java.util.HashMap;

// https://leetcode.cn/problems/target-sum/submissions/
public class Code07_TargetSum {
    // 1、暴力递归
    public int findTargetSumWays1(int[] nums, int target) {
        return process1(nums, 0, target);
    }

    // 可以自由使用arr[index....]所有的数字！
    // 搞出rest这个数，方法数是多少？返回
    // index == 7 rest = 13
    // map "7_13" 256
    public int process1(int[] nums, int index, int rest) {
        // basecae  如果此时已经递归遍历完了所有的数组，就去判断当前rest是否为0，如果为0说明就凑出了target目标数，返回1表示找到了一种方法，否则返回0
        if (index == nums.length) {
            return rest == 0 ? 1 : 0;
        }

        // 两种选择，将两种选的的结果加起来就是当前这个状态的答案
        // 选择一：将当前index位置的数前面搞+号
        // 选择二：将当前index位置的数前面搞-号
        return process1(nums, index + 1, rest + nums[index]) + process1(nums, index + 1, rest - nums[index]);
    }

    // 2、记忆化搜索
    public int findTargetSumWays2(int[] nums, int target) {
        // 因为可变参数有可能为负数，我们这里就不用二维数组了，直接用map
        // 一定要记住，如果存在负数变量时，尽量不要搞数组了，因为用数组表示起来会非常麻烦（数组下标不能用负数），反而会把程序写乱，就直接用map，不要瞎折腾了
        HashMap<Integer, HashMap<Integer, Integer>> dp = new HashMap<>();
        return process2(nums, 0, target, dp);
    }

    // 这里关键是要熟悉HashMap的结构，这里因为有两个可变参数，所以套了两层HashMap，第一层的key表示index，然后value是一个HashMap，关联的是rest和方法数
    public int process2(int[] nums, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        // 检查缓存中是否存在当前情况的答案
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            // 直接从缓存中取答案，两层Map，用两次get
            return dp.get(index).get(rest);
        }

        // basecae  如果此时已经递归遍历完了所有的数组，就去判断当前rest是否为0，如果为0说明就凑出了target目标数，返回1表示找到了一种方法，否则返回0
        if (index == nums.length) {
            // 这里的basecase是越界情况，不需要将这个答案写到dp中，因为此时index本身也是越界的。
            return rest == 0 ? 1 : 0;
        }

        // 两种选择，将两种选的的结果加起来就是当前这个状态的答案
        // 选择一：将当前index位置的数前面搞+号
        // 选择二：将当前index位置的数前面搞-号
        int ans = process2(nums, index + 1, rest + nums[index], dp) + process2(nums, index + 1, rest - nums[index], dp);
        // 因为是HashMap，所以并不是想数组那样只要是创建了就包含了所有index情况的下标
        // 有可能执行到这里Map中还没用添加index的数据，所以这里要判断一下，如果不存在，就初始化创建一个index和对应的Map添加到dp中
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        // 将当前index和rest情况下的结果ans加入dp中
        dp.get(index).put(rest, ans);
        return ans;
    }


    // 3、动态规划
    public int findTargetSumWays3(int[] nums, int target) {
        int n = nums.length;
        int sum = 0;
        int[] arr = new int[n];
        // 将nums迁移到另一个数组中，对另一个数组操作
        // nums数组都是非负数，求他们的累加和，得到最大可能的累加数量
        for (int i = 0; i < n; i++) {
            arr[i] = nums[i];
            sum += arr[i];
        }

        // 两个优化点：
        // 1、计算所有数的累加和假设是sum，如果target>sum，不可能有答案
        // 2、如果所有数加起来是个奇数，同样一批数要么奇数，要么偶数，如果target跟sum奇偶性不一样（(target & 1) ^ (sum & 1)) != 0）, 那么一定0种方法
        if (Math.abs(target) > sum || ((target & 1) ^ (sum & 1)) != 0) {
            return 0;
        }

        // dp缓存，因为存在负数情况，所以这里用HashMap
        HashMap<Integer, HashMap<Integer, Integer>> dp = new HashMap<>();

        // 根据暴力递归中的basecase，来对dp赋初值。根据basecase，初值是在index=n时赋值的，只有当rest==0时是1，其他情况都是0
        // 赋值过程一定要严格按照暴力递归的代码，不要按照自己的想法来，比如暴力递归中basecaseindex就是等于n，那么在这里赋初值时就是对index=n时赋值，这里虽然知道idnex=n没有意义，都越界了，但是我们就严格按照暴力递归的来，不然整个动态规划可能就改错了
        HashMap<Integer, Integer> tempMap = new HashMap<>();
        for (int i = -sum; i <= sum; i++) {
            // 只有rest为0时，才赋值为1
            if (i != 0) {
                tempMap.put(i, 0);
            } else {
                tempMap.put(i, 1);
            }
        }
        // 完成赋初值
        dp.put(n, tempMap);

        // 对普遍位置赋值，根据暴力递归，每一层都依赖他们的下面一层的数据（递归函数都是index+1），所以我们确定是自底向上赋值
        for (int index = n - 1; index >= 0; index--) {
            HashMap<Integer, Integer> tMap = new HashMap<>();
            // 至于从左往右还是从右往左，不重要，反正每一层的数都是依赖下面一层，下面一层所有的数一定都已经计算出来了
            for (int rest = -sum; rest <= sum; rest++) {
                int ans = 0;
                // 这里要注意判断rest + arr[index]不要超过-sum~sum的范围，因为在赋初值的时候只赋值到了-sum~sum位置的值，如果这里不判断，就有可能取到越界位置的值，越界位置的是在本题的含义中是无意义的
                if (rest + arr[index] <= sum && rest + arr[index] >= -sum) {
                    ans = dp.get(index + 1).get(rest + arr[index]) ;
                }
                // 这里也要判断rest - arr[index]不要超过-sum~sum的范围
                if (rest - arr[index] <= sum  && rest - arr[index] >= -sum) {
                    ans += dp.get(index + 1).get(rest - arr[index]);
                }
                // 将计算的rest-ans的键值对添加到Map中
                tMap.put(rest, ans);
            }
            // 将当前index和rest情况下的答案ans添加到do中
            dp.put(index, tMap);
        }

        // 根据暴力递归主函数中递归入口位置的入参来决定这里要返回哪个位置的dp
        return dp.get(0).get(target);
    }



    // 优化点一 :
    // 你可以认为arr中都是非负数
    // 因为即便是arr中有负数，比如[3,-4,2]
    // 因为你能在每个数前面用+或者-号
    // 所以[3,-4,2]其实和[3,4,2]达成一样的效果
    // 那么我们就全把arr变成非负数，不会影响结果的
    // 优化点二 :
    // 如果arr都是非负数，并且所有数的累加和是sum
    // 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0
    // 优化点三 :
    // arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
    // 所以，如果所有数的累加和是sum，
    // 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
    // 优化点四 :
    // 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
    // 其中一个方案是 : +1 -2 +3 -4 +5 = 3
    // 该方案中取了正的集合为P = {1，3，5}
    // 该方案中取了负的集合为N = {2，4}
    // 所以任何一种方案，都一定有 sum(P) - sum(N) = target
    // 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
    // sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
    // 2 * sum(P) = target + 数组所有数的累加和
    // sum(P) = (target + 数组所有数的累加和) / 2
    // 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
    // 那么就一定对应一种target的方式
    // 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
    // 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法
    // 优化点五 :
    // 二维动态规划的空间压缩技巧
    public static int findTargetSumWays(int[] arr, int target) {
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset2(arr, (target + sum) >> 1);
    }

    // 求非负数组nums有多少个子集，累加和是s
    // 二维动态规划
    // 不用空间压缩
    public static int subset1(int[] nums, int s) {
        if (s < 0) {
            return 0;
        }
        int n = nums.length;
        // dp[i][j] : nums前缀长度为i的所有子集，有多少累加和是j？
        int[][] dp = new int[n + 1][s + 1];
        // nums前缀长度为0的所有子集，有多少累加和是0？一个：空集
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= s; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - nums[i - 1] >= 0) {
                    dp[i][j] += dp[i - 1][j - nums[i - 1]];
                }
            }
        }
        return dp[n][s];
    }

    // 求非负数组nums有多少个子集，累加和是s
    // 二维动态规划
    // 用空间压缩:
    // 核心就是for循环里面的：for (int i = s; i >= n; i--) {
    // 为啥不枚举所有可能的累加和？只枚举 n...s 这些累加和？
    // 因为如果 i - n < 0，dp[i]怎么更新？和上一步的dp[i]一样！所以不用更新
    // 如果 i - n >= 0，dp[i]怎么更新？上一步的dp[i] + 上一步dp[i - n]的值，这才需要更新
    public static int subset2(int[] nums, int s) {
        if (s < 0) {
            return 0;
        }
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for (int n : nums) {
            for (int i = s; i >= n; i--) {
                dp[i] += dp[i - n];
            }
        }
        return dp[s];
    }
}
