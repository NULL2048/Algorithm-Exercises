package 新手班.class03;

/*
    局部最小值问题
    给一个数组，这个数组相邻的数都不相等，从数组中返回一个符合局部最小的数的下标即可

    局部最小值的定义如下：
    假设一个a数组长度为N，则其下标范围是1~N
    1）如a[0] < a[1]，则a[0]为局部最小值
    2）如a[N - 1] < a[N - 2]，则a[N - 1]为局部最小值
    3）除了0和N-1以外的其他下标位置i，如a[i - 1] > a[i] < a[i + 1]，则a[i]为局部最小值
 */
public class Code04_BSAwesome {
    // arr 整体无序，但是相邻的数都不相等！
    public static int oneMinIndex(int[] arr) {
        // 如何数组为空，直接返回
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int N = arr.length;
        // 如何数组只有一个数，这个数就已经符合局部最小了，直接返回下标
        if (N == 1) {
            return 0;
        }

        // 先判断两个特殊情况，也就是数组两头的数是不是局部最小，如果是的话直接返回。反正题目只要求返回一个符合局部最小的数即可
        if (arr[0] < arr[1]) {
            return 0;
        }
        if (arr[N - 1] < arr[N - 2]) {
            return N - 1;
        }

        // 设置数组的左右范围
        int L = 0;
        int R = N - 1;
        // L...R 肯定有局部最小。
        // 最左端的数比相邻的数大，最右端的数比相邻的数大，因为相邻的数没有相等的，他们在坐标系上的线是一个向下凹的弧度，所在在中间必然有一个值即小于左边相邻的数，也小于右边相邻的数。
        // 开始通过二分法进行查找
        while (L < R - 1) { // 这里用的是L < R - 1，之所以用R - 1是因为怕越界，因为要想判断mid,mid-1,mid+1就要保证这三个位置都在L~R的范围里面，所以这里就要让L < R - 1。
            // 设置中间下标
            int mid = (L + R) / 2;
            // 如果找到了符合局部最小的数，直接返回其下标
            if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
                return mid;
            } else {
                // 如果中间位置的数大于其左侧相邻的数，这样我们就可以从左半段继续查找，更新R的值即可
                if (arr[mid] > arr[mid - 1]) {
                    R = mid - 1;
                // 如果中间位置的数大于其右侧相邻的数，这样我们就可以从右半段继续查找，更新L的值即可
                } else {
                    L = mid + 1;
                }
            }
        }
        // 如果跳出循环还没有找到局部最小，说明当前L~R范围的数只有2个或者1个，这样我们就直接判断这两个数谁比较小就返回谁。因为L位置一定比左侧的数小，R位置一定比右侧的小，只要是比较出L和R谁小，就能知道谁是局部最小了
        // 如果L = R，说明范围内只有一个数，直接将其返回即可
        return arr[L] < arr[R] ? L : R;
    }

    // 生成随机数组，且相邻数不相等
    public static int[] randomArray(int maxLen, int maxValue) {
        int len = (int) (Math.random() * maxLen);
        int[] arr = new int[len];
        if (len > 0) {
            arr[0] = (int) (Math.random() * maxValue);
            for (int i = 1; i < len; i++) {
                do {
                    arr[i] = (int) (Math.random() * maxValue);
                } while (arr[i] == arr[i - 1]);
            }
        }
        return arr;
    }

    // 也用于测试  判断查找出来的局部最小位置的数是不是都小于左右相邻的数
    public static boolean check(int[] arr, int minIndex) {
        if (arr.length == 0) {
            return minIndex == -1;
        }
        // 设置左右相邻的数组下标
        int left = minIndex - 1;
        int right = minIndex + 1;
        // 判断是不是比左右相邻的数小
        boolean leftBigger = left >= 0 ? arr[left] > arr[minIndex] : true;
        boolean rightBigger = right < arr.length ? arr[right] > arr[minIndex] : true;
        // 只有同时比左右相邻的两个数都小，才是局部最小
        return leftBigger && rightBigger;
    }

    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int ans = oneMinIndex(arr);
            if (!check(arr, ans)) {
                printArray(arr);
                System.out.println(ans);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
