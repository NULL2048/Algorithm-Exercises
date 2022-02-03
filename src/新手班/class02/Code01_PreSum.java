package 新手班.class02;

/*
    计算数组下标 l - r 范围值的加和
 */
public class Code01_PreSum {
    // 前缀数组解法，能够提高性能，减少空间消耗
    public static class RangeSum {
        private int[] preSum;

        // 提前把0-r范围的加和存储到数组中
        public RangeSum(int[] array) {
            preSum = new int[array.length];
            preSum[0] = array[0];

            for (int i = 1; i < array.length; i++) {
                preSum[i] = array[i - 1] + array[i];
            }
        }

        // 计算数组l-r范围的加和。例：下标3-6的加和，就是下标0-6的加和减去下标0-2的加和
        public int rangeSum(int l, int r) {
            return l == 0 ? preSum[r] : preSum[r] - preSum[l - 1];
        }
    }
}
