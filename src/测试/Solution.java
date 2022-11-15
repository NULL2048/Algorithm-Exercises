package 测试;

import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

class Solution {
    public static int shortestBridge(int[][] grid) {
        int n = grid.length;
        int m = grid.length;
        int all = n * m;
        int[][] distanceRecord = new int[2][all];
        int[] curs = new int[all];
        int[] nexts = new int[all];
        int islandNo = 0;
        //int cursIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    int cursIndex = infect(n, m, i, j, grid, 0, distanceRecord[islandNo], curs);
                    int distance = 2;
                    while (cursIndex != 0) {
                        int nextsIndex = bfs(n, m, grid, distanceRecord[islandNo], curs, cursIndex, nexts, distance++);

                        int[] temp = curs;
                        curs = nexts;
                        nexts = temp;
                        cursIndex = nextsIndex;
                    }

                    islandNo++;
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < distanceRecord[0].length; i++) {
            min = Math.min(distanceRecord[0][i] + distanceRecord[1][i], min);
        }

        return min - 3;
    }

    public static int infect(int n, int m, int i, int j, int[][] grid, int index, int[] distanceRecord, int[] curs) {
        if (i < 0 || i >= n || j < 0 || j >= m || grid[i][j] != 1) {
            return index;
        }

        grid[i][j] = 2;
        int curIndex = i * m + j;
        distanceRecord[curIndex] = 1;
        curs[index++] = curIndex;

        index = infect(n, m, i + 1, j, grid, index, distanceRecord, curs);
        index = infect(n, m, i - 1, j, grid, index, distanceRecord, curs);
        index = infect(n, m, i, j + 1, grid, index, distanceRecord, curs);
        index = infect(n, m, i, j - 1, grid, index, distanceRecord, curs);

        return index;
    }

    public static int bfs(int n, int m, int[][] grid, int[] distanceRecord, int[] curs, int cursSize, int[] nexts, int distance) {
        int nextsIndex = 0;
        for (int i = 0; i < cursSize; i++) {
            int leftIndex = curs[i] % m == 0 ? -1 : curs[i] - 1;
            int rightIndex = curs[i] % m == m - 1 ? -1 : curs[i] + 1;
            int upIndex = curs[i] / m == 0 ? -1 : curs[i] - m;
            int downIndex = curs[i] / m == n - 1 ? -1 : curs[i] + m;

            if (leftIndex != -1 && distanceRecord[leftIndex] == 0) {
                distanceRecord[leftIndex] = distance;
                nexts[nextsIndex++] = leftIndex;
            }
            if (rightIndex != -1 && distanceRecord[rightIndex] == 0) {
                distanceRecord[rightIndex] = distance;
                nexts[nextsIndex++] = rightIndex;
            }
            if (upIndex != -1 && distanceRecord[upIndex] == 0) {
                distanceRecord[upIndex] = distance;
                nexts[nextsIndex++] = upIndex;
            }
            if (downIndex != -1 && distanceRecord[downIndex] == 0) {
                distanceRecord[downIndex] = distance;
                nexts[nextsIndex++] = downIndex;
            }
        }

        return nextsIndex;
    }


    public static void main(String[] args) {

        int[][] grid = {{0,1,0},{0,0,0},{0,0,1}};
        int[] nums = {1,2,31,33};
        int[] nums2 = {2,5,6};
        int n = 13;

        String str1 = "yezruvnatuipjeohsymapyxgfeczkevoxipckunlqjauvllfpwezhlzpbkfqazhexabomnlxkmoufneninbxxguuktvupmpfspwxiouwlfalexmluwcsbeqrzkivrphtpcoxqsueuxsalopbsgkzaibkpfmsztkwommkvgjjdvvggnvtlwrllcafhfocprnrzfoyehqhrvhpbbpxpsvomdpmksojckgkgkycoynbldkbnrlujegxotgmeyknpmpgajbgwmfftuphfzrywarqkpkfnwtzgdkdcyvwkqawwyjuskpvqomfchnlojmeltlwvqomucipcwxkgsktjxpwhujaexhejeflpctmjpuguslmzvpykbldcbxqnwgycpfccgeychkxfopixijeypzyryglutxweffyrqtkfrqlhtjweodttchnugybsmacpgperznunffrdavyqgilqlplebbkdopyyxcoamfxhpmdyrtutfxsejkwiyvdwggyhgsdpfxpznrccwdupfzlubkhppmasdbqfzttbhfismeamenyukzqoupbzxashwuvfkmkosgevcjnlpfgxgzumktsexvwhylhiupwfwyxotwnxodttsrifgzkkedurayjgxlhxjzlxikcgerptpufocymfrkyayvklsalgmtifpiczwnozmgowzchjiop";
        String str2 = "rabbit";
        System.out.println(shortestBridge(grid));
    }
}