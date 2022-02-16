package 新手班.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class ShowComparator2 {
    public static class MyComparator implements Comparator<Integer> {

        // 负，第一个参数在前
        // 正，第二个参数在前
        // 0, 谁放前都行
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2) {
                return 1;
            } else if (o1 > o2) {
                return -1;
            } else {
                return 0;
            }
        }

    }

    public static class Student {
        public String name;
        public int id;
        public int age;

        public Student(String name, int id, int age) {
            this.name = name;
            this.id = id;
            this.age = age;
        }
    }

    // 谁id大，谁放前！
    public static class IdComparator implements Comparator<Student> {

        // 如果返回负数，认为第一个参数应该排在前面
        // 如果返回正数，认为第二个参数应该排在前面
        // 如果返回0，认为谁放前面无所谓
        @Override
        public int compare(Student o1, Student o2) {
            if (o1.id < o2.id) {
                return 1;
            } else if (o2.id < o1.id) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        String str1 = "abc"; // 小   因为字典序a比b小，所以在判断第一个位置的时候，就直接得出abc比b后面也就不用再比较了
        String str2 = "b"; // 大
        /*
            string是按照字典序排序的，在字典中越靠前的越小，也就是a比b小，b比c小
             1、如何两个长度一样，就依次把每一位按照字典序排序
             2、如果两个长度不一样长，就把短的用0来不齐（0在字典序中是最小的），让它和长的一样长，然后再按照上面的方法依次按位比较
         */
        System.out.println(str1.compareTo(str2));

        // 这个叫做优先级队列，实际是一个堆，默认是小根堆。小根堆就是小数在前，大根堆就是大树在前。
        // 比如小根堆就是会把所有按照升序依次排列，小的在前，在弹出数据的时候先弹出小的，再弹出大的。可以把它看成是一个队列，但是这个队列是按照由小到大的顺序排列好的，小的在队列头部，优先弹出，大的在队列尾部，最后弹出
        // 在创建优先级对联的时候，可以直接将自定义的比较器传入构造方法，这样就可以实现自定义排序规则的优先级队列

        // 创建根据id排序的大根堆，也就是id大的放在堆的前面
        PriorityQueue<Student> heap = new PriorityQueue<>(new IdComparator());
        Student s1 = new Student("张三", 5, 27);
        Student s2 = new Student("李四", 1, 17);
        Student s3 = new Student("王五", 4, 29);
        Student s4 = new Student("赵六", 3, 9);
        Student s5 = new Student("左七", 2, 34);
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        System.out.println("=========");
        while (!heap.isEmpty()) {
            Student s = heap.poll();
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
    }
}
