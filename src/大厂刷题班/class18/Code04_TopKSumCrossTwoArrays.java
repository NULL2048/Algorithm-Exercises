package 大厂刷题班.class18;

// 牛客的测试链接：
// https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
// 不要提交包信息，把import底下的类名改成Main，提交下面的代码可以直接通过
// 因为测试平台会卡空间，所以把set换成了动态加和减的结构
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

// 大根堆
public class Code04_TopKSumCrossTwoArrays {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int N = (int) in.nval;
            in.nextToken();
            int K = (int) in.nval;
            int[] arr1 = new int[N];
            int[] arr2 = new int[N];
            for (int i = 0; i < N; i++) {
                in.nextToken();
                arr1[i] = (int) in.nval;
            }
            for (int i = 0; i < N; i++) {
                in.nextToken();
                arr2[i] = (int) in.nval;
            }
            int[] topK = topKSum(arr1, arr2, K);
            for (int i = 0; i < K; i++) {
                out.print(topK[i] + " ");
            }
            out.println();
            out.flush();
        }
    }

    public static class Node {
        // 当前遍历到的arr1的下标，也就是矩阵的第一个下标
        public int i;
        // 当前遍历到的arr2的下标，也就是矩阵的第二个下标
        public int j;
        // 两个数组成的加和
        public int sum;

        public Node(int i, int j, int s) {
            this.i = i;
            this.j = j;
            this.sum = s;
        }
    }

    // 构造大根堆比较器，加和大的在堆顶
    public static class MaxHeapComp implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.sum - o1.sum;
        }
    }

    // 返回加和前k大的结果
    public static int[] topKSum(int[] arr1, int[] arr2, int k) {
        // 矩阵右下角就是加和的最大值，因为两个数组是有序的，最后一个数一定是最大的
        int i = arr1.length - 1;
        int j = arr2.length - 1;

        // 标记当前矩阵位置是否已经加入过大根堆
        // 这里使用矩阵的二位坐标转一维坐标来标记的
        HashSet<Integer> set = new HashSet<>();
        // 创建大根堆
        PriorityQueue<Node> maxHead = new PriorityQueue<>(new MaxHeapComp());
        // 先将矩阵右下角的数加入大根堆
        maxHead.add(new Node(i, j, arr1[i] + arr2[j]));
        // 标记这个位置已经加入过大根堆了，二维坐标转一维坐标
        set.add(i * arr2.length + j);
        // 记录答案
        int[] ans = new int[k];
        int index = 0;
        // 如果k大于所有数的总数量，那么topK就设置为所有数的总数量
        int topK = Math.min(k, arr1.length * arr2.length);

        // 收集完topK就结束循环
        while (index != topK) {
            // 弹出堆顶，记录一个答案
            Node node = maxHead.poll();
            ans[index++] = node.sum;

            // 将弹出堆顶的上面和左边的位置加入到大根堆中，需要判断上面和下面是否还有数，并且这个数还不能加入过大根堆
            if (node.i > 0 && !set.contains((node.i - 1) * arr2.length + node.j)) {
                // 将上面的数加入大根堆
                maxHead.add(new Node(node.i - 1, node.j, arr1[node.i - 1] + arr2[node.j]));
                // 标记已经加入过堆了
                set.add((node.i - 1) * arr2.length + node.j);
            }

            if (node.j > 0 && !set.contains(node.i * arr2.length + (node.j - 1))) {
                // 将左面的数加入大根堆
                maxHead.add(new Node(node.i, node.j - 1, arr1[node.i] + arr2[node.j - 1]));
                // 标记已经加入过堆了
                set.add(node.i * arr2.length + (node.j - 1));
            }
        }
        // 返回答案
        return ans;
    }
}
