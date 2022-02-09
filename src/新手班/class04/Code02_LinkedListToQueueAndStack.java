package 新手班.class04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
    用单链表实现队列和栈
 */
public class Code02_LinkedListToQueueAndStack {

    // 定义单链表内部类
    public static class Node<V> {
        public V value;
        public Node<V> next;

        public Node(V v) {
            value = v;
            next = null;
        }
    }

    // 使用单链表实现队列，所有操作的实现复杂度都是O(1)
    public static class MyQueue<V> {
        // 指向链表头，也就是当前队列中最早插入的元素
        private Node<V> head;
        // 指向链表尾，也就是当前队列中最新插入的元素
        private Node<V> tail;
        // 队列大小
        private int size;

        /**
         * 队列构造方法
         */
        public MyQueue() {
            head = null;
            tail = null;
            size = 0;
        }

        /**
         * 队列是否为空
         * @return
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * 获取队列中元素数量
         * @return
         */
        public int size() {
            return size;
        }

        /**
         * 向队列中插入元素
         */
        public void offer(V value) {
            Node node = new Node(value);

            // tail为空，说明当前队列是空
            if (tail == null) {
                head = node;
                tail = node;
            // 更新tail的指向为最新插入队列的元素
            } else {
                tail.next = node;
                tail = node;
            }

            size++;
        }

        /**
         * 从队列中弹出数据
         * 直接将head指向的数据弹出即可，队列是先进先出
         * @return
         */
        public V poll() {
            V ans = null;

            if (head != null) {
                ans = head.value;
                head = head.next;
                size--;
            }

            // 这里不能拼else语句，因为上面的步骤操作完之后，可能head就变成null了
            if (head == null) {
                // 将tail也变成null，队列重新回到初始状态。
                tail = null;
            }

            return ans;
        }

        /**
         * 返回队列中将要弹出的元素，但是不真正弹出
         * @return
         */
        public V peek() {
            if (head != null) {
                return head.value;
            } else {
                return null;
            }
        }
    }

    // 使用单链表实现栈，所有操作的实现复杂度都是O(1)
    public static class MyStack<V> {
        private Node<V> head;
        private int size;

        public MyStack() {
            head = null;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        /**
         * 向栈中压入元素
         *
         * 这里采用的是头插法，并且头节点也存储数据
         * @param value
         */
        public void push(V value) {
            Node<V> cur = new Node<>(value);

            if (head == null) {
                head = cur;
            } else {
                cur.next = head;
                head = cur;
            }
            size++;
        }

        /**
         * 删除栈中元素
         *
         * 直接将头节点删除即可
         * @return
         */
        public V pop() {
            V ans = null;

            if (head != null) {
                ans = head.value;
                // 原头节点已经没有引用指向它了，所以它就会被JVM的垃圾回收机制给清除掉
                head = head.next;
                size--;
            }
            return ans;
        }

        public V peek() {
            return head == null ? null : head.value;
        }
    }


    public static void testQueue() {
        MyQueue<Integer> myQueue = new MyQueue<>();
        Queue<Integer> test = new LinkedList<>();
        int testTime = 5000000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myQueue.isEmpty() != test.isEmpty()) {
                System.out.println("Oops!");
            }
            if (myQueue.size() != test.size()) {
                System.out.println("Oops!");
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                myQueue.offer(num);
                test.offer(num);
            } else if (decide < 0.66) {
                if (!myQueue.isEmpty()) {
                    int num1 = myQueue.poll();
                    int num2 = test.poll();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            } else {
                if (!myQueue.isEmpty()) {
                    int num1 = myQueue.peek();
                    int num2 = test.peek();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        if (myQueue.size() != test.size()) {
            System.out.println("Oops!");
        }
        while (!myQueue.isEmpty()) {
            int num1 = myQueue.poll();
            int num2 = test.poll();
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }

    public static void testStack() {
        MyStack<Integer> myStack = new MyStack<>();
        Stack<Integer> test = new Stack<>();
        int testTime = 5000000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myStack.isEmpty() != test.isEmpty()) {
                System.out.println("Oops!");
            }
            if (myStack.size() != test.size()) {
                System.out.println("Oops!");
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                myStack.push(num);
                test.push(num);
            } else if (decide < 0.66) {
                if (!myStack.isEmpty()) {
                    int num1 = myStack.pop();
                    int num2 = test.pop();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            } else {
                if (!myStack.isEmpty()) {
                    int num1 = myStack.peek();
                    int num2 = test.peek();
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        if (myStack.size() != test.size()) {
            System.out.println("Oops!");
        }
        while (!myStack.isEmpty()) {
            int num1 = myStack.pop();
            int num2 = test.pop();
            if (num1 != num2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("测试结束！");
    }

    public static void main(String[] args) {
        testQueue();
        testStack();
    }
}
