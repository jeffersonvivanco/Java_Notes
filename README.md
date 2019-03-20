# Java Notes
My notes from reading the book Java Cookbook by Ian F. Darwin and my some of my own notes.

## JVM Memory Model
* `-xmx` - defines max heap size of your JVM
* If you set `xmx` to 2GB, will your java process (application memory) go beyond 2GB?
  * Yes
  * Java Heap has 2 regions, *young generation* and *old generation*
    * When you create a new object, it gets created in young generation
    * If the object is long lived, it will get promoted to old generation
    * Most objects are short lived objects. Short lived objects get created in young generation region and die there too
  * `- xmx` defines size for young generation and old generation **alone**
  * In addition to old generation and new generation, there is also *metaspace*
    * In *metaspace*, the JVM stores the metadata information to run the application, like class definitions, method
    definitions,
    * Size can be defined by max-metaspace
  * There's an additional space called *others*
    * A Java application creates a lot of threads to process the incoming records which are stored in *others*
      * threads take up memory
      * you can define your thread size (size of one thread) via this property `- xss`
      * For example, lets say you define `- xss` to be 1mb, and your application creates 200 threads, then this adds 200mb
      to your overall application memory
    * Garbage collection is also stored in *others*
    * A Java process opens a lot of socket buffers for inbound connections also stored in *others*
    * File descriptors (opening a file) also need memory, also stored here in *others*
    * There is no parameter to define the size of *others* region
  * To conclude, when you define `xmx`, you are only defining the space for young generation and old generation. There is
  also metaspace and others, which add memory on top of the 2GB that you defined.

## Compiling, Running, and Debugging

### Avoiding the need for debuggers with Unit Testing
* To avoid constantly debugging your code, use unit testing to validate each class as you develop it.
* Typically, in an OO language like Java, unit testing is applied to individual classes, in contrast to "system" or "integration" testing where the entire application is tested.
* Developers of the software methodology known as Extreme Programming (XP for short) advocate "Test Driven Development"(TDD): writing the unit tests before you write the code. They also advocate running your tests almost every time you build your application.

### Maintaining your code with Continuous Integration
* To be sure that your entire code base compiles and passes its tests periodically, use a Continuous Integration server such as Jenkins/Hudson.
* CI is simply the practice of having all developers on a project periodically integrate their changes into a single master copy of the project's "source". This might be a few times a day, or every few days, but should not be more than that, else the integration will likely run into larger hurdles where multiple developers have modified the same file.
* And it's not just code-based projects that benefit from CI. If you have a number of small websites, putting them all under CI control is one of the several important steps toward developing an automated, "dev-ops" culture around website deployment and management.

## Dates and Times
Look at programming.DatesAndTime
### New Date/Time API Common Methods
| Name | Description |
| ---  | --- |
| at | Combines with another object |
| format | Use provided formatter to produce a formatted string |
| from | Factory: Convert input parameters to instance of target |
| get | Retrieve one field from the instance |
| is | Examine the state of the given object |
| minus | Return a copy with the given amount subtracted |
| of | Factory: Create new method by parsing inputs |
| parse | Factory: Parse single input string to produce instance of target |
| 

## Strings and Things
* Strings in Java are immutable. If you need to change characters within a String, you should instead create a StringBuilder (possibly initialized to the starting value of the String), manipulate the StringBuilder to your heart's content and then convert that to String at the end, using the ubiquitous toString() method.
  * It may be possible to tinker with the String's internal data structures using the Reflection API. Secured environments, of course, do not permit access to the Reflection API.
  
### Breaking strings into words
* To accomplish this, construct a StringTokenizer around your and call its methods hasMoreTokens() and nextToken().
* Or use regular expressions. ====> see /javanotes/StringNotes
* Many occurrences of StringTokenizer may be replaced with regular expressions with considerably more flexibility.

### Putting strings together with StringBuilder
* Use string concatenation: the + operator. The compiler implicitly constructs a StringBuilder for you and uses its append() methods. You can also construct a
StringBuilder yourself.
* StringBuilder and StringBuffer are identical, except for the fact that StringBuilder is not threadsafe

### Processing a String one character at a time
* Use a for loop and the String's charAt() method. Or a "for each" loop and the String's toCharArray method.

### Reverse a String by Word or by Character
* You can reverse a string by character easily, using a StringBuilder and calling the reverse(). ==> look at /javanotes/StringNotes

### Regex Processing
* regex - is a string of characters that describes a character sequence. This general description called a pattern can then be used to find matches in other character sequences. 
* Use the **Pattern** class to define a regular expression.
* Match the pattern against another sequence using **Matcher**.
* The simplest pattern matching method is **matches()**. For there to be a match, the entire character sequence must match the pattern, not just a subsequence of it. If it matches, matches() returns true, otherwise false.
* To determine if a subsequence of the input sequence matches the pattern, use **find()**. It returns true if there is a matching subsequence and false otherwise. This method can be called repeatedly allowing it to find all matching subsequences. Each call to find() begins where the previous one left off. 
* You can obtain a string containing the last matching sequence by calling **group()**.
* If no matching exists, then an **IllegalStateException** is thrown.
* You can replace all occurrences of a matching sequence with another sequence by calling **replaceAll()**.
* example, parsing the file name to get the extension
* <pre>Pattern pat = Pattern.compile("(\\.[^.]+)$");
  Matcher mat = pat.matcher("today.jpeg");
  // To get the extension first call find then group
  if(mat.find()) // if it finds an extension
    System.out.println(mat.group())
  else
    System.out.println("NO MATCH");
  </pre>
  
  
## Object Oriented Techniques
* Advice, or Mantras
  * Use the API
    * Exceptions
      * the `clone()` method in `java.lang.Object` should generally not be used. If you need to copy an object, just
      write a copy method or a "copy constructor".
      * Also, the `finalize()` method in java.lang.Object, dont use it. It isn't guaranteed to be invoked, but because
      it might get invoked, it will cause your dead objects not to be garbage collected, resulting in a memory leak. If
      you need some kind of cleanup, you must take responsibility for defining a method and invoking it before you let
      any object of that class go out of reference.
  * Generalize
    * There is a trade-off between generality (and the resulting reusability), which is emphasized here, and the convenience
    of application specificity. If you're writing one small part of a very large application designed according to OO design
    techniques, you'll have in mind a specific set of use cases. On the other hand, if you're writing "toolkit-style" code,
    you should write classes with few assumptions about how they'll be used. Making code easy to use from a variety of programs
    is the route to writing reusable code.
  * Read and Write Javadoc
  * Use subclassing and delegation
    * Use subclassing. But don't overuse subclassing.
    * There are several alternatives. One alternative to subclassing is delegation. Think about "is-a" versus "has-a". 
    For example, instead of subclassing NameAndAddress to make Supplier and Customer, make Supplier and Customer have
    instances of NameAndAddress. That is a clearer structure; having a supplier be a NameAndAddress just because a 
    supplier has a name and address would not make sense. And delegation also makes it easier for a Customer to have
    both a billing address and a shipping address.
    * Another alternative is aspect-oriented-programming (AOP), which allows you to "bolt on" extra functionality from the
    outside of your classes. AOP is provided by the Java EE using EJB Interception, and by the Spring Framework AOP mechanism.
  * Using Design Patterns
    * Read Design Patterns (Addison Wesley)
  
  
## Functional Programming Techniques: Functional Interfaces, Streams, Parallel Collections
* Functional Programming
  * ... a programming paradigm, a style of building the structure and elements of computer programs, that treats computation
  as the evaluation of mathematical functions and avoids state and mutable data.
  * Functional programming emphasizes functions that produce results that depend only on their inputs and not on the program
  state - i.e. pure mathematical functions.
  * It is a declarative programming paradigm, which means programming is done with expressions.

* Some features of FP include
  * Pure functions; having no side-effects and whose results depend only on their inputs and not on mutable state elsewhere
  in the program.
  * First-class functions (e.g. functions as data)
  * Immutable data
  * Extensive use of recursion and lazy evaluation
  * "Functions as data" means that you can create an object that is a function, pass into another function, write a function
  that returns another function, and so on--with no special syntax, because well, functions are data.
* One of Java 8's approach to FP is the definition of functional interfaces. Look at programming/Lambdaz.java
* Also new in Java 8, interfaces can have methods annotated with the new-in-this-context default keyword.
  * A default method in an interface becomes available for use in any class that implements the interface; you'll see that
  such methods cannot depend on instance state in a particular class because they would have no way of referring to it at
  compile time.
  * So a functional interface is more precisely defined as one that has a single nondefault method.
  
### Using Lambdas/Closures Instead of Inner Classes
* You want to avoid all the typing that even the anonymous style of inner classes requires. Use Java's lambda expressions.
===> look at programming/FunctionalProgramming.java

### Using Lambda Predefined Interfaces Instead of your Own
* You want to use existing interfaces, instead of defining your own, for use with Lambdas. Use the Java 8 lambda Functional
interfaces from java.util.function.

