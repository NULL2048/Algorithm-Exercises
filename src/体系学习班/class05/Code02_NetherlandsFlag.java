package 体系学习班.class05;

import static 排序.堆排序.swap;

// 荷兰国旗问题
public class Code02_NetherlandsFlag {
    public static int[] partition(int[] arr, int l, int r, int num) {
        int less = l - 1;
        int more = r + 1;
        int cur = l;
        while (cur < more) {
            if (arr[cur] < num) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > num) {
                swap(arr, --more, cur);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }

}
