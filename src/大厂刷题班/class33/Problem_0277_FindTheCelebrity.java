package 大厂刷题班.class33;
// 模拟
// https://leetcode.cn/problems/find-the-celebrity/
public class Problem_0277_FindTheCelebrity {
    // 提交时不要提交这个函数，因为默认系统会给你这个函数
    // knows方法，自己会认识自己，也就是传入的x和i是同一个值得话，会返回true
    public static boolean knows(int x, int i) {
        return true;
    }

    // 只提交下面的方法 0 ~ n-1
    public int findCelebrity(int n) {
        // 谁可能成为明星，谁就是cand
        // 初始化将cand设置为0号
        int cand = 0;
        // 开始遍历
        for (int i = 0; i < n; ++i) {
            // 如果发现cnad认识i,那么说明cand一定不是明星，因为明星不能认识任何人
            // 但是此时这个i就有可能是明星，因为此时发现有人认识他，并且还没有发现他认识别人
            if (knows(cand, i)) {
                // 所以将cand设置为i
                cand = i;
            }
        }
        // 当循环结束后，最后的cand就是此时唯一可能是明星人的人。因为cand留在最后说明将这个人设置为cand后，没有找到这个人还认识别人，
        // 但是以前找的所有cand都发现他们其实还认识别人，所以才会更新cand的值
        // 根据题意明星数不会超过1个人

        // cand是什么？唯一可能是明星的人！
        // 下一步就是验证，它到底是不是明星
        // 1) cand是不是不认识所有的人 cand...（右侧cand都不认识）
        // 所以，只用验证 ....cand的左侧即可
        for (int i = 0; i < cand; ++i) {
            if (knows(cand, i)) {
                return -1;
            }
        }
        // 2) 是不是所有的人都认识cand
        for (int i = 0; i < n; ++i) {
            if (!knows(i, cand)) {
                return -1;
            }
        }
        return cand;
    }
}
