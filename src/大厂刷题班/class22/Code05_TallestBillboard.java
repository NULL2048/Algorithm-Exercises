package 大厂刷题班.class22;

import java.util.HashMap;
import java.util.Map;

// 动态规划   只不过这个题和以前的动态规划都不一样，以前都是一个值依赖另一个位置的值，但是这个是一个表依赖另一个表
// dp[i]依赖dp[i-1]，但是这个dp[i]是一个map表，并不是具体的一个值，所以整个题的思路依旧是动态规划。构建新表需要依赖老表的内容
// 本题测试链接 : https://leetcode.cn/problems/tallest-billboard/
public class Code05_TallestBillboard {
    // 1、我自己写的代码，比左神写的代码在常数时间上更优秀
    public int tallestBillboard(int[] rods) {
        // 过滤无效参数
        if (rods == null || rods.length == 0) {
            return 0;
        }
        // 数组长度
        int n = rods.length;
        // 记录所有的不相交集合的差值记录
        // key：从数组中选择的两个子集累加和的差值。在数组还没有遍历之前，能够找到两个集合就是两个空集，空集对空集， 差值为0。所以一开始Map中存入的是（0，0）。
        // value：值是一个集合的累加和，那么存储的是哪个集合的累加和呢？就是所有差值为key的集合对中，两个集合累加和最大的那一对集合中累加和较小的那个（作为两个集合累加和的基线，基线越大，意味着广告牌越高，我们要尽可能存基线大的）。
        HashMap<Integer, Integer> dp = new HashMap<>();
        // 在遍历数组的过程中，记录之前的旧dp中的数据，因为对同一个Map边遍历边修改会出现错误，我们这里只是需要取出旧dp中的数据来和rods[i]组成新的集合对
        HashMap<Integer, Integer> oldDp = null;
        // 一开始只有两个空集，所以加入(0,0)
        dp.put(0, 0);
        // 遍历数组
        for (int i = 0; i < n; i++) {
            // 用此时dp的数据构造出oldDp
            oldDp = new HashMap<>(dp);
            // 遍历oldDp中的数据
            for (Map.Entry<Integer, Integer> entry : oldDp.entrySet()) {
                // 取出所有的集合对，计算出每个集合对中每个集合的累加和
                // sum1使用value基线加差值得出来的
                int sum1 = entry.getValue() + entry.getKey();
                int sum2 = entry.getValue();

                // 1、将rods[i]加入到sum1所在集合，和sum2构成新的集合对
                // 新的集合累加和
                int newSum = rods[i] + sum1;
                // 新的差值
                int newKey = newSum - sum2;
                // 查看当前旧dp中是否已经存在这个差值了
                // getOrDefault() 方法：获取指定 key 对应对 value，如果找不到 key ，则返回设置的默认值。
                int value = dp.getOrDefault(newKey, 0);
                // 如果已经存在这个差值了，当新的基线sum2大于老的基线value，就更新这个差值的基线
                // 或者如果旧dp中就没有这个差值，就将该差值的信息加入到map中
                // 因为这里是rods[i]加入到sum1，本来sum2就小于sum1，现在sum2已经还是小于newSum的，所以sum2仍然是新差值newKey的基线
                if ((value != 0 && sum2 > value) || value == 0) {
                    // 需要将所有的差值情况都加入到map中，因为后面的数我们还没有遍历到，是有可能将任何非0的差值调平的
                    dp.put(newKey, sum2);
                }

                // 2、将rods[i]加入到sum2所在集合，和sum1构成新的集合对
                newSum = rods[i] + sum2;
                // 这次就不能确定sum1和newSum谁大了，需要比较一下。用绝对值取newKey
                newKey = Math.abs(sum1 - newSum);
                value = dp.getOrDefault(newKey, 0);
                // 新组成的集合对的基线就是sum1和newSum中较小的那一个
                if ((value != 0 && Math.min(sum1, newSum) > value) || value == 0) {
                    dp.put(newKey, Math.min(sum1, newSum));
                }
            }
        }

        // 最后key为0（即差值为0）的value就是最优答案。（因为value存储的一定是集合对的累加和最大的那一个）
        return dp.get(0);
    }

    // 2、左神的代码
    public int tallestBillboard2(int[] rods) {
        // key 集合对的某个差
        // value 满足差值为key的集合对中，最好的一对，较小集合的累加和
        // 较大 -> value + key
        HashMap<Integer, Integer> dp = new HashMap<>(), cur;
        dp.put(0, 0);// 空集 和 空集
        for (int num : rods) {
            if (num != 0) {
                // cur 内部数据完全和dp一样
                cur = new HashMap<>(dp); // 考虑x之前的集合差值状况拷贝下来
                for (int d : cur.keySet()) {
                    int diffMore = cur.get(d); // 最好的一对，较小集合的累加和
                    // x决定放入，比较大的那个
                    dp.put(d + num, Math.max(diffMore, dp.getOrDefault(num + d, 0)));
                    // x决定放入，比较小的那个
                    // 新的差值 Math.abs(x - d)
                    // 之前差值为Math.abs(x - d)，的那一对，就要和这一对，决策一下
                    // 之前那一对，较小集合的累加和diffXD
                    int diffXD = dp.getOrDefault(Math.abs(num - d), 0);
                    if (d >= num) { // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
                        dp.put(d - num, Math.max(diffMore + num, diffXD));
                    } else { // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
                        dp.put(num - d, Math.max(diffMore + d, diffXD));
                    }
                }
            }
        }
        return dp.get(0);
    }
}
