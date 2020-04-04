import java.util.* ;   
public class Demo {
    public static String replaceSpace(String[] strs) {
        Arrays.sort(strs);
        
        int n = strs.length;
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < strs[0].length(); i++) {
            if (strs[0].charAt(i) == strs[n - 1].charAt(i)) {
                sb.append(strs[0].charAt(i));
            } else {
                break;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"dog","racecar","car"};

        System.out.println(replaceSpace(strs));
    }
}