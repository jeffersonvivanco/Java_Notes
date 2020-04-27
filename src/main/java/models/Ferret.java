package models;

/*
Records

* Records are data classes with far less boilerplate than we normally have in Java
* this class automatically has a constructor, getters, toString, hashCode, and equals
* constructors
  * canonical - the usual Java constructor
  * compact - if you are doing simple things with the constructor, you can use this one
    as shown below
* What you can do?
  * can implement interfaces
  * add static fields and instance or static methods
* What you can't do?
  * it cannot be defined as an abstract class, because it is implicitly a final class
  * it cannot extend another class since it already extends the java.lang.Record class
  * you can't add any instance variables to it--the state of the record is defined by
    the components you have in its definition
* You can also add annotations like @NotNull
* File / IO
  * implement Serializable
 */
public record Ferret(String name, int age) {
    public Ferret {
        validateFields(name, age);
    }
    private void validateFields(String name, int age) {
        // validation logic goes here
    }
}
