package 体系学习班.class29;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_FindMinKth {

    public static class MaxHeapComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }

    }

    // 1、利用大根堆，时间复杂度O(N*logK)
    public static int minKth1(int[] arr, int k) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
        for (int i = 0; i < k; i++) {
            maxHeap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.add(arr[i]);
            }
        }
        return maxHeap.peek();
    }

    // 2、改写快排，时间复杂度O(N)
    // k >= 1
    public static int minKth2(int[] array, int k) {
        int[] arr = copyArray(array);
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i != ans.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // arr 第k小的数
    // process2(arr, 0, N-1, k-1)
    // arr[L..R]  范围上，如果排序的话(不是真的去排序)，找位于index的数
    // index [L..R]
    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) { // L = =R ==INDEX
            return arr[L];
        }
        // 不止一个数  L +  [0, R -L]
        int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, L, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, R, index);
        }
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, cur++, ++less);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

    public static void swap(int[] arr, int i1, int i2) {
        int tmp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = tmp;
    }

    // 3、利用bfprt算法，时间复杂度O(N)
    public static int minKth3(int[] array, int k) {
        int[] arr = copyArray(array);
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    // arr[L..R]  如果排序的话，位于index位置的数，是什么，返回
    public static int bfprt(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }

        // 这个算法和快排唯一的区别就是如何去选定划分值的策略
        // L...R  每五个数一组
        // 每一个小组内部排好序
        // 小组的中位数组成新数组
        // 这个新数组的中位数返回
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfprt(arr, L, range[0] - 1, index);
        } else {
            return bfprt(arr, range[1] + 1, R, index);
        }
    }

    // arr[L...R]  五个数一组
    // 每个小组内部排序
    // 每个小组中位数领出来，组成marr
    // marr中的中位数，返回
    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int team = 0; team < mArr.length; team++) {
            int teamFirst = L + team * 5;
            // L ... L + 4
            // L +5 ... L +9
            // L +10....L+14
            mArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }
        // marr中，找到中位数
        // 这里继续调用bfprt(0, marr.len - 1,  mArr.length / 2 )。形成了一个递归
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    // 取得五个一组数中的中位数
    public static int getMedian(int[] arr, int L, int R) {
        // 首先先使用插入排序对这五个数进行排序，因为固定5个数的话，插入排序是效率最高的排序算法。
        // 因为要排序的数的个数是固定的，那么我们就选择常数时间最好的排序算法即可，插入排序就是常数时间最少的算法（程序语言指令少）
        insertionSort(arr, L, R);
        // 取排序好的中间的那个中位数
        return arr[(L + R) / 2];
    }

    // 插入排序
    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // 4、自己写的次优解，如果最优解实在写不出来，就会写这个代码就够了
    // 下面的代码可以直接提交力扣通过，这个代码更好理解一些，也更容易实现
    // 快速选择算法 + 三向切分算法（3-way-partition）
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        int mid = 0;
        // 区分数组长度为奇数和偶数的情况
        // 如果是奇数情况，数组划分的前半段要比后半段多1，并且mid也作为数组中间位置的下标
        if ((n & 1) == 1) {
            mid = n >> 1;
        } else {
            mid = (n >> 1) - 1;
        }
        // 设置数组a和数组b的长度，用来存储划分出来的两部分数据
        // 如果nums长度为奇数，则an比bn多1
        int an = mid + 1;
        int bn = n - (mid + 1);
        int[] a = new int[an];
        int[] b = new int[bn];

        // 使用快速选择法定位到数组的中位数，并且再用三向切分算法（3-way-partition）将小于中位数的数放到中位数左边，大于中位数的放到中位数右边
        int l = 0;
        int r = n - 1;
        // 基准数字
        int pivot = 0;
        // 每一轮返回的三段分割点下标
        int[] range = null;
        // 快速选择法找中位数  求数组中第mid大的数字是多少（也就是求中位数）
        while (l < r) {
            // 这里用随机数随机生成一个位置的数做基准即可，并不会影响通过力扣测试，随机数不会造成错误的。这里也可以选择l~r范围内一个固定位置的数，例如(l + r) / 2位置的数作为基准
            pivot = nums[l + (int) (Math.random() * (r - l + 1))];
            // 三向切分算法来将数组划分成三部分
            range = patition(nums, pivot, l, r);

            // 判断我们是不是找到了中位数，如果mid落在了划分出来的中间相等部分，说明我们找到了中位数，就是此时的mid位置的数，大小是pivot
            if (mid > range[0] && mid < range[1]) {
                break;
                // 只向一个方向递归，所以时间复杂度是O(N)
            } else if (mid <= range[0]) {
                r = range[0];
            } else if (mid >= range[1]) {
                l = range[1];
            }
        }

        // 还是和方法1一样将两部分逆序，来避免相等的数相邻的情况
        reverse(nums, 0, mid);
        reverse(nums, mid + 1, n - 1);

        // 将划分出来的两部分分别加入到a数组和b数组中
        int index = 0;
        for (int i = 0; i < an; i++) {
            a[i] = nums[index++];
        }
        for (int i = 0; i < bn; i++) {
            b[i] = nums[index++];
        }

        // 将a数组和b数组中的数据依次交叉覆写到nums中，完成摆动排序
        index = 0;
        int i = 0;
        while (index < n) {
            if (i < an) {
                nums[index++] = a[i];
            }
            // 要记得判断i不能越界，因为在奇数长度情况下，bn<an
            if (i < bn) {
                nums[index++] = b[i++];
            }
        }
    }

    // 将小于pivot的数放到pivot左边，大于pivot的数放到pivot右边
    public int[] patition(int[] nums, int pivot, int l, int r) {
        // less左边是小于pivot的数，包括less位置
        int less = l - 1;
        // more右边是大于pivot的数，包括more位置
        int more = r + 1;
        // 当前遍历到的数
        int cur = l;
        // 这里要注意，条件是cur < more，不是cur < r
        while (cur < more) {
            // 遍历过程中如果当前位置的数小于pivot，则将less++，为小于区扩充一个位置，将这个数移动到扩充出来的这个位置，并且cur++去遍历下一个位置，因为交换到cur位置的数一定是等于pivot的
            if (nums[cur] < pivot) {
                swap(nums, ++less, cur++);
                // 这个分支要注意
                // 遍历过程中如果当前位置的数大于pivot，则将more++，为大于区扩充一个位置，将这个数移动到扩充出来的这个位置，但是cur不右移，因为交换到cur位置的数是我们还没有遍历到的，并不能确定他是不是等于pivot，所以在下一轮还需要继续判断它
            } else if (nums[cur] > pivot) {
                swap(nums, cur, --more);
                // 相等，则cur右移
            } else {
                cur++;
            }
        }

        // 结束之后就将数组划分成了以pivot为划分的三部分，返回划分断点位置
        return new int[] {less, more};
    }

    // 逆序数组
    public void reverse(int[] nums, int l, int r) {
        while (l < r) {
            swap(nums, l++, r--);
        }
    }



    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }

}
