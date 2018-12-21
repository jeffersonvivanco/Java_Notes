package programming;

import java.util.Random;

/*
Functional Interfaces

* a functional interface is an interface that specifies only one abstract method.
* there is an important feature related to lambda expressions called the method reference
  * a method reference provides a way to refer to a method without executing it. it relates to
  * lambda expressions because it, too, requires a target type context that consists of a compatible
  * functional interface. there are different types of method references
    * method references to static methods
      * To create a static method reference, use this general syntax
      * ClassName::methodName
      * the :: is a new seperator that has been added to JDK 8 expressly for this purpose. this method reference
      * can be used anywhere in which is compatible with its target type
    * method references to instance methods
      * to pass a reference to an instance method on a specific object, use this basic syntax: objRef::methodName
      * you can refer to the superclass version of a method by use of super, as shown here: super::name
      * the name of the method is specified by name
    * method references with Generics
      * you can use method references with generic classes and/or generic methods.
 */
interface MyNumber {
    double getValue();
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
        number = () -> 123.35;

        // call getValue(), which is implemented by the previously assigned lambda expression
        System.out.println(number.getValue());

        // generic functional interface demo
        SomeFunc<String> reverse = (str) -> new StringBuilder(str).reverse().toString();
        System.out.println(reverse.func("Hello World"));

        // passing lambda expressions as arguments
        System.out.println(makeHappy((s -> s + " :)"), null));

        // using a static method in lambda expression
        String happyStr = makeHappy(MyRandomNumber::getRandom, "");
        System.out.println(happyStr);

    }

    static String makeHappy(SomeFunc<String> someFunc, String s){
        return someFunc.func(s);
    }

}
