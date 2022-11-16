package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int cherryPickup(int[][] grid) {
        int n = grid.length;
        int m = grid.length;

        return process(0, 0, 0, 0, grid, n, m);
    }

    public static int process(int ai, int aj, int bi, int bj, int[][] grid, int n, int m) {
        if (ai >= n || bi >= n || aj >= m || bj >= m) {
            return Integer.MIN_VALUE;
        }

        if (ai == n - 1 && aj == m - 1) {
            return grid[ai][aj];
        }

        int p1 = process(ai + 1, aj, bi + 1, bj, grid, n, m);
        int p2 = process(ai, aj + 1, bi, bj + 1, grid, n, m);
        int p3 = process(ai + 1, aj, bi, bj + 1, grid, n, m);
        int p4 = process(ai, aj + 1, bi + 1, bj, grid, n, m);


        int next = Math.max(p1, Math.max(p2, Math.max(p3, p4)));

        int cur = 0;
        if (grid[ai][aj] == -1 || grid[bi][bj] == -1 || next == -1) {
            return -1;
        } else {
//            if (ai == bi) {
//                cur = grid[ai][aj];
//            } else {
//                cur = grid[ai][aj] + grid[bi][bj];
//            }

            if (ai != bi || aj != bj) {
                cur = grid[ai][aj] + grid[bi][bj];
            } else {
                cur = grid[ai][aj];
            }
        }

        return cur + next;
    }

    public static void main(String[] args) {

        int[][] grid = {{1, 1, -1}, {1, -1, 1}, {-1, 1, 1}};
        int[] nums = {1, 2, 31, 33};
        int[] nums2 = {2, 5, 6};
        int n = 13;

        String str1 = "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop";
        String str2 = "rabbit";
        System.out.println(cherryPickup(grid));
    }
}