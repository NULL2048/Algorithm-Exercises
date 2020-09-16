package 排序;

import java.io.*;

public class 插入排序 {
    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));

        int[] a = new int[20];
        int index = 0;
        while (true) {
            in.nextToken();
            int num = (int) in.nval;
            if (num == -1) {
                break;
            }

            a[index++] = num;
        }

        // i表示的是每一轮比较的下标范围最右端的下标边界
        for (int i = 1; i < index; i++) {
            // j表示新加入到下标范围集合的元素的下标  j指向的元素还没有完成在当前下标范围内的排序，还没有插入到它应该在的位置
            // j要保证大于0，因为当j等于零的时候说明j的左边已经没有数了，也就是说他就是最小的元素，本轮已经排序完成
            // a[j-1]>a[j]当紧挨着j左边的元素大于j的元素时，说明两个元素需要交换一下，让效地元素到左边去，所以进入到循环体中。
            // 如果a[j-1]<a[j]，说明当前一轮的排序已经完成，新加入的元素已经插入到了它应该在的位置，因为除了进加入的一个元素以外，其他的元素原来就已经排序好了，所以当j指向的元素已经大于左边的元素了，j元素已经插入到了自己应该在的位置
            for (int j = i; j > 0 && a[j - 1] > a[j] ; j--) {
                // 交换
                int temp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = temp;
            }
        }

        for (int i = 0; i < index; i++) {
            out.print(a[i] + " ");
        }

        out.flush();
    }
}
