package 大厂刷题班.class34;

import java.util.Arrays;

// 数据结构设计   随机数
// https://leetcode.cn/problems/shuffle-an-array/
public class Problem_0384_ShuffleAnArray {
    class Solution {
        // 记录原数组
        public int[] originalArr;
        // 当前数组（原数组或乱序后的数组）
        public int[] nowArr;

        // 初始化
        public Solution(int[] nums) {
            // 记录原数组
            this.originalArr = nums;
            // 将原数组拷贝一份给nowArr，不要直接赋值，这两个不能共用同一块内存，否则打乱顺序的时候就直接将原数组的顺序打乱了
            this.nowArr = Arrays.copyOf(nums, nums.length);
        }

        // 恢复原顺序
        public int[] reset() {
            // 将原数组拷贝一份给nowArr
            this.nowArr = Arrays.copyOf(this.originalArr, originalArr.length);
            return nowArr;
        }

        // 打乱顺序
        public int[] shuffle() {
            // 0...N-1做一个随机，然后把它交换到最后一个位置。
            // 0...N-2做一个随机，然后把它交换到最后N-2位置。
            // 0...N-3做一个随机，然后把它交换到最后N-3位置。
            // ...
            // 全部都弄完，顺序就被彻底打乱了
            for (int i = nowArr.length - 1; i >= 0; i--) {
                // 随机生成0~i位置的下标
                int index = (int) (Math.random() * (i + 1));
                // 将i和index位置的数交换
                swap(nowArr, index, i);
            }

            return nowArr;
        }
        // 交换
        public void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
