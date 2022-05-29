package 体系学习班.class02;

/**
 * 不用额外变量交换两个数的值
 */
public class Code01_Swap {
    public static void main(String[] args) {
        int a = 16;
        int b = 603;

        System.out.println(a);
        System.out.println(b);

        // 通过三个异或操作，就将a和b的值交换了过来
        // 注意，只有当a和b指向不同内存区域的时候才可以使用这个方法，如果a和b指向相同的内存区域，这个方法就失效了
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
    }
}
