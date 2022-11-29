package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    public static int tallestBillboard(int[] rods) {
        if (rods == null || rods.length == 0) {
            return 0;
        }
        int n = rods.length;
        HashMap<Integer, Integer> dp = new HashMap<>();
        dp.put(0, 0);
        for (int i = 0; i < n; i++) {
            for (Map.Entry<Integer, Integer> entry : dp.entrySet()) {
                int sum1 = entry.getValue() + entry.getKey();
                int sum2 = entry.getValue();

                int newSum = rods[i] + sum1;
                int newKey = newSum - sum2;
                int value = dp.getOrDefault(newKey, 0);
                if ((value != 0 && sum2 > value) || value == 0) {
                    dp.put(newKey, sum2);
                }

                newSum = rods[i] + sum2;
                newKey = Math.abs(sum1 - newSum);
                value = dp.getOrDefault(newKey, 0);
                if ((value != 0 && Math.min(sum1, newSum) > value) || value == 0) {
                    dp.put(newKey, Math.min(sum1, newSum));
                }
            }
        }

        return dp.get(0);
    }

    public static void main(String[] args) {

        int[][] grid = {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,2,3,6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

        String str1 = "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop";
        String str2 = "rabbit";
        System.out.println(tallestBillboard(nums2));

//        int[] ans = maxSumOfThreeSubarrays(nums, n);
//        for (int a : ans) {
//            System.out.print(a + " ");
//        }
    }
}