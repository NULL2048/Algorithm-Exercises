package 测试;

import java.util.*;

class Solution {
    public static int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        // 构造每一个车站能够直接到达的公交线路
        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++) {
                if (!map.containsKey(routes[i][j])) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(i);
                    map.put(routes[i][j], list);
                } else {
                    map.get(routes[i][j]).add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<Integer>();
        boolean[] set = new boolean[routes.length];
        for (Integer line : map.get(source)) {
            queue.add(line);
            set[line] = true;
        }
        int cnt = 0;

        while (!queue.isEmpty()) {
            Queue<Integer> next = new LinkedList<Integer>();

            for (Integer line : queue) {
                //if (!set[line]) {
                for (Integer station : routes[line]) {
                    if (station == target) {
                        return cnt + 1;
                    }
                    for (Integer newLine : map.get(station)) {
                        if (!set[line]) {
                            next.add(newLine);
                            set[newLine] = true;
                        }
                    }
                }
                // }
            }

            queue = next;
            cnt++;
        }
        return -1;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {7,13,20,19,19,2,10,1,1,19};
        int[] nums2 = {1,1,2,1,2,2,1};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        System.out.println(numBusesToDestination(grid, 1, 6));
//        for (int i = 0; i < nums2.length; i++) {
//            System.out.print(nums2[i] + ' ');
//        }
        //System.out.println(getMaxMatrix(grid));


//        String ans = minWindow(nums, n);
//        for (Integer a : ans) {
//            System.out.print(a + " ");
//        }

    }
}