package 体系学习班.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Code02_PrintAllSubsquences {

    // s -> "abc" ->
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        // 最初始一轮的字符串
        String path = "";
        // 用来记录找到的所有子序列
        List<String> ans = new ArrayList<>();
        process1(str, 0, ans, path);
        // 返回结果
        return ans;
    }

    // str：固定参数，要找到str的全部子序列
    // index：递归遍历来到了str[index]字符，index是位置，当走到index时，寿说明字符串中str[0..index-1]位置的字符已经走过了！
    // path：之前的决定保留的字符串结果，都在path上。之前的决定已经不能改变了，就是path；str[index....]还能决定，之前已经确定，而后面还能自由选择的话，
    // ans：把所有生成的子序列，放入到ans里去
    public static void process1(char[] str, int index, List<String> ans, String path) {
        // 当递归遍历完整个字符串，就将最后的结果添加到path中，结束递归
        // 递归出口
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 下面就是每当遍历到一个位置index时，做两个不同的判断，要当前index位置的字符，不要当前index位置的字符
        // 不管怎么决定，都继续向下递归遍历，将index+1，其实就是没遍历一层，就分裂出两个子分支，整个递归过程其实就形成了一个二叉树结构
        // 没有要index位置的字符
        process1(str, index + 1, ans, path);
        // 要了index位置的字符
        process1(str, index + 1, ans, path + String.valueOf(str[index]));
    }

    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        // 唯一的区别就是将存储最终结果的List变成了Set，这样就能把重复的数据去重了
        // 比如：abcccc，这个明显就会出现很多重复，因为虽然每个字符的位置不一样，但是有很多相同的字符c，所以一定会出现很多相同的子序列
        // 如果用Set存储的话，就可以直接去重了
        HashSet<String> set = new HashSet<>();
        process2(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        String no = path;
        process2(str, index + 1, set, no);
        String yes = path + String.valueOf(str[index]);
        process2(str, index + 1, set, yes);
    }

    public static void main(String[] args) {
        String test = "acccc";
        List<String> ans1 = subs(test);
        List<String> ans2 = subsNoRepeat(test);

        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=================");
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=================");

    }

}
