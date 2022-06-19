package 体系学习班.class11;

public class Code07_PaperFolding {

    // N表示折叠了几次，其实就是这棵树有多少层
    public static void printAllFolds(int N) {
        // 从第一层开始，也就是从根节点开始，中序遍历N层，并且设置根节点打印凹
        process(1, N, true);
        System.out.println();
    }

    // 当前你来了一个节点，脑海中想象的！
    // 这个节点在第i层，一共有N层，N固定不变的
    // 这个节点如果是凹的话，down = T
    // 这个节点如果是凸的话，down = F
    // 函数的功能：中序打印以你想象的节点为头的整棵树！
    public static void process(int i, int N, boolean down) {
        // 递归出口，当已经完成N层的打印之后，就结束
        if (i > N) {
            return;
        }
        // 下面就是经典的中序遍历递归写法，在这个过程中按照我们的规则来打印凹凸
        // 左子树根节点是凹
        process(i + 1, N, true);
        // 打印当前节点
        System.out.print(down ? "凹 " : "凸 ");
        // 左子树根节点是凸
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        // 自定义层数，也就是折叠次数
        int N = 4;
        printAllFolds(N);
    }
}
