package 树;

import java.io.*;
import java.util.Scanner;

public class Main {
    static StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    static PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
//        Scanner scan = new Scanner(System.in);
//
//        while (true) {
//            String str = scan.next();
//            out.print(str + " ");
//            if ("+".equals(str)) {
//                break;
//            }
//        }

        TreeNode root = create();


        树.层序遍历.Main.LevelOrder(root);
        out.flush();

    }

    public static TreeNode create() throws IOException {
        TreeNode node;

        String str = scan.next();

        if ("#".equals(str)) {
            node = null;
        } else {
            node = new TreeNode();
            node.setVal(str);
            node.setLeft(create());
            node.setRight(create());
        }

        return node;
    }
}
