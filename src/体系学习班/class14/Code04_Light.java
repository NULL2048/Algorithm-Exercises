package 体系学习班.class14;

import java.util.HashSet;

public class Code04_Light {
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) { // 结束的时候
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前位置是点的话
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            // i X .
            int no = process(str, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    // 贪心  这个题看着好像是一个模拟，其实这个模拟思路的策略本身就是一种贪心思想
    // 贪心就是一种最自然的思想，就是在现实生活中我们会如何思考这个问题去解决，贪心也是同样的思考
    public static int minLight2(String road) {
        // 将字符串转换成字符数组
        char[] str = road.toCharArray();
        // 记录当前位置
        int i = 0;
        // 记录灯的个数
        int light = 0;
        // i不能溢出数组
        while (i < str.length) {
            // 如果当前位置是个墙，则不能放灯，直接到下一个位置
            if (str[i] == 'X') {
                i++;
                // 如果当前位置是街道
            } else {
                // 先将等数量++,因为我们的目标就是照亮所有街道，所以出现了街道必然会使用一个灯，这里就先将等的个数++
                light++;
                // 如果当前位置已经是数组最后一个位置了，则直接结束循环即可
                if (i + 1 == str.length) {
                    break;
                    // 如果当前位置不是数组最后一个位置
                } else { // 有i位置 i+ 1 X .
                    // 当前位置的下一个位置是墙，那么我们就到下下个位置去
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                        // 如果当前位置的下一个位置是街道，那么当前位置的下下个位置不管是墙还是街道，我们都可以将灯放在当前位置的下一个位置来照亮最多3各街道
                        // 所以这种情况下我们就可以直接跳到当前位置的下下下个位置就行了，灯就会放到当前位置的下个位置，这样灯最多能照亮当前位置，当前位置下个位置，当前位置下下个位置
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        // 返回灯的个数
        return light;
    }

    // 更简洁的解法
    // 两个X之间，数一下.的数量，然后除以3，向上取整
    // 把灯数累加
    public static int minLight3(String road) {
        char[] str = road.toCharArray();
        int cur = 0;
        int light = 0;
        for (char c : str) {
            if (c == 'X') {
                light += (cur + 2) / 3;
                cur = 0;
            } else {
                cur++;
            }
        }
        light += (cur + 2) / 3;
        return light;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            int ans3 = minLight3(test);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }
}
