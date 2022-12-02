package 大厂刷题班.class23;

import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 超级水王问题  一个数组中水王数肯定最多只可能有1个，因为水王数出现次数要求大于数组长度，所以不可能存在两个水王数
// 非常高频  数组   摩尔投票

// 扩展1：摩尔投票
// 给定一个数组arr，如果有某个数出现次数超过了数组长度的一半，打印这个数，如果没有不打印
// 169.[[多数元素]] [E]
// https://leetcode.cn/problems/majority-element/

// 扩展2：给定一个正数K，返回所有出现次数>N/K的数
// 给定一个数组arr和整数k，arr长度为N，如果有某些数出现次数超过了N/K，打印这些数，如果没有不打印
// 229.[[求众数 II]] [M]
// https://leetcode.cn/problems/majority-element-ii/
public class Code03_FindKMajority {
    // 下面两个题是我自己写的代码，很好懂，即使没有注释也一看就懂，结合笔记看思路就可以
    // 169. 多数元素：https://leetcode.cn/problems/majority-element
    public int majorityElement(int[] nums) {
        int n = nums.length;
        // 血量
        int cnt = 0;
        // 候选值
        int num = 0;

        for (int i = 0; i < n; i++) {
            if (cnt == 0) {
                num = nums[i];
                cnt++;
            } else {
                if (nums[i] == num) {
                    cnt++;
                } else {
                    cnt--;
                }
            }
        }

        return num;
    }

    // 229. 多数元素 II：https://leetcode.cn/problems/majority-element-ii/
    public List<Integer> majorityElement2(int[] nums) {
        HashMap<Integer, Integer> cntMap = new HashMap<>();
        int n = nums.length;
        List<Integer> ans = new ArrayList<Integer>();
        int k = 3;

        for (int i = 0; i < n; i++) {
            // 候选表中存在nums[i]
            if (cntMap.containsKey(nums[i])) {
                // 直接将该值的血量++
                int value = cntMap.get(nums[i]);
                cntMap.put(nums[i], value + 1);
                // 候选表中不存在nums[i]
            } else {
                // 当前Map表还没有满，就将nums[i]加入到Map候选表中，作为候选值
                if (cntMap.size() < k - 1) {
                    cntMap.put(nums[i], 1);
                    // 当前Map表已经满了，就将表中所有候选值的血量减1，然后如果剪到0的从Map中移除
                } else {
                    // 使用迭代器边遍历，边移除才不会报错
                    Iterator<Map.Entry<Integer, Integer>> iterator = cntMap.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<Integer, Integer> entry = iterator.next();

                        int key = entry.getKey();
                        int value = entry.getValue();

                        if (--value == 0) {
                            iterator.remove();
                        } else {
                            cntMap.put(key, value);
                        }

                    }
                }
            }
        }

        // 如果没有候选值，就说明这个表里面没有次品为n/k的数
        if (cntMap.size() == 0) {
            return ans;
        }

        // 找到候选值，去遍历数组来确定他们的真实词频，然后判断是否超过了n/k次，超过了就加入到ans
        for (Map.Entry<Integer, Integer> entry : cntMap.entrySet()) {
            int key = entry.getKey();
            int cnt = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] == key) {
                    cnt++;
                }
            }

            if (cnt > n / k) {
                ans.add(key);
            }
        }

        return ans;

    }


    // 下面都是左神的代码
    // 原题：摩尔投票
    public static void printHalfMajor(int[] arr) {
        int cand = 0;
        int HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (HP == 0) {
                cand = arr[i];
                HP = 1;
            } else if (arr[i] == cand) {
                HP++;
            } else {
                HP--;
            }
        }
        if(HP == 0) {
            System.out.println("no such number.");
            return;
        }
        HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                HP++;
            }
        }
        if (HP > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }

    // 拓展题
    public static void printKMajor(int[] arr, int K) {
        if (K < 2) {
            System.out.println("the value of K is invalid.");
            return;
        }
        // 攒候选，cands，候选表，最多K-1条记录！ > N / K次的数字，最多有K-1个
        HashMap<Integer, Integer> cands = new HashMap<Integer, Integer>();
        for (int i = 0; i != arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else { // arr[i] 不是候选
                if (cands.size() == K - 1) { // 当前数肯定不要！，每一个候选付出1点血量，血量变成0的候选，要删掉！
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }
        // 所有可能的候选，都在cands表中！遍历一遍arr，每个候选收集真实次数




        HashMap<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Map.Entry<Integer, Integer> set : cands.entrySet()) {
            Integer key = set.getKey();
            if (reals.get(key) > arr.length / K) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number.");
    }

    public static void allCandsMinusOne(HashMap<Integer, Integer> map) {
        List<Integer> removeList = new LinkedList<Integer>();
        for (Map.Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            map.put(key, value - 1);
        }
        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    public static HashMap<Integer, Integer> getReals(int[] arr,
                                                     HashMap<Integer, Integer> cands) {
        HashMap<Integer, Integer> reals = new HashMap<Integer, Integer>();
        for (int i = 0; i != arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 2, 3, 1, 1, 2, 1 };
        printHalfMajor(arr);
        int K = 4;
        printKMajor(arr, K);
    }
}
