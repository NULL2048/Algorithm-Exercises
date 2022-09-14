package 体系学习班.class31;

public class Code01_SegmentTree {
    // 线段树
    public static class SegmentTree {
        // 原始数组大小+1
        private int MAXN;
        // arr[]为原序列的信息从0开始，但在arr里是从1开始的
        private int[] arr;
        // sum[]模拟线段树维护区间和
        private int[] sum;
        // lazy[]为累加和懒惰标记
        private int[] lazy;
        // change[]为更新的值
        private int[] change;
        // update[]为更新慵懒标记
        private boolean[] update;
        // 根据原始数组初始化线段树的成员属性
        public SegmentTree(int[] origin) {
            // 我们规定从数组下标1开始使用，0下标不使用，所以所需要的数组大小需要用原始数组+1
            MAXN = origin.length + 1;
            // arr[0] 不用 从1开始使用
            arr = new int[MAXN];
            // 将原始数组origin的数据迁移到arr数组中，arr数组从下标1开始存储数据
            for (int i = 1; i < MAXN; i++) {
                arr[i] = origin[i - 1];
            }
            // 构造线段树需要开辟4*N的数组空间，左移两位就是乘4
            // 用来支持脑补概念中，某一个范围的累加和信息
            sum = new int[MAXN << 2];
            // 用来支持脑补概念中，某一个范围沒有往下传递的累加任务
            lazy = new int[MAXN << 2];
            // 用来支持脑补概念中，某一个范围有没有更新操作的任务
            change = new int[MAXN << 2];
            // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
            update = new boolean[MAXN << 2];
        }
        // 在初始化阶段，先把sum数组填好
        // 在arr[l~r]范围上，去构造这个位置范围对应的区间和数组sum，sum数组也是从下标1开始
        // rt: 这个范围在sum数组中的下标
        public void build(int l, int r, int rt) {
            // 如果l==r，说明此时这个区间只剩下1个数了，相当于构造到了区间和二叉树的叶子节点，递归可以在这里返回
            if (l == r) {
                // 这个区间的累加和就是这个值本身
                sum[rt] = arr[l];
                // 递归出口
                return;
            }
            // 递归过程中，将区间均分成两份，向下层递归
            int mid = (l + r) >> 1;
            // 数组中，下标rt节点的左子节点下标为2 * rt，右子节点下标为2 * rt + 1
            build(l, mid, rt << 1);
            build(mid + 1, r, rt << 1 | 1);  // 这里使用位运算实现的2 * rt + 1
            // 这个方法用来实现将下层递归返回上来的数据进行处理，因为这里sum数组是成员属性，所有的递归栈都是操作的同一个数组，所以在处理下层递归返回上来的数据时，就不需要通过返回值来拿到下层处理的值了，直接取成员属性sum数组中的值即可
            pushUp(rt);
        }
        // 处理上推上来的数据，更新位置区间累加和数组
        // 将下一层传递上来的区间加和合并，计算得到大区间的累加和
        private void pushUp(int rt) {
            // 将左右子节点的累加和合并，计算得到两个子节点的合并位置范围的累加和
            sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
        }
        // 将当前节点位置上的懒增加和懒更新任务，都向下分发一层，也就是从父节点范围，分发给左右两个子节点范围
        // rt表示当前递归到节点，就是要将rt位置上的任务都向下分发一层。ln表示左子树元素结点个数，rn表示右子树结点个数
        private void pushDown(int rt, int ln, int rn) {
            // 将当前位置的懒更新任务向下分发一层
            // update[rt] == true表示当前节点是存在更新任务的
            if (update[rt]) {
                // 将当前位置的更新任务向下分发一层，分发给左右子节点
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                // 左右子节点上以前存在的累加任务，一定是在更新任务之前的，会直接被更新任务覆盖
                // 所以这里直接将左右子节点的累加任务清空
                lazy[rt << 1] = 0;
                lazy[rt << 1 | 1] = 0;
                // 更新左右子节点的位置区间范围累加和
                sum[rt << 1] = change[rt] * ln;
                sum[rt << 1 | 1] = change[rt] * rn;
                // 当前位置的更新任务被下发到子节点上了，所以将update[rt]设置为false，清空当前位置的更新任务
                update[rt] = false;
            }
            // 将当前位置的懒增加任务向下分发一层
            // lazy[rt] != 0说明当前节点的范围上存在懒增加任务，需要将其向下层分发
            if (lazy[rt] != 0) {
                // rt << 1：是rt左子节点的下标
                // rt << 1 | 1：是rt右子节点的下标

                // 将左子节点的懒增加任务累加上由父节点下发下来的任务
                lazy[rt << 1] += lazy[rt];
                // 将左子节点的位置区间累加和更新，累加上由父节点下发下来的累加任务的值  lazy[rt]是左子节点位置区间上每个数要累加的数，ln是左子节点的位置范围内有多少个数
                sum[rt << 1] += lazy[rt] * ln;
                // 将右子节点的懒增加任务累加上由父节点下发下来的任务
                lazy[rt << 1 | 1] += lazy[rt];
                // 将右子节点的位置区间累加和更新，累加上由父节点下发下来的累加任务的值  lazy[rt]是右子节点位置区间上每个数要累加的数，ln是右子节点的位置范围内有多少个数
                sum[rt << 1 | 1] += lazy[rt] * rn;
                // 将当前位置上的懒增加任务腾空，用来后面承接新发给当前位置rt的懒增加任务
                lazy[rt] = 0;
            }
        }

        // 当前任务是将位置区间L~R范围上的所有数都加C，递归过程中L，R，C这三个参数是一直不变的，因为任务就是这个任务，不会中途改变
        // l，r，rt属于递归中的内部变量，表示当前已经递归到哪个区间了（l~r），对应到sum和lazy数组的下标是什么（rt）
        // 在递归入口，l、r、rt这三个参数就直接传入整个区间范围和根节点对应的下标即可，也就是1、N、1（整个位置区间范围是1~N，根节点对应的数组下标是1）
        public void add(int L, int R, int C, int l, int r, int rt) {
            // 如果任务的范围L~R把此时递归到的节点范围l~r全包了，那么这个节点就是我们要用的。
            if (L <= l && r <= R) {
                // 累加和数组中，当前这个区间对应的位置去累加上C * (r - l + 1)
                // r - l + 1：当前区间有多少个数
                // C：当前区间每一个数都要加上C
                // C * (r - l + 1)：累加任务会把当前这个区间的累加和增加多少
                sum[rt] += C * (r - l + 1);
                // 将当前累加任务添加到懒累加数组中，当前区间对应的数组下标是rt，这个就表示将rt下标对应的区间上的每个值都有一个累加C的任务，注意这里C是累加上去的，因为这个懒累加位置之前可能也有任务，不能丢掉
                lazy[rt] += C;
                return;
            }
            // 累加任务没有把当前节点的位置范围全包，则继续向下平分递归t
            // 因为如果任务没有把当前节点的位置范围全包，就说明当前节点中还存在不能被累加的数（不在累加任务范围之内的数），所以如果直接对这个节点执行任务的话，就会将一些本来不应该累加的数据进行累加了，任务就出现错误了
            // 所以需要继续将任务向下分发，直到找到一个节点的位置区间范围能完全被任务范围全包，就可以直接对这个节点执行任务了。
            // l  r  mid = (l+r)/2
            int mid = (l + r) >> 1;
            // 如果累加任务没有把当前区间全包，就会来到这个位置。（只要是出现不全包的情况，就将当前节点的任务向下分发一层）
            // 对于这一层递归到的节点，就相当于来了一个新任务，只要是来了一个新任务并且当前节点还不能被任务全包，就需要将当前rt节点上的老任务都向下分发一层
            // rt：当前递归到的节点   mid - l + 1：rt节点左孩子范围包含多少个数     r - mid：rt节点右孩子范围包含多少个数
            pushDown(rt, mid - l + 1, r - mid);
            // 将区间均分成两份，向下层递归。但是要注意，只有子节点的位置区间范围和累加任务的位置区间范围有交集，才可以向下层递归，否则这一条分支就直接终止，不用向下递归了。
            // 如果左子节点的范围完全不在我们的累加任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (L <= mid) {
                // 入参修改为左子节点的位置范围l~mid，左子节点的下标为rt << 1。L、R、C累加任务参数不变
                add(L, R, C, l, mid, rt << 1);
            }
            // 如果右子节点的范围完全不在我们的累加任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (R > mid) {
                // 入参修改为右子节点的位置范围mid+1~r，右子节点的下标为rt << 1 | 1。L、R、C累加任务参数不变
                add(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 对下层递归返回上来的数据进行处理，更新位置区间累加和数组。
            // 整个递归是直接对成员属性sum数组直接进行处理，所以如果想那下层返回上来的sum数组进行处理，就直接取sum数组就可以了，不需要获取返回参数
            // 因为所有的递归栈操作的是同一个sum数组
            pushUp(rt);
        }

        // 当前任务是将位置区间L~R范围上的所有数都更新为C，递归过程中L，R，C这三个参数是一直不变的，因为任务就是这个任务，不会中途改变
        // l，r，rt属于递归中的内部变量，表示当前已经递归到哪个区间了（l~r），对应到sum、lazy、update、change数组的下标是什么（rt）
        // 在递归入口，l、r、rt这三个参数就直接传入整个区间范围和根节点对应的下标即可，也就是1、N、1（整个位置区间范围是1~N，根节点对应的数组下标是1），因为所有的流程就是从根节点开始向下递归处理。
        public void update(int L, int R, int C, int l, int r, int rt) {
            // 如果任务的范围L~R把此时递归到的节点范围l~r全包了，那么这个节点就是我们要用的。
            if (L <= l && r <= R) {
                // 将当前节点的change[rt]设置为C，表示要将当前节点位置范围内的所有数都更新为C
                change[rt] = C;
                // 将当前节点的update[rt]设置为true，表示当前位置存在更新任务。
                // 这个是为了区分change[rt] == 0的情况，这种情况下其实是有歧义的。1、rt位置存在一个更新为0的任务。2、rt位置不存在更新任务
                // 所以就需要用update数组来标识某个位置是否存在更新任务
                update[rt] = true;
                // 更新将当前节点位置区间的累加和
                // r - l + 1：当前递归到的节点位置区间范围内有多少个数
                // C：要将当前递归到的节点位置区间范围内的所有书都更新为C
                sum[rt] = C * (r - l + 1);
                // 如果当前这个节点存在累加任务，此时这个更新任务一定是在这个节点的累加任务之前的
                // 也就是说不管累加任务对这个区间范围内的数加多少，后面都会被这个更新任务一并覆盖
                // 这里干脆就直接将这个累加任务清空即可
                lazy[rt] = 0;
                return;
            }
            // 更新任务没有把当前节点的位置范围全包，则继续向下平分递归。
            // 因为如果任务没有把当前节点的位置范围全包，就说明当前节点中还存在不能被更新的数（不在更新任务范围之内的数），所以如果直接对这个节点执行任务的话，就会将一些本来不应该更新的数据进行更新了，任务就出现错误了
            // 所以需要继续将任务向下分发，直到找到一个节点的位置区间范围能完全被任务范围全包，就可以直接对这个节点执行任务了。
            int mid = (l + r) >> 1;
            // 如果累加任务没有把当前区间全包，就会来到这个位置。（只要是出现不全包的情况，就将当前节点的任务向下分发一层）
            // 对于这一层递归到的节点，就相当于来了一个新任务，只要是来了一个新任务并且当前节点还不能被任务全包，就需要将当前rt节点上的老任务都向下分发一层
            // rt：当前递归到的节点   mid - l + 1：rt节点左孩子范围包含多少个数     r - mid：rt节点右孩子范围包含多少个数
            pushDown(rt, mid - l + 1, r - mid);
            // 将区间均分成两份，向下层递归。但是要注意，只有子节点的位置区间范围和更新任务的位置区间范围有交集，才可以向下层递归，否则这一条分支就直接终止，不用向下递归了，因为这个节点中的数据一定都不在任务执行范围内了。
            // 如果左子节点的范围完全不在我们的更新任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (L <= mid) {
                // 入参修改为左子节点的位置范围l~mid，左子节点的下标为rt << 1。L、R、C更新任务参数不变
                update(L, R, C, l, mid, rt << 1);
            }
            // 如果右子节点的范围完全不在我们的更新任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (R > mid) {
                // 入参修改为右子节点的位置范围mid+1~r，右子节点的下标为rt << 1 | 1。L、R、C更新任务参数不变
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            // 对下层递归返回上来的数据进行处理，更新位置区间累加和数组
            pushUp(rt);
        }

        // 当前任务是求出位置区间L~R范围上的累加和，递归过程中L，R这两个参数是一直不变的，因为任务就是这个任务，不会中途改变
        // l，r，rt属于递归中的内部变量，表示当前已经递归到哪个区间了（l~r），对应到sum、lazy、update、change数组的下标是什么（rt）
        // 在递归入口，l、r、rt这三个参数就直接传入整个区间范围和根节点对应的下标即可，也就是1、N、1（整个位置区间范围是1~N，根节点对应的数组下标是1），因为所有的流程就是从根节点开始向下递归处理。
        public long query(int L, int R, int l, int r, int rt) {
            // 如果任务的范围L~R把此时递归到的节点范围l~r全包了，那么这个节点的区间和就是我们要用的。
            if (L <= l && r <= R) {
                // 直接返回
                return sum[rt];
            }
            // 求区间和任务没有把当前节点的位置范围全包，则继续向下平分递归。
            // 因为如果任务没有把当前节点的位置范围全包，就说明当前节点中还存在并不是我们要求的数（不在要求累加和任务范围之内的数），所以如果直接对这个节点执行任务的话，就会将一些本来不应该算进去的数加进来，求出的累加和结果就是错的
            // 所以需要继续将任务向下分发，直到找到一个节点的位置区间范围能完全被任务范围全包，就可以直接对这个节点执行任务了。
            int mid = (l + r) >> 1;
            // 如果累加任务没有把当前区间全包，就会来到这个位置。（只要是出现不全包的情况，就将当前节点的任务向下分发一层）
            // 对于这一层递归到的节点，就相当于来了一个新任务，只要是来了一个新任务并且当前节点还不能被任务全包，就需要将当前rt节点上的老任务都向下分发一层
            // rt：当前递归到的节点   mid - l + 1：rt节点左孩子范围包含多少个数     r - mid：rt节点右孩子范围包含多少个数
            pushDown(rt, mid - l + 1, r - mid);
            // 记录当前节点左右子树求出来的符合区间范围的累加和
            long ans = 0;
            // 将区间均分成两份，向下层递归。但是要注意，只有子节点的位置区间范围和求区间和任务的位置区间范围有交集，才可以向下层递归，否则这一条分支就直接终止，不用向下递归了，因为这个节点中的数据一定都不在任务执行范围内了。
            // 如果左子节点的范围完全不在我们的求区间和任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (L <= mid) {
                // 入参修改为左子节点的位置范围l~mid，左子节点的下标为rt << 1。L、R求区间和任务参数不变
                // 将返回结果累加进ans中
                ans += query(L, R, l, mid, rt << 1);
            }
            // 如果右子节点的范围完全不在我们的求区间和任务L~R范围内，那么这个分支就终止，不再向下递归了。
            if (R > mid) {
                // 入参修改为右子节点的位置范围mid+1~r，右子节点的下标为rt << 1 | 1。L、R求区间和任务参数不变
                // 将返回结果累加进ans中
                ans += query(L, R, mid + 1, r, rt << 1 | 1);
            }
            // 返回当前节点代表位置范围的累加和
            return ans;
        }
    }

    // for test
    public static class Right {
        public int[] arr;
        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }
        public void update(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] = C;
            }
        }
        public void add(int L, int R, int C) {
            for (int i = L; i <= R; i++) {
                arr[i] += C;
            }
        }
        public long query(int L, int R) {
            long ans = 0;
            for (int i = L; i <= R; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }
    // for test
    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }
    // for test
    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int S = 1;
            int N = origin.length;
            int root = 1;
            seg.build(S, N, root);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C, S, N, root);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C, S, N, root);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R, S, N, root);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }
    // for test
    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        int S = 1; // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int N = origin.length; // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int root = 1; // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int L = 2; // 操作区间的开始位置 -> 可变
        int R = 5; // 操作区间的结束位置 -> 可变
        int C = 4; // 要加的数字或者要更新的数字 -> 可变
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(S, N, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(L, R, C, S, N, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(L, R, C, S, N, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(L, R, S, N, root);
        System.out.println(sum);
        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }
}
