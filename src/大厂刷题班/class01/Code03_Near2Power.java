package 大厂刷题班.class01;

public class Code03_Near2Power {

    // 已知n是正数
    // 返回大于等于，且最接近n的，2的某次方的值
    public static final int tableSizeFor(int n) {
        n--;
        // 相当于把(n-1)左边第一个1右边所有位置都设置为1，注意这里是无符号右移，左边有0补全
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;

        // 如果结果是负数，则最近的2次方一定是1，因为2^0=1。如果不是负数，则最近的二次方一定是n+1
        return (n < 0) ? 1 : n + 1;
    }

    public static void main(String[] args) {
        int cap = 120;
        System.out.println(tableSizeFor(cap));
    }

}
