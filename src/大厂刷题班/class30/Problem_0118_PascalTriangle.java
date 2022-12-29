package 大厂刷题班.class30;
import java.util.ArrayList;
import java.util.List;

// 数组    动态规划
// https://leetcode.cn/problems/pascals-triangle/
public class Problem_0118_PascalTriangle {
    public List<List<Integer>> generate(int numRows) {
        // 记录要返回的每一层的答案
        List<List<Integer>> ans = new ArrayList<>();

        // 从第一层开始构造杨辉三角形，一共有numRows层
        for (int i = 1; i <= numRows; i++) {
            // 记录当前第i层杨辉三角形的数
            List<Integer> ansLevel = new ArrayList<>();
            // 一开始先加入一个1
            ansLevel.add(1);
            // 如果不是第一层，那么这一层一定有多个数
            if (i != 1) {
                // 从ans中获取上一层杨辉三角形的结果
                List<Integer> preLevel = ans.get(ans.size() - 1);
                // 遍历上一层杨辉三角形的结果
                for (int j = 0; j < i - 2; j++) {
                    // 当前这一层的数就是上一层对应位置，和上一层对应位置的下一个位置的两个数加和得到的
                    ansLevel.add(preLevel.get(j) + preLevel.get(j + 1));
                }
                // 在当前层最后也要加一个1
                ansLevel.add(1);
            }
            // 将当前层的答案加入到ans中
            ans.add(ansLevel);
        }
        // 返回答案
        return ans;
    }
}
