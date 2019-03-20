package programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
Functional Interfaces

* a functional interface is an interface that specifies only one abstract method.
* there is an important feature related to lambda expressions called the method reference
  * a method reference provides a way to refer to a method without executing it. it relates to
  lambda expressions because it, too, requires a target type context that consists of a compatible
  functional interface. there are different types of method references
    * method references to static methods
      * To create a static method reference, use this general syntax
      * ClassName::methodName
      * the :: is a new separator that has been added to JDK 8 expressly for this purpose. this method reference
      * can be used anywhere in which is compatible with its target type
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
* Consumer<T> - Apply an operation on an object of type T. Its method is called accept().
* Supplier<T> - Return an object of type T. Its method is called get()
* Function<T, R> - Apply an operation to an object of type T and return the result as an object of type R. Its method is
called apply().
* Predicate<T> - Determine if an object of type T fulfills some constraint. Return a boolean value that indicates the outcome.
Its method is called test()
 */
interface MyNumber {
    int getValue();
}

// use a generic functional interface with lambda expressions
interface SomeFunc<T>{
    T func(T t);
}

class MyRandomNumber {
    public static String getRandom(String s){
        return new Random().nextInt() + "s: " + s;
    }

    public static <T> String getIndex(T t){
        return "1";
    }
}


public class Lambdaz {

    public static void main(String[] args){

        // Create a reference to a MyNumber instance
        MyNumber number;
        // use a lambda in an assignment context
        number = () -> 123;

        // call getValue(), which is implemented by the previously assigned lambda expression
//        System.out.println(number.getValue());

        // generic functional interface demo
        SomeFunc<String> reverse = (str) -> new StringBuilder(str).reverse().toString();
//        System.out.println(reverse.func("Hello World"));

        // passing lambda expressions as arguments
//        System.out.println(makeHappy((s -> s + " :)"), "Hello Jeff"));

        // using a static method in lambda expression
        String happyStr = makeHappy(MyRandomNumber::getRandom, "Hi");
//        System.out.println(happyStr);

        // MyNumberCompare Example
        class SillyNumber implements MyNumber {
            int n;

            public SillyNumber(int n) {
                this.n = n;
            }

            @Override
            public int getValue() {
                return n;
            }
        }
        SillyNumber n1 = new SillyNumber(1);
        SillyNumber n2 = new SillyNumber(2);
        SillyNumber n3 = new SillyNumber(3);
        List<SillyNumber> sillyNumbers = new ArrayList<>();
        sillyNumbers.add(n1); sillyNumbers.add(n2); sillyNumbers.add(n3);
        SillyNumber maxSillyNumber = Collections.max(sillyNumbers, (sn1, sn2) -> {
           return sn1.getValue() - sn2.getValue();
        });
        System.out.println(maxSillyNumber.getValue());


    }




    static String makeHappy(SomeFunc<String> someFunc, String s){
        return someFunc.func(s);
    }

}
