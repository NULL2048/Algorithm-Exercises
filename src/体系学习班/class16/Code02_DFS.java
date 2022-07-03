package 体系学习班.class16;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {
    // 需要提供遍历起始节点
    public static void dfs(Node node) {
        // 空直接返回
        if (node == null) {
            return;
        }
        // 创建栈和Set容器
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        // 先将起始节点加入栈和Set
        stack.add(node);
        set.add(node);
        // 在加入栈的时候就打印
        System.out.println(node.value);
        // 深度优先遍历
        while (!stack.isEmpty()) {
            // 栈顶出栈
            Node cur = stack.pop();
            // 将其直接邻居加入栈
            for (Node next : cur.nexts) {
                // 只有从来没加入栈的节点才可以加入栈，否则直接跳过，因为不能重复遍历
                if (!set.contains(next)) {
                    // 如果找到了可以加入栈的直接邻居，则先将弹出的节点再次压入栈   栈中记录的就本轮深度优先遍历的路径
                    stack.push(cur);
                    // 然后将直接邻居加入
                    stack.push(next);
                    // 并将直接邻居注册到Set中
                    set.add(next);
                    // 入栈就打印
                    System.out.println(next.value);
                    // 找到了可以入栈的直接邻居后直接结束循环，继续深度向下遍历
                    break;
                }
            }
        }
    }
}
