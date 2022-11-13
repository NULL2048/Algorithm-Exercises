package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int distinctSubseqII(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int mod = 1000000007;
        char[] s = str.toCharArray();
        int all = 1;
        int[] count = new int[26];
        for (int i = 0; i < s.length; i++) {
            int index = s[i] - 'a';
            int add =  (all - count[index] + mod) % mod;
            all = (all + add) % mod;
            count[index] = (count[index] + add) % mod;
        }
        return all - 1;
    }


    public static void main(String[] args) {
        int[][] grid = {{1,1,3,8,13},{4,4,4,8,18},{9,14,18,19,20},{14,19,23,25,25},{18,21,26,28,29}};
        int[] nums = {1,2,31,33};
        int[] nums2 = {2,5,6};
        int n = 13;

        String str1 = "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop";
        String str2 = "rabbit";
        System.out.println(distinctSubseqII(str1));
    }
}