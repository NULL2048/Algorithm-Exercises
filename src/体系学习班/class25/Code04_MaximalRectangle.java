package 体系学习班.class25;

// 测试链接：https://leetcode.com/problems/maximal-rectangle/
public class Code04_MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        // 过滤无效参数
        if (matrix.length == 0 || matrix == null || matrix[0].length == 0) {
            return 0;
        }
        // 记录矩阵的行数和列数
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        // 这个是用来记录每一行的所有直方图的高度
        int[] heights = new int[colLen];
        // 记录面积最大值（这里也就相当于包含1的个数）
        int max = Integer.MIN_VALUE;
        // 从第一层开始向下遍历，以每一层为地基，看能像上扩张多高。每遍历一层时，此时heights数组记录的就是上一层的直方图高度
        // 如果当前这一层的值为1，那么就可以直接累加上一层对应位置的直方图高度，完成扩张
        // 如果当前这一层的值为0，那么就没办法向上扩张了，就需要从0继续开始，更新heights数组对应位置的值为0
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                // 如果当前这一层这个位置为0，就没办法接着上一层的直方图高度进行扩张了，需要重新开始记录直方图高度，将heights对应位置设置为0
                if (matrix[i][j] != '0') {
                    heights[j] += (matrix[i][j] - '0');
                } else {
                    heights[j] = 0;
                }
            }
            // 每次遍历完，heights就是存储的当前这一层的直方图向上扩张的最大高度
            // 这是就将这道题转换成了LeeyCode 84题求直方图中最大的矩形面积的问题了，只需要去看每一个直方图左右最大能扩多少，这里直接用这道题的求解代码，就能返回出来当前这一层的直方图中存在的最大矩形面积（利用单调栈求解）
            // 然后取比较每一层的值，取最大值返回即可
            max = Math.max(max, largestRectangleArea(heights));
        }
        // 返回最大面积（包含1个数最多的矩形）
        return max;
    }
    public int largestRectangleArea(int[] heights) {
        // 过滤无效参数
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int n = heights.length;
        // 创建单调栈
        int[] stack = new int [n];
        int top = -1;
        int max = Integer.MIN_VALUE;
        // 遍历数组，将每个数组添加进单调栈中
        // 找到每个位置左右能扩大的最大范围，就是距离当前位置的长方形最近的并且高度小于它的长方形，在这两个高度小于它的长方形之间的全部长方形，都是可以扩张到形成大长方形的
        for (int i = 0; i < n; i++) {
            while (top != -1 && heights[stack[top]] >= heights[i]) {
                int index = stack[top--];
                // 形成的长方形高度都是固定的，都是heights[index]，也就是从栈中弹出的高度
                max = Math.max(max, top == -1 ? heights[index] * i : heights[index] * (i - stack[top] - 1));
            }
            stack[++top] = i;
        }
        // 单独处理单调栈中剩下的长方形
        while (top != -1) {
            int index = stack[top--];
            max = Math.max(max, top == -1 ? heights[index] * n : heights[index] * (n - stack[top] - 1));
        }
        return max;
    }

}
