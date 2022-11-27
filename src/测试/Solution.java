package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        int[] leftStartIndex = new int[n];
        int[] right = new int[n];
        int[] rightStartIndex = new int[n];

        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        left[k - 1] = sum;
        leftStartIndex[k - 1] = 0;

        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            if (left[i - 1] >= sum) {
                left[i] = left[i - 1];
                leftStartIndex[i] = leftStartIndex[i - 1];
            } else {
                left[i] = sum;
                leftStartIndex[i] = i - k + 1;
            }
        }

        sum = 0;
        for (int i = n - 1; i >= n - k; i--) {
            sum += nums[i];
        }
        //right[n - 1 - k + 1]
        right[n - k] = sum;
        leftStartIndex[n - k] = n - k;

        for (int i = n - k - 1; i >= 0; i--) {
            sum = sum - nums[i + k] + nums[i];
            if (right[i + 1] > sum) {
                right[i] = right[i + 1];
                rightStartIndex[i] = rightStartIndex[i + 1];
            } else {
                right[i] = sum;
                rightStartIndex[i] = i;
            }
        }

        sum = 0;
        for (int i = k - 1; i < 2 * k - 1; i++) {
            sum += nums[i];
        }
        int max = Integer.MIN_VALUE;
        int[] ans = new int[3];
        int ansSum;
        int limit =  n - 2 * k;
        for (int l = k; l <= limit; l++) {
            int r = l + k - 1;
            sum = sum - nums[l - 1] + nums[r];
            ansSum = left[l - 1] + sum + right[r + 1];
            if (max < ansSum) {
                max = ansSum;
                ans[0] = leftStartIndex[l - 1];
                ans[1] = l;
                ans[2] = rightStartIndex[r + 1];
            }
        }

        return ans;
    }

    public static void main(String[] args) {

        int[][] grid = {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}};
        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {2, 5, 6};
        int n = 3;

//        Arrays.sort(nums);
//        for (int num : nums) {
//            System.out.print(num + " ");
//        }
        //System.out.println(nums);

        String str1 = "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop";
        String str2 = "rabbit";
        //System.out.println(cherryPickup(grid));

        int[] ans = maxSumOfThreeSubarrays(nums, n);
        for (int a : ans) {
            System.out.print(a + " ");
        }
    }
}