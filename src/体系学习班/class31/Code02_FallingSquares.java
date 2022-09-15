package 体系学习班.class31;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

// https://leetcode.cn/problems/falling-squares/submissions/
class Code02_FallingSquares {
    public List<Integer> fallingSquares(int[][] positions) {
        /*
         * 这道题我们就可以把他抽象成线段树的问题
         * 这些方块的边长，也就是他们占据x轴的范围，就是对应线段树中的位置区间范围。
         * 而方块摞起来的高度，就是对应每一个数的值。
         *
         * 整个题目就转换成了求指定范围内的最大值问题。
         * 我们要求的就是当某一个方块落到某一个位置后，这个位置的高度变为多少（线段树的位置区间范围内的更新操作）
         * 然后我们就求在整体的范围中，最高的高度是多少（线段树求位置区间中的最值）
         */

        // 这里我们就使用到了第一个技巧：我们要善于利用题目中的范围
        // 我们能发现，题目中给的方块可能掉落的范围太大了，已经10^8了，这道题如果用真实的位置下标来标识范，肯定会导致溢出
        // 其实我们通过题意就能够发现，这道题其实和真实的范围并没有关系，最终只是求最高高度而已
        // 根据这一点，我们就可以把这些真实范围进行压缩，仅仅是用连续的数来标识不同真实位置之间的位置关系即可，并不用写出真实的位置
        // 我们要做的就是用这些压缩之后的数和原本真实的位置做一个关联表就可以了，让我们能够用真实的位置映射找到我们压缩之后的位置
        // 然后在线段树操作的时候，就用压缩之后的位置来参与运算即可
        HashMap<Integer, Integer> positionsMap = index(positions);
        // 一共会用到多少个位置，就根据这个大小来初始化线段树
        int n = positionsMap.size();
        // 用来记录每一次方块掉落后整个范围的最大高度
        List<Integer> ans = new ArrayList<>();

        // 初始化创建线段树
        SegmentTree st = new SegmentTree(n);
        // 记录此时整个范围内的最大高度
        int max = -1;
        // 按照顺序模拟方块下落
        for (int[] arr : positions) {
            // 记录当前方块下落的左边界（是压缩后的位置，通过建立的映射关系map，用真实位置找到的其对应的压缩后位置）
            int L = positionsMap.get(arr[0]);
            // 记录当前方块下落的右边界（这里注意做右边界处理，需要减1）
            int R = positionsMap.get(arr[0] + arr[1] - 1);
            // 查询这个方块掉落范围的最大高度，然后加上这个方块的边长arr[1]，就可以得到这个范围现在最新的最大高度
            int height = st.query(L, R, 1, n, 1) + arr[1];
            // 将这个方块掉落范围的最大高度更新为height
            st.update(L, R, height, 1, n, 1);
            // 用这个方块下落范围的最大高度和之前记录的整个范围的最大高度比较，看能否刷新整个范围最大高度记录
            max = Math.max(max, height);
            // 将本次下落方块后的整个范围最大高度加入ans中
            ans.add(max);
        }
        // 返回结果
        return ans;
    }

    // 将真实位置进行压缩，并创建压缩后和真是位置的映射关系表
    public HashMap<Integer, Integer> index(int[][] positions) {
        // TreeSet可以对插入数据进行排序和去重，如果插入Integer型，默认就是从小到大排序
        TreeSet<Integer> set = new TreeSet<>();
        // 将每一个方块的左右边界都加入到set中进行排序和去重
        // 我们只需要加入这些肯定会用到的位置即可
        for (int[] arr : positions) {
            set.add(arr[0]);
            // 注意这里要处理右边界，要将右边界的下标减1，因为如果两个方块的左右边界重合，它两个是摞不到一起的，也就不算是同一个范围内的值了
            set.add(arr[0] + arr[1] - 1);
        }

        // 进行位置压缩，将压缩后的位置和真是位置进行映射
        // key：真实位置   value：压缩后的位置
        HashMap<Integer, Integer> map = new HashMap<>();
        // 我们就用连续的整数来作为压缩后的位置，只是利用这个整数的大小关系来区分不同位置之间的关系，一个数比另一个数小，说明它在另一个数的左边，反之就再右边
        // 并且将重复的位置都合并了，因为没必要设置多个相同的位置
        int cnt = 1;
        for (Integer index : set) {
            // 注意压缩后的位置下标是从1开始
            map.put(index, cnt++);
        }
        return map;
    }

    public class SegmentTree {
        // 下面所属的节点就是线段树的节点，每一个节点都会表示一个位置范围，这个位置指的是压缩之后的位置。
        // 我们就想象成一课二叉树即可，但是线段树真实是存在数组中的

        // 记录每个节点的最大高度。
        private int[] max;
        // 记录每一个节点是否存在更新任务
        private boolean[] update;
        // 记录每一个节点更新任务要将值修改为多少
        private int[] change;

        // 初始化线段树
        public SegmentTree(int size) {
            // 数组下标都是从1开始的，所以如果有size个位置，就需要开辟size+1个空间
            int n = size + 1;
            // 线段树辅助数组都需要开辟4*N的空间
            max = new int[n << 2];
            update = new boolean[n << 2];
            change = new int[n << 2];
        }

        // 将下层返回上来的数据做处理
        // 将左右两颗子树的最大值进行比较，选择最大的作为父节点的最大值
        public void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        // 将任务下推一层，只要是出现不能被全包的情况就将当前节点的任务下推
        public void pushDown(int rt) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // 这里也要把左右两个节点的最大值更新
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                // 腾空当前节点的任务
                update[rt] = false;
            }
        }

        // 更新最大值任务
        public void update(int L, int R, int newValue, int l, int r, int rt) {
            // 此时递归到的节点范围正好被任务范围全包，说明这个节点就是我们要用的
            if (L <= l && R >= r) {
                // 将这个节点最大值更新为newValue
                max[rt] = newValue;
                // 设置上这个节点的更新任务
                update[rt] = true;
                change[rt] = newValue;
                return;
            }

            // 如果当前节点没有被任务范围全包，就将当前节点的任务下推一层
            pushDown(rt);
            // 将范围划分成两半，继续向下递归寻找符合全包要求的节点
            int mid = (l + r) >> 1;
            if (mid >= L) {
                update(L, R, newValue, l, mid, rt << 1);
            }

            if (mid + 1 <= R) {
                update(L, R, newValue, mid + 1, r, rt << 1 | 1);
            }

            // 将下层的更新结果进行处理，找到两者的最大值，将其赋值给当前这一层的最大值
            pushUp(rt);
        }

        // 查找指定范围的最大值
        public int query(int L, int R, int l, int r, int rt) {
            // 能被全包说明这个节点是我们要用的，直接将其最大值返回
            if (L <= l && R >= r) {
                return max[rt];
            }

            // 不能全包就下推任务
            pushDown(rt);
            // 左右分成两半，向下递归找到能够被全包的节点
            int mid = (l + r) >> 1;
            // 记录左右子树的最值
            int leftMax = 0;
            int rightMax = 0;
            if (mid >= L) {
                leftMax = query(L, R, l, mid, rt << 1);
            }

            if (mid + 1 <= R) {
                rightMax = query(L, R,  mid + 1, r, rt << 1 | 1);
            }

            // 这里直接取左右子树的最大值即可，不用那个上推的方法了，因为这里我们需要有返回值
            return Math.max(leftMax, rightMax);
        }
    }
}

