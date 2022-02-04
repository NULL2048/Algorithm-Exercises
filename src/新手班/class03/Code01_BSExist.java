package 新手班.class03;

import java.util.Arrays;
/*
    判断有序数组中是否存在num

    就记住，只要是用二分法 ，mid就这样计算
    mid=(L + R) / 2
    更新的时候就是这样计算
    L = mid + 1
    R = mid - 1

    就记住都这样算就行了，不要用别的计算方法了
 */
public class Code01_BSExist {

    // 二分法查找。arr保证有序
    public static boolean find(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            // 找到数组中间位置的下标
            int mid = (L + R) / 2;
            // 找到num，直接返回true
            if (arr[mid] == num) {
                return true;
            // 如果中间位置的数比num小，说明num可能在右侧范围中，再去查找右边的数组元素
            } else if (arr[mid] < num) {
                // 更新L的值，R保持不变
                L = mid + 1;
            // 如果中间位置的数比num大，说明num可能在左侧范围中，再去查找左边的数组元素
            } else {
                // 更新R的值，L保持不变
                R = mid - 1;
            }
        }
        return false;
    }

    // for test  这个就是我们的对数器，用最暴力的方法遍历数组，来找到指定的数。能保证绝对正确
    public static boolean test(int[] sortedArr, int num) {
        for (int cur : sortedArr) {
            if (cur == num) {
                return true;
            }
        }
        return false;
    }

    // for test  生成随机数组
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        // 用对数器去测试find()方法正不正确
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != find(arr, value)) {
                System.out.println("出错了！");
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}

