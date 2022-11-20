package 大厂刷题班.class19;

import java.util.LinkedList;

/*
 * 一张扑克有3个属性，每种属性有3种值（A、B、C）
 * 比如"AAA"，第一个属性值A，第二个属性值A，第三个属性值A
 * 比如"BCA"，第一个属性值B，第二个属性值C，第三个属性值A
 * 给定一个字符串类型的数组cards[]，每一个字符串代表一张扑克
 * 从中挑选三张扑克，一个属性达标的条件是：这个属性在三张扑克中全一样，或全不一样
 * 挑选的三张扑克达标的要求是：每种属性都满足上面的条件
 * 比如："ABC"、"CBC"、"BBC"
 * 第一张第一个属性为"A"、第二张第一个属性为"C"、第三张第一个属性为"B"，全不一样
 * 第一张第二个属性为"B"、第二张第二个属性为"B"、第三张第二个属性为"B"，全一样
 * 第一张第三个属性为"C"、第二张第三个属性为"C"、第三张第三个属性为"C"，全一样
 * 每种属性都满足在三张扑克中全一样，或全不一样，所以这三张扑克达标
 * 返回在cards[]中任意挑选三张扑克，达标的方法数
 *
 * */
// 根据数量猜解法   递归
public class Code05_CardsProblem {

    public static int ways1(String[] cards) {
        LinkedList<String> picks = new LinkedList<>();
        return process1(cards, 0, picks);
    }

    public static int process1(String[] cards, int index, LinkedList<String> picks) {
        if (picks.size() == 3) {
            return getWays1(picks);
        }
        if (index == cards.length) {
            return 0;
        }
        int ways = process1(cards, index + 1, picks);
        picks.addLast(cards[index]);
        ways += process1(cards, index + 1, picks);
        picks.pollLast();
        return ways;
    }

    public static int getWays1(LinkedList<String> picks) {
        char[] s1 = picks.get(0).toCharArray();
        char[] s2 = picks.get(1).toCharArray();
        char[] s3 = picks.get(2).toCharArray();
        for (int i = 0; i < 3; i++) {
            if ((s1[i] != s2[i] && s1[i] != s3[i] && s2[i] != s3[i]) || (s1[i] == s2[i] && s1[i] == s3[i])) {
                continue;
            }
            return 0;
        }
        return 1;
    }

    // 上课讲的版本，上课讲的版本整体思路和上面的方法一样的，但是上课讲的这个代码用三进制数来标识牌面，上面的方法就还是直接用字符串来表示牌面，
    // 其实上面的方法相对更简单一些，因为直接存的就是牌面字符串，而上课讲的代码最后还涉及到将三进制数每一个属性对应位置的数取出来，就比较麻烦，所以自己写的话直接写成上面的代码即可
    public static int ways2(String[] cards) {
        int[] counts = new int[27];
        // 先把所有牌面的张数都统计出来
        for (String s : cards) {
            char[] str = s.toCharArray();
            counts[(str[0] - 'A') * 9 + (str[1] - 'A') * 3 + (str[2] - 'A') * 1]++;
        }

        // 先求三张牌的牌面完全一样的情况，先对同一牌面的扑克中任选三张一定都是达标的，计算这种情况下达标的数量
        // 这种情况可以直接算出来，不需要递归。这种情况下只可能是三个属性全都相同的情况
        // 记录总的达标数量
        int ways = 0;
        for (int status = 0; status < 27; status++) {
            int n = counts[status];
            // 要保证这一种牌面的扑克数量大于等于3张
            if (n > 2) {
                // 计算C(N, 3)
                ways += n == 3 ? 1 : (n * (n - 1) * (n - 2) / 6);
            }
        }

        // 再求三张扑克是不同牌面的情况，这种情况下可能是三种属性都不相同或者一部分属性相同一部分属性都不相同的情况。
        // 记录此时找到的牌面组合的分支，这个分支中只会放三张扑克，超过三张就不再向下递归了，直接返回是否达标的判断结果
        LinkedList<Integer> path = new LinkedList<>();
        // 先一次选定第一张牌的牌面，遍历27中牌面
        for (int i = 0; i < 27; i++) {
            // 如果这个牌面数量不是0，就选择i为第一张牌面，然后向下递归
            if (counts[i] != 0) {
                // 将该牌面加入到path中
                path.addLast(i);
                //向下递归，去尝试后面的牌面
                ways += process2(counts, i, path);
                // 恢复现场
                path.pollLast();
            }
        }
        return ways;
    }

    // 之前的牌面，拿了一些    ABC  BBB  ...
    // pre = BBB
    // ABC  ...
    // pre  = ABC
    // ABC BBB CAB
    // pre = CAB
    // 牌面一定要依次变大，所有形成的有效牌面，把方法数返回
    // 整个递归过程是按照牌面对应数值的从小到大遍历的，pre表示的是上一次选择牌面对应的三进制值。
    // 所以整个递归过程中保证了找到的牌面组合一定都是不同牌面的（就有可能存在一部分属性都相同，一部分属性都不相同的情况）
    public static int process2(int[] counts, int pre, LinkedList<Integer> path) {
        // 如果此时path中已经有三张牌了，直接判断这三张牌是否达标，并返回结果
        if (path.size() == 3) {
            return getWays2(counts, path);
        }
        int ways = 0;
        // 继续尝试选择牌面，从pre的下一个位置开始遍历
        for (int next = pre + 1; next < 27; next++) {
            if (counts[next] != 0) {
                path.addLast(next);
                // 选择完就向下递归继续选择
                ways += process2(counts, next, path);
                // 恢复现场
                path.pollLast();
            }
        }
        return ways;
    }

    // 判断此时的三张牌是否达标
    public static int getWays2(int[] counts, LinkedList<Integer> path) {
        // 获取三张牌的牌面
        int v1 = path.get(0);
        int v2 = path.get(1);
        int v3 = path.get(2);
        // 判断三张牌的对应位置的属性是不是都相同或都不相同
        for (int i = 9; i > 0; i /= 3) {
            int cur1 = v1 / i;
            int cur2 = v2 / i;
            int cur3 = v3 / i;
            v1 %= i;
            v2 %= i;
            v3 %= i;
            // 如果三张牌对应的属性不是都相同或者都不相同，会直接执行后面的retrun 0，表示这三种组合不达标
            if ((cur1 != cur2 && cur1 != cur3 && cur2 != cur3) || (cur1 == cur2 && cur1 == cur3)) {
                continue;
            }
            return 0;
        }
        // 如果三种牌面达标，就直接将三种牌面的数量取出来，相乘得到的就是达标的总数。
        v1 = path.get(0);
        v2 = path.get(1);
        v3 = path.get(2);
        return counts[v1] * counts[v2] * counts[v3];
    }

    // for test
    public static String[] generateCards(int size) {
        int n = (int) (Math.random() * size) + 3;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            char cha0 = (char) ((int) (Math.random() * 3) + 'A');
            char cha1 = (char) ((int) (Math.random() * 3) + 'A');
            char cha2 = (char) ((int) (Math.random() * 3) + 'A');
            ans[i] = String.valueOf(cha0) + String.valueOf(cha1) + String.valueOf(cha2);
        }
        return ans;
    }

    // for test
    public static void main(String[] args) {
        int size = 20;
        int testTime = 100000;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            String[] arr = generateCards(size);
            int ans1 = ways1(arr);
            int ans2 = ways2(arr);
            if (ans1 != ans2) {
                for (String str : arr) {
                    System.out.println(str);
                }
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("test finish");

        long start = 0;
        long end = 0;
        String[] arr = generateCards(10000000);
        System.out.println("arr size : " + arr.length + " runtime test begin");
        start = System.currentTimeMillis();
        ways2(arr);
        end = System.currentTimeMillis();
        System.out.println("run time : " + (end - start) + " ms");
        System.out.println("runtime test end");
    }

}
