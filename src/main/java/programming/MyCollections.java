package programming;

import models.Student;

import java.util.*;

public class MyCollections {

    public static void main(String[] args){
        asList();
    }

    private static void maps(){
        // ways to create immutable maps

        // works for any arbitrary number of key/value pairs
        Map<String, String> wordToMood = Map.ofEntries(
                Map.entry("happy", "HAPPY"),
                Map.entry("sad", "SAD")
        );

        // for 10 values of less
        Map<String, String> wordToMood2 = Map.of(
                "good", "GOOD",
                "great", "great"
        );

        // copy of immutable map
        Map<String, String> copyOfMoods = Map.copyOf(wordToMood);
    }

    public static void twoDArray(){
        // two dimensional array 10 X 24
        int[][] me = new int[10][];
        for (int i=0; i<10; i ++){
            me[i] = new int[24];
        }
    }

    private static void asList(){
        List<String> names = Arrays.asList("Jeff", "Zen");
        List<String> singleName = Collections.singletonList("Jeff");

        // ways to create immutable collections

        // Au contraire to Arrays.asList(), below is immutable
        List<String> moods = List.of("Happy", "Sad");

        // to create a copy of an immutable list, and the copy be immutable as well
        List<String> moodsCopy = List.copyOf(moods);
        System.out.println(moodsCopy.get(0));
    }

    private static void sets(){
        Set<String> animals = new HashSet<>();
        animals.add("lion");
        animals.add("zebra");

        SortedSet<String> names = new TreeSet<>();
        names.add("Zen");
        names.add("Jeff");
        names.add("Buzz");
        names.add("Edwarda");

        names.remove("Zen");

//        names.forEach(n -> System.out.println(n));

        Student student = new Student("Jeff", 23);
        Student student1 = new Student("Zen", 21);
        Student student2 = new Student("Zalma", 20);

        Set<Student> students = new HashSet<>();
        students.add(student); students.add(student1); students.add(student2);

        students.forEach(s -> System.out.println(s.getFullName()));

        // ways to create immutable sets

        Set<String> moods = Set.of("Happy", "Sad");

        // create copy of immutable sets
        Set<String> moodsCopy = Set.copyOf(moods);

    }

    private static void myQueues(){
        Deque<Student> studentArrayDeque = new ArrayDeque<>();
        Student student = new Student("Jeff", 23);
        Student student1 = new Student("Zen", 21);
        Student student2 = new Student("Zalma", 20);

        studentArrayDeque.push(student); studentArrayDeque.push(student1); studentArrayDeque.push(student2);

        studentArrayDeque.removeIf(s -> s.getFullName().equals("Jeff"));

        studentArrayDeque.forEach(t -> System.out.println(t.getFullName()));
    }
}
