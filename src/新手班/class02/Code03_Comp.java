package 新手班.class02;

public class Code03_Comp {
    // 选择排序
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 插入排序
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) { // 0 ~ i 做到有序
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    // 返回一个数组arr，arr的长度范围是[0,maxLen-1],arr中的每个值的大小范围是[0,maxValue-1]
    public static int[] lenRandomValueRandom(int maxLen, int maxValue) {
        // 随机生成范围是[0,maxLen-1]的数组长度
        int len = (int) (Math.random() * maxLen);
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            // 随机生成大小范围是[0,maxValue-1]的数组值
            ans[i] = (int) (Math.random() * maxValue);
        }
        return ans;
    }

    // 拷贝数组
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 判断该数组是不是升序数组
    public static boolean isSorted(int[] arr) {
        // 长度小于2，一定是有序的
        if (arr.length < 2) {
            return true;
        }
        // 将数组第一个数设置为最大数
        int max = arr[0];
        // 向后遍历该数组，只要是出现了两个相邻的书，后面的数小于前面的数，说明这个数组不是升序的，也就没有完成正确的排序，直接返回false
        for (int i = 1; i < arr.length; i++) {
            if (max > arr[i]) {
                return false;
            }
            max = Math.max(max, arr[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        // 设置数组最大长度
        int maxLen = 5;
        // 设置数组最大值
        int maxValue = 1000;
        // 设置测试次数
        int testTime = 10000;
        // 测试10000次
        for (int i = 0; i < testTime; i++) {
            // 生成随机数组
            int[] arr1 = lenRandomValueRandom(maxLen, maxValue);
            // 备份样例
            int[] tmp = copyArray(arr1);
            selectionSort(arr1);
            // 如果发现了错误样例，则输出错误样例，方便修改代码
            if (!isSorted(arr1)) {
                for (int j = 0; j < tmp.length; j++) {
                    System.out.print(tmp[j] + " ");
                }
                System.out.println();
                System.out.println("选择排序错了！");
                break;
            }
        }
    }
}
