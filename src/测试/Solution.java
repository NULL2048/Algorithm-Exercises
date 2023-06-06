package 测试;

import java.util.*;

class Solution {
    static class RandomizedSet {
        public HashMap<Integer, Integer> valMap;
        public HashMap<Integer, Integer> indexMap;

        public RandomizedSet() {
            this.valMap = new HashMap<>();
            this.indexMap = new HashMap<>();
        }

        public boolean insert(int val) {
            if (valMap.containsKey(val)) {
                return false;
            } else {
                int index = valMap.size();
                valMap.put(val, index);
                indexMap.put(index, val);
                return true;
            }
        }

        public boolean remove(int val) {
            if (valMap.containsKey(val)) {
                int deleteIndex = indexMap.get(val);
                int endIndex = valMap.size() - 1;
                int endIndexVal = indexMap.get(endIndex);

                indexMap.remove(deleteIndex);
                valMap.remove(val);
                indexMap.remove(endIndex);
                valMap.remove(endIndexVal);

                if (endIndexVal != val) {
                    valMap.put(endIndexVal, deleteIndex);
                    indexMap.put(deleteIndex, endIndexVal);
                }


                return true;
            } else {
                return false;
            }
        }

        public int getRandom() {
            int index = (int) (Math.random() * valMap.size());
            return indexMap.get(index);
        }
    }

    public static void main(String[] args) {
        int[][] grid = {{1,2,7},{3,6,7}};

        int[] nums = {2,3,6,7};
        int[] nums2 = {1,1,2,1,2,2,1};
        int n = 3;

        char[] task = {'A','A','A','B','B','B'};



        String str1 = "abcabcbb";
        String str2 = "ABC";

        RandomizedSet a = new RandomizedSet();

        a.insert(1);
        a.insert(10);
        a.insert(20);
        a.insert(30);
        System.out.println(a.getRandom());

        System.out.println(a.getRandom());

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