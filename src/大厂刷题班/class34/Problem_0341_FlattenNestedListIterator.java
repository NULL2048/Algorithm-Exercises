package 大厂刷题班.class34;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
// 数据结构设计   迭代器   栈   深度优先遍历
// https://leetcode.cn/problems/flatten-nested-list-iterator/
public class Problem_0341_FlattenNestedListIterator {
    // 不要提交这个接口类
    public interface NestedInteger {

        // @return true if this NestedInteger holds a single integer, rather than a
        // nested list.
        // 判断这个结构是否为一个整数，是整数就返回true
        public boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a
        // single integer
        // Return null if this NestedInteger holds a nested list
        // 如果这个结构是一个整数，就获取这个整数
        public Integer getInteger();

        // @return the nested list that this NestedInteger holds, if it holds a nested
        // list
        // Return null if this NestedInteger holds a single integer
        // 如果这个结构是一个数组，就返回这个List
        public List<NestedInteger> getList();
    }

    // 方法一：所有数据撸平，转换成一维数组（最优解）
    public class NestedIterator implements Iterator<Integer> {
        // 将给的嵌套数组结构拉直变成一维放到这个list中
        private List<Integer> list = new ArrayList<>();
        // 记录当前已经遍历到的下标位置
        private int index;

        // 将给的嵌套数组结构拉直，按照深度优先遍历的顺序变成一维放到这个list中
        private void add(List<NestedInteger> nestedList) {
            // 遍历nestedList中的每一个元素，每一个元素可能是一个数组，也可能是一个整数
            for (NestedInteger nestedInteger : nestedList) {
                // 当前遍历到的元素是一个整数
                if (nestedInteger.isInteger()) {
                    // 直接将这个数加入到list当中
                    list.add(nestedInteger.getInteger());
                    // 当前遍历到的元素是一个数组
                } else {
                    // 继续以这个数组开始递归，将这个数组中的数据按照深度优先遍历的顺序加入到list中
                    add(nestedInteger.getList());
                }
            }
        }

        // 构造方法
        public NestedIterator(List<NestedInteger> nestedList) {
            // 将嵌套数组结构拉直
            add(nestedList);
        }

        // 后面的操作就是直接对一维数组的简单操作了

        @Override
        public Integer next() {
            // 返回当前指针指向的位置，并将指针向后移动一个位置
            return list.get(index++);
        }

        @Override
        public boolean hasNext() {
            // 如果指针还没有到末尾，就说明后面还有数据，返回true
            return index < list.size();
        }
    }



    // 方法二：炫技方法：栈（但实际上这个方法过于笨重了，不如直接用上一个方法，在力扣提交的时候上一种方法才是最优解）
    public class NestedIterator1 implements Iterator<Integer> {

        private List<NestedInteger> list;
        private Stack<Integer> stack;
        private boolean used;

        public NestedIterator1(List<NestedInteger> nestedList) {
            list = nestedList;
            stack = new Stack<>();
            stack.push(-1);
            used = true;
            hasNext();
        }

        @Override
        public Integer next() {
            Integer ans = null;
            if (!used) {
                ans = get(list, stack);
                used = true;
                hasNext();
            }
            return ans;
        }

        @Override
        public boolean hasNext() {
            if (stack.isEmpty()) {
                return false;
            }
            if (!used) {
                return true;
            }
            if (findNext(list, stack)) {
                used = false;
            }
            return !used;
        }

        private Integer get(List<NestedInteger> nestedList, Stack<Integer> stack) {
            int index = stack.pop();
            Integer ans = null;
            if (!stack.isEmpty()) {
                ans = get(nestedList.get(index).getList(), stack);
            } else {
                ans = nestedList.get(index).getInteger();
            }
            stack.push(index);
            return ans;
        }

        private boolean findNext(List<NestedInteger> nestedList, Stack<Integer> stack) {
            int index = stack.pop();
            if (!stack.isEmpty() && findNext(nestedList.get(index).getList(), stack)) {
                stack.push(index);
                return true;
            }
            for (int i = index + 1; i < nestedList.size(); i++) {
                if (pickFirst(nestedList.get(i), i, stack)) {
                    return true;
                }
            }
            return false;
        }

        private boolean pickFirst(NestedInteger nested, int position, Stack<Integer> stack) {
            if (nested.isInteger()) {
                stack.add(position);
                return true;
            } else {
                List<NestedInteger> actualList = nested.getList();
                for (int i = 0; i < actualList.size(); i++) {
                    if (pickFirst(actualList.get(i), i, stack)) {
                        stack.add(position);
                        return true;
                    }
                }
            }
            return false;
        }

    }
}
