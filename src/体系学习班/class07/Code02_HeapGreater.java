package 体系学习班.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code02_HeapGreater<T> {
    // 存放堆的数组
    private ArrayList<T> heap;
    // 反向索引表，记录每一个在堆中的节点具体在堆的什么位置(数组下标)
    private HashMap<T, Integer> indexMap;
    // 堆的大小
    private int heapSize;
    // 比较器
    private Comparator<? super T> comp;

    // 构造方法，传入比较器
    public Code02_HeapGreater(Comparator<? super T> c) {
        // 初始化成员属性
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    // 堆是否为空
    public boolean isEmpty() {
        return heapSize == 0;
    }

    // 堆目前的大小
    public int size() {
        return heapSize;
    }

    // 堆中是否存在obj对象
    public boolean contains(T obj) {
        // 直接用反向索引表去找，如果反向索引表中有这个对象，则说明堆中一定存在这个对象
        return indexMap.containsKey(obj);
    }

    // 返回堆顶，但是不弹出
    public T peek() {
        return heap.get(0);
    }

    // 向堆中加入对象，这个和传统的堆原理一致
    public void push(T obj) {
        // 将对象加入动态数组
        heap.add(obj);
        // 将该对象的索引下标添加进反向索引表
        indexMap.put(obj, heapSize);
        // 使用heapInsert操作维护堆结构
        heapInsert(heapSize++);
    }

    // 弹出堆顶节点
    public T pop() {
        // 获取要弹出的对象
        T ans = heap.get(0);
        // 将对数组下标0位置和下标最后的一个位置的对象交换，并更新反向索引表
        swap(0, heapSize - 1);
        // 将弹出堆的对象在反向索引表中移除
        indexMap.remove(ans);
        // 将弹出堆的对象在堆数组中移除
        heap.remove(--heapSize);
        // 使用heapify重新维护调整堆结构
        heapify(0);
        return ans;
    }

    // 移除堆中指定的对象
    public void remove(T obj) {
        // 将堆数组中最后一个位置的对象与要移除的对象进行位置交换，来代替要移除对象的位置。这个操作有点类似于pop()，只不过这里是能移除任意的对象
        // 获取堆数组最后一个位置的对象
        T replace = heap.get(heapSize - 1);
        // 获取要移除对象的数组下标
        int index = indexMap.get(obj);
        // 将该对象从反向索引表中移除
        indexMap.remove(obj);
        // 将该对象从堆数组中移除
        heap.remove(--heapSize);
        // 如果移除的对象不在堆数组的最后一个位置
        if (obj != replace) {
            // 将之前取出来的最后一个位置的对象替换到已经移除对象的原位置
            heap.set(index, replace);
            // 然后更新该对象的反向索引表
            indexMap.put(replace, index);
            // 只针对这个对象做堆结构的重新维护，这个方法的维护效率更高
            resign(replace);
        }
    }

    // 传入指定对象，然后去调整对象的位置来维护堆结构
    // 这里我们就是假定堆中某个位置的值得大小有了变化，但是我们也不知道具体是变大还是变小，是变化了多少
    // 因为只要出现变化，就有可能打破堆的结构，所以我们就针对这个对象来重新维护一下对的结构，再调整的过程当中这个对象有可能向上移动，也有可能向下移动
    // 用这个方法就能最高效的调整堆的结构，时间复杂度最差是O(logN)，最好是O(N)
    public void resign(T obj) {
        /**
         * 当堆中某个节点变化导致堆结构不成立时，假设当前我们不知道这个数变化是变大还是变小，变化了多少。
         * 我们就可以拿出heapInsert和heapify这两个操作，分别对变化的位置去执行，
         * 如果heapInsert操作可以成功执行，说明当前这个数是变大了。 O(logN)
         * 如果heapify操作可以成功执行，说明当前这个数是变小了。 O(N)
         * 注意，heapInsert和heapify两个操作中，肯定有且只有一个能成功执行。
         */

        // 如果这个数是变大了，则就会执行heapInsert方法，在执行heapify时会直接返回，不会执行
        heapInsert(indexMap.get(obj));
        // 如果这个数是变小了，则就会执行heapify方法，在执行heapInsert时会直接返回，不会执行
        heapify(indexMap.get(obj));

        // 当完成了上面的操作后，整个堆的结构就重新维护好了
    }

    // 请返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    // heapInsert操作，自下向上维护堆结构
    private void heapInsert(int index) {
        // 使用比较器将刚插入的节点和自己的父节点来比较大小
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            // 如果儿子比父亲小，则将儿子和父亲的位置交换，将儿子向上移动一层，并且更新反向索引表
            swap(index, (index - 1) / 2);
            // 更新当前节点的下标
            index = (index - 1) / 2;
        }
    }

    // heapify操作，自上向下维护堆结构
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    // 交换堆中的节点位置
    private void swap(int i, int j) {
        // 交换两个对象在堆中存储的位置
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        // 同步更新两个对象的反向索引表
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

}
