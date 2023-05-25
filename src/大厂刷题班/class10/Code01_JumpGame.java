package 大厂刷题班.class10;

// 本题测试链接 : https://leetcode.cn/problems/jump-game-ii/
// 模拟    这道题应该也有贪心的解，但是绝不是简单的选择能跳到的最大步数的位置，而是一种需要综合考虑很多指标的贪心，课程上就不做讲解了，自己私下想吧
public class Code01_JumpGame {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // step：到当前位置已经跳了多少步了
        // cur：当前如果你不增加步数，step步以内，你最远能到哪儿
        // next：当前如果可允许我多跳一步，请问我最远的到哪儿
        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; i++) {
            // 如果向右一步一步移动的位置超过了之前一步能到达的最远位置cur，说明想要来到当前位置就必须再多迈一步了，step++
            // 然后就将当前一步能走到的最远位置更新，即将next赋值给cur
            if (cur < i) {
                // 加的这1步，就是指跳到next位置的那一步
                step++;
                cur = next;
            }
            // 在当前还没有超过之前一步能走到的最远位置cur时，就不断地更新推高下一次能一步走到的最远位置next，供下一次更新cur使用
            // 当cur需要更新的时候，就知道下一次能一步走到的最远距离是多少了。
            next = Math.max(next, i + nums[i]);
        }

        return step;
    }
}
