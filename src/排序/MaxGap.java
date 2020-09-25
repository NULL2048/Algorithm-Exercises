package 排序;

import java.util.Arrays;

public class MaxGap {
    public static int maxGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // 输入数组的长度  注意长度是len  但是下标范围是[0,len-1]
        int len = nums.length;
        // 创建每一个桶要保存的数据数组

        // 多开辟一个桶，所以要让桶的下标范围是[0, len]， 所以数组开辟len+1
        // 当前桶是否加入了数
        boolean[] hasNum = new boolean[len + 1];
        // 当前桶中的最小值
        int[] mins = new int[len + 1];
        // 当前同中的最大值
        int[] maxs = new int[len + 1];

        // 记录所有数据中的最小值和最大值   最好还是用局部变量，用全局变量定义会报错，玄学
        int MIN = Integer.MAX_VALUE;
        int MAX = Integer.MIN_VALUE;

        // 找到数组的最大值和最小值
        for (int i = 0; i < len; i++) {
            if (nums[i] < MIN) {
                MIN = nums[i];
            }

            if (nums[i] > MAX) {
                MAX = nums[i];
            }
        }

        // 如果最大值和最小值相同，说明所有的数是一致的，直接返回差值为0
        if (MIN == MAX) {
            return 0;
        }

        // 将所有的数放入对应的桶中，并且记录相关信息
        for (int i = 0; i < len; i++) {
            // 当前数要放入哪个桶中
            int bid = bucket(nums[i], len, MIN, MAX);

            // 这里注意，要判断当前桶是否为空，如果为空直接将当前数放入。如果不判断当前桶是否为空直接去比较当前数和桶记录的最小数谁小谁就放到桶中的话，会导致一开始当前同记录的min为初始值0，那么当地一个要入桶的数大于零时就会导致不会被装入，出现错误
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
            hasNum[bid] = true;
        }

        // 记录当前同上一个非空桶中的最大值
        int lastMax = maxs[0];
        // 最大差值
        int maxValue = Integer.MIN_VALUE;

        for (int i = 1; i <= len; i++) {
            if (hasNum[i]) {
                // 更新最大差值
                maxValue = Math.max(maxValue, mins[i] - lastMax);
                // 这个上一个非空桶最大值每遍历到一个非空桶就要更新
                lastMax = maxs[i];
            }
        }
        return maxValue;
    }

    public static int bucket(int num, int len, int MIN, int MAX) {
        // 这里之所以是乘以len，而不是乘以（len + 1），是因为虽然桶的个数是len+1个，但是这里求的是下标，桶的下标是从0开始的，所以len+1的桶最后一个位置的下标也是len
        // 也可以把len看作两个数之间的区间个数，而不是看作桶的个数
        return ((num - MIN)  * len / (MAX - MIN));
    }

    // for test
    public static int comparator(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        Arrays.sort(nums);
        int gap = Integer.MIN_VALUE;
        for (int i = 1; i < nums.length; i++) {
            gap = Math.max(nums[i] - nums[i - 1], gap);
        }
        return gap;
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
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            if (maxGap(arr1) != comparator(arr2)) {
                succeed = false;
                for (int j = 0; j < arr1.length; j++) {
                    System.out.print(arr1[j] + " ");
                }

                System.out.println();
                System.out.println();

                for (int j = 0; j < arr2.length; j++) {
                    System.out.print(arr2[j] + " ");
                }
                System.out.println();
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

}
