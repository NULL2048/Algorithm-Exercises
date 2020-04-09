package 字符串问题.验证回文串;

public class Demo {
    public static void main(String[] args) {
        String str = "race a car";

        for (int i = 0, j = str.length() - 1; i <= j; ) {
            boolean flag = true;
            char a1 = str.charAt(i);
            char a2 = str.charAt(j);

            if (!isDC(a1)) {
                i++;
                flag = false;
            }

            if (!isDC(a2)) {
                j--;
                flag = false;
            }

            if (flag == true && a1 != a2) {
                System.out.println(false);
                return;
            } else if (flag == true) {
                i++;
                j--;
            }
        }
        System.out.println(true);

    }

    public static boolean isDC(char a) {
        if (a >= '0' && a <= '9') {
            if ((a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z')) {
                return true;
            }
        }
        return false;
    }


}