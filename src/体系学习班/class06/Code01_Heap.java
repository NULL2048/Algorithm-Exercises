package 体系学习班.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_Heap {
    /**
     * 构建大根堆
     */
    public static class MyMaxHeap {
        // 用数组来实现堆结构
        private int[] heap;
        // 堆的最大容量
        private final int limit;
        // 记录当前堆的大小，堆中有多少个元素
        private int heapSize;

        // 堆的构造方法，初始化堆结构
        public MyMaxHeap(int limit) {
            // 构造堆数组
            heap = new int[limit];
            // 将数组大小设置成堆的最大容量
            this.limit = limit;
            // 初始化堆大小
            heapSize = 0;
        }

        // 当前堆是否为空
        public boolean isEmpty() {
            return heapSize == 0;
        }

        // 当前对是否已经满了
        public boolean isFull() {
            return heapSize == limit;
        }

        // 向堆中插入元素
        public void push(int value) {
            // 判断当前对是否已经满了
            if (heapSize == limit) {
                // 如果满了则抛出异常，中止插入
                throw new RuntimeException("heap is full");
            }

            // 1、先将要插入的元组追加到当前堆数组中最后一个有效位置（如果这个位置以前就有数了，直接就覆盖掉了，我们只关注下标小于headSize的部分，这一部分才是堆真正有效的范围）
            heap[heapSize] = value;
            // 2、执行heapInsert操作，将新插入的元素逐渐移动到其合适的位置，完成堆结构的维护，并且将headSize++
            heapInsert(heap, heapSize++);
        }

        // 返回堆的最大值，并且将最大值删掉。并且要保证剩下的数依然保持大根堆结构
        public int pop() {
            // 堆顶就是我们要返回的最大值
            int ans = heap[0];
            // --heapSize，这样就将堆中元素数量减1，并且将数组中有效位置缩小了一个，即此时heapSize的数值就是最后一个堆有效位置下标的下一个位置
            // 将堆顶（也就是下标0位置的元素）和堆中最后一个元素（也就是数组中最后一个堆有效位置的数）交换
            // 因为--heapSize，这样原堆顶的元素就在堆数组的有效范围之外了，也就实现了将其删除
            swap(heap, 0, --heapSize);
            // 重新维护堆结构
            heapify(heap, 0, heapSize);
            return ans;
        }

        // 新加进来的数，现在停在了index位置，请依次往上移动，
        // 移动到0位置，或者不比自己的父节点大了，则停止操作
        private void heapInsert(int[] arr, int index) {
            /**
             * 这个循环有两种情况会结束循环：
             * 1、当index == 0时，(index - 1) / 2也等于0，并且arr[0]不可能出现大于arr[0]的情况，这样当index已经走到整棵树顶部的时候，就会跳出循环
             * 2、其他情况就是正常比较两个位置[index]和[index-1]/2数的大小，如果出现父节点大于子节点的情况，则跳出循环
             */
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 从index位置，往下看，不断的下沉
        // 较大的孩子都不再比index位置的数大或者已经没孩子了，则停止操作
        private void heapify(int[] arr, int index, int heapSize) {
            // 当前节点左子节点下标
            int left = index * 2 + 1;
            // 如果左子节点下标没有超过heapSize，说明存在左孩子。否则说明不存在子节点，直接结束循环
            while (left < heapSize) {
                // 把较大孩子的下标，给largest。 left + 1 < heapSize判断右子节点下标是否超过heapSize，如果没有，则说明有右子节点，如果超过了，说明没有右子节点，直接返回左子节点下标
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                // 比较子结点中较大的节点是否大于父节点，将较大节点的下标记录下来
                largest = arr[largest] > arr[index] ? largest : index;
                // 说明当前父节点大于子节点数，堆结构已经重新构造完成，结束循环
                if (largest == index) {
                    break;
                }
                // index和较大孩子，要互换
                swap(arr, largest, index);
                // 更新当前节点为最新的数值大的节点下标
                index = largest;
                // 更新左孩子节点下标
                left = index * 2 + 1;
            }
        }

        // 交换操作
        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

    }

}
