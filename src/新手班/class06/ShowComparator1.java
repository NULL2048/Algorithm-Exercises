package 新手班.class06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ShowComparator1 {

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

    /*
        比较器是一种可以非常方便我们自定义排下序规则的工具

        我们可以自己定义实现Comparator接口的比较器类，然后在使用比较方法或者创建集合的时候将其传入
        这样就能根据我们制定的排序规则来进行排序了

        比较器有两种，一种是Comparator接口，还有一种是Comparable接口
        这两个的区别就是Comparator接口可以不改变原有代码，就能实现自定义的排序规则
        而Comparable必须使要排序的类继承这个接口，然后再对该类进行相关修改才能实现自定义排序规则

        Comparator接口的侵入性更低，所以我们一般都用这个。
     */


    // 谁id大，谁放前！
    public static class IdComparator implements Comparator<Student> {

        // 下面就是compare方法的规则，不管里面怎么先，比较器只会根据compare返回的数来进行排序
        // 所以我们可以根据自己的需求编写代码，来决定什么情况下返回正数，什么情况下返回负数，什么情况下返回0

        /*
            如果返回负数，认为第一个参数应该排在前面
            如果返回正数，认为第二个参数应该排在前面
            如果返回0，认为谁放前面无所谓
         */
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

    // 谁age大，谁放前！
    public static class AgeComparator implements Comparator<Student> {

        // 下面就是compare方法的规则，不管里面怎么先，比较器只会根据compare返回的数来进行排序
        // 所以我们可以根据自己的需求编写代码，来决定什么情况下返回正数，什么情况下返回负数，什么情况下返回0

        /*
            如果返回负数，认为第一个参数应该排在前面
            如果返回正数，认为第二个参数应该排在前面
            如果返回0，认为谁放前面无所谓
         */
        @Override
        public int compare(Student o1, Student o2) {
            if (o1.age < o2.age) {
                return 1;
            } else if (o2.age < o1.age) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printStudents(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].name + ", " + students[i].id + ", " + students[i].age);
        }
    }

    public static void main(String[] args) {
        int[] arr = { 8, 1, 4, 1, 6, 8, 4, 1, 5, 8, 2, 3, 0 };
        printArray(arr);
        // 可以正常的进行排序
        Arrays.sort(arr);
        printArray(arr);

        Student s1 = new Student("张三", 5, 27);
        Student s2 = new Student("李四", 1, 17);
        Student s3 = new Student("王五", 4, 29);
        Student s4 = new Student("赵六", 3, 9);
        Student s5 = new Student("左七", 2, 34);

        // 数组
        Student[] students = { s1, s2, s3, s4, s5 };
        printStudents(students);

        System.out.println("=======");
        // students如果不传入比较器，是没有办法比较的，会报错。因为Student是自定义类，系统不知道其比较规则
        Arrays.sort(students, new IdComparator());
        printStudents(students);

        System.out.println("=======");

        ArrayList<Student> arrList = new ArrayList<>();
        arrList.add(s1);
        arrList.add(s2);
        arrList.add(s3);
        arrList.add(s4);
        arrList.add(s5);
        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
        System.out.println("=======");
        // 如果我们不用数字，而是用ArrayList的话，想要排序直接调用其sort方法就行，但是因为Student是自定义类，还是要传入比较器进去
        arrList.sort(new AgeComparator());
        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
    }
}
