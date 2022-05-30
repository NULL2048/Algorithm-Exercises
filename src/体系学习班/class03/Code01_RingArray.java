package 体系学习班.class03;

public class Code01_RingArray {
    /**
     * 这里我们使用循环数组来实现的
     *
     * 但是我们引入了一个size，这样能更加方便地实现这个循环数组
     * 不去考虑end是不是追上bigin了，只判断size的大小是不是已经满了，满了的话end和bigin下标就不能移动了
     * 如果没有满，就继续循环向下移动即可
     *
     * 这样就能很简单的实现出队列了
     */
    public static class MyQueue {
        private int[] arr;
        private int pushi;// end
        private int polli;// begin
        private int size;
        private final int limit;

        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            polli = 0;
            size = 0;
            this.limit = limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("队列满了，不能再加了");
            }
            size++;
            arr[pushi] = value;
            pushi = nextIndex(pushi);
        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            size--;
            int ans = arr[polli];
            polli = nextIndex(polli);
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        // 如果现在的下标是i，返回下一个位置
        // 如果还没有到数组最末尾，就直接+1，如果到了数组最末尾，就再跳转回下标0
        private int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }

    }
}
