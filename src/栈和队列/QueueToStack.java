package 栈和队列;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class QueueToStack {
    public static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    public static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) {
        Stack stack = new Stack();

        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println(stack.poll());
        System.out.println(stack.poll());


        out.flush();
    }
}

class Stack {
    private Queue<Integer> date;
    private Queue<Integer> help;

    public Stack() {
        this.date = new LinkedList<>();
        this.help = new LinkedList<>();
    }

    public void push(int num) {
        date.offer(num);
    }

    public int poll() {
        if (help.isEmpty()) {
            while (date.size() != 1) {
                help.offer(date.poll());
            }

            return date.poll();
        } else {
            while (help.size() != 1) {
                date.offer(help.poll());
            }

            return help.poll();
        }
    }
}
