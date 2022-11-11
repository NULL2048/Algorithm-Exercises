package 大厂刷题班.class17;

import java.util.Comparator;
import java.util.PriorityQueue;

// 二分
// 本题测试链接 : https://leetcode.cn/problems/kth-smallest-element-in-a-sorted-matrix/
public class Code02_KthSmallestElementInSortedMatrix {
    // 1、二分的方法
    public int kthSmallest(int[][] matrix, int k) {
        // 矩阵行数
        int n = matrix.length;
        // 矩阵列数
        int m = matrix[0].length;
        // 有序矩阵的最小值，即矩阵左上角的值
        int left = matrix[0][0];
        // 有序矩阵的最大值，即矩阵右下角的值
        int right = matrix[n - 1][m - 1];
        // 矩阵中第k小的数
        int ans = 0;
        // 记录小于等于mid这个数的信息，包含小于等于mid的数有多少个和小于等于mid并且最接近mid的数是什么
        Info equalOrLessMidInfo = null;
        // 利用二分法，找到小于等于mid的数正好有k个的信息
        while (left <= right) {
            // 二分
            int mid = left + ((right - left) >> 1);

            // 获取矩阵中小于等于mid的Info信息，包括小于等于mid的个数，和小于等于mid的数中最接近mid的数是什么
            equalOrLessMidInfo = getequalOrLessNumInfo(matrix, mid);
            // 如果小于等于mid的数少于k个，意味着我们还需要提高mid的值
            if (equalOrLessMidInfo.equalOrLessNumCnt < k) {
                // 取右半部分
                left = mid + 1;
                // 这个分支需要注意:
                // 如果小于等于mid的数大于k个，意味着我们还需要减少mid的值，尝试找到正好等于k个的值
                // 但是一定存在一个mid，正好使得矩阵中小于等于它的数有k个吗？并不一定，因为矩阵中可能存在重复的数。
                // 假设要找第7小的数，矩阵中从小到大排列出来是1、2、3、4、5、5、5、5、6、7
                // 正确答案应该是5，5是这个矩阵中第7小的数，但是在这个代码中是无法找到一个mid使小于等于它的数有7个的
                // 比如如果找小于等于5，最后的结果就是8个，如果找小于等于6，结果就是9个，小于等于4，结果就是4个
                // 所以并不能保证一定能找到equalOrLessMidInfo.equalOrLessNumCnt == k的结果，有可能equalOrLessMidInfo.equalOrLessNumCnt > k，但是最终的结果就在这个equalOrLessMidInfo中。
                // 所以，我们需要在等于k和大于k的时候都去记录一下ans，因为这个时候的equalOrLessMidInfo.nearNum都有可能是答案
                // 不用记录equalOrLessMidInfo.equalOrLessNumCnt < k的情况，因为如果此时小于等于mid的数都不够k个，就不可能找到矩阵中第k小的数，答案一定不在这种情况中。
            } else if (equalOrLessMidInfo.equalOrLessNumCnt >= k) {
                // 如果存在小于等于Mid的数正好有k个，那么此时的距离mid最近的数一定就是答案了，就算是继续执行这个while循环，也永远不会进入到这个分支了，因为这里会取左半部分，会降低下一轮mid的值，那么必然就会使小于mid的值变得小于k
                // 如果如果存在小于等于Mid的数正好大于k个，就会继续执行循环，找更小的mid来尝试能不能找到更接近k个的解。但是同时也会将此时的最接近mid的值作为临时的答案，如果后面的循环再也进不到这个分支了，那是因为后面的都不足k个了，答案不可能在不足k个的情况里，所以这一次记录的临时答案就是最终的答案，这个答案在矩阵中肯定是存在相同的值的
                ans = equalOrLessMidInfo.nearNum;
                // 取左半部分
                right = mid - 1;
            }
        }

        return ans;
    }

    public class Info {
        // 小于等于num并且最接近num的数是多少
        int nearNum;
        // 小于等于num的数一共有多少个
        int equalOrLessNumCnt;

        public Info(int nearNum, int equalOrLessNumCnt) {
            this.nearNum = nearNum;
            this.equalOrLessNumCnt = equalOrLessNumCnt;
        }
    }

    // 找到矩阵中小于等于num的数有多少个，并且小于等于num且最接近它的数是多少
    public Info getequalOrLessNumInfo(int[][] matrix, int num) {
        // 从右上角开始
        int row = 0;
        int col = matrix[0].length - 1;
        // 记录小于等于num的个数
        int cnt = 0;
        // 记录小于等于num并且最接近num的数是多少
        int ans = Integer.MIN_VALUE;

        while (row <= matrix[0].length - 1 && col >= 0) {
            // if (matrix[row][col] == num) {
            //     ans = matrix[row][col];
            //     cnt += (col + 1);
            //     return new Info(ans, cnt);
            // }

            // 如果matrix[row][col] == num，有可能这个位置就是答案点，也有可能它的下一行还会有符合要求的，所以记录一下临时答案，然后再去下一行看有没有小于等于num的数，如果有的话，就再加上这一部分才不会遗漏答案。因为这个矩阵只是列和行有序的，但是不同行不同列并不存在严格的有序性，所以还需要额外判断判断一下
            if (matrix[row][col] <= num) {
                // 如果找到的数可以推高ans的答案，让他更接近num，就更新
                ans = Math.max(ans, matrix[row][col]);
                // 当前行col列及其左边的数都满足小于等于num，所以累加到数量中去
                cnt += (col + 1);
                // 向下移动一行
                row++;
                // 如果当前数大于num
            } else if (matrix[row][col] > num) {
                // 就向左移动一个位置，去看左边的位置能不能小于等于num，因为左边都是比自己小的数
                col--;
            }
        }

        // 返回答案
        return new Info(ans, cnt);
    }



    // 2、堆的方法
    public static int kthSmallest1(int[][] matrix, int k) {
        int N = matrix.length;
        int M = matrix[0].length;
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        boolean[][] set = new boolean[N][M];
        heap.add(new Node(matrix[0][0], 0, 0));
        set[0][0] = true;
        int count = 0;
        Node ans = null;
        while (!heap.isEmpty()) {
            ans = heap.poll();
            if (++count == k) {
                break;
            }
            int row = ans.row;
            int col = ans.col;
            if (row + 1 < N && !set[row + 1][col]) {
                heap.add(new Node(matrix[row + 1][col], row + 1, col));
                set[row + 1][col] = true;
            }
            if (col + 1 < M && !set[row][col + 1]) {
                heap.add(new Node(matrix[row][col + 1], row, col + 1));
                set[row][col + 1] = true;
            }
        }
        return ans.value;
    }

    public static class Node {
        public int value;
        public int row;
        public int col;

        public Node(int v, int r, int c) {
            value = v;
            row = r;
            col = c;
        }

    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }

    }
}
