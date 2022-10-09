package 大厂刷题班.class04;

// 数组压缩 + 子数组 + 动态规划
// 本题测试链接 : https://leetcode-cn.com/problems/max-submatrix-lcci/
public class Code03_SubMatrixMaxSum {
    public static int[] getMaxMatrix(int[][] matrix) {
        // 最大累加和矩阵左右两个顶点的下标
        int[] ans = new int[4];
        // 矩阵最大累加和
        int maxSum = Integer.MIN_VALUE;

        /**
         矩阵每一行的列数都是一样的，所以某个矩阵的累加和，就相当于将这个矩阵同一列每一层的数相加，形成一个一维数组，这个一维数组的累加和和原来矩阵的累加和是一样的，
         当我们求求一个矩阵的最大累加子矩阵时，就相当于求这个矩阵压缩后的一维数组的子数组的最大累加和。
         压缩数组的原理基础就是矩阵的每一行的列数一定都是相等的，不可能存在同一个矩阵不同行的列数不同的情况。
         */

        // 尝试从行0~0、0~1、0~2...0~matrix.length ... 1~1、1~2 ... 2~2、2~3、... 所有的可能组成子矩阵的情况中压缩数组找最大累加和
        for (int i = 0; i < matrix.length; i++) {
            // 记录以i行为开始，到j行为结束的矩阵的压缩数组
            int[] nums = new int[matrix[0].length];

            // 一开始先将i行的数复制到nums中，不能在原位置操作
            for (int index = 0; index < matrix[0].length; index++) {
                nums[index] = matrix[i][index];
            }
            // 记录当前压缩数组找到的最大累加和子数组情况
            int[] tempInfo = maxSubArray(nums);
            // 如果找出来的最大累加和已经超过了maxSum，那么就更新maxSum
            if (tempInfo[2] > maxSum) {
                // 左上角顶点坐标   左上角顶点就在i行上
                ans[0] = i;
                ans[1] = tempInfo[0];
                // 右下角顶点坐标   最开始的时候右下角顶点也在i行上
                ans[2] = i;
                ans[3] = tempInfo[1];
                // 更新最大累加和
                maxSum = tempInfo[2];
            }
            // 遍历以i行开始，j行结束的所有情况
            for (int j = i + 1; j < matrix.length; j++) {
                // 将j行的数据累加到nums中，进行压缩数组
                for (int k = 0; k < matrix[0].length; k++) {
                    nums[k] += matrix[j][k];
                }

                // 计算当前压缩数组的最大累加和信息
                tempInfo = maxSubArray(nums);
                // 如果找出来的最大累加和已经超过了maxSum，那么就更新maxSum
                if (tempInfo[2] > maxSum) {
                    // 左上角顶点坐标   左上角顶点就在i行上
                    ans[0] = i;
                    ans[1] = tempInfo[0];
                    // 右下角顶点坐标   右下角顶点在j行上
                    ans[2] = j;
                    ans[3] = tempInfo[1];
                    // 更新最大累加和
                    maxSum = tempInfo[2];
                }
            }
        }

        return ans;
    }

    // 找到一维数组nums的最大累加和子数组的左右下标范围和最大值，nums最大累加和子数组下标范围是（ans[0], ans[1]）,最大累加和为ans[2]
    public static int[] maxSubArray(int[] nums) {
        // 记录以上一个位置结尾的子数组中，最大的累加和
        int preMaxSum = nums[0];
        // 记录最大子数组累加和
        int max = preMaxSum;

        // 记录最大累加和信息
        int[] ans = new int[3];
        ans[2] = preMaxSum;
        // 记录以上一个位置结尾的子数组中最大累加和子数组范围
        int[] preInfo = new int[2];

        // 去遍历以每一个位置为结尾的子数组，查找最大累加和
        for (int i = 1; i < nums.length; i++) {
            // 情况一：以i位置结尾的子数组只有i位置本身
            int p1 = nums[i];
            // 情况二：以i位置结尾的子数组还有左边其他的数，这种情况下最大累加和就是以i-1位置为结尾的子数组中的最大累加和 + nums[i]
            int p2 = preMaxSum + nums[i];

            if (p1 > p2) {
                preMaxSum = p1;
                // 更新最大累加和信息
                if (max < preMaxSum) {
                    ans[0] = i;
                    ans[1] = i;
                    ans[2] = preMaxSum;
                    max = preMaxSum;
                }

                // 将以当前位置为结尾的最大累加和子数组信息更新到preInfo中，也就是只有他本身。
                preInfo[0] = i;
                preInfo[1] = i;
            } else {
                preMaxSum = p2;
                // 更新最大累加和信息
                if (max < preMaxSum) {
                    // 左边界就是上一个位置最大累加和子数组的左边界
                    ans[0] = preInfo[0];
                    // 右边界就是当前的位置
                    ans[1] = i;
                    ans[2] = preMaxSum;
                    max = preMaxSum;
                }

                // 更新preInfo，将以当前位置为结尾的最大累加和子数组右边界更新到preinfo[1]中，preinfo[0]还是维持以前的值不用变。
                preInfo[1] = i;

            }
        }

        return ans;
    }
}
