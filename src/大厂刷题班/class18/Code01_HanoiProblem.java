package 大厂刷题班.class18;
// 递归   汉诺塔
public class Code01_HanoiProblem {
    // 上课现场写的版本，看这个版本就可以了
    public static int kth(int[] arr) {
        int N = arr.length;
        return step(arr, N - 1, 1, 3, 2);
    }

    // 其实整个思路就是把小的步骤都看成一个整体大步骤，从整体去执行流程，不用在意具体的细节。
    // 在举具体的例子时，就用第一轮最初始的例子来看就行，这样更简单好理解，后面更复杂的就不用管了，把控好整体流程就行了
    // 0...index这些圆盘，arr[0..index] index+1层塔
    // 在哪？from 去哪？to 另一个是啥？other
    // arr[0..index]这些状态，是index+1层汉诺塔问题的，最优解第几步
    public static int step(int[] arr, int index, int from, int to, int other) {
        // basecase   i=-1，表示已经没有圆盘了，0层汉诺塔问题，直接返回第一步0，直接就是最终状态了
        if (index == -1) {
            return 0;
        }
        // basecase    index的目标应该是去to，如果发现index在other了，肯定是不对的，说明此状态一定不是最优解的状态
        if (arr[index] == other) {
            return -1;
        }
        // 要么arr[index] == from或者arr[index] == to;
        if (arr[index] == from) { // 表示第一步还没有走完，继续将第一步的盘子进行移动
            return step(arr, index - 1, from, other, to);
        } else {
            // arr[index] == to说明第一大步（是小步集合，也就是把全部的每一个盘从from移动到to位置就是一大步）和第二大步都走完了
            // 第一大步和第二大步可以直接用公式算出来
            int p1 = (1 << index) - 1;
            int p2 = 1;
            // 第三大步继续用递归求要走多少步
            int p3 = step(arr, index - 1, other, to, from);
            // 第三步无效直接返回，不要再向下执行了
            if (p3 == -1) {
                return -1;
            }
            return p1 + p2 + p3;
        }
    }




    // 递归版本
    // n层汉诺塔的最优解一定要走(2^n)-1步
    public static int step1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        return process(arr, arr.length - 1, 1, 2, 3);
    }

    // 目标是: 把0~i的圆盘，从from全部挪到to上
    // 返回，根据arr中的状态arr[0..i]，它是最优解的第几步？
    // f(i, 3 , 2, 1) f(i, 1, 3, 2) f(i, 3, 1, 2)
    public static int process(int[] arr, int i, int from, int other, int to) {
        if (i == -1) {
            return 0;
        }
        if (arr[i] != from && arr[i] != to) {
            return -1;
        }
        if (arr[i] == from) { // 第一大步没走完，继续让剩余的圆盘继续完成从from移动到to位置
            return process(arr, i - 1, from, to, other);
        } else { // arr[i] == to
            // 已经走完1，2两步了，
            int rest = process(arr, i - 1, other, from, to); // 第三大步完成的程度
            if (rest == -1) {
                return -1;
            }
            return (1 << i) + rest;
        }
    }

    // 把递归改成迭代的版本
    public static int step2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int from = 1;
        int mid = 2;
        int to = 3;
        int i = arr.length - 1;
        int res = 0;
        int tmp = 0;
        while (i >= 0) {
            if (arr[i] != from && arr[i] != to) {
                return -1;
            }
            if (arr[i] == to) {
                res += 1 << i;
                tmp = from;
                from = mid;
            } else {
                tmp = to;
                to = mid;
            }
            mid = tmp;
            i--;
        }
        return res;
    }



    public static void main(String[] args) {
        int[] arr = { 3, 3, 2, 1 };
        System.out.println(step1(arr));
        System.out.println(step2(arr));
        System.out.println(kth(arr));
    }
}
