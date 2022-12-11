package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public static int maxPoints(int[][] points) {
        int n = points.length;
        int max = 1;
        for (int i = 0; i < n - 1; i++) {
            int cnt1 = 1;
            int cnt2 = 1;
            int cnt3 = 1;
            int cnt4 = 1;
            int cnt = 0;
            int molecule;
            int denominator;
            int gcd;

            int x = points[i][0];
            int y = points[i][1];
            if (y != 0) {
                gcd = Math.abs(gcd(x, y));
                x /= gcd;
                y /= gcd;
            }
            HashMap<Integer, HashMap<Integer, Integer>> fractionMap = new HashMap<>();
            for (int j = i + 1; j < n; j++) {
                if (x == points[j][0] && y == points[j][1]) {
                    cnt1++;
                    cnt = Math.max(cnt, cnt1);
                } else if (x == points[j][0]) {
                    cnt2++;
                    cnt = Math.max(cnt, cnt2);
                } else if (y == points[j][1]) {
                    cnt3++;
                    cnt = Math.max(cnt, cnt3);
                } else {
                    molecule = x - points[j][0];
                    denominator = y - points[j][1];
                    gcd = Math.abs(gcd(molecule, denominator));
                    molecule = molecule / gcd;
                    denominator = denominator / gcd;
                    cnt4 = add(molecule, denominator, fractionMap);
                    cnt = Math.max(cnt, cnt4);
                }
            }

            max = Math.max(max, cnt);
        }


        return max;
    }

    public static int gcd(int a, int b) {
        int c = a % b;
        if (c == 0) {
            return b;
        } else {
            return gcd(b, c);
        }
    }

    public static int add(int x, int y, HashMap<Integer, HashMap<Integer, Integer>> fractionMap) {
        if (fractionMap.containsKey(x) && fractionMap.get(x).containsKey(y)) {
            HashMap<Integer, Integer> map = fractionMap.get(x);
            int cnt = map.get(y);
            map.put(y, cnt + 1);
            fractionMap.put(x, map);
            return cnt + 1;
        } else {
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(y, 2);
            fractionMap.put(x, map);
            return 2;
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1},{3,2},{5,3},{4,1},{2,3},{1,4}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

//        "ADOBECODEBANC"
//        "ABC"
        String str1 = "rusrbofeggbbkyuyjsrzornpdguwzizqszpbicdquakqws";
        String str2 = "aa";
        System.out.println(maxPoints(grid));
//        System.out.println("hesitxyplovdqfkz");
//        System.out.println("hesitxyplovdqfkz".equals(removeDuplicateLetters(str1)));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}