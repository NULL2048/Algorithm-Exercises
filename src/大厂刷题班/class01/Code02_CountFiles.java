package 大厂刷题班.class01;


import java.io.File;
import java.util.Stack;

public class Code02_CountFiles {

    // 注意这个函数也会统计隐藏文件
    public static int getFileNumber(String folderPath) {
        // 文件类
        File root = new File(folderPath);
        // 文件类对象可以判断是文件夹还是文件
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Stack<File> stack = new Stack<>();
        stack.add(root);
        int files = 0;
        // 使用栈实现图的遍历
        while (!stack.isEmpty()) {
            File folder = stack.pop();
            for (File next : folder.listFiles()) {
                if (next.isFile()) {
                    files++;
                }
                if (next.isDirectory()) {
                    stack.push(next);
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        // 你可以自己更改目录
        String path = "/Users/97307/Desktop/";
        System.out.println(getFileNumber(path));
    }

}
