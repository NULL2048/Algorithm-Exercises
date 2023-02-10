package 大厂刷题班.class36;
import java.util.ArrayList;
import java.util.HashMap;

// 宽度优先遍历（记住这种宽度优先遍历的方法，就是一次性把一层搞出来，同时记录上下一层的东西，在下一轮循环的时候把下一层的数据作为下一轮时当前层的数据） 最短路径问题   数组   哈希表
// 来自三七互娱   阿里实习笔试第二题
// Leetcode原题 : https://leetcode.cn/problems/bus-routes/
public class Code11_BusRoutes {
    // routes数组表示每一种公交线路包括哪些公交站
    // 0 : [1,3,7,0]   0号公交线路包括1、3、7、0这四个公交站
    // 1 : [7,9,6,2]
    // ....
    // 返回：返回换乘次数+1 -> 返回一共坐了多少次公交。
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // 如果起点和目标一致，直接返回0
        if (source == target) {
            return 0;
        }
        // 全部的公交线路数
        int n = routes.length;
        // key : 车站
        // value : list -> 该车站被哪些线路拥有  list中存储的公交线路编号
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        // 根据routes数组构造出map
        for (int i = 0; i < n; i++) {
            // 遍历每一条路线下的每一个车站
            for (int station : routes[i]) {
                // 将每一个车站所在的线路编号加入到map中这个车站对应的value中
                // 如果这个车站还没有创建map，就手动创建一个
                if (!map.containsKey(station)) {
                    map.put(station, new ArrayList<Integer>());
                }
                // station车站被i号路线拥有
                map.get(station).add(i);
            }
        }
        // 至此，通过map就可以快速取出一个车站可以通往哪些公交路线

        // 宽度优先遍历用的队列，里面加入的是公交线路编号
        ArrayList<Integer> queue = new ArrayList<>();
        // 标记路线是否已经被宽度优先遍历过了
        boolean[] set = new boolean[n];
        // 将起点能够走到的公交线路都取出来，这些公交线路就是我们一开始能走的路线
        for (int route : map.get(source)) {
            // 将公交线路加入到队列中
            queue.add(route);
            // 将这个线路标记为true，表示已经遍历到了
            set[route] = true;
        }
        // 记录换乘次数
        int cnt = 0;
        // 开始深度优先遍历，每次就直接把一层的都遍历出来
        while (!queue.isEmpty()) {
            // 记录下一层宽度遍历的路线列表
            ArrayList<Integer> nextRoutes = new ArrayList<>();
            // 遍历当前队列中的所有线路，表示当前能走到的线路
            for (Integer route : queue) {
                // 获取这些线路包括车站
                int[] stations = routes[route];
                // 遍历每一条线路的所有车站
                for (int station : stations) {
                    // 如果这个车站就是终点，直接返回换乘次数+1
                    if (station == target) {
                        // 之所以加1，是因为cnt统计的是换乘次数，题目要求要返回乘坐公交车的数量，所以要加1
                        return cnt + 1;
                    }
                    // 通过这个车站再去找下一次换乘能走到的路线由哪些。利用之前构造的map结构找
                    for (int nextRoute : map.get(station)) {
                        // 保证这个路线没有被遍历过
                        if (!set[nextRoute]) {
                            // 将这个路线加入到nextRoutes中，供下一层遍历使用
                            nextRoutes.add(nextRoute);
                            // 将这个路线标记为已经遍历到了
                            set[nextRoute] = true;
                        }
                    }
                }
            }
            // 每遍历一层，换成次数就加1
            cnt++;
            // 将下一层的路线列表赋值给queue，作为下一轮遍历过程中可以走到的路线列表
            queue = nextRoutes;
        }
        // 不能走到就返回-1
        return -1;
    }
}
