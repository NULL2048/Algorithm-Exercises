package 体系学习班.class26;

// 测试链接：https://leetcode.com/problems/sum-of-subarray-minimums/
public class Code01_SumOfSubarrayMinimums {
    public int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        // 创建单调栈
        int top = -1;
        int[] stack = new int[n];
        long sum = 0;
        // 执行单调栈流程，找到每一个位置的一个最大范围，这个范围内的最小值就是这个位置上的值
        for (int i = 0; i < n; i++) {
            // 也就是找到这个位置左右两边最近的并且小于它的数，再找到的这两个数之间的范围中，最小值就是当前这个位置，然后就在这个范围内找到以这个值为最小值的子数组即可
            while (top != -1 && arr[stack[top]] >= arr[i]) {
                int index = stack[top--];
                // 找到左右两个边界
                int leftIndex = top == -1 ? -1 : stack[top];
                int rightIndex = i;
                // (index - leftIndex)：这个求的在这个范围内所有以index位置的值为最小值的子数组的全部开始点有多少个
                // (rightIndex - index)：这个求的在这个范围内所有以index位置的值为最小值的子数组的全部结束点有多少个
                // 起始点个数 * 结束点个数就等于所有的子数组个数
                // 而这些子数组的都是以arr[index]为最小值，所以所以再乘上arr[index]即可得到最小值累加和
                // 注意：再找这个大区域内的以arr[index]为最小值的子数组时，必须要让这个子数组的范围包含index位置才行，毕竟是要以index位置的值为最小值，必须得包括index位置。所以子数组的起始点下标一定小于等于index，子数组的结束点下标一定大于等于index，这样才能保证Index位置的值能包含进去
                // 这里还有一个注意点就是要对arr[index]类型转化为Long，这样就能将乘出来的结果自动转换为Long型，Java中自动转化都是向位数多的类型转化，避免数据溢出。sum已经是long值了
                sum += (index - leftIndex) * (rightIndex - index) * (long)arr[index];
            }
            // 加入单调栈
            stack[++top] = i;
        }
        // 处理剩余的单调栈节点
        while (top != -1) {
            int index = stack[top--];
            int leftIndex = top == -1 ? -1 : stack[top];
            int rightIndex = n;
            sum += (index - leftIndex) * (rightIndex - index) * (long) arr[index];
        }
        // 这里要对1000000007取余
        return (int) (sum % 1000000007);
    }
}
