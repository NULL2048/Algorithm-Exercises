package 大厂刷题班.class20;

import java.util.HashMap;

public class Code02_LargestComponentSizebyCommonFactor {

    // 1、暴力解 O(N^2)
    public static int largestComponentSize1(int[] arr) {
        int N = arr.length;
        UnionFind set = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (gcd(arr[i], arr[j]) != 1) {
                    set.union(i, j);
                }
            }
        }
        return set.maxSize();
    }

    // O(1)
    // m,n 要是正数，不能有任何一个等于0
    // 求m和n的最大公约数，这个方法直接背过，非常重要。辗转相除法
    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // 2、并查集求解  O(N * sqrt(v))
    public int largestComponentSize(int[] nums) {
        // 因子表， key 某个因子   value 哪个位置拥有这个因子
        HashMap<Integer, Integer> factorMap = new HashMap<>();
        // 构建并查集，并查集中存储的数据是nums的下标
        UnionFind unionFind = new UnionFind(nums.length);

        // 去遍历nums数组，找到每一个数的所有因子
        for (int i = 0; i < nums.length; i++) {
            // 先单独算出来结果，不要写到for循环语句中，这样每一次循环都要算一遍，影响效率
            int limit = (int) Math.sqrt(nums[i]);
            // 找到num的所有因子，如果这个因子不曾被任何数拥有，就加入因子表，如果别的数也有这个因子，就说明这两个数是连通的，可以加入到同一个集合中，执行合并操作
            for (int k = 1; k <= limit; k++) {
                // nums[i] % k == 0，说明k是nums[i]的因子
                // 这种情况下k和nums[i]/k都是nums[i]的因子
                if (nums[i] % k == 0) {
                    // 尽量减少声明和创建的语句
                    int index;
                    // 因为题目说公因子大于1才算连通，所以不将1因子加入到因子表
                    if (k != 1) {
                        // 如果因子表不存在k，就将k加入
                        if (!factorMap.containsKey(k)) {
                            // k被下标i拥有
                            factorMap.put(k, i);
                            // 如果已经有数拥有k因子了，就将这两个数合并
                        } else {
                            index = factorMap.get(k);
                            unionFind.union(i, index);
                        }
                    }

                    // 另一个因子是nums[i] / k
                    int o = nums[i] / k;
                    // 执行相同操作
                    if (o != 1) {
                        if (!factorMap.containsKey(o)) {
                            factorMap.put(o, i);
                        } else {
                            index = factorMap.get(o);
                            unionFind.union(i, index);
                        }
                    }
                }
            }
        }

        // 找到所有独立集合中大小最大的返回
        return unionFind.maxSize();

    }

    // 并查集
    public static class UnionFind {
        public int[] parents;
        public int[] size;
        public int[] help;


        public UnionFind(int n) {
            parents = new int[n];
            size = new int[n];
            help = new int[n];

            // 初始化集合，O(N)
            for (int i = 0; i < n; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int find(int num) {
            int hi = 0;
            // 路径压缩
            while (parents[num] != num) {
                help[hi++] = num;
                num = parents[num];
            }

            for (int i = 0; i < hi; i++) {
                parents[help[i]] = num;
            }

            return num;
        }

        public void union(int num1, int num2) {
            int parent1 = find(num1);
            int parent2 = find(num2);

            if (parent1 != parent2) {
                int big = size[parent1] >= size[parent2] ? parent1 : parent2;
                int small = big == parent1 ? parent2 : parent1;

                parents[small] = big;
                size[big] += size[small];
                // 将samll集合大小设置为0，为maxSize方法做准备
                size[small] = 0;
            }
        }

        // 找到所有独立集合中大小最大的返回集合大小
        public int maxSize() {
            int max = 0;
            // 有效的代表节点对应的集合大小一定不是0，其他的位置就都是0
            for (int s : size) {
                max = Math.max(max, s);
            }

            return max;
        }
    }
}
