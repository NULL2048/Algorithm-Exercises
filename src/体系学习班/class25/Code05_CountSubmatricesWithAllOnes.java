package 体系学习班.class25;

// 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
public class Code05_CountSubmatricesWithAllOnes {
    // 先按行遍历二维数组，生成每一行的直方图
    public int numSubmat(int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        // 用来记录当前遍历到这一行的上一行的直方图高度
        int[] heights = new int[col];
        // 记录子矩阵个数
        int cnt = 0;
        // 按行遍历二维数组
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 生成本行的直方图高度，如果本行为0，则直接将对应位置的高度设置为0，如果不为零，则继承类加上一行直方图的高度
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            // 调用countMatInRow方法，利用单调栈统计当前这一行直方图中有多少个子矩阵
            cnt += countMatInRow(heights);
        }
        return cnt;
    }

    // 比如
    //              1
    //              1
    //              1         1
    //    1         1         1
    //    1         1         1
    //    1         1         1
    //
    //    2  ....   6   ....  9
    // 如上图，假设在6位置，1的高度为6
    // 在6位置的左边，离6位置最近、且小于高度6的位置是2，2位置的高度是3
    // 在6位置的右边，离6位置最近、且小于高度6的位置是9，9位置的高度是4
    // 此时我们求什么？
    // 1) 求在3~8范围上，必须以高度6作为高的矩形，有几个？
    // 2) 求在3~8范围上，必须以高度5作为高的矩形，有几个？
    // 也就是说，<=4的高度，一律不求
    // 那么，1) 求必须以位置6的高度6作为高的矩形，有几个？
    // 3..3  3..4  3..5  3..6  3..7  3..8
    // 4..4  4..5  4..6  4..7  4..8
    // 5..5  5..6  5..7  5..8
    // 6..6  6..7  6..8
    // 7..7  7..8
    // 8..8
    // 这么多！= 21 = (9 - 2 - 1) * (9 - 2) / 2
    // 这就是任何一个数字从栈里弹出的时候，计算矩形数量的方式
    public int countMatInRow(int[] heights) {
        // 创建单调栈
        int n = heights.length;
        int top = -1;
        // 栈中存储的是下标
        int[] stack = new int[n];
        // 记录当前这一层直方图中有多少个子矩阵
        int cnt = 0;
        // 遍历当前这一行的直方图
        for (int i = 0; i < n; i++) {
            // 整个过程就是单调栈的弹出和压入
            // 如果要压入栈的值小于单调栈栈顶的值，这就会破坏单调栈结构，需要将栈顶元素弹出，收集该元素的信息，得到能左右扩张的最大区域范围
            while (top != -1 && heights[stack[top]] >= heights[i]) {
                // 弹出栈顶元素
                int index = stack[top--];
                // 得到左边距离弹出元素最近的并且小于它的值的下标，如果栈中已经没有元素了，说明弹出值的左边已经没有小于它的值了，就赋值为-1
                // 注意这里如果没有比它小的了，就设置为-1，也就相当于标记上可用大区域的左边界的向左一个为止，用于后续的计算
                int leftIndex = top == -1 ? -1 : stack[top];
                // 右边距离弹出元素最近并且小于它的值的下标
                int rightIndex = i;
                // 此时我们得到最大扩张的大区域范围就是leftIndex~rightIndex之前的范围，不包括下标leftIndex和rightIndex
                // 选取左右最近的比自己小的最大值，注意这里要讨论top == -1的情况
                int maxHeight = top == -1 ? heights[rightIndex] : Math.max(heights[leftIndex], heights[rightIndex]);

                // 这里要统计高度heights[index] ~ maxHeight+1 的矩阵的所有可能情况
                // 每一种高度可能的情况就有((1 + n) * n) >> 1中，n为当前大区域的宽度，其实就是求一个等差数列
                // 大区域宽度 = rightIndex - leftIndex - 1
                // 这里如果大范围右边的rightIndex为止高度等于此时大范围的高度heights[index]，我们就直接不求了，而是在后续遍历到相等高度的直方图时再求，因为现在求得结果肯定也是不全的，只有该高度最右侧的值才能扩张出最完整的大区域
                // 在相等的情况下heights[index] - maxHeight == 0，此时就将0累加进cnt了，相当于没有计算
                cnt += (heights[index] - maxHeight) * computeLength(rightIndex - leftIndex - 1);
            }
            // 将新的直方图下标加入单调栈
            stack[++top] = i;
        }

        // 单独处理栈中剩余的元素
        while (top != -1) {
            // 栈中剩余的元素他们的右边已经没有比他们小的值了
            // 弹出栈顶
            int index = stack[top--];
            // 得到左边距离弹出元素最近的并且小于它的值的下标，如果栈中已经没有元素了，说明弹出值的左边已经没有小于它的值了，就赋值为-1
            int leftIndex = top == -1 ? -1 : stack[top];
            // 此时弹出的值的右边已经没有比他小的元素了，所以这里直接设置成n，也就是边界下标n-1的右边一个位置，这样方便后面计算子矩阵个数
            int rightIndex = n;
            // 选取左右最近的比自己小的最大值，注意这里要讨论top == -1的情况
            int maxHeight = top == -1 ? 0 : heights[leftIndex];

            // 此时我们得到最大扩张的大区域范围就是leftIndex~n之前的范围，不包括下标leftIndex和n
            cnt += (heights[index] - maxHeight) * computeLength(rightIndex - leftIndex - 1);
        }
        return cnt;
    }

    // 计算指定n宽度的大区域内，固定高度的子矩阵一共有多少个
    public int computeLength(int n) {
        // 这个计算结果一定是偶数(存在一个(n + 1) * n 的操作)，所以可以通过右移1位计算出精确的除以2的结果
        return ((1 + n) * n) >> 1;
    }
}
