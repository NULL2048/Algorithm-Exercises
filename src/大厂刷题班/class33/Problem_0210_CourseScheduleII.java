package 大厂刷题班.class33;
import java.util.*;

// 拓扑排序
// https://leetcode.cn/problems/course-schedule-ii/
public class Problem_0210_CourseScheduleII {
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
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        // 要返回的拓扑排序的答案
        int[] ans = new int[numCourses];
        // 此时的课程有0~numCourses - 1这几个编号
        // 先将这几个编号顺序加入ans
        for (int i = 0; i < numCourses; i++) {
            ans[i] = i;
        }
        // 如果所有的课程之间没有学习的先后依赖关系，就直接返回ans
        if (prerequisites == null || prerequisites.length == 0) {
            return ans;
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
        // 标记ans记录到哪里了
        int index = 0;
        // 找到拓扑序列中入度为0的节点，将其加入到队列中，这个节点就是拓扑序列的起点
        // 遍历0~numCourses-1全部的课程，如果这个课程不存在学习的依赖关系，直接加入ans；如果有依赖关系，就先去进行拓扑排序
        for (int i = 0; i < numCourses; i++) {
            // i不在map中，说明不存在依赖关系
            if (!map.containsKey(i)) {
                // 直接加入答案
                ans[index++] = i;
            } else {
                if (map.get(i).in == 0) {
                    zeroInQueue.add(map.get(i));
                }
            }
        }

        // 记录在拓扑序列遍历了多少个节点了，即已经学完了多少个课程了
        int cnt = 0;
        // 开始遍历拓扑序列
        while (!zeroInQueue.isEmpty()) {
            // 弹出队列头部，cur为当前遍历到的节点
            Course cur = zeroInQueue.poll();
            // 弹出的就加入到ans中，也就可以输出拓扑排序
            ans[index++] = cur.courseId;
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
        return cnt == map.size() ? ans : new int[] {};
    }
}
