package 大厂刷题班.class34;

import java.util.ArrayList;
import java.util.List;
// 这道题的思路和归并排序章节讲过的逆序对问题一样：第二题：逆序对问题
// 归并排序   逆序对问题
// https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
public class Problem_0315_CountOfSmallerNumbersAfterSelf {
    public class Node {
        // 记录当前value在原数组中的下标
        public Integer index;
        // 表示在原数组中的值
        public Integer value;

        public Node(int i, int v) {
            this.index = i;
            this.value = v;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        // 要返回的答案
        List<Integer> ans = new ArrayList<>();
        // 过滤无效参数
        if (nums == null || nums.length == 0) {
            return ans;
        }
        if (nums.length == 1) {
            ans.add(0);
            return ans;
        }

        // 这里必须要将ans都赋初值为0，因为后面会有调用index位置的值加上找到的右边小于自己值的数量，如果不提前赋值就会取出来null导致报错
        for (int i = 0; i < nums.length; i++) {
            ans.add(0);
        }

        // 将原数组转化成Node数组
        Node[] arr = new Node[nums.length];
        for (int i = 0; i < nums.length; i++) {
            arr[i] = new Node(i, nums[i]);
        }

        // 开始归并排序，并统计每一个位置右边比自己小的数量
        process(arr, 0, nums.length - 1, ans);
        return ans;
    }

    // 整个代码逻辑就是经典的归并排序代码
    public void process(Node[] arr, int l, int r, List<Integer> ans) {
        // 递归条件
        if (l < r) {
            int mid = (l + r) >> 1;
            //int mid = l + ((r - l) >> 1);
            // 分成左组和右组
            process(arr, l, mid, ans);
            process(arr, mid + 1, r, ans);
            merge(arr, l, mid, r, ans);
        }
    }

    // 整体上就是归并排序，中间夹着统计右边比自己小的数
    public void merge(Node[] arr, int l, int mid, int r, List<Integer> ans) {
        int li = mid;
        int ri = r;
        Node[] help = new Node[r - l + 1];
        // 是从后向前向help数组中添加数据的，也就是找到大的追加到help数组后面
        int index = r - l;
        while (li >= l && ri >= mid + 1) {
            // 如果此时右组的数小于左组的数，就说明此时右组指针及其右边的数都比arr[li].index位置的数小，因为组内都是按照从小到大排序好的
            if (arr[li].value > arr[ri].value) {
                // 此时ri及右侧的数有ri - mid个，将这个数累加到之前已经统计到的arr[li].index位置的数的答案中
                // 整个代码和归并操作唯一有区别的就是这一行代码
                ans.set(arr[li].index, ans.get(arr[li].index) + (ri - mid));
                // 将较大的数放到help后面，从后向前防止
                help[index--] = arr[li--];
            } else {
                // 将较大的数放到help后面，从后向前防止
                help[index--] = arr[ri--];
            }

        }

        // 后面就是经典的归并操作
        while (li >= l) {
            help[index--] = arr[li--];
        }

        while (ri >= mid + 1) {
            help[index--] = arr[ri--];
        }

        // 此时index为-1，所以下面的赋值操作是++index，需要先将index加回到0
        for (int i = l; i <= r; i++) {
            arr[i] = help[++index];
        }
    }
}
