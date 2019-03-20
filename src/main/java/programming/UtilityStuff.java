package programming;

import models.Student;


import java.util.Optional;
import java.util.StringTokenizer;

public class UtilityStuff {

    public static void main(String[] args) throws MyException {
        optionalStuff();
    }

    public static void stringTokenizer(){
        String names = "name: Jefferson, name: Zen";
        StringTokenizer st = new StringTokenizer(names, ":,");
        while (st.hasMoreTokens()){
            System.out.println(st.nextToken() + " : " + st.nextToken());
        }
    }

    public static void optionalStuff() throws MyException {
        // Optional type was not intended as a "general purpose [Maybe...] type" but a way to let libraries and APIs
        // express the absence of a return type
        // But can be used to check for null

        // methods
        Optional<Student> s1 = Optional.empty(); // returns an object for which isPresent returns false
        System.out.println(s1.isPresent());

        Optional<Student> smartestStudent = Optional.of(new Student("Zen", 23));
        /*
        Returns an Optional instance that contains the same value as the invoking object if that value satisfies
        a condition. Otherwise, an empty object is returned.
         */
        smartestStudent = smartestStudent.filter(student -> student.getFullName().equals("Jeff"));
        System.out.println(smartestStudent.isPresent());

        /*
        If the invoking object contains a value, the value is returned. Otherwise, the value in orElse() is returned
         */
        Student s2 = smartestStudent.orElse(new Student("Mitch", 18));
        System.out.println(s2.getFullName());


        Student s3 = null;
        /*
        Returns the value in the invoking object. However if no value is present, the exception is thrown.
         */
        Student s4 = Optional.ofNullable(s3).orElseThrow(MyException::new);
    }


}
