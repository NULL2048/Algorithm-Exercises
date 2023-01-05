package 大厂刷题班.class32;

import java.util.Stack;

// 栈
// https://leetcode.cn/problems/min-stack/
public class Problem_0155_MinStack {
    class MinStack {
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MinStack() {
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        public void push(int val) {
            // 只有最小栈为空或者压入的数小于最小栈栈顶才将该数压入最小栈，这样做是为了节约空间
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(val);
            } else if (val <= this.getMin()) {
                this.stackMin.push(val);
            }

            this.stackData.push(val);
        }

        public void pop() {
            if (this.stackData.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            int value = this.stackData.pop();
            // 弹出的数等于最小栈栈顶才将最小栈栈顶同步弹出
            if (value == this.getMin()) {
                this.stackMin.pop();
            }
        }

        public int top() {
            return this.stackData.peek();
        }

        public int getMin() {
            if (this.stackMin.isEmpty()) {
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }
    }
}
