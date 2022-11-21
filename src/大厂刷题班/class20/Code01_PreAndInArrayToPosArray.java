package 大厂刷题班.class20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
// 二叉树    递归
public class Code01_PreAndInArrayToPosArray {
    // 上课讲的版本
    public static int[] zuo(int[] pre, int[] in) {
        // 过滤无效参数
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        // 先序、中序、后续长度一定是一样的
        int N = pre.length;
        // 记录每一个节点在中序序列中的位置，因为题目中规定了没有重复节点，所以可以直接在Map的key存储结点的值
        // 这样下次再取中序位置就可以直接取出来了
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            inMap.put(in[i], i);
        }
        // 存储后序序列
        int[] pos = new int[N];
        // 开始递归构造后序序列数组
        func(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
        return pos;
    }

    // 参数有：
    // 先序遍历数组pre[]和其范围L1, R1
    // 中序遍历数组in[]和其范围L2, R2
    // inMap：存储每一个节点在中序序列的位置
    // 该递归方法就是将后续序列数组pos[]的L3~R3范围填满，并且将L3和R3的数填上。整个过程中R1-L1 = R2-L2 = R3-L3
    public static void func(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
                            HashMap<Integer, Integer> inMap) {
        // basecase  递归出口
        if (L1 > R1) {
            return;
        }
        // 如果此时先序只有一个数了，那么此时pos的赋值范围也肯定只有一个数(L3=R3)，直接将其赋值给pos[L3]
        if (L1 == R1) {
            pos[L3] = pre[L1];
            // 还有多个数，继续递归
        } else {
            // 此时先序序列的第一个位置一定是这个范围的根节点，根节点已经在后序序列的最后位置，将其赋值
            pos[R3] = pre[L1];
            // 但是其他位置的数还不好确定，需要接着递归减少数据规模
            // 下面再去分别递归构造pre[L1]根节点的左树和右树的后序序列
            // 获取此时根节点pre[L1]在中序序列的位置，这样就能知道左右子树的数据规模
            int index = inMap.get(pre[L1]);

            // 左树有index - L2个节点   右树有R2 - index个节点

            // 左树
            // 在先序序列的范围 L1 + 1 ~ (L1 + 1) + (index - L2) - 1    很好理解，就是开头下标加上左树的数据规模再减1就是结尾下标
            // 在中序序列的范围 L2 ~ index - 1
            // 在后序序列的范围 L3 ~ L3 + (index - L2) - 1    很好理解，就是开头下标加上左树的数据规模再减1就是结尾下标
            func(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index - L2 - 1, inMap);

            // 右树
            // 在先序序列的范围 (L1 + 1) + (index - L2) - 1 + 1 ~ R1    很好理解，左树的结尾下标加1就是右树的开始下标，R2就是右树结尾下标
            // 在中序序列的范围 index + 1 ~ R2
            // 在后序序列的范围 L3 + (index - L2) - 1 + 1 ~  R3 - 1    很好理解，左树的结尾下标加1就是右树的开始下标，R3 - 1就是右树结尾下标
            func(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index - L2, R3 - 1, inMap);
        }
    }


    // 另一种写法
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static int[] preInToPos1(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        int[] pos = new int[N];
        process1(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1);
        return pos;
    }

    // L1...R1 L2...R2 L3...R3
    public static void process1(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        pos[R3] = pre[L1];
        int mid = L2;
        for (; mid <= R2; mid++) {
            if (in[mid] == pre[L1]) {
                break;
            }
        }
        int leftSize = mid - L2;
        process1(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1);
        process1(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1);
    }

    public static int[] preInToPos2(int[] pre, int[] in) {
        if (pre == null || in == null || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            inMap.put(in[i], i);
        }
        int[] pos = new int[N];
        process2(pre, 0, N - 1, in, 0, N - 1, pos, 0, N - 1, inMap);
        return pos;
    }

    public static void process2(int[] pre, int L1, int R1, int[] in, int L2, int R2, int[] pos, int L3, int R3,
                                HashMap<Integer, Integer> inMap) {
        if (L1 > R1) {
            return;
        }
        if (L1 == R1) {
            pos[L3] = pre[L1];
            return;
        }
        pos[R3] = pre[L1];
        int mid = inMap.get(pre[L1]);
        int leftSize = mid - L2;
        process2(pre, L1 + 1, L1 + leftSize, in, L2, mid - 1, pos, L3, L3 + leftSize - 1, inMap);
        process2(pre, L1 + leftSize + 1, R1, in, mid + 1, R2, pos, L3 + leftSize, R3 - 1, inMap);
    }

    // for test
    public static int[] getPreArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillPreArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillPreArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        arr.add(head.value);
        fillPreArray(head.left, arr);
        fillPreArray(head.right, arr);
    }

    // for test
    public static int[] getInArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillInArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillInArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        fillInArray(head.left, arr);
        arr.add(head.value);
        fillInArray(head.right, arr);
    }

    // for test
    public static int[] getPosArray(Node head) {
        ArrayList<Integer> arr = new ArrayList<>();
        fillPostArray(head, arr);
        int[] ans = new int[arr.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = arr.get(i);
        }
        return ans;
    }

    // for test
    public static void fillPostArray(Node head, ArrayList<Integer> arr) {
        if (head == null) {
            return;
        }
        fillPostArray(head.left, arr);
        fillPostArray(head.right, arr);
        arr.add(head.value);
    }

    // for test
    public static Node generateRandomTree(int value, int maxLevel) {
        HashSet<Integer> hasValue = new HashSet<Integer>();
        return createTree(value, 1, maxLevel, hasValue);
    }

    // for test
    public static Node createTree(int value, int level, int maxLevel, HashSet<Integer> hasValue) {
        if (level > maxLevel) {
            return null;
        }
        int cur = 0;
        do {
            cur = (int) (Math.random() * value) + 1;
        } while (hasValue.contains(cur));
        hasValue.add(cur);
        Node head = new Node(cur);
        head.left = createTree(value, level + 1, maxLevel, hasValue);
        head.right = createTree(value, level + 1, maxLevel, hasValue);
        return head;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int maxLevel = 5;
        int value = 1000;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            Node head = generateRandomTree(value, maxLevel);
            int[] pre = getPreArray(head);
            int[] in = getInArray(head);
            int[] pos = getPosArray(head);
            int[] ans1 = preInToPos1(pre, in);
            int[] ans2 = preInToPos2(pre, in);
            int[] classAns = zuo(pre, in);
            if (!isEqual(pos, ans1) || !isEqual(ans1, ans2) || !isEqual(pos, classAns)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");

    }
}
