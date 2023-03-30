package 大厂刷题班.class26;

import java.util.ArrayList;
import java.util.List;
// 递归
// 本题测试链接 : https://leetcode.cn/problems/expression-add-operators/
public class Code03_ExpressionAddOperators {
    public List<String> addOperators(String numStr, int target) {
        // 存储所有的答案
        List<String> ans = new ArrayList<>();
        // 无效参数直接返回空
        if (numStr == null || numStr.length() == 0) {
            return ans;
        }

        // 将数字字符串转换为字符数组
        char[] num = numStr.toCharArray();
        // 字符数组个数
        int n = num.length;
        // 将递归路径中沿途的数字和+ - * 的运算符，放在path里，如果找到了一条符合条件的路径，就将path中的式子加入到ans中
        char[] path = new char[(n << 1) - 1];

        // 必须用long，避免溢出，否则输入numStr："2147483648"   target: -2147483648结果就会出现问题，代码会输出["2147483648"]，但正确答案应该输出空
        // 我们要先划定第一部分的数，例如我们如果给了“1234”，那么第一个数可能是1+2+3+4，也可能是12+3+4，也可能是123+4
        // 总之我们需要去将所有可能合并出第一个数的情况都尝试一遍，下面这个part1就表示第一部分的数是什么
        long part1 = 0;
        // 去循环尝试第一个数所有的可能性
        for (int i = 0; i < n; i++) { // 尝试0~i前缀作为第一部分
            // 将字符串的前缀依次合并成一个数
            part1 = part1 * 10 + (num[i] - '0');
            // 将合并出来的数也作为一个字符加入到path中
            path[i] = num[i];
            // 以part1作为第一个数，然后往后面开始递归找符合条件的式子。此时num[]来到了i+1，path[]也来到了i+1，i+1之前的位置都填好了
            process(num, i + 1, n, path, i + 1, 0, part1, target, ans); // 递归进行后续过程

            // 这个只可能在第一次循环的时候进入，如果给的字符串numStr第一个位置是0，那么整个式子的第一个数一定就是一个0，因为题目规定了不能有前缀为0的数，所以可能的式子就是0....或者0+....或者0-.....或者0*.....，也就不需要再去循环合并后面的作为式子中的第一个数了，递归一次找到答案后就直接结束循环
            if (part1 == 0) {
                break;
            }
        }

        return ans;
    }

    // char[] num：固定参数，字符类型数组，等同于numStr
    // ni：表示此时已经尝试到了num的哪一个下标位置，ni是还没有开始尝试的位置，要在本层对其进行尝试
    // n：num的数组长度
    // char[] path：之前做的决定，已经从左往右依次填写的字符在其中，可能含有'0'~'9' 与 * - +
    // pi：此时尝试结果已经填到path的哪个下标位置了，pi位置此时还没有填，需要在本层递归中填上
    // left：绝对不会改变的部分的计算结果
    // cur：可能改变部分的结算结果，它的结果有可能因为你在之前填的运算符号而改变。
    // 当尝试到最后，式子的结果就是left + cur
    // int target 要求的式子的目标值
    // 要将收集到的path中符合条件的式子作为答案加入到ans中
    public void process(char[] num, int ni, int n, char[] path, int pi, long left, long cur, int target, List<String> ans) {
        // basecase
        // 当前已经尝试完了num中的所有数
        if (ni == n) {
            // 如果此时尝试出来的path中的式子等于target，说明这个式子符合要求，将其加入ans
            if (left + cur == target) {
                ans.add(new String(path, 0, pi));
            }
            // 无论怎么样，当尝试到ni==n时，递归就返回
            return;
        }

        // 下面的代码和主函数中差不多，也是当前来到了ni位置的数，然后我们要尝试组合数左右第一个位置的数的情况
        // 第一个数可能只有num[ni],也可能是num[ni]、num[ni+1]这两个数合并成的一个十位数，也可能继续和后面的数合并成三位数、四位数...
        // 总之就是要把第一个数所有的合并情况都尝试一遍，以此为基础向下层递归
        long part1 = 0;
        // pi之前的位置都是已经选择好的尝试方案了，不能再变了。
        // 我们此时来到pi位置，pi位置应该尝试放运算符，然后从pi+1开始放所有可能的合并后生成的数字
        // 通过这个方法我们还能模拟出不写运算符的可能，pi位置是一定要放运算符的，然后后面如果不想让pi+2放运算符，那么直接让num[ni]和num[ni+1]合并成一个二维数字加入到path[pi+1]和path[pi+2]位置即可，这样就相当于没有放运算符，很好理解。
        int nextPi = pi + 1;
        // 从ni开始遍历num[]数组
        for (int i = ni; i < n; i++) {
            part1 = part1 * 10 + (num[i] - '0');
            // 将合并的数字加入path
            path[nextPi++] = num[i];

            // 放+号
            path[pi] = '+';
            // 继续向下递归，后面的递归num[]来到了i+1，path[]数组来到了nextPi   left为left+cur   cur为part
            process(num, i + 1, n, path, nextPi, left + cur, part1, target, ans);

            // 放-号
            path[pi] = '-';
            // 继续向下递归，后面的递归num[]来到了i+1，path[]数组来到了nextPi   left为left+cur   cur为-part
            process(num, i + 1, n, path, nextPi, left + cur, -part1, target, ans);

            // 放*号
            path[pi] = '*';
            // 继续向下递归，后面的递归num[]来到了i+1，path[]数组来到了nextPi   left为left   cur为cur * part1
            process(num, i + 1, n, path, nextPi, left, cur * part1, target, ans);

            // 也是同样的道理，题目要求数字前缀不能为0，所以这个if只有可能在第一次循环时并且num[ni]为'0'的情况下进入，只尝试一遍'+'、'-'、'*'就结束循环，不在尝试合并其他可能的第一个数了
            if (num[ni] == '0') {
                break;
            }
        }
    }
}
