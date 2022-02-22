package 新手班.class08;

public class Code01_MergeSort {
    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        // 空数组或者只有两个元素，不需要排序直接返回
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    // arr[L...R]范围上，请让这个范围上的数，有序！
    // 递归驱动
    public static void process(int[] arr, int L, int R) {
        // 当L=R，说明已经递归到最底了，也就是每一段只有一个元素了，只有一个元素说明这个数据段一定已经是排序好的了。
        //这就是递归出口了，开始向上返回，从小的数据段开始一点点的进行排序
        if (L == R) {
            return;
        }
        // int mid = (L + R) / 2
        int mid = L + ((R - L) >> 1); // 这是写是为了避免溢出
        // 进行划分，将大的数据划分成小的数据，递归到最后一层，就能将数据划分成最小单位，方便排序
        process(arr, L, mid);
        process(arr, mid + 1, R);
        // 对划分出来的数据段进行排序
        merge(arr, L, mid, R);
    }

    // 这个方法是用来对分隔后的数据进行排序的
    // 传入这方法的左右两段数组，一定都是排序好的两段数组，然后这个方法的目标就是将两段已经排序好的数组合并成一段排序好的数组
    public static void merge(int[] arr, int L, int M, int R) {
        // 创建临时数组，将存储当前数据段排序后的结果
        int[] help = new int[R - L + 1];
        // 当前已经排序到的位置
        int i = 0;
        // 左边的数据段的左边界
        int p1 = L;
        // 右边的数据段左边界
        int p2 = M + 1;
        // M为左段数据的右边界，R为右段数据的右边界

        // 同时遍历左右两段数据，然后依次将逐渐递增的数据添加进help数组中，这一段很好理解
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 要么p1越界，要么p2越界
        // 不可能出现：共同越界
        // 上面循环结束后，将剩下还没有遍历完的数组中剩下的元素全部添加到help数组中
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        // 然后再将help中排序好的数据添加回原数组对应的位置，这样就完成了一段排序
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }


    // 非递归方法实现
    // 用非递归循环的方法，来模拟递归驱动，将数组划分成一个个小的数据段，来逐渐对其进行排序的过程
    public static void mergeSort2(int[] arr) {
        // 空数组或者只有两个元素，不需要排序直接返回
        if (arr == null || arr.length < 2) {
            return;
        }
        // 记录数组长度
        int N = arr.length;
        // 这个就是步长，从步长1开始，逐渐二倍增加，也就模拟了递归到底层，然后开始向上返回排序的过程。递归驱动process方法的作用就是将数组划分，从划分出来的数据段只有一个元素开始，向上返回排序，每返回一层长度就增加二倍
        // 所以这里我们是通过循环来模拟递归向上返回排序的过程。步长范围内的数组，就是已经排序好的数据段
        int mergeSize = 1;
        // 每一轮循环就相当于依次递归返回排序，当补偿大于等于数组长度的时候，就说明所有的数据已经排序完成，直接跳出循环
        while (mergeSize < N) {
            // 循环开始前，mergeSize范围内的数据还没有排序完成

            // 从数组的第一个位置开始，对当前步长范围内的数据进行排序
            int L = 0;
            // 开始对每一个步长范围内的数据段进行排序
            while (L < N) {
                // 当最后剩下还没有排序到的元素数量已经不足以填充满步长范围（因为至少需要左段数据长度能够超过步长，右段数据长度可以不超过步长，如果连左段数据都不够了，直接跳出循环即可），则不再对其进行处理，直接结束排序，跳出循环
                if (mergeSize >= N - L) {
                    break;
                }
                // 开始手动划分左右两段数据，也就是一次排序两个mergeSize长度的数据段
                // 左段数据的右边界
                int M = L + mergeSize - 1;
                // 右段数据的右边界  如果右半段的剩余数据已经不足步长个数，就用M+(N-M-1),直接将右边剩余的元素全部添加到右段数据中
                int R = M + Math.min(mergeSize, N - M - 1);
                // 将左右两段数据进行排序，还是用和递归相同的方法进行排序
                merge(arr, L, M, R);
                // 将L后移到有边界后一位
                L = R + 1;
            }
            // 如果步长超过了N的一半，这样如果乘2，会导致越界，这种情况直接跳出
            // 当已经出现了mergeSize > N / 2时，其实数组已经排序完成了。因为左组数据是mergeSize个，超过了数组的的一半了，然后右段数组就把剩余的全都算进去了，调用了merge()方法进行排序，所以此时所有的数据已经排序完成了
            if (mergeSize > N / 2) { // N除以2是向下取整   17/2=8  如果这里写的是>=N/2，因为向下取整，就会错过step=2*8=16的时候的操作，这样做后的结果就错了
                break;
            }
            // 步长乘2，扩大排序范围
            mergeSize <<= 1;

            // 循环结束后，mergeSize范围内的数据已完成排序
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
