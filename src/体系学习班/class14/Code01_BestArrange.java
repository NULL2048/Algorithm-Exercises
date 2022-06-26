package 体系学习班.class14;

import java.util.Arrays;
import java.util.Comparator;

public class Code01_BestArrange {
    // 记录每个会议的开始和结束时间
    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 暴力！所有情况都尝试！
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    // 还剩下的会议都放在programs里
    // done之前已经安排了多少会议的数量
    // timeLine目前来到的时间点是什么

    // 目前来到timeLine的时间点，已经安排了done多的会议，剩下的会议programs可以自由安排
    // 返回能安排的最多会议数量
    public static int process(Program[] programs, int done, int timeLine) {
        // 已经没有还没有被安排的会议了，直接返回
        if (programs.length == 0) {
            return done;
        }
        // 已经安排了多少个会议了
        int max = done;
        // 当前安排的会议是什么会，每一个都试一遍
        for (int i = 0; i < programs.length; i++) {
            // 只有开始时间晚于上一个会议的结束时间才可以
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                // 递归获取已经安排了多少个会议了
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    // 将已经安排的会议剔除，生成余下还没有安排的会议的数组
    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    // 会议的开始时间和结束时间，都是数值，不会 < 0
    public static int bestArrange2(Program[] programs) {
        // 按照我们设定的贪心规则进行排序
        Arrays.sort(programs, new ProgramComparator());
        // 记录每一次会议的结束日期
        int timeLine = 0;
        // 记录已经安排了多少个会议了
        int result = 0;
        // 依次遍历每一个会议，结束时间早的会议先遍历
        for (int i = 0; i < programs.length; i++) {
            // 会议开始时间必须晚于上一次会议的结束时间
            if (timeLine <= programs[i].start) {
                result++;
                // 更新当前会议的结束时间
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    // 比较器，谁的结束时间更早，谁就放在前面
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }

    }

    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
