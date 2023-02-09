package 大厂刷题班.class03;

import java.util.Arrays;

// 双指针+贪心
// 给定一个正数数组arr，代表若干人的体重
// 再给定一个正数limit，表示所有船共同拥有的载重量
// 每艘船最多坐两人，且不能超过载重
// 想让所有的人同时过河，并且用最好的分配方法让船尽量少
// 返回最少的船数
// 测试链接 : https://leetcode.cn/problems/boats-to-save-people/
public class Code06_BoatsToSavePeople {
    // 1、贪心 + 双指针     不要看这个方法了，直接看第二个方法，最好理解
    public static int numRescueBoats1(int[] arr, int limit) {
        // 过滤无效参数
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 一共有多少个人
        int N = arr.length;
        // 对每个人的重量按从小到大排序，构造单调性，为以后用双指针做准备
        Arrays.sort(arr);

        // 先判断特殊情况
        // 特殊情况一：存在重量大于limit的人，那么一定不能把把所有人都送过河，因为任何船都没办法把这个人送过去，直接返回-1
        // 如果重量最重的人大于limit，就说明一定存在有人根本就上不了船，也就无法将所有的人都送到河对面，返回-1
        if (arr[N - 1] > limit) {
            return -1;
        }


        // 从右边向遍历，找到第一个小于等于limit一半的人，用lessR记录下来
        int lessR = -1;
        for (int i = N - 1; i >= 0; i--) {
            if (arr[i] <= (limit / 2)) {
                lessR = i;
                break;
            }
        }
        // 特殊情况二：所有的人重量都大于limit/2的情况下，每个船只能装一个人，所以返回N
        // 如果leesR等于-1，说明从大到小遍历没有找到小于等于limit/2的人，说明一个船只能装下1个人，所以需要N个船
        if (lessR == -1) {
            return N;
        }

        // 创建双指针，让双指针按照贪心规则从中间向两边扩。左部分的数都是小于等于limit / 2的，右部份所有的数都是大于limit / 2，所以右部份落单的人只能自己做一条船走，但是左部分落单的人还可以和左部分的其他人两两拼船
        // 指向第一个小于等于limit / 2的位置
        int L = lessR;
        // 指向第一个大于limit / 2的位置
        int R = lessR + 1;
        // 左部分不能和右部份配对的人数
        int noUsed = 0;
        // 先让L左移，找到和R指向位置相加不超过limit的位置
        // 然后L固定，再让R右移，找到第一个和L相加超过limit的位置
        // 那么我们可以与确定，R划过的所有的人，和L指针左边的人两两配对一定是可以拼一条船过河的人，并且这种组合肯定是最不浪费空间的，因为按照单调性来说两边都是取得相加能不超过Limit的最大值了
        // 当然这样有可能出现L左边有没有和右部分的人匹配完的情况，也有可能L左边的人数不够和右部分的人两两配对的情况，这个后面再单独讨论
        while (L >= 0) {
            int solved = 0;
            // 将右指针向右移动，找到和左指针相加第一个超过limit的位置，这个位置向左到右指针最开始的位置之间的这些人就是可以和左半部分的人拼一条船过河的人
            while (R < N && arr[L] + arr[R] <= limit) {
                // 只要是arr[L] + arr[R]还没有超过limit，就让右指针继续右移找到第一个超过limit的位置
                R++;
                // 这个记录的是右指针划过的人数，本质就是记录下右半部分可以和左边部分人拼一条船过河的人数，这里也可以理解为这一拨人拼船需要几条船
                solved++;
            }
            // 如果solved为0，说明此时L指针还没有找到能和右指针组合相加小于limit的位置，就继续左移，由于单调性，左移就是在找更小的位置
            if (solved == 0) {
                // 这个记录的是左指针划过的人数，本质就是左部分不能和右部份配对的人。因为单调性，左指针划过的人是左部分最大的那一批人，他们之所以要被左指针划过，是因为他们和右部份的右指针指向的人组合相加超过limit了
                // 而右指针最开始指向的是右部份最小的人，现在左指针划过的这些人和右部份最轻的人相加都超过limit了，那么因为单调性，他们就更不可能和右指针右边的那些人拼一条船了，因为右指针右边的人重量更大，所以他们就只能自己坐船过去
                noUsed++;
                // 左指针左移，找到第一个能和右指针相加小于等于limit的位置，找到这个位置以后，这个位置左边的人都可以和右部份R指针划过的人拼一船，因为左边的人重量更小
                L--;
            } else {
                // 执行到这里，说明这一轮左右指针都已经找好了。
                // 按正常来说此时L左边的人和R划过的这solved两两拼一条船过河。但是有可能出现L左边的人不够solved的情况
                // 所以这里单独判断一下，如果此时左指针左边的人数已经不够solved个了，直接将L设置为-1
                // 如果足够的话，直接将L一定到L - solved，从新的位置再执行一遍流程，将左部分剩余还没有判断的人和右部分的右指针右边还没有判断的人再走一遍循环，找可以两两拼船的人
                L = Math.max(-1, L - solved);
            }
        }
        // all是用来计算下面的used的，在右半部分中，all位置左边的都是可以和别人拼船的人
        int all = lessR + 1;
        // 将左半部分不能和别人拼船的人剪掉，剩下的used就是所有可以两两拼船的人
        int used = all - noUsed;
        // 这一部分是右部份落单的人，右部份落单的
        int moreUnsolved = (N - all) - used;
        return used + ((noUsed + 1) >> 1) + moreUnsolved;
    }

    // 2、首尾双指针的解法。这个方法要远比上面的方法好理解，直接看这个代码即可。这两个方法的是最优解
    public static int numRescueBoats2(int[] people, int limit) {
        /**
         * 要使需要的船数尽可能地少，应当使载两人的船尽可能地多。
         设people的长度为n。考虑体重最轻的人：
         1、若他不能与体重最重的人同乘一艘船，那么体重最重的人无法与任何人同乘一艘船，此时应单独分配一艘船给体重最重的人。
         从people中去掉体重最重的人后，我们缩小了问题的规模，变成求解剩余 n−1 个人所需的最小船数，将其加一即为原问题的答案。
         2、若他能与体重最重的人同乘一艘船，那么他能与其余任何人同乘一艘船，为了尽可能地利用船的承载重量，
         选择与体重最重的人同乘一艘船是最优的。从people中去掉体重最轻和体重最重的人后，我们缩小了问题的规模，
         变成求解剩余 n−2 个人所需的最小船数，将其加一即为原问题的答案。

         在代码实现时，我们可以先对people排序，然后用两个指针分别指向体重最轻和体重最重的人，按照上述规则来移动指针，并统计答案。
         */
        int ans = 0;
        Arrays.sort(people);
        // 设置左右指针，分别从数组的左边界和右边界，开始无回退地向中间移动
        int light = 0, heavy = people.length - 1;
        while (light <= heavy) {
            // 如果此时最轻的和最重的人相加小于limit，此时这两个人可以拼一条船，是最优的解
            if (people[light] + people[heavy] <= limit) {
                // 左指针右移
                ++light;
            }
            // 如果如果此时最轻的和最重地人相加大于limit，说明此时最重的这个人和此时最轻的人相加都大于limit了，那么他和其他任何一个人相加都会大于limit，这样它就只能自己乘坐一条船过河了，所以ans++

            // 右指针左移
            --heavy;
            // 增加一条船
            ++ans;
        }
        return ans;
    }
}
