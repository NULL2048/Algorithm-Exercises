package 大厂刷题班.class16;

// 归并排序思想
public class Code04_MergeRecord {

    /*
     * 腾讯原题
     *
     * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
     * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
     * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
     * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
     * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
     * 接下来根据reverse对arr进行调整：
     * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [3,1,4,2]，此时有3个逆序对。
     * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对
     * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对。
     * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [4,2,3,1]，此时有5个逆序对。
     * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
     *
     * 输入数据状况：
     * power的范围[0,20]
     * arr长度范围[1,10的7次方]
     * reverse长度范围[1,10的6次方]
     *
     * */

    // 1、暴力方法
    public static int[] reversePair1(int[] originArr, int[] reverseArr, int power) {
        int[] ans = new int[reverseArr.length];
        for (int i = 0; i < reverseArr.length; i++) {
            reverseArray(originArr, 1 << (reverseArr[i]));
            ans[i] = countReversePair(originArr);
        }
        return ans;
    }

    public static void reverseArray(int[] originArr, int teamSize) {
        if (teamSize < 2) {
            return;
        }
        for (int i = 0; i < originArr.length; i += teamSize) {
            reversePart(originArr, i, i + teamSize - 1);
        }
    }

    public static void reversePart(int[] arr, int L, int R) {
        while (L < R) {
            int tmp = arr[L];
            arr[L++] = arr[R];
            arr[R--] = tmp;
        }
    }

    public static int countReversePair(int[] originArr) {
        int ans = 0;
        for (int i = 0; i < originArr.length; i++) {
            for (int j = i + 1; j < originArr.length; j++) {
                if (originArr[i] > originArr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }


    // 2、最优解
    public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
        // 拷贝一份数组，用来做出一个一个逆序数组
        int[] reverse = copyArray(originArr);
        // 反转数组，得到一个逆序数组reverse
        reversePart(reverse, 0, reverse.length - 1);
        // 记录每种划分情况的的逆序数量
        int[] recordDown = new int[power + 1];
        // 记录每种划分情况的的正序数量
        int[] recordUp = new int[power + 1];
        // 用正序数组计算得到逆序对数量
        process(originArr, 0, originArr.length - 1, power, recordDown);
        // 用逆序数组计算得到正序对数量（用一个逆序的数组，再走一遍相同的流程，求出来逆序数组中的逆序对数量，就相当于求出来原数组中的正序对数量）
        process(reverse, 0, reverse.length - 1, power, recordUp);
        // 记录每一次操作的答案
        int[] ans = new int[reverseArr.length];
        // 按照笔记上讲的来进行数量交换，并加和逆序对数量作为答案
        for (int i = 0; i < reverseArr.length; i++) {
            int curPower = reverseArr[i];
            // 划分组内数量小于2^curPower的，去将逆序对数量和正序对数量做交换，大于的不用管
            for (int p = 1; p <= curPower; p++) {
                int tmp = recordDown[p];
                recordDown[p] = recordUp[p];
                recordUp[p] = tmp;
            }
            // 将所有的逆序对数量加和就是本轮操作的答案
            for (int p = 1; p <= power; p++) {
                ans[i] += recordDown[p];
            }
        }
        return ans;
    }

    // originArr[L...R]完成排序！
    // L...M左  M...R右  merge
    // L...R  2的power次方
    public static void process(int[] originArr, int L, int R, int power, int[] record) {
        // 技术向下划分，此时一组只有一个数
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        // 传入时保证L~mid和mid+1~R是从小到大有序的
        process(originArr, L, mid, power - 1, record);
        process(originArr, mid + 1, R, power - 1, record);
        // 计算这种划分下的逆序对数量
        record[power] += merge(originArr, L, mid, R);
    }
    // 利用merge过程统计逆序对数量
    // arr的L~M一定是从小到大排序的
    // arr的M+1~R一定是从小到大排序的
    public static int merge(int[] arr, int L, int m, int r) {
        int[] help = new int[r - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;
        int ans = 0;
        // 就是在l~m中选一个数，在m+1~r中选一个数，看着两个数组合在一起能不能形成逆序对
        while (p1 <= m && p2 <= r) {
            // 如果p1位置大于p2位置，因为两个部分都是从小到大排序，所以如果p1位置大于p2位置，那么p1~m范围上的数就都大于p2位置的数
            // 所以就能以p2位置为逆序对第二个数，p1~m位置为第一个数，能组成m-p1+1个逆序对
            ans += arr[p1] > arr[p2] ? (m - p1 + 1) : 0;

            // 只要是p1位置不大于p2位置的数，就说明l~p1位置上的数和p2位置上的数组合不出逆序对，直接将p1++，去看下一个位置的数能不能和p2组合出逆序对
            // 如果p1位置大于p2位置，说明可以组合出逆序对，然后再让p2++，看p2下一个位置的数能不能和p1组合出逆序对
            // 将这一范围的数组处理成由小到大的有序数组，方便下一次merge处理
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 有序化
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= r) {
            help[i++] = arr[p2++];
        }
        // 将arr的L~R范围设置为有序，变成从小到大排序
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
        // 返回这个范围的逆序对数量
        return ans;
    }

    // for test
    public static int[] generateRandomOriginArray(int power, int value) {
        int[] ans = new int[1 << power];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value);
        }
        return ans;
    }

    // for test
    public static int[] generateRandomReverseArray(int len, int power) {
        int[] ans = new int[len];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * (power + 1));
        }
        return ans;
    }

    // for test
    public static void printArray(int[] arr) {
        System.out.println("arr size : " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
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

    public static void main(String[] args) {
        int powerMax = 8;
        int msizeMax = 10;
        int value = 30;
        int testTime = 50000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int power = (int) (Math.random() * powerMax) + 1;
            int msize = (int) (Math.random() * msizeMax) + 1;
            int[] originArr = generateRandomOriginArray(power, value);
            int[] originArr1 = copyArray(originArr);
            int[] originArr2 = copyArray(originArr);
            int[] reverseArr = generateRandomReverseArray(msize, power);
            int[] reverseArr1 = copyArray(reverseArr);
            int[] reverseArr2 = copyArray(reverseArr);
            int[] ans1 = reversePair1(originArr1, reverseArr1, power);
            int[] ans2 = reversePair2(originArr2, reverseArr2, power);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish!");

        powerMax = 20;
        msizeMax = 1000000;
        value = 1000;
        int[] originArr = generateRandomOriginArray(powerMax, value);
        int[] reverseArr = generateRandomReverseArray(msizeMax, powerMax);
        // int[] ans1 = reversePair1(originArr1, reverseArr1, powerMax);
        long start = System.currentTimeMillis();
        reversePair2(originArr, reverseArr, powerMax);
        long end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
    }

}
