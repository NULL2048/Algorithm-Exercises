package 大厂刷题班.class37;

// 动态规划   这道题就是会尝试就行了，所以会尝试的能力非常重要

// 来自字节
// 扑克牌中的红桃J和梅花Q找不到了（这个条件没什么作用，就是为了吸引你的注意力，注意到这道题的数据规模是52张牌，除此之外并没有什么作用），为了利用剩下的牌做游戏，小明设计了新的游戏规则
// 1) A,2,3,4....10,J,Q,K分别对应1到13这些数字，大小王对应0
// 2) 游戏人数为2人，轮流从牌堆里摸牌，每次摸到的牌只有“保留”和“使用”两个选项，且当前轮必须做出选择
// 3) 如果选择“保留”当前牌，那么当前牌的分数加到总分里，并且可以一直持续到游戏结束
// 4) 如果选择“使用”当前牌，那么当前牌的分数*3，加到总分上去，但是只有当前轮，下一轮，下下轮生效，之后轮效果消失。
// 5) 每一轮总分大的人获胜
// 假设小明知道每一轮对手做出选择之后的总分，返回小明在每一轮都赢的情况下，最终的最大分是多少
// 如果小明怎么都无法保证每一轮都赢，返回-1
public class Code02_GameForEveryStepWin {

//	public static max(int[] cands, int[] sroces) {
//		return f(cands, sroces, 0, 0, 0, 0);
//	}

    // 1、暴力递归

    // 上课讲的版本  代码1
    // 当前来到index位置，牌是cands[index]值
    // 对手第i轮的得分，sroces[i]
    // int hold : i之前保留的牌的总分
    // int cur : 当前轮得到的，之前的牌只算上使用的效果，加成是多少
    // int next : 之前的牌，对index的下一轮，使用效果加成是多少   只需要向后记录两轮就够了，因为使用的有效时长就是三轮，知道了上一轮，当前轮和下一轮的正好就三轮都记录上了
    // 返回值：如果i...最后，不能全赢，返回-1
    // 如果i...最后，能全赢，返回最后一轮的最大值

    // index -> 26种
    // hold -> (1+2+3+..13) -> 91 -> 91 * 4 - (11 + 12) -> 341
    // cur -> 26
    // next -> 13
    // 26 * 341 * 26 * 13 -> ? * (10 ^ 5)
    public static int f(int[] cands, int[] sroces, int index, int hold, int cur, int next) {
        // 最后一张
        if (index == 25) {
            // 最后的牌肯定是选择使用的
            int all = hold + cur + cands[index] * 3;
            // 如果无法赢对面，直接返回-1
            if (all <= sroces[index]) {
                return -1;
            }
            return all;
        }

        // 不是最后一张
        // 1、保留
        // 计算如果保留index位置的派，此时的总得分是多少
        // 之前所有选择保留的牌的总分 + 之前选择使用的牌，对当前这一轮影响加成的分数 + index位置牌的分数
        int all1 = hold + cur + cands[index];
        // 初始化-1，如果不能应对面，就是-1   p1就是下一轮返回上来的赢的情况下的得分
        int p1 = -1;
        // 保留可以赢对面
        if (all1 > sroces[index]) {
            // 传递给下一轮的hold：当前这一轮的hold + cands[index]
            // 传递给下一轮的cur：也就是当前这一轮的上一轮产生的影响，因为硬想最多传三轮，所以下一轮是上一轮能够影响到的最后一轮  直接传递本轮的next
            // 传递给下一轮的next：因为本轮选择保留，所以不会对下一轮有影响，传递0
            p1 = f(cands, sroces, index + 1, hold + cands[index], next, 0);
        }
        // 2、使用：分数爆发乘3
        // 计算如果使用index位置的派，此时的总得分是多少
        // 之前所有选择保留的牌的总分 + 之前选择使用的牌，对当前这一轮影响加成的分数 + index位置牌的分数*3
        int all2 = hold + cur + cands[index] * 3;
        // 初始化-1，如果不能应对面，就是-1    p2就是下一轮返回上来的赢的情况下的得分
        int p2 = -1;
        // 使用可以赢对面
        if (all2 > sroces[index]) {
            // 传递给下一轮的hold：传递当前这一轮的hold，因为本轮选择使用index，所以保留的分数不会增加
            // 传递给下一轮的cur：也就是当前这一轮和上一轮对下一轮产生的影响，因为影响最多传三轮，所以下一轮是上一轮能够影响到的最后一轮，这一轮也能影响到下一轮  直接传递本轮的next + 本轮产生的新影响cands[index] * 3
            // 传递给下一轮的next：因为本轮选择使用，所以对下一轮有影响，传递cands[index] * 3，上一轮的影响就不会再传递给下下轮了，所以就不传了
            p2 = f(cands, sroces, index + 1, hold, next + cands[index] * 3, cands[index] * 3);
        }

        // 取两种选择的最大值返回
        return Math.max(p1, p2);
    }


    // 代码2   另一种版本，但也是暴力递归
    // 26 * 341 * 78 * 39 = 2 * (10 ^ 7)
    public static int process(int[] cards, int[] scores, int index, int hold, int cur, int next) {
        if (index == 25) {
            int all = hold + cur + cards[index] * 3;
            if (all > scores[index]) {
                return all;
            } else {
                return -1;
            }
        } else {
            int d1 = hold + cur + cards[index];
            int p1 = -1;
            if (d1 > scores[index]) {
                p1 = process(cards, scores, index + 1, hold + cards[index], next, 0);
            }
            int d2 = hold + cur + cards[index] * 3;
            int p2 = -1;
            if (d2 > scores[index]) {
                p2 = process(cards, scores, index + 1, hold, next + cards[index] * 3, cards[index] * 3);
            }
            return Math.max(p1, p2);
        }
    }


    // 代码3   另一种版本，但也是暴力递归
    // cur -> 牌点数    ->  * 3 之后是效果
    // next -> 牌点数   ->  * 3之后是效果
    public static int p(int[] cands, int[] sroces, int index, int hold, int cur, int next) {
        if (index == 25) { // 最后一张
            int all = hold + cur * 3 + cands[index] * 3;
            if (all <= sroces[index]) {
                return -1;
            }
            return all;
        }
        // 不仅最后一张
        // 保留
        int all1 = hold + cur * 3 + cands[index];
        int p1 = -1;
        if (all1 > sroces[index]) {
            p1 = f(cands, sroces, index + 1, hold + cands[index], next, 0);
        }
        // 爆发
        int all2 = hold + cur * 3 + cands[index] * 3;
        int p2 = -1;
        if (all2 > sroces[index]) {
            p2 = f(cands, sroces, index + 1, hold, next + cands[index], cands[index]);
        }
        return Math.max(p1, p2);
    }

    // 自己有时间了可以改出动态规划，记忆化搜索！很简单
}

