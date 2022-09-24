package 体系学习班.class39;

import java.util.Map;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.TreeMap;

public class Code02_SnacksWaysMain {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StreamTokenizer in = new StreamTokenizer(br);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        while (in.nextToken() != StreamTokenizer.TT_EOF) {
            int n = (int) in.nval;
            in.nextToken();
            int bag = (int) in.nval;
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                in.nextToken();
                arr[i] = (int) in.nval;
            }
            long ways = ways(arr, bag);
            out.println(ways);
            out.flush();
        }
    }

    public static long ways(int[] arr, int bag) {
        // 过滤简单参数
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }

        // 这道题需要用到分治，将整个大数组左右分成两个部分
        // 记录左部分能够实现的放零食的方法  key：表示包内正好放多大体积的零食  value：正好放满key体积的零食一共有多少种方法
        TreeMap<Long, Long> lmap = new TreeMap<Long, Long>();
        // 记录又部分能够实现的放零食的方法  key：表示包内正好放多大体积的零食  value：正好放满key体积的零食一共有多少种方法
        TreeMap<Long, Long> rmap = new TreeMap<Long, Long>();
        // 将数组评分
        int mid = (arr.length - 1) >> 1;
        // 暴力递归，返回值是整个左部分全部的全部不超过bag大小的放零食方法数
        long leftWays = process(arr, 0, mid, 0L, bag, lmap);
        // 暴力递归，返回值是整个右部分全部的全部不超过bag大小的放零食方法数
        long rightWays = process(arr, mid + 1, arr.length - 1, 0L, bag, rmap);

        // 这里取一个右部份的前缀方法数，用来和左部分组合，整合出中间部分的方法数
        // (key， value)：表示包内装零食体积小于等于key的方法总数value
        TreeMap<Long, Long> preRightMap =  new TreeMap<Long, Long>();
        Long pre = 0L;
        // 生成右部分的前缀方法数
        for (Map.Entry<Long, Long> entry : rmap.entrySet()) {
            // 将方法数进行前缀累加，TreeMap会自动根据key从小到大排序
            pre += entry.getValue();
            preRightMap.put(entry.getKey(), pre);
        }

        // 记录从中间部分选的方法总数
        long midWays = 0L;
        // 遍历左部分的方法数，以每一个左部分方法为基准，凑一个右部分最合适的方法数，来整合出中间部分的方法数
        for (Map.Entry<Long, Long> entryL : lmap.entrySet()) {
            // floorKey(K key)	返回小于或等于给定键的最大键，如果没有这样的键，则null
            // 找到当前左部分的体积 +  右部份的体积   小于等于bag的方法数最多的那一个选择
            Long preRightKey = preRightMap.floorKey(bag - entryL.getKey());
            if (preRightKey != null) {
                // 两个方法数相乘，记录中间部分的方法数
                midWays += (preRightMap.get(preRightKey) * entryL.getValue());
            }
        }
        // 将左部分，右部份，中间部分的方法数相加，还要加1，是指什么都不选，因为在暴力递归，并没有将什么都不选算成是一种方法，为了避免在整合期间多算一个方法。这里我们就在最后手动加上
        return leftWays + rightWays + midWays + 1;

    }
    public static long process(int[] arr, int index, int end, long sumV, int bag, TreeMap<Long, Long> map) {
        // 递归出口，当前放零食的总体积大于bag，背包放不下了，所以这种放法不成立，返回0
        if (sumV > bag) {
            return 0;
        }
        // 遍历完了所有的零食，并且sumV没有超过bag，说明这是一种合法的方法
        if (index > end) {
            // sumV != 0说明有选择零食
            if (sumV != 0) {
                // 如果这个选择的体积在map中已经存在了，就将这个体积的方法数+1
                if (map.containsKey(sumV)) {
                    map.put(sumV, map.get(sumV) + 1L);
                    // 如果是第一次凑出这个体积，就创建这个key-value对
                } else {
                    map.put(sumV, 1L);
                }
                // 返回方法数1，用来在递归过程中统计整个index~end范围的总方法数
                return 1;
                // 如果没有选择零食，这里返回0，不把他算成一种方法，这是为了避免在后续左右部分整合过程中算重，比如左边什么都不选算是一种方法，右边什么都不选也算一种方法，左右整合就变成两种的方法了，但实际还应该是一种方法
            } else {
                return 0;
            }

        }

        // 两种选择，index位置的零食要或者不要
        long ways = process(arr, index + 1, end, sumV + arr[index], bag, map);
        ways += process(arr, index + 1, end, sumV, bag, map);
        // 返回左右递归分支总方法数
        return ways;
    }
}
