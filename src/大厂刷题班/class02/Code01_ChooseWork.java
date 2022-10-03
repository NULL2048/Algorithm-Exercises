package 大厂刷题班.class02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class Code01_ChooseWork {
    // 工作类
    public static class Job {
        // 工作的报酬
        public int money;
        // 工作要求的能力
        public int hard;

        public Job(int m, int h) {
            money = m;
            hard = h;
        }
    }

    // 比较器，先按照工作需要的能力由小到大排序，如果要求的能力一样，再按照报酬由大到小排序
    // 这个就是我们整个贪心策略的基础
    public static class JobComparator implements Comparator<Job> {
        @Override
        public int compare(Job o1, Job o2) {
            return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
        }
    }

    //
    public static int[] getMoneys(Job[] job, int[] ability) {
        // 先将工作按照我们定好的贪心策略排序
        Arrays.sort(job, new JobComparator());
        // key : 难度   value：报酬
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(job[0].hard, job[0].money);
        // pre : 上一份进入map的工作
        Job pre = job[0];
        for (int i = 1; i < job.length; i++) {
            // 如果要求能力相同的工作，只保留第一个加入到map，其他的都舍弃，因为按照我们的排序规则，后面的一定都是报酬不如第一个的
            // 而且还要保证整个map报酬的单调性，如果相邻的两个工作，虽然要求的能力不同，但是后面要求能力大的工作报酬反而没有能力要求小的高，那么这个工作也不加入到map中
            if (job[i].hard != pre.hard && job[i].money > pre.money) {
                pre = job[i];
                map.put(pre.hard, pre.money);
            }
        }
        // 至此，我们就建立好了工作难度和工作报酬具有单调性的这么一个map

        int[] ans = new int[ability.length];
        // 我们找到每一个人的能力刚好能找到的最大能力要求的工作，这个工作按照题目的单调性来说一定也是报酬最大的
        for (int i = 0; i < ability.length; i++) {
            // ability[i] 当前人的能力 <= ability[i]  且离它最近的
            Integer key = map.floorKey(ability[i]);
            // 如果这个人能力太差，都找不到能胜任的工作，直接设置为0
            ans[i] = key != null ? map.get(key) : 0;
        }
        return ans;
    }

}

