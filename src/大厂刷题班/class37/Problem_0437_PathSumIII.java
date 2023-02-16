package 大厂刷题班.class37;
import java.util.HashMap;
// （数组三连问题第二问）前缀和   二叉树
// https://leetcode.cn/problems/path-sum-iii/
public class Problem_0437_PathSumIII {
    public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public int pathSum(TreeNode root, int targetSum) {
        // 记录当前已经遍历的路径中每一种前缀和的路径个数有多少
        // key：路径的前缀和
        // value：前缀和为key的路径有多少个
        HashMap<Long, Integer> preSumMap = new HashMap<>();
        // 一开始要向map中加入（0，1）
        preSumMap.put(0L, 1);

        return process(root, targetSum, 0, preSumMap);
    }

    /**
     * 遍历二叉树，统计所有路径和为targetSum的路径个数
     * @param node 当前遍历到的节点
     * @param targetSum 要凑出来的目标路径和
     * @param preSum 根节点到上一次遍历到的节点的前缀和
     * @param preSumMap 前缀和词频表
     * @return
     */
    public int process(TreeNode node, int targetSum, long preSum, HashMap<Long, Integer> preSumMap) {
        // 如果遍历到最底部了，直接返回0，因为都没有节点了，啥也凑不出来了
        if (node == null) {
            return 0;
        }

        // 从根节点一直向下到node节点路径的前缀和为allSum   之前的前缀和+当前节点的值，就是当前的总前缀和
        long allSum = preSum + node.val;
        // 记录以node节点为尾部的路径，并且路径和为targetSum的路径个数有多少
        int ans = 0;
        // 首先如果前面已经有前缀和是allSum - targetSum的路径了，根据前缀和原理，我们就相当于找到了以node节点为尾部的路径和为targetSum的路径了
        // 这一步操作一定要在下面往Map里插入新数据的前面，为了避免被影响。这里就是要在加入数据前查询才行。
        if (preSumMap.containsKey(allSum - targetSum)) {
            // 前面有多少个前缀和是allSum - targetSum的路径，我们就找到了多少个以node节点为尾部的路径和为targetSum的路径
            ans = preSumMap.get(allSum - targetSum);
        }

        // 将当前node到根节点的总前缀和加入到Map中
        // 第一次加入该前缀和
        if (!preSumMap.containsKey(allSum)) {
            preSumMap.put(allSum, 1);
            // 之前已经有该前缀和了
        } else {
            // 路径个数加1
            preSumMap.put(allSum, preSumMap.get(allSum) + 1);
        }


        // 此时ans为以node节点为尾部的路径和为targetSum的路径个数
        // 再累加上以node.left节点为尾部的路径和为targetSum的路径个数
        ans += process(node.left, targetSum, allSum, preSumMap);
        // 再累加上以node.right节点为尾部的路径和为targetSum的路径个数
        ans += process(node.right, targetSum, allSum, preSumMap);

        // 需要将本层allSum前缀和的数据都抹去，避免想上层返回时留下影响因素
        if (preSumMap.get(allSum) == 1) {
            preSumMap.remove(allSum);
        } else {
            preSumMap.put(allSum, preSumMap.get(allSum) - 1);
        }

        // 将收集到的所有符合条件的路径个数向上层返回
        return ans;
    }
}
