package programming;

import models.Person;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApi {

    public static void main(String[] args){
        //stream_example();
        //reduction_example();
//        parallel_stream_example();
//        map_example();
        createImmutableCollections();
    }

    public static void stream_example(){
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(7);
        myList.add(10);
        myList.add(1);
        myList.add(3);

        System.out.println("Original List: " + myList);

        // Obtain a stream to the arrayList
        Stream<Integer> myStream = myList.stream();

        // Obtain the min and max value
        Optional<Integer> minVal = myStream.min(Integer::compare);
        if (minVal.isPresent()) System.out.println("Min val: " + minVal.get());

        // Must obtain a new stream because previous call to min()
        // is a terminal operation that consumed the stream
        myStream = myList.stream();
        Optional<Integer> maxVal = myStream.max(Integer::compareTo);
        if (maxVal.isPresent()) System.out.println("Max val: "+ maxVal.get());

        // sort the stream by use of sorted
        Stream<Integer> sortedStream = myList.stream().sorted();

        // Display the sorted stream
        System.out.print("Sorted Stream:");
        sortedStream.forEach((n) -> System.out.print(n + " "));

        System.out.println();

        // Display only the odd values by use of filter()
        Stream<Integer> oddVals = myList.stream().sorted().filter((n) -> (n%2) == 1);
        System.out.println("Odd Values:");
        oddVals.forEach(o -> System.out.print(o + " "));

        System.out.println();

        // Display only the odd values greater than 5
        oddVals = myList.stream().filter(n -> (n%2) == 1 )
                .filter(o -> o > 5);
        System.out.println("Odd values greater than 5");
        oddVals.forEach(o -> System.out.print(o + " "));

        System.out.println();
    }

    /*
    count() - counts the number of elements in a stream
    reduce() - you can return a value from a stream based on any arbitrary criteria

    Stream defines two versions of reduce(). The two we will use first are shown here.
    * Optional<T> reduce(BinaryOperator<T> accumulator)
    * T reduce(T identityVal, BinaryOperator<T> accumulator)
    In both forms, accumulator is a function that operates on two values and produces a result.
    BinaryOperator is a functional interface that extends the BiFunction functional interface.
    BiFunction defines the abstract method:
    * R apply(T val, U val2)
    As it relates to reduce(), val will contain the previous result and val2 will contain the next
    element. In its first invocation, val will contain either the identity value or the first element,
    depending on which version of reduce() is used.

    Accumulator operation must satify three constraints
    1. Stateless
    2. Non-interfering
    3. Associative
     */
    public static void reduction_example() {
        Person p1 = new Person("Jeff", 23);
        Person p2 = new Person("Zen", 21);
        Person p3 = new Person("Buzz", 6);
        Person p4 = new Person("Boots", 1);
        Person p5 = new Person("Zalma", 20);

        ArrayList<Person> family = new ArrayList<>();
        family.add(p1);
        family.add(p2);
        family.add(p3);
        family.add(p4);
        family.add(p5);

        // using second version of reduce
        int count = family.stream().map(Person::getAge)
                .reduce(0, (age1, age2) -> {
                    if (age2 >= 20)
                        return age1 += 1;
                    return age1;
                });

        System.out.println("Number of family members older than 20: " + count);
    }

    /*
    Ways to obtain a parallel stream
    1. use parallelStream() defined in collection
    2. call parallel() method on a sequential stream

    When using parallel streams, you might find the following version of reduce especially helpful.
    It gives you a way to specify how partial results are combined:
    * <U> U reduce(U identityVal, BiFunction<U, ? super T, U>accumulator, BinaryOperator<U> combiner)
    In this version, combiner defines the function that combines two values that have been produced by the
    accumulator function.

    You can switch a parallel stream to sequential by calling the sequential() method. In general, a stream
    can be switched between parallel and sequential on an as-needed basis.

    When using a parallel stream, a performance boost can sometimes be obtained by allowing a stream to be
    unordered. In cases in which the order of the operations does not matter, it is possible to specify unordered
    behavior by calling the unordered() method.

    One other point: the forEach() method may not preserve the ordering of a parallel stream. If you want to perform
    an operation on each element in a parallel stream while preserving the order, consider using the forEachOrdered().
    It is used just like forEach().
     */
    public static void parallel_stream_example(){
        Double numbers[] = {2.0, 3.0, 4.0};
        List<Double> n = Arrays.asList(numbers);
        Optional<Double> sum = n.parallelStream().reduce((a, b) -> a + b);
        if (sum.isPresent()) System.out.println("Sum of numbers is: " + sum.get());

        double parallelProduct = n.parallelStream().reduce(1.0, (a, b) -> a * b);
        System.out.println("Parallel product of numbers: "+parallelProduct);

        double parallelProduct2 = n.parallelStream().reduce(1.0, (a, b) -> {
            System.out.println(String.format("[Debug] in accumulator function a:%.1f b:%.1f", a, b));
            return a * b;
        }, (a, b) -> {
            System.out.println(String.format("[Debug] in combiner function a:%.1f b:%.1f", a, b));
            return a * b;
        });
        System.out.println("Parallel product using accumulator and combiner: " + parallelProduct2);

        /* Computes the product of the square roots for numbers in n
           Notice that the accumulator function multiplies the square roots of the two elements, but the
           combiner multiplies the partial results. Thus, the two functions differ. Moreover, for this
           computation to work correctly, they must differ.
           */
        double productOfSqrRoots = n.parallelStream().reduce(
                1.0,
                (a, b) -> a * Math.sqrt(b),
                (a, b) -> a * b
        );


    }

    public static void map_example(){
        Person p1 = new Person("Jeff", 23);
        Person p2 = new Person("Buzz", 6);
        Person p3 = new Person("Boots", 1);
        Person p4 = new Person("Billie", 1);
        Person p5 = new Person("Zen", 21);
        Person p6 = new Person("Eduarda", 15);

        Person family[] = {p1, p2, p3, p4, p5, p6};
        List<Person> familyList = Arrays.asList(family);

        /*
        Collecting
        To obtain a collection from a stream the Stream API provides the collect() method.
        The toList() method returns a collector that can be used to collect elements into a list.
        The toSet() method returns a collector that can be used to collect elements into a Set.
         */

        List<String> names = familyList.stream().map((person) -> person.getName()).collect(Collectors.toList());
        System.out.println("Names: " + names);

        // using iterators
        Iterator<String> namesIterator = familyList.stream().map(person -> person.getName()).iterator();
        while (namesIterator.hasNext())
            System.out.println(namesIterator.next());

        /*
        Spliterator
        In some cases, you might want to perform some action on each element collectively, rather than one at
        a time. To handle this type of situation, Spliterator provides the forEachRemaining() method.
         */
        System.out.println("\nUsing Spliterator\n");
        Spliterator<Person> familySpliterator = familyList.stream().spliterator();
        while (familySpliterator.tryAdvance(f -> System.out.println(f.getName())));


    }

    private static void createImmutableCollections(){
        // creating immutable collections from streams
        // Java 10 makes it easier to create immutable collections from stream operations, with the addition of the
        // toUnmodifiableList, toUnmodifiableSet, and toUnmodifiableMap method on Collectors

        // ex: we have a stream operation that takes a sentence and turns it into a list of unique words
        Pattern p = Pattern.compile("\\s*[^\\p{IsAlphabetic}]");
        String message = "Java java is fun";
        List<String> uniqueWords = p.splitAsStream(message)
                .map(String::toLowerCase).distinct().collect(Collectors.toUnmodifiableList());

        // using an immutable map
        // ex: also calculates the number of times a word appears
        Map<String, Integer> wordCount = p.splitAsStream(message).map(String::toLowerCase)
                .collect(Collectors.toUnmodifiableMap(Function
                        .identity(), word -> 1, (oldCount, newVal) -> oldCount + newVal));

        for(var e: wordCount.entrySet()){
            System.out.println(String.format("word %s, count %s", e.getKey(), e.getValue()));
        }

    }

}
