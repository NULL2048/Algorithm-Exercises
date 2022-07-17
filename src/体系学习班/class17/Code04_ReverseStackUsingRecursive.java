package 体系学习班.class17;

import java.util.Stack;

public class Code04_ReverseStackUsingRecursive {
    // 反转栈
    public static void reverse(Stack<Integer> stack) {
        // 递归出口，栈空直接返回
        if (stack.isEmpty()) {
            return;
        }
        // 递归弹出栈底元素，并且记录在i中
        int i = f(stack);
        // 递归依次弹出栈底
        reverse(stack);
        // 按照弹出栈底元素的顺序，再将弹出的栈底元素压回栈中，就实现了栈的逆序
        stack.push(i);
    }

    // 将栈底的元素弹出，返回移除掉的栈底元素
    public static int f(Stack<Integer> stack) {
        // 将栈顶元素弹出，并且存入到result中
        int result = stack.pop();
        // 递归出口，当栈空时，说明本层递归弹出的栈顶元素就是最初时栈的栈底元素，将其返回
        if (stack.isEmpty()) {
            return result;
        } else {
            // 继续将剩余的栈向下递归，弹出栈顶
            int last = f(stack);
            // 当递归开始向上调用后，将每一层的弹出栈顶元素再压回栈中，恢复现场
            stack.push(result);
            // 但是这里返回的都是最初时栈的栈底元素
            return last;
        }
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<Integer>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }
    }
}
