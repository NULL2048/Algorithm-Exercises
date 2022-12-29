package 大厂刷题班.class30;
import java.util.ArrayList;
import java.util.List;
// 数组   动态规划   空间压缩
// https://leetcode.cn/problems/pascals-triangle-ii/
public class Problem_0119_PascalTriangleII {
    public List<Integer> getRow(int rowIndex) {
        // 记录上一层的答案
        List<Integer> preLevel  = new ArrayList<>();

        // 从第一层开始构造杨辉三角形，构造到第rowIndex + 1层即可，因为最后返回的是preLevel上一层，所以我们需要构造到rowIndex + 1层才行
        for (int i = 1; i <= rowIndex + 1; i++) {
            // 记录当前第i层杨辉三角形的数
            List<Integer> ansLevel = new ArrayList<>();
            // 一开始先加入一个1
            ansLevel.add(1);
            // 如果不是第一层，那么这一层一定有多个数
            if (i != 1) {
                // 遍历上一层杨辉三角形的结果
                for (int j = 0; j < i - 2; j++) {
                    // 当前这一层的数就是上一层对应位置，和上一层对应位置的下一个位置的两个数加和得到的
                    ansLevel.add(preLevel.get(j) + preLevel.get(j + 1));
                }
                // 在当前层最后也要加一个1
                ansLevel.add(1);
            }
            preLevel = ansLevel;
        }
        // 返回答案
        return preLevel;
    }

    // 左神的代码写的更好，完全只用了一个ans，就实现了代码。我自己写的代码都用了两个List
    public List<Integer> getRow1(int rowIndex) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i <= rowIndex; i++) {
            for (int j = i - 1; j > 0; j--) {
                ans.set(j, ans.get(j - 1) + ans.get(j));
            }
            ans.add(1);
        }
        return ans;
    }
}
