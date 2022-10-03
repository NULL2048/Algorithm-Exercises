package 大厂刷题班.class02;

import java.util.HashMap;

public class Code05_SetAll {
    public static class MyValue<V> {
        // 加入的value
        public V value;
        // 加入该value时的时间戳
        public long time;

        public MyValue(V v, long t) {
            value = v;
            time = t;
        }
    }

    public static class MyHashMap<K, V> {
        private HashMap<K, MyValue<V>> map;
        // 当前的时间戳
        private long time;
        // setAll操作的value集合   会记录这每一个setAll的时间点
        private MyValue<V> setAll;

        public MyHashMap() {
            map = new HashMap<>();
            time = 0;
            setAll = new MyValue<V>(null, -1);
        }

        // put操作会将时间戳++
        public void put(K key, V value) {
            map.put(key, new MyValue<V>(value, time++));
        }

        // setAll操作将时间戳++
        public void setAll(V value) {
            setAll = new MyValue<V>(value, time++);
        }

        public V get(K key) {
            if (!map.containsKey(key)) {
                return null;
            }
            // 获取数据的时候需要将获取数据的时间戳和setAll的时间戳相比较，如果当前get的数据时间大于setAll的时间戳，那么就直接获取该位置原本的值
            if (map.get(key).time > setAll.time) {
                return map.get(key).value;
                // 如果时间戳比较setAll的比get位置的时间戳更大，那么就直接返回setAll的值
            } else {
                return setAll.value;
            }
        }
    }

}
