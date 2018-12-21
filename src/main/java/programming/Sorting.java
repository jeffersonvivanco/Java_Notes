package programming;

import models.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorting {

    static final Comparator<Student> AGE_ORDER = new Comparator<Student>() {
        @Override
        public int compare(Student o1, Student o2) {
            int cmp1 = o1.getFullName().compareTo(o2.getFullName());
            if(cmp1 != 0){
                return cmp1;
            }
            return Integer.compare(o1.getAge(), o2.getAge());
        }
    };

    public static void main(String[] args){
        var student1 = new Student("Jefferson Vivanco", 30);
        var student2 = new Student("Zenyase Vivanco", 21);
        var student3 = new Student("Lourdes M Vivanco", 17);
        var student4 = new Student("Jefferson Vivanco", 23);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        Collections.sort(students, AGE_ORDER);
        System.out.println(students);

    }
}