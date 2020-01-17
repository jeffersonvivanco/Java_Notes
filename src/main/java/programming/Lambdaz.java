package programming;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
Lambda Expression - can be used where a functional interface is expected: a functional interface is an interface defining
only one abstract method.

* The signature of the abstract method (called function descriptor) can describe the signature of a lambda expression.
  For example, the Comparator interface represents a function descriptor
  (T, T) -> int

Example with sort: students.sort((std1, std2) -> std1.getFullName().compareTo(std2.getFullName()));
Look at Sorting.java for example

* Java's compiler could infer the types of parameters of a lambda expression by using the context in which the lambda appears.
  So, we can avoid the use of types parameters.

* We can use a method reference to make our code slightly less verbose: studentlist.sort(comparing(Student::getAge));

Functional Interfaces

* a functional interface is an interface that specifies only one abstract method.
* there is an important feature related to lambda expressions called the method reference
  * a method reference provides a way to refer to a method without executing it. it relates to
  lambda expressions because it, too, requires a target type context that consists of a compatible
  functional interface. there are different types of method references
    * method references to static methods
      * To create a static method reference, use this general syntax: ClassName::methodName
      * the :: is a new separator that has been added to JDK 8 expressly for this purpose. this method reference
        can be used anywhere in which is compatible with its target type
    * method references to instance methods
      * to pass a reference to an instance method on a specific object, use this basic syntax: objRef::methodName
      * you can refer to the superclass version of a method by use of super, as shown here: super::name
      * the name of the method is specified by name
    * method references with Generics
      * you can use method references with generic classes and/or generic methods.
      * when a generic method is specified as a method reference, its type argument comes after the :: and before
      the method name. In cases in which a generic class is specified, the type argument follows the class name and
      precedes the ::.
  * One place method references can be quite useful is in conjunction with the Collections framework. Look at MyNumberCompare

Predefined Functional Interfaces
* UnaryOperator<T> - Apply a unary operation to an object of type T and return the result, which is also of type
T. Its method is called apply()

* BinaryOperator<T> - Apply an operation to two objects of type T and return the result, which is also of type T. Its
method is called apply().

* DoubleBinaryOperator - represents an operation upon two double-valued operands and producing a double-valued result.

* Consumer<T> - Apply an operation on an object of type T. It doesn't return anything when called. This functional interface
  has two methods, void accept(T t) that triggers, which triggers the interface, and andThen(Consumer<T>), which lets you
  chain multiple Consumers. All Consumers in this chaining will use the same input given via accept().

* BiConsumer<T,U> - Represents an operation that accepts two input arguments and returns no results.

* DoubleConsumer - Represents an operation that accepts a single double-valued argument and returns no result.

* Supplier<T> - Until now, we have talked about the interfaces that take input parameters. Supplier on the other hand, does
  not demand any arguments; it just produces one by calling get(), similar to methods that produce values without input, like
  toString() or hashCode(). We can't use chaining in this since it doesn't take input parameters.

* BooleanSupplier - represents a supplier of boolean-valued results

* Function<T, R> - Apply an operation to an object of type T and return the result as an object of type R. Function uses
  apply(T t) for its functions and andThen(anotherFunction) for chaining. But dissimilar to Consumer and Predicate, this
  chaining does not use the same value to all the Functions. Instead they use the result of the preceding Function. However,
  keep in mind that the subsequent Function has the input type equivalent to the current Function's return type.

* BiFunction<T,U,V> - Similar to Function, except this accepts two input arguments as opposed to Function, which only handles
  one input. It uses apply(T t, U u) for triggering. Chaining in this uses Function instead of BiFunction in andThen(Function<V v,U u),
  since methods don't have two return values.

* Predicate<T> - Determine if an object of type T fulfills some constraint. It does this via the boolean test(T t). You
  can use chaining with and(anotherPredicate). When calling test(T t) in the end, we will complete the chain. Similar to
  Consumer, it uses the same input throughout the chain. It returns true only if all predicates return true else false.

* BiPredicate<T,U> - represents a predicate (boolean-valued function) of two arguments.
 */

interface MyNumberType {
    Integer transform(Integer n1);

    default MyNumberType andThen(MyNumberType after) {
        Objects.requireNonNull(after);
        return (nt) -> after.transform(transform(nt));
    }
}

// use a generic functional interface with lambda expressions
interface SomeFunc<T>{
    T func(T t);
}

public class Lambdaz {

    public static void main(String[] args){

        // generic functional interface demo
        SomeFunc<String> reverse = (str) -> new StringBuilder(str).reverse().toString();
//        System.out.println(reverse.func("Hello World"));

        // passing lambda expressions as arguments
//        System.out.println(makeHappy((s -> s + " :)"), "Hello Jeff"));

        MyNumberType doubleNum = (nt) -> nt * 2;
        MyNumberType addOneToNum = (nt)-> nt + 1;
        System.out.printf("2 doubled is %d, 2 + 1 is %d", doubleNum.transform(2), addOneToNum.transform(2));
        System.out.printf("\n2 doubled + 1 is %d", doubleNum.andThen(addOneToNum).transform(2));

//        consumerDemo();
//        predicateDemo();
//        functionDemo();
//        supplierDemo();

    }

    static String makeHappy(SomeFunc<String> someFunc, String s){
        return someFunc.func(s);
    }

    static void consumerDemo() {
        // define the consumer which consumes the age to print it
        Consumer<Integer> printAgeConsumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("Age is " + integer);
            }
        };
        // call the method
        printAgeConsumer.andThen(age -> System.out.println("Again age is " + age)).accept(25);

        // using lambda
        Consumer<Integer> printAgeWithLambda = (age) -> System.out.println("Age with lambda is " + age);
        printAgeWithLambda.andThen(age -> System.out.println("Again age with lambda us " + age))
                .accept(23); // this value will be given to each consumer
    }

    static void predicateDemo() {
        Predicate<Integer> checkAgePredicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer >= 21;
            }
        };
        String message = checkAgePredicate.test(23) ? "Give him/her a modelo please" : "Tell him/her to go home";
        System.out.println(message);

        // using lambda
        Predicate<Integer> isEvenLambda = num -> num % 2 == 0;
        System.out.println("Is 5 even? " + isEvenLambda.test(5));
    }

    static void functionDemo() {
        Function<Integer, Integer> doubleTheNumber = (n1) -> 2 * n1;
        System.out.printf("2 doubled, %s", doubleTheNumber.andThen((n1) -> n1 + " is the answer").apply(2));
    }

    static void supplierDemo() {
        Supplier<String> greeting = () -> "Hello World";
        System.out.printf("Greeting: %s", greeting.get());
    }

}
