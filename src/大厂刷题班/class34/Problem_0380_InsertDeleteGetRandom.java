package 大厂刷题班.class34;

import java.util.HashMap;

// 数据结构设计   哈希表
// 测试链接 : https://leetcode.cn/problems/insert-delete-getrandom-o1/
public class Problem_0380_InsertDeleteGetRandom {
    class RandomizedSet {
        // 下面两个表就是数字和对应index的关联表，两个方向都做了关联，方便根据需要任意查找
        // key：要存储的数字val   value：要存储的数字val对应的index(这个index是我们自己设定的)
        public HashMap<Integer, Integer> valIndexMap;
        // key：数字val的index   value：要存储的数字val
        public HashMap<Integer, Integer> indexValMap;
        // 当前结构中的数字数量
        public int size;

        // 构造方法
        public RandomizedSet() {
            this.valIndexMap = new HashMap<>();
            this.indexValMap = new HashMap<>();
            this.size = 0;
        }

        // 插入
        public boolean insert(int val) {
            // 只有当结构中不存在val时才可以插入，这样就保证了结构中不存在重复数字
            if (!valIndexMap.containsKey(val)) {
                // 将val和其对应的Index关联起来加入到两个Map中
                // 当前val的index其实就是size，当加入完了之后再将size++
                valIndexMap.put(val, size);
                indexValMap.put(size++, val);
                // 操作完成，返回true
                return true;
            }
            // 如果结构中存在val，直接返回false
            return false;
        }

        // 删除
        public boolean remove(int val) {
            // 只有结构中存在val才可以将其删除
            if (valIndexMap.containsKey(val)) {
                // 获取要删除val的index
                int deleteIndex = valIndexMap.get(val);

                // 获取当前结构中最后一个index对应的val
                int lastVal = indexValMap.get(size - 1);
                // 当前结构最后的index就是size - 1

                // 我们的删除原理其实就是将结构中最后一个index对应的所有数据覆盖掉要删除val的所有数据

                // lastVal这个值和deleteIndex这个index还是有用的，他们相关的数据不能删掉，需要对其进行修改来实现覆盖操作
                // 将lastVal和deleteIndex绑定在一起，覆盖掉要删除的deleteIndex在valIndexMap中的数据
                valIndexMap.put(lastVal, deleteIndex);
                // 将deleteIndex和lastVal绑定在一起，覆盖掉要删除的deleteIndex在indexValMap中的数据
                indexValMap.put(deleteIndex, lastVal);

                // val这个值和size-1这个index就相当于没用了，可以直接将他们的信息删除
                // 此时还要删除的val在valIndexMap中的数据
                valIndexMap.remove(val);
                // 此时还要删除的lastIndex（即size - 1）在indexValMap中的数据
                indexValMap.remove(size - 1);

                // 至此就完成了将lastVal的数据覆盖到deleteIndex位置上了，又删除了无效的数据，完成删除操作
                // 将数据个数减1
                size--;
                // 操作完成，返回true
                return true;
            }
            // 如果结构中不存在val，直接返回false
            return false;
        }

        // 随机返回一个数
        public int getRandom() {
            int index = (int) (Math.random() * size);
            return indexValMap.get(index);
        }
    }
}
