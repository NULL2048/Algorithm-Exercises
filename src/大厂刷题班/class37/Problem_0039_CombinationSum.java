package 大厂刷题班.class37;

import java.util.ArrayList;
import java.util.List;

// 回溯  递归
// https://leetcode.cn/problems/combination-sum/
public class Problem_0039_CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        return process(candidates, target, candidates.length - 1);
    }

    // 想凑出来的和叫target
    // 在arr[0... index]所有的数中自由选择，每一种数任意个。(index+1....) 不用管，认为这个范围已经选择好了
    // 凑出target,所有的方案，返回，List<List<Integer>>
    public List<List<Integer>> process(int[] arr, int target, int index) {
        // 每一层都要提前创建一个ans，会将下层递归返回上来的答案再全部加入到ans中
        List<List<Integer>> ans = new ArrayList<>();
        // basecase 如果此时target为0，说明当前层已经不需要再去凑数了，目前已经都凑出来了，直接返回
        if (target == 0) {
            // 需要再加一个List，供上一层添加数据
            ans.add(new ArrayList<>());
            return ans;
        }
        // 如果越界了，说明没数可以用了，直接返回ans，这样上一层也就没办法给这一种组合情况追加数了，就相当于丢弃了一种情况
        if (index == -1) {
            return ans;
        }

        // 当前尝试放入arr[index]这个数，尝试这个数的可用的所有数量，即i * arr[index] <= target
        // arr[index]这个数用i个
        for (int i = 0; i * arr[index] <= target; i++) {
            // 递归回溯返回下一层的已经找到的结果。此时preAns中所有的组合都是加上i个arr[index]后，总加和就都为target了，即此时preAns中所有组合的加和数都是一样的
            List<List<Integer>> preAns = process(arr, target - i * arr[index], index - 1);
            // 遍历下一层的结果，将这一层的选择都追加到下一层的结果中
            for (List<Integer> list : preAns) {
                // arr[index]这个数用i个,所以向每一个结果组合list中加入i个arr[index]
                for (int j = 0; j < i; j++) {
                    list.add(arr[index]);
                }
                // 将下一层的结果和这一层的选择都加入到这一层的ans中
                ans.add(list);
            }
        }
        return ans;
    }
}
