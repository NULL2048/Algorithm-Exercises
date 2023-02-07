package 大厂刷题班.class35;

// 贪心   环形问题
// 价值洼地一定是只分一块糖，价值洼地就是得分最少的人，他肯定是只分一块糖，他肯定不能有别的情况，只要是确定了一个位置的确定值了，那么就可以将整个环给拆开了，价值洼地已经不可能再被别的位置影响了，它的答案已经确定了
// 来自网易笔试
// 给定一个正数数组arr，表示每个小朋友的得分
// 任何两个相邻的小朋友，如果得分一样，怎么分糖果无所谓，但如果得分不一样，分数大的一定要比分数少的多拿一些糖果
// 假设所有的小朋友坐成一个环形，返回在不破坏上一条规则的情况下，需要的最少糖果数
public class Code05_CircleCandy {
    public static int minCandy(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return 1;
        }
        int n = arr.length;
        // 找价值洼地，即得分最小值
        int minIndex = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] <= arr[lastIndex(i, n)] && arr[i] <= arr[nextIndex(i, n)]) {
                minIndex = i;
                break;
            }
        }
        // 调整数组位置，将价值洼地移动到数组两端
        int[] nums = new int[n + 1];
        for (int i = 0; i <= n; i++, minIndex = nextIndex(minIndex, n)) {
            // 生成新的数组
            nums[i] = arr[minIndex];
        }
        // 构造左坡数组
        int[] left = new int[n + 1];
        left[0] = 1;
        for (int i = 1; i <= n; i++) {
            left[i] = nums[i] > nums[i - 1] ? (left[i - 1] + 1) : 1;
        }
        // 构造右坡数组
        int[] right = new int[n + 1];
        right[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            right[i] = nums[i] > nums[i + 1] ? (right[i + 1] + 1) : 1;
        }
        // 统计答案
        int ans = 0;
        for (int i = 0; i < n; i++) {
            // 取left和right的最大值，就是i号小朋友的最少的糖数，累加到ans
            ans += Math.max(left[i], right[i]);
        }
        // 返回分发的最少糖数
        return ans;
    }

    // 实现环形数组的下标移动，向右移动
    public static int nextIndex(int i, int n) {
        return i == n - 1 ? 0 : (i + 1);
    }
    // 实现环形数组的下标移动，向左移动
    public static int lastIndex(int i, int n) {
        return i == 0 ? (n - 1) : (i - 1);
    }

    public static void main(String[] args) {
        int[] arr = { 3, 4, 2, 3, 2 };
        System.out.println(minCandy(arr));
    }
}

