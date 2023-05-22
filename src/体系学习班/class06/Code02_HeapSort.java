package 体系学习班.class06;

import java.io.*;

// 堆排序
public class Code02_HeapSort {
    public static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    public static void main(String[] args) throws IOException {
        int[] a = new int[5];
        int index = 0;
        // 注意输入五个数字 因为开辟的是5个空间大小，也关系着后面算法的正确运行
        while (true) {
            in.nextToken();
            int num = (int) in.nval;
            if (num == -1) {
                break;
            }
            a[index++] = num;
        }
        heapSort(a);
        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }
        out.flush();
    }

    /**
     * 堆排序
     * @param arr
     */
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 这里就是将数组一点点的构建成一个大根堆
        // i表示当前我们大根堆的构建完成范围  从0~i一点点的构建，当i=arr.length-1就说明整个数组的大根堆已经构建完成了
        // O(N*logN)
        for (int i = 0; i < arr.length; i++) {
            // 把数组中下标位i的节点新加入到大根堆中，并且进行调整形成新的大根堆
            heapInsert(arr, i);
        }


        // 下面这个方法是用heapify构建大根堆，和上面用heapInsert构建一样，这两种方法用哪个都行，下面这个好一些，时间复杂度只有O(N)
//        // O(N)
//        for (int i = arr.length - 1; i >= 0; i--) {
//            heapify(arr, i, arr.length);
//        }


        // 获取堆得大小
        int size = arr.length;
        // 已经完成的大根堆的树根一定是所有数中最大的，我们直接将其与数组的最后一个元素进行交换，这样也就将最大的数放到了数组的最右边，完成了这个数的交换
        // 后面也是继续按照这个操作进行堆排序
        // O(N*logN)
        while (size > 0) { // O(N)
            // 将大根堆的树根与数组的最后一个位置的数交换，完成树根这个数的排序，并且将size自减，相当于将已完成排序的数从堆中剔除掉
            swap(arr, 0, --size); // O(1)
            // 交换到树根的新数一定是比以前小的，所以执行heapifu与子结点向下进行交换来构成新的大根堆
            heapify(arr, 0, size); // O(logN)
        }
    }

    /**
     * 将新加入的节点插入到堆中，并通过向上与父结点交换来维持大根堆结构
     * 新插入的节点都在树的最下面的位置，因为是尾插进数组的
     * @param arr
     * @param index 新加入的节点的下标
     */
    public static void heapInsert(int[] arr, int index) {
        // 将当前节点与他的父结点比较，如果大于父结点就与父结点交换，并且更新index游标的指向
        // 如果index已经交换到树根了，也就是index=0,那么(index-1)/2依旧等于0，而两者因为相等不满足循环条件所以依旧会结束循环
        while (arr[index] > arr[(index - 1) / 2]) {
            // 与自己的父结点进行交换
            swap(arr, index, (index - 1) / 2);
            // 将游标更新指向父结点
            index = (index - 1) / 2;
        }
    }

    /**
     * 当一个节点上的数变小后，通过与子结点进行向下交换，来维持大根堆的结构
     * @param arr
     * @param index 数字变小的节点的下标
     * @param size 当前要构建大根堆的堆的大小
     */
    public static void heapify(int[] arr, int index, int size) {
        // 数字变小节点的左子节点
        int left = 2 * index + 1;
        // 开始进行向下交换  当左节点的下标已经越界了，说明已经交换完成
        while (left < size) {
            // 判断左子节点和右子节点哪个节点数值更大   这里就有一个小技巧，先判断left+1有没有越界，如果越界了，说明当前结点没有右子节点，只有左子节点，直接返回左子节点下标
            int largest = left + 1 < size && arr[left] < arr[left + 1] ? left + 1 : left;
            // 然后在判断当前结点大还是子结点大，返回最大的节点的下标
            largest = arr[index] > arr[largest] ? index : largest;
            // 如果当前结点比子结点还大，说明交换完成，直接跳出循环
            if (index == largest) {
                break;
            }
            // 与最大子结点进行交换
            swap(arr, index, largest);
            // 更新当前节点游标
            index = largest;
            // 更新左子节点下标
            left = 2 * index + 1;
        }
    }

    /**
     * 交换方法
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
