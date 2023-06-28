package 大厂刷题班.class09;

import java.util.Arrays;
import java.util.Comparator;

// 本题测试链接 : https://leetcode.cn/problems/russian-doll-envelopes/
// 最长递增子序列   动态规划    排序
public class Code05_EnvelopesProblem {
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes == null || envelopes.length == 0) {
            return 0;
        }

        Envelopes[] envelopesArr = sort(envelopes);

        // 后面就按照求最长递增子序列的模板代码该即可
        int[] end = new int[envelopesArr.length];
        end[0] = envelopesArr[0].length;
        int endIndex = 0;
        int max = 1;

        for(int i = 1; i < envelopesArr.length; i++) {
            int l = 0;
            int r = endIndex;

            // end数组的值也是由大到小排序好的
            // 这一步表面是是找end数组最左边的数值大于envelopesArr[i].length的位置，我们假设该位置为x,
            // 此时envelopesArr[i].length就使得end[x]代表的长度为x+1长度的递增子序列的最小结尾值变得更小了，因为envelopesArr[i].length小于end[x]
            // 所以我们就可以用envelopesArr[i].length更新end[x]的值，这个x就是下面二分过程最终的l
            while (l <= r) {
                int mid = (l + r) >> 1;
                if (envelopesArr[i].length > end[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            // 如果l比endIndex大了，说明end数组的有效区扩大了，直接更新endIndex
            endIndex = Math.max(endIndex, l);
            // 将当前位置的值记录到end[l]上，因为找的l位置的值需要更新了，
            end[l] = envelopesArr[i].length;
            max = Math.max(max, endIndex + 1);
        }

        return max;
    }

    // 创建俄罗斯套娃信息类
    public class Envelopes {
        private int width;
        private int length;

        public Envelopes(int width, int length) {
            this.width = width;
            this.length = length;
        }
    }

    // 排序：长度由小到大，长度一样的时候， 高度由大到小。
    public class EnvelopesComparator implements Comparator<Envelopes> {
        @Override
        public int compare(Envelopes o1, Envelopes o2) {
            return o1.width - o2.width != 0 ? o1.width - o2.width : o2.length - o1.length;
        }
    }

    // 按照我们的排序规则生成排序好的数组并返回
    public Envelopes[] sort(int[][] envelopes) {
        Envelopes[] envelopesArr = new Envelopes[envelopes.length];
        for (int i = 0; i < envelopes.length; i++) {
            // 一定要记住构建这种自定义类型的数组的方式，是通过new每一个元素来构建的，而不是直接用arr[i].weight = envelopes[i][0]; arr[i].height = envelopes[i][1];这种方式来构建
            envelopesArr[i] = new Envelopes(envelopes[i][0], envelopes[i][1]);
        }

        Arrays.sort(envelopesArr, new EnvelopesComparator());
        return envelopesArr;
    }

}
