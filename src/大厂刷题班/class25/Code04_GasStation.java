package 大厂刷题班.class25;

// 之前讲过这道题了，在窗口内最大值结构里面，当时标题叫【第三题：加油站的良好出发点问题】  这里讲的是这道题的最优解
// 本题测试链接 : https://leetcode.cn/problems/gas-station/
// 注意本题的实现比leetcode上的问法更加通用
// leetcode只让返回其中一个良好出发点的位置
// 本题是返回结果数组，每一个出发点是否是良好出发点都求出来了
// 得到结果数组的过程，时间复杂度O(N)，额外空间复杂度O(1)
// 这是一个高频题，最优解的代码其实比较难写，大家酌情去写吧
// 贪心
public class Code04_GasStation {
    // 1、这个是最完美的最优解，可以判断出所有的位置是否为良好出发点。时间复杂度O(N)，额外空间复杂度O(1)
    // 主函数
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        // 过滤无效参数
        if (gas == null || gas.length == 0) {
            return -1;
        }
        if (gas.length == 1) {
            return gas[0] < cost[0] ? -1 : 0;
        }
        // 判断所有位置是否为良好出发点，good数组中记录每一个位置是由为良好出发点，是就为true，不是就为false
        boolean[] good = stations(cost, gas);
        for (int i = 0; i < gas.length; i++) {
            // 只要是找到了一个良好出发点就返回，这是力扣这道题的要求，其实我们这个代码是可以将所有的良好出发点都找到的
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] stations(int[] cost, int[] gas) {
        // 过滤无效参数
        if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
            return null;
        }
        // 如果可能存在良好出发点，init就不为-1
        int init = changeDisArrayGetInit(cost, gas);
        // 如果一定不存在良好出发点，就直接构造一个初始化的boolean数组，所有位置都是false即可
        // 如果可能存在良好出发点，就调用enlargeArea返回good数组
        return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
    }

    // 构造纯能数组，如果所有的纯能值都小于0，就不可能有良好出发点，直接返回-1
    public static int changeDisArrayGetInit(int[] dis, int[] oil) {
        int init = -1;
        for (int i = 0; i < dis.length; i++) {
            // 创建纯能数组，这里将纯能数组放到了dis数组中，实现额外空间复杂度为O(1)
            dis[i] = oil[i] - dis[i];
            // 只要是存在纯能值大于等于0的，就有可能存在良好出发点
            if (dis[i] >= 0) {
                init = i;
            }
        }
        return init;
    }

    // 尝试每一个位置为起始点，判断是否为良好出发点，构造良好出发点good数组
    public static boolean[] enlargeArea(int[] dis, int init) {
        // 要返回的答案
        boolean[] res = new boolean[dis.length];
        // 连通区开始位置
        int start = init;
        // 连通区结束位置
        int end = nextIndex(init, dis.length);
        // 如果你的车现在要从此时的a出发，你能带多少燃料到达连通区尾部。
        int need = 0;
        // 代表我如果要接上连通区的开头，我至少要多少油。
        int rest = 0;
        do {
            // 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
            if (start != init && start == lastIndex(end, dis.length)) {
                break;
            }
            // 当前来到的start不在连通区域中，就扩充连通区域
            // start(5) ->  联通区的头部(7) -> 2
            // start(-2) -> 联通区的头部(7) -> 9
            if (dis[start] < need) { // 当前start无法接到连通区的头部
                need -= dis[start];
            } else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
                // start(7) -> 联通区的头部(5)
                rest += dis[start] - need;
                need = 0;
                while (rest >= 0 && end != start) {
                    rest += dis[end];
                    end = nextIndex(end, dis.length);
                }
                // 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
                if (rest >= 0) {
                    res[start] = true;
                    connectGood(dis, lastIndex(start, dis.length), init, res);
                    break;
                }
            }
            start = lastIndex(start, dis.length);
        } while (start != init);
        return res;
    }

    // 已知start的next方向上有一个良好出发点
    // start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
    public static void connectGood(int[] dis, int start, int init, boolean[] res) {
        int need = 0;
        while (start != init) {
            if (dis[start] < need) {
                need -= dis[start];
            } else {
                res[start] = true;
                need = 0;
            }
            start = lastIndex(start, dis.length);
        }
    }

    // 计算环中index的上一个位置下标
    public static int lastIndex(int index, int size) {
        return index == 0 ? (size - 1) : index - 1;
    }
    // 计算环中index的下一个位置下标
    public static int nextIndex(int index, int size) {
        return index == size - 1 ? 0 : (index + 1);
    }



    // 2、下面这个代码就是针对leetcode的题，然后将上面的代码修改后的最优解答案，
    // 下面这个代码只要是找到了一个良好出发点就会直接返回，不会再去执行后面的流程了，下面的代码只支持找一个良好出发点
    // 时间复杂度O(N)，额外空间复杂度O(1)
    // 下面的代码解开注释，就可以在力扣上直接提交
    /*
    // 主函数
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        // 过滤无效参数
        if (gas == null || gas.length == 0) {
            return -1;
        }
        if (gas.length == 1) {
            return gas[0] < cost[0] ? -1 : 0;
        }
        // 判断所有位置是否为良好出发点，good数组中记录每一个位置是由为良好出发点，是就为true，不是就为false
        return stations(cost, gas);
    }

    public static int stations(int[] cost, int[] gas) {
        // 过滤无效参数
        if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
            return -1;
        }
        // 如果可能存在良好出发点，init就不为-1
        int init = changeDisArrayGetInit(cost, gas);
        // 如果不存在良好出发点，就直接返回-1
        // 如果可能存在良好出发点，就调用enlargeArea来查找是否真的有良好出发点
        return init == -1 ? -1 : enlargeArea(cost, init);
    }

    // 构造纯能数组，如果所有的纯能值都小于0，就不可能有良好出发点，直接返回-1
    public static int changeDisArrayGetInit(int[] dis, int[] oil) {
        int init = -1;
        for (int i = 0; i < dis.length; i++) {
            // 创建纯能数组，这里将纯能数组放到了dis数组中，实现额外空间复杂度为O(1)
            dis[i] = oil[i] - dis[i];
            // 只要是存在纯能值大于等于0的，就有可能存在良好出发点
            if (dis[i] >= 0) {
                init = i;
            }
        }
        return init;
    }

    // 尝试每一个位置为起始点，判断是否为良好出发点，只要是找到一个良好出发点就会犯该点的位置
    public static int enlargeArea(int[] dis, int init) {
        // 连通区开始位置
        int start = init;
        // 连通区结束位置
        int end = nextIndex(init, dis.length);
        // 如果你的车现在要从此时的a出发，你能带多少燃料到达连通区尾部。
        int need = 0;
        // 代表我如果要接上连通区的开头，我至少要多少油。
        int rest = 0;
        do {
            // 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
            if (start != init && start == lastIndex(end, dis.length)) {
                break;
            }
            // 当前来到的start不在连通区域中，就扩充连通区域
            // start(5) ->  联通区的头部(7) -> 2
            // start(-2) -> 联通区的头部(7) -> 9
            if (dis[start] < need) { // 当前start无法接到连通区的头部
                need -= dis[start];
            } else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
                // start(7) -> 联通区的头部(5)
                rest += dis[start] - need;
                need = 0;
                while (rest >= 0 && end != start) {
                    rest += dis[end];
                    end = nextIndex(end, dis.length);
                }
                // 如果连通区域已经覆盖整个环，当前的start是良好出发点，返回该start下标
                if (rest >= 0) {
                    return start;
                }
            }
            start = lastIndex(start, dis.length);
        } while (start != init);
        return -1;
    }


    // 计算环中index的上一个位置下标
    public static int lastIndex(int index, int size) {
        return index == 0 ? (size - 1) : index - 1;
    }
    // 计算环中index的下一个位置下标
    public static int nextIndex(int index, int size) {
        return index == size - 1 ? 0 : (index + 1);
    }

     */
}
