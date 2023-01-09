package 大厂刷题班.class33;
import java.util.*;

// 拓扑排序
// https://leetcode.cn/problems/course-schedule/
public class Problem_0207_CourseSchedule {
    // 1、下面这是我自己写的代码
    // 课程类
    public class Course {
        // 该课程在拓扑序列中的入度
        public int in;
        // 该课程编号
        public int courseId;
        // 该课程在拓扑序列中的下一个课程集合
        public List<Course> nexts;

        public Course(int courseId) {
            this.in = 0;
            this.courseId = courseId;
            nexts = new ArrayList<Course>();
        }
    }
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 过滤无效参数
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }

        // 构建课程编号和其对应的Course对象的关联表
        HashMap<Integer, Course> map = new HashMap<>();
        // 遍历prerequisites数组，构造拓扑序列的图
        for (int[] a : prerequisites) {
            // 要想学课程a[0]，就要先学课程a[1]，所以在拓扑序列中a[1]在a[0]前面
            // 源节点
            int from = a[1];
            // 目的节点
            int to = a[0];

            // 如果from课程还没有为其创建Course对象，就创建并加入到map中
            if (!map.containsKey(from)) {
                map.put(from, new Course(from));
            }
            // 如果to课程还没有为其创建Course对象，就创建并加入到map中
            if (!map.containsKey(to)) {
                map.put(to, new Course(to));
            }

            // 获取from和to课程对象
            Course f = map.get(from);
            Course t = map.get(to);

            // 将t的入度加1
            t.in++;
            // 将t加入到f的nexts集合中
            f.nexts.add(t);
        }
        // 至此，构造完了拓扑序列的结构

        // 入度为0的队列，用来做拓扑序列的遍历
        // 只有入度为0的课程，才能保证这个课程所需要的所有前置课程都学完了，才可以遍历到这个入度为0的课程上
        // 我们只能遍历从队列中弹出的入读为0的课程节点，这样才能保证遍历到的课程是可以进行学习的
        Queue<Course> zeroInQueue = new LinkedList<>();
        // 找到拓扑序列中入度为0的节点，将其加入到队列中，这个节点就是拓扑序列的起点
        for (Course c : map.values()) {
            if (c.in == 0) {
                zeroInQueue.add(c);
            }
        }

        // 记录在拓扑序列遍历了多少个节点了，即已经学完了多少个课程了
        int cnt = 0;
        // 开始遍历拓扑序列
        while (!zeroInQueue.isEmpty()) {
            // 弹出队列头部，cur为当前遍历到的节点
            Course cur = zeroInQueue.poll();
            // cur的入度为0，说明他需要的前置课程都已经学完了，所以可以学cur这门课了
            // 将已经学完的课程数加1
            cnt++;
            // 遍历cur课程所有的nexts集合中的课程，cur作为这些课程所需要的前置课程
            for (Course nextCourse : cur.nexts) {
                // 将每一个nextCourse的入度减1，表示学完cur后，nextCourse所需要的前置课程数少了一个
                // 如果nextCourse课程的入度减1之后为0了，说明nextCourse这个可能所需要的前置课程也都学完了，将其加入到队列中，用于后续对其遍历
                if (--nextCourse.in == 0) {
                    zeroInQueue.add(nextCourse);
                }
            }
        }
        // map.size()表示全部的课程数
        // 如果最多可以学完的课程数cnt 等于 map.size()，就说明我们学完了所有课程，返回true
        return cnt == map.size();
    }

    // 下面的两个代码都是左神的代码

    // 2、左神的代码1，这个代码和我自己写的代码思路完全一样，性能是完全一样的。但是我们可以学习左神写代码的技巧和变量命名规则
    // 一个node，就是一个课程
    // name是课程的编号
    // in是课程的入度
    public static class Course1 {
        public int name;
        public int in;
        public ArrayList<Course1> nexts;

        public Course1(int n) {
            name = n;
            in = 0;
            nexts = new ArrayList<>();
        }
    }

    public static boolean canFinish1(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        // 一个编号 对应 一个课的实例
        HashMap<Integer, Course1> nodes = new HashMap<>();
        for (int[] arr : prerequisites) {
            int to = arr[0];
            int from = arr[1]; // from -> to
            if (!nodes.containsKey(to)) {
                nodes.put(to, new Course1(to));
            }
            if (!nodes.containsKey(from)) {
                nodes.put(from, new Course1(from));
            }
            Course1 t = nodes.get(to);
            Course1 f = nodes.get(from);
            f.nexts.add(t);
            t.in++;
        }
        int needPrerequisiteNums = nodes.size();
        Queue<Course1> zeroInQueue = new LinkedList<>();
        for (Course1 node : nodes.values()) {
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        int count = 0;
        while (!zeroInQueue.isEmpty()) {
            Course1 cur = zeroInQueue.poll();
            count++;
            for (Course1 next : cur.nexts) {
                if (--next.in == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return count == needPrerequisiteNums;
    }

    // 2、左神的代码，和左神的代码1算法思路一样，只不过做了一些常数上的优化，用数组来实现的队列，也没有使用Course类
    // 这面这个代码基本就是最优解了
    public static boolean canFinish2(int courses, int[][] relation) {
        if (relation == null || relation.length == 0) {
            return true;
        }
        // 3 :  0 1 2
        // nexts :   0   {}
        //           1   {}
        //           2   {}
        //           3   {0,1,2}
        // 记录每个课程的nexts集合
        ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
        for (int i = 0; i < courses; i++) {
            nexts.add(new ArrayList<>());
        }
        // 3 入度 1  in[3] == 1
        int[] in = new int[courses];
        // 统计每个课程的入度
        for (int[] arr : relation) {
            // arr[1] from   arr[0] to
            nexts.get(arr[1]).add(arr[0]);
            in[arr[0]]++;
        }

        // 队列
        int[] zero = new int[courses];
        // 该队列有效范围是[l,r)
        // 新来的数，放哪？r位置，r++
        // 出队列的数，从哪拿？l位置，l++
        // l == r 队列无元素  l < r 队列有元素
        int l = 0;
        int r = 0;
        for (int i = 0; i < courses; i++) {
            if (in[i] == 0) {
                zero[r++] = i;
            }
        }
        int count = 0;
        while (l != r) {
            count++; // zero[l] 出队列   l++
            for (int next : nexts.get(zero[l++])) {
                if (--in[next] == 0) {
                    zero[r++] = next;
                }
            }
        }
        return count == nexts.size();
    }
}
