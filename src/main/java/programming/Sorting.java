package programming;

import models.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.Comparator.comparing;

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

    public static void sortWithLambdaExpression(List<Student> students) {
        students.sort((std1, std2) -> std1.getFullName().compareTo(std2.getFullName()));
        System.out.println("Students sorted by name");
        students.forEach(s -> System.out.println(s.getFullName()));

        /*
        Can we make our code even more readable? Yes, Comparator has a static helper method called, comparing, that takes
        a function extracting a Comparable key and produces a Comparator object
         */
        students.sort(comparing((std) -> std.getFullName()));

        // We can use method references to make our code slightly less verbose
        students.sort(comparing(Student::getFullName));

    }
}