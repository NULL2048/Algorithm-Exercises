import java.util.* ;   
public class Demo {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String str = "abccccdd";

        HashSet<Character> set = new HashSet<Character>();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!set.contains(a)) {
                set.add(a);
            } else {
                set.remove(a);
                count++;
            }
        }

        System.out.println(set.isEmpty() ? 2 * count : 2 * count + 1);
    }
}