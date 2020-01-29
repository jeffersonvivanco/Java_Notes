# Java Notes
My notes from reading the book Java Cookbook by Ian F. Darwin and my some of my own notes.

## The History and Evolution of Java
* Computer language innovation and development occurs for two fundamental reasons:
  1. To adapt to changing environments and uses
  2. To implement refinements and improvements in the art of programming
* Java's Lineage
  * The birth of modern programming: C
    * The creation of C was a direct result of the need for a structured, efficient, high-level language that could replace
    assembly code when creating systems programs.
    * As you probably know, when a computer language is designed, trade-offs are often made, such as the following:
      * Ease-of-use versus power
      * Safety versus efficiency
      * Rigidity versus extensibility
    * Prior to C, programmers usually had to choose between languages that optimized one set of traits or the other. For
    example, although FORTRAN could be used to write fairly efficient programs for scientific applications, it was not very
    good for system code. And while BASIC was easy to learn, it wasn't very powerful, and its lack of structure made its 
    usefulness questionable for large programs. Assembly language can be used to produce highly efficient programs, but it's
    not easy to learn or use effectively. Further, debugging assembly code can be quite difficult. 
    * Another compounding problem was that early computer languages such as BASIC, COBOL, and FORTRAN were not designed around
    structured principals. Instead, they relied upon the GOTO as a primary means of program control. As a result, programs
    written using these languages tended to produce "spaghetti code"--a mass of tangled jumps and conditional branches that
    make a program virtually impossible to understand. While languages like Pascal are structured, they were not designed
    for efficiency, and failed to include certain features necessary to make them applicable to a wide range of programs.
    (Specifically, given the standard dialects of Pascal available at the time, it was not practical to consider using Pascal
    for systems-level code.)
  * C++: The next step
    * During the late 70s and early 80s, C became the dominant computer programming language, and it's still widely used today.
    Since C is a successful and useful language, you might ask why a need for something else existed. The answer is *complexity*.
    * C++ is a response to that need.
    * The 1960s gave birth to *structured programming*. This is the method of programming championed by languages such as C. The
    use of structured languages enabled programmers to write, for the first time, moderately complex programs fairly easily. However,
    even with structured programming methods, once a project reaches a certain size, its complexity exceeds what a programmer can
    manage. By the early 1980s, many projects were pushing the structured approach past its limits. To solve this problem, a new way
    to program was invented, called *object oriented programming (OOP)*.
    * In the final analysis, although C is one of the world's great programming languages, there's a limit to its ability to
    handle complexity. Once the size of the program exceeds a certain point, it becomes so complex that is difficult to grasp
    as a totality. While the precise size at which this occurs differs, depending upon both the nature of the program and the
    programmer, there is always a threshold at which a program becomes unmanageable. C++ added features that enabled this threshold
    to be broken, allowing programmers to comprehend and manage larger programs.
* The creation of Java
  * The language was initially called "Oak," but was renamed "Java" in 1995.

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
  
* How much memory does a Java thread take?
  * A memory which is taken by all Java threads, is a significant part of the total memory consumption your application.
  * There are a few techniques on how to limit the number of created threads, depending on whether your application is
  CPU-bound or IO-bound. If your application is rather IO-bound, you will very likely need to create a thread pool with
  a significant number of threads which can be bound to some IO operations (in blocked/waiting state, reading from DB,
  sending HTTP request).
  * However, if your app rather spends time on some computing task, you can, for instance, use HTTP server (ex Netty) with a lower
  number of threads and save a lot of memory.
  * To see the memory settings in java use this 
  `java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version`
  * We can see two types of memory:
    * Reserved - the size which is guaranteed to be available by a host's OS (but still not allocated and cannot be accessed
    by JVM) - it's just a promise.
    * Committed - already taken, accessible, and allocated by JVM
  * In the section `Thread`, we can spot the same number in Reserved and Committed memory, which is very close to a
  `number of threads * 1MB`. The reason is that the JVM aggressively allocates the maximum available memory for threads
  from the very beginning.
  
* Java Garbage Collection
  * Java manages heap memory for you. You run your application inside a virtual machine (JVM). The JVM does the work for
  of allocating space when necessary and freeing it when it is no longer needed. The garbage collector (GC) is what does
  this work for you.
  * **Recycling memory involves garbage collection "cycles" that have an impact on performance.** How much impact depends
  on the nature of your application and the GC you choose. 
  * New Garbage Collectors - the last releases of Java introduced 3 new GCs. Java 11 introduced Epsilon and the Z garbage
  collector (ZGC). Java 12 adds the Shenandoah garbage collector.
    * Epsilon is the "no-op" garbage collector. It allocates new memory but never recycles it. When your application exhausts
    the Java heap, the JVM shuts down. If you need to squeeze every bit of performance out of your application, Epsilon
    might be your best option for a GC. But you need to have a complete understanding of how your code uses memory. If it
    creates almost no garbage or you know exactly how much memory it uses for the period it runs in, Epsilon is a viable
    option.
    * ZGC promises to manage vast amounts of memory with high throughput and short pause times. ZGC is a low-latency GC
    designed to work well with huge amounts of memory. The Oracle documentation refers to multi-terabyte heaps in its
    description of Z. It's only available on 64-bit linux.
      * How does ZGC work?
        * ZGC works concurrently with your application, performing all of its work in threads. It uses load barriers for heap
        references. Load barriers cause fewer delays than those imposed by the G1 collector's pre-and-post-write barriers.
        * ZGC takes advantage of 64-bit pointers with a technique called pointer coloring. Colored pointers store extra 
        information about objects on the heap. (This is one of the reasons it's limited to the 64-bit JVM.) By limiting the
        GC to 4TB heaps, the developers have 22 extra bits in each pointer to encode additional information. Z uses 4 extra
        bits at the moment. Each pointer has a bit for `finalizable`, `remapped`, `mark0`, or `mark1`.
        * ZGC remaps objects when memory becomes fragmented. The mapping avoids the performance hit incurred when the GC needs
        to find space for a new allocation. Pointer coloring helps with remapping since a remapped reference discovers the new
        location at the next access.
        * When your application loads a reference from the heap, ZGC checks the extra bits. If it needs to do any extra work
        (e.g., getting a remapped instance), it handles it in the load barrier. It only has to do this once, when it loads the
        reference. This sets it apart from the write barriers used by mainline garbage collectors like G1.
        * ZGC performs its cycles in its threads. It pauses the application for an average of 1 ms. The G1 and Parallel collectors
        average roughly 200ms.
      * How to use ZGC?
      
        | COMMAND LINE OPTIONS | NOTES |
        | --- | --- |
        | `-XX:+UnlockExperimentalVMOptions` | Unlock Java experimental options |
        | `-XX:+UseZGC` | Use ZGC |
        | `-XmxXg` | Set heap size |
        | `-XX:ConcGCThreads=X` | Set number of GC threads |
        * ZGC is a concurrent garbage collector, so setting the right heap size is very important. The heap must be large
        enough to accomodate your application but also needs extra headroom so Z can meet new requests while relocating
        active objects. The amount of headroom you need depends on how quickly your application requests new memory.
        * ZGC will try to set the number of threads itself, and it's usually right. But if ZGC has too many threads, it 
        will starve your application. If it doesn't have enough, you'll create garbage faster than the GC can collect it.
      * Why use ZGC?
        * ZGC's design works well with applications with large heap sizes. It manages these heaps with pause times under
        10ms and little impact on throughput. These times are better than G1's.
        * ZGC does it's marking in three phases.
          * The first is a short stop-the-world phase. It examines the GC roots, local variables that point to the rest
          of the heap. The total number of these roots is usually minimal and doesn't scale with the size of the load, so
          ZGC's pauses are very short and don't increase as your heap grows.
          * Once the initial phase completes, ZGC continues with a concurrent phase. It walks the object graph and examines
          the colored pointers, marking accessible objects. The load barrier prevents contention between the GC phase and
          any application's activity.
          * After ZGC has completed marking, it moves live objects to free up large sections of the heap to make allocations
          faster. When the relocation phase begins, ZGC divides the heap into pages and works on one page at a time. Once
          ZGC finishes moving any roots, the rest of the relocation happens in a concurrent phase.
        * ZGC's phases illustrate how it manages large heaps without impacting performance as application memory grows.
      * Updates in JDK 13
        * ZGC's original design did not allow for memory pages to be returned to the OS when they were no longer required,
        e.g. when the heap shrinks and the memory is unused for an extended period of time. For environments such as
        containers, where resources are shared between a number of services, this can limit the scalability and efficiency
        of the system.
        * The ZGC heap consists of a set of heap regions called *ZPages*. When ZPages are emptied during a GC cycle, they
        are returned to the *ZPageCache*. ZPages in this cache are organized in order of those used least recently. In
        JDK 13, the ZGC will return pages that have been identified as unused for a sufficiently long period of time to
        the OS. This allows them to be reused for other processes. Uncommitting memory will never cause the heap size to
        shrink below the minimum size specified on the command line. If the minimum and maximum heap sizes are set to the
        same value, no memory will be uncommitted.
    * Shenandoah like ZGC, it manages large heaps with short pause times but uses a very different approach.
      * What is Shenandoah?
        * Another GC with low pause times. These times are short and predictable, regardless of the size of the heap.
        Shenandoah was developed at Red Hat and has been around for several years. It is now part of the Java 12 release.
        * Like ZGC, it does most of its work in parallel with the running application. But its approach to garbage collection
        is different. Shenandoah uses memory regions to manage which objects are no longer in use and which are live and
        ready for compression. Shenandoah also adds a forwarding pointer to every heap object and uses it to control access
        to the object.
      * How does Shenandoah work?
        * Shenandoah's design trades concurrent CPU cycles and space for pause time improvements. The forwarding pointer
        makes it easy to move objects, but the aggressive moves mean Shenandoah uses more memory and requires more parallel
        work than other GC's. But it does the extra work with very brief stop-the-world pauses.
      * Shenandoah Phases
        * Shenandoah processes the heap in many small phases, most of which are concurrent with the application. This design
        makes it possible for the GC to manage a large heap efficiently.
        * The first phase contains the first stop-the-world pause in the cycle. It prepares the heap for concurrent marking
        and scans the root set. Like ZGC, the length of this pause corresponds to the size of the root set, not the heap.
        Next a concurrent phase walks the heap and identifies reachable and unreachable objects.
        * The third finishes the process of marking by draining pending heap updates and re-scanning the root set. This
        phase triggers the second stop-the-world pause in the cycle. The number of pending updates and size of the root set
        determine how long the pause is.
        * Then, another concurrent phase copies the objects out of the regions identified in the final mark phase. This
        process sets Shenandoah apart from other GC's since it aggressively compacts the heap in parallel with application
        threads.
        * The next phase triggers the third (and shortest pause) in the cycle. It ensures that all GC threads have finished
        evacuation. When it finishes, a concurrent phase walks the heap and updates references to objects moved earlier in
        the cycle.
        * The last stop-the-world pause in the cycle finishes updating the references by updating the root set. At the same
        time, it recycles the evacuated regions. Finally, the last phase reclaims the evacuated regions, which now have no
        references in them.
      * How to use Shenandoah?
      
      | COMMAND LINE OPTIONS | NOTES |
      | --- | --- |
      | `-XX:+UnlockExperimentalVMOptions` | Unlock Java experimental options |
      | `-XX:+UseShenanodoahC` | Use Shenandoah GC |
      | `-XmxXg` | Set heap size |
      | `-XX:ShenandoahGCHeuristics=` | Select heuristics |
      
    * Shenandoah Heuristics - You can configure Shenandoah with one of three heuristics. They govern when the GC starts its
    cycles and how it selects regions for evacuation.
      1. Adaptive: Observes GC cycles and starts the next cycle so it completes before the application exhausts the heap.
      This heuristic is the default mode.
      2. Static: Starts a GC cycle based on heap occupancy and allocation pressure.
      3. Compact: Runs GC cycles continuously. Shenandoah starts a new cycle as soon as the previous one finishes or based
      on the amount of heap allocated since the last cycle. This heuristic incurs throughput overhead but provides the best
      space reclamation.
    * Failure Modes - Shenandoah needs to collect heap faster than the application it's serving allocates it. If the allocation
    pressure is too high and there's not enough space for new allocations, there will be a failure. Shenandoah has configurable
    mechanisms for this situation.
      * Pacing: If Shenandoah starts to fall behind the rate of allocation, it will stall allocation threads to catch up.
      The stalls are usually enough for mild allocation spikes. Shenandoah introduces delays of 10ms or less. If pacing
      fails, Shenandoah will move to the next step: degenerated GC
      * Degenerated GC: If an allocation failure occurs, Shenandoah starts a stop-the-world phase. It uses the phase to
      complete the current GC cycle. Since a stop-the-world doesn't contend with the application for resources, the cycle
      should finish quickly and clear the allocation shortfall. Often, a degenerated cycle happens after most of the cycle's
      work is already completed, so the stop-the-world is brief. The GC log will report it as a full pause, though.
      * Full GC: If both pacing and degenerated GC fail, Shenandoah falls back to a full GC cycle. This final GC guarantees
      the application wont fail with an out-of-memory error unless there's no heap left.
    * Why use Shenandoah?
      * Shenandoah offers the same advantages as ZGC with large heaps but more tuning options. Depending on the nature of
      your application, the different heuristics may be a good fit. Its pause times might not be as brief as ZGC's, but they
      are more predictable. While Shenandoah was not made available as part of Java until version 12, it's been around longer
      than ZGC. It's seen more testing and is even available as a backport for both java 8 and 10.
      

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

### Parsing strings into dates
You need to convert user input into `java.time` objects.
* Use a `parse()` method
* Many of the date/time classes have a `parse()` factory method, which tries to parse a string into an object of that
class. 
* As you probably expect by now, the default format is ISO8601 date format. However, we often have to deal with dates in
other formats. For this, the `DateTimeFormatter` allows you to specify a particular pattern. For example, "dd MMM uuuu"
represents the day of the month (two digits), three letters of the name of the month (Jan, Feb, ...), and a four digit
year.
* As its name implies, the `DateTimeFormatter` object is bidirectional; it can both parse input and format output. Ex:
`System.out.println(aLD + " formats as " + df.format(aLD));`

### Adding to or subtracting from a Date or Calendar
You need to add or subtract a fixed period to or from a date
* Create a past or future date by using a locution such as `LocalDate.plus(Period.ofDays(N))`
* `java.time` offers a `Period` class to represent a length of time, such as a number of days, or hours and minutes.
`LocalDate` and friends offer `plus()` and `minus()` methods to add or subtract a `Period` or another time-related
object. `Period` offers factory methods such as `ofDays()`.

### Interfacing with legacy Date and Calendar Classes
You need to deal with the old `Date` and `Calendar` classes
* Assuming you have code using the original `java.util.Date` and `java.util.Calendar`, you can convert values as needed
using conversion methods.
* To keep the mew API clean, most of the necessary conversion routines were added to the old API.

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
* Polymorphism and Interfaces
  * Basically, the best uses of polymorphism in Java is the ability to refer to a child class by using the parent class
  reference. In polymorphism, "many forms" means the ability of an object or method to take many forms. Method overriding
  and method overloading basically mean a behavior in Java that allows the developer to take advantage of that principle.
  * Basically, an interface is a contract that a developer must follow while implementing it. It states that when you are
  implementing something, you must provide the given set of the feature, or else you will be incomplete. Here, incomplete
  means that the class is not a complete one (for ex, an abstract class). Another advantage -- from a program design perspective -- is
  the idea of "programming to an interface, not implementations." That means that when we are designing our code, we should
  focus on the interface or the functionalities that the interface provides, not the actual implementation.
  
  
  
  
  
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

### Functional programming patterns with Java 8

**Prefer named functions over anonymous lambdas**

Always extract complex lambdas into functions with an expressive name that you can then reference using `::` from
* the same class (`this::`)
* another class (`mapper::`)
* some static helper method (`SomeClass::`)
* the Stream *item* type (`Item::`)
* even some constructor (`UserDto::new`), if its simple enough

In short never type `-> {`.

**Stream wrecks**

The idea here is to avoid excessive method chaining by introducing explanatory variables. This means extracting methods
and even work with variables of a function or `Stream` type, in order to make the code as clear as possible to your
reader.

**Fighting the greatest beast of all: Null Pointer**

Whenever null gives you problems in Java 8, don't hesitate to jump on Optional and apply transformation functions on the,
potentially empty, magic box. The clean code rule becomes, don't take Optional parameters; instead, return an Optional
whenever your function wants to signal to your caller that there might be NO return value in some cases.

**The Loan Pattern / Passing a block**

For the following exercise, let's export the orders to a CSV file
```java
interface OrderedRepo {
    List<Object> findByActiveTrue();
}
public class LoanPattern {
    private OrderedRepo orderedRepo;
    public File exportFile(String fileName) throws Exception {
        File file = new File("export/" + fileName);
        try (Writer writer = new FileWriter(file)) {
            writer.write("OrderID;Date\n");
            // Unchecked: you need this because write() throws IOException, even when the Consumer expected by the forEach
            // does not. You should suffer if you throw checked exceptions.
            orderedRepo.findByActiveTrue().map(o -> o.getId() + ";" + o.getCreationDate()).forEach(Unchecked.consumer(writer::write));
            return file;
        } catch (Exception e) {
            // Todo: Send email notification
            throw e;
        }
    }
}
```
I'll open a `Writer`, stream over all the orders, convert them, and then write each to the file. The vague odor of fear
at the end of this example stems from the possibility that, perhaps, no one will ever catch my exception afterward. Ideally,
you should trust your team with these exceptions, so that if they are thrown on any threads, they are gracefully caught
and logged.

There are many ways you could stray from the path of righteousness while doing that, including booleans, enum ExportType,
and @Overriding concrete methods, but I will sketch here an application of the Template Method design pattern.
```java
interface OrderedRepo {
    List<Object> findByActiveTrue();
}
abstract class FileExporter {
    public File exportFile(String filename) throws Exception {
        File file = new File("export/"+filename);
        try(Writer writer = new FileWriter(file)) {
            writeContent(writer);
        } catch (Exception e) {
            // todo: send email notification
            throw e;
        }
    }
    
    protected abstract void writeContent(Writer writer) throws IOException;
}

class OrderExportWriter extends FileExporter {
    private OrderRepo orderRepo;
    @Override
    protected void writeContent(Writer writer) throws IOException {
        writer.write("OrderID;Date\n");
        orderRepo.findByActiveValue().map(o -> o.getId() + ";" + o.getCreationDate())
            .forEach(Unchecked.consumer(writer::write));
    }
}
```

I want you to ask yourself: why did we use that dangerous word there? Why did we play with fire? What's the excuse for
that awful `extends` in the code? To force me to provide the missing logic, there will be a function `f(Writer):void`
whenever I subclass the `FileExporter`.

But we can do that a lot easier in Java 8. We just need to take a `Consumer<Writer>` as a method parameter!

```java
import java.io.IOException;interface OrderedRepo {
    List<Object> findByActiveTrue();
}
class FileExporter {
    public File exportFile(String filename, Consumer<Writer> contentWriter) throws Exception {
        File file = new File("export/" + filename);
        try (Writer writer = new FileWriter(file)) {
            contentWriter.accept(writer); // passing writer object
            return file;
        } catch (Exception e) {
            // todo: send email notification
            throw e;
        }
    }
}
class OrderExportWriter {
    private OrderRepo orderRepo;
    public void writeOrders(Writer writer) throws IOException {
        writer.write("OrderID;Date\n");
        orderRepo.findByActiveTrue().map(o -> o.getId() + ";" + o.getCreationDate())
            .forEach(Unchecked.consumer(writer::write));
    }
}
```

Wow, a lot of things have changed here. Instead of `abstract` and `extends`, the `exportFile()` function got a new
`Consumer<Writer>` parameter, which it calls to write the actual export content. To get the whole picture, let's sketch
the client code:

`fileExporter.exportFile("orders.csv", Unchecked.consumer(orderWrite::writeOrders));`

Here I had to use `Unchecked` again to make it compile, because `writeOrders()` declaration throws an exception!

The fundamental idea is that **whenever you have some "variable logic", you can consider taking it as a method parameter**.
The above example, however, is a slight variation, in which the function given as a parameter works with a resource that
is managed by the host function. In our example, `OrderExportWriter.writeOrders` receives a `Writer` as a parameter to
write the content to it. However, `writeOrders` is not concerned with creating, closing, or handling errors related to
`FileWriter`. That's why we call this the **Loan pattern**. **This is a function we pass in that is essentially borrowed
so the Writer can do its job**.

One major benefit of the *Loan pattern* is that it decouples nicely with the infrastructural code (`FileExporter`) from
the actual export format logic (`OrderExportWriter`). Through a better separation by layers of abstraction, this enables
a *Dependency Inversion*, i.e. you could keep the `OrderWriter` in a more interior layer. Because it decouples the code
so nicely, the design becomes a lot easier to reason with and unit test. You can test `writeOrders()` by passing it a
`StringWriter` and, afterward, see what was written to it. To test the `FileExporter`, you can pass simply a dummy
`Consumer` that just writes "dummy", and then verify that the infra code did its job.

There is one more variation of the *Passing-a-Block* pattern, the *Execute Around* pattern. Syntactically, the code is
very similar: `measure(() -> stuff()`. However, the purpose is slightly is different. Here, `stuff()` was already
implemented, but, afterward, we wanted to execute some arbitrary code around it (before and after). **With Execute Around,
we write this before/after code in some helper function and then wrap our original call within a call to this helper**.

The key takeaway of this section is that **you should force yourself into thinking about handling bits of logic and
juggling them as first-class citizens in Java 8**. It will make your code more elegant, simple, and expressive.

**How to implement type specific logic**
The task is simple: there are 3 movie types, each with its own formula for computing the price based on the loan number
of days.

One way to do it
```java
class Movie {
    enum Type {
        REGULAR, NEW_RELEASE, CHILDREN

    }

    private final Type type;
    public Movie(Type type) {
        this.type = type;
    }

    public int computePrice(int days) {

        switch (type) { 
            case REGULAR: return days + 1;
            case NEW_RELEASE: return days * 2;
            case CHILDREN: return 5;
            default: throw new IllegalArgumentException(); // Always have this here!
        }
    }
}
```
The problem in the code above could be the `switch`: whenever you add a new value to the enum, you need to hunt down all
the switches and make sure you handle the new case. But this is fragile. The `IllegalArgumentsException`will pop up, but
only if you walk the path from tests/UI/API. In short, although anyone can read this code, it's a bit risky.

One way to avoid this risk would be an OOP solution.

```java
abstract class Movie {
    public abstract int computePrice(int days);
}

class RegularMovie extends Movie {

    public int computePrice(int days) {
        return days+1;
    }
}

class NewReleaseMovie extends Movie {

    public int computePrice(int days) {
        return days*2;
    }
}

class ChildrenMovie extends Movie {

    public int computePrice(int days) {
        return 5;
    }
}
```
If you create a new type of movie, a new subclass actually, the code won't compile unless you implement `computePrice()`.
But, it `extends` again! What if you want to classify the movies by another criterion, say release year? Or how would
you handle the "downgrade" from a `Type.NEW_RELEASE` to a `TYPE.REGULAR` movie after several months?

One more requirement added to the original task. The factor in the price formula for new release movies must be updatable
via the database. This means that I have to get this factor from some injected repository. But, since I can't inject repos
in my `Movie` entity, let's move the logic to a separate class:

```java
public class PriceService {

    private final NewReleasePriceRepo repo;

    public PriceService(NewReleasePriceRepo repo) {
        this.repo = repo;
    }

    public int computeNewReleasePrice(int days) {
        return (int) (days * repo.getFactor());
    }

    public int computeRegularPrice(int days) {
        return days + 1;
    }

    public int computeChildrenPrice(int days) {
        return 5;
    }

    public int computePrice(Movie.Type type, int days) {

        switch (type) {
            case REGULAR: return computeRegularPrice(days);
            case NEW_RELEASE: return computeNewReleasePrice(days);
            case CHILDREN: return computeChildrenPrice(days);
            default: thrownew IllegalArgumentException();
        }
    }
}
```
But the `switch` is back with the inherent risks! Is there any way to make sure no one forgets to define the associated
price formula? And mow for the grand finale:

```java
public class Movie { 

   public enum Type {
        REGULAR(PriceService::computeRegularPrice),

        NEW_RELEASE(PriceService::computeNewReleasePrice),

        CHILDREN(PriceService::computeChildrenPrice);

        public final BiFunction<PriceService, Integer, Integer> priceAlgo;

        private Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
      }
   }
}
// And instead of the switch

class PriceService {
    // ...
    public int computePrice(Movie.Type type, int days) {
        return type.priceAlgo.apply(this, days);
    }
}
```
I am storing into each enum value a method reference to the corresponding instance method from `PriceService`. Since I
refer to instance methods in a static way (from `PriceService::`), I will need to provide the `PriceService` instance as
the first parameter at the invocation time. And I give it `this`. This way I can effectively reference methods from any
(Spring) bean from the static context of the definition of an enum value. Look at programming/MyEnumerations.java for an
example.





























