package 大厂刷题班.class13;

// 贪心   这种题见过就会，没见过就不会，硬背
// 本题测试链接 : https://leetcode.cn/problems/super-washing-machines/
public class Code03_SuperWashingMachines {
    public int findMinMoves(int[] machines) {
        // 过滤无效参数
        if (machines == null || machines.length == 0) {
            return 0;
        }

        int n = machines.length;
        int sum = 0;
        // 统计衣服总数
        for (int i = 0; i < n; i++) {
            sum += machines[i];
        }
        // 如果衣服总数不能整除洗衣机数量，那么就不可能让让所有的洗衣机都拥有相同的衣服数，直接返回-1
        if (sum % n != 0) {
            return -1;
        }

        // 计算每个洗衣机平均应该放多少件衣服
        int avl = sum / n;
        // 当前i位置左部分的衣服总数
        int leftSum = 0;
        // 要返回的答案，最少轮次
        int ans = Integer.MIN_VALUE;
        // 开始从左向右遍历，按照我们的贪心策略，根据那三种情况来进行决策
        for (int i = 0; i < n; i++) {
            // 计算i左边部分一共多多少件/欠多少件衣服
            int leftRest = leftSum - i * avl; // 用左边现在已有的衣服总数减去左边应该有的衣服总数（左边的洗衣机数量*平均每台洗衣机应该放的衣服数）
            // 计算i右边部分一共多多少件/欠多少件衣服
            int rightRest = sum - leftSum - machines[i] - (n - i - 1) * avl; // 用总的衣服数减去i左边部分的衣服数，再减去i位置洗衣机的衣服数，得到的就是i右遍部分的衣服数（sum - leftSum - machines[i]），然后用这个数再减去右遍应该有的衣服总数（右边的洗衣机数量*平均每台洗衣机应该放的衣服数）
            // 当i的左右两边都是欠衣服，说明左右两边只能靠i来分给他们衣服了
            if (leftRest < 0 && rightRest < 0) {
                // 这种情况的轮数至少是Math.abs(leftRest) + Math.abs(rightRest)，然后再和ans比较，看看是否推高了最大瓶颈
                ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
                // 剩下的两种情况，i的左边欠衣服，右边多衣服和i的左边多衣服，右边欠衣服。这两种情况只能是靠多衣服的给补给欠衣服的
            } else {
                // 这种情况的轮数至少是Math.max(Math.abs(leftRest), Math.abs(rightRest)，然后再和ans比较，看看是否推高了最大瓶颈
                ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
            }

            // 累加i左边部分的所有衣服数量
            leftSum += machines[i];
        }
        // 返回最少轮数
        return ans;
    }
}
