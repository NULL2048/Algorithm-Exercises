package 栈和队列;

import java.util.Stack;

public class StackToQueue {
    public static void main(String[] args) {
        Queue queue = new Queue();

        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        queue.offer(4);
        queue.offer(5);
        System.out.println(queue.poll());
    }
}

class Queue {
    private Stack<Integer> push;
    private Stack<Integer> pop;

    public Queue() {
        this.push = new Stack<>();
        this.pop = new Stack<>();
    }

    public void offer(int num) {
        this.push.push(num);
    }

    public int poll() {
        if (!this.pop.isEmpty()) {
            return this.pop.pop();
        } else {
            while (!this.push.isEmpty()) {
                pop.push(push.pop());
            }

            return this.pop.pop();
        }
    }
}
