package programming;

/*
Generics

* At its core, the term generics means parameterized types. Parameterized types are important because they
enable you to create classes, interfaces, and methods in which the type of data upon which they operate is
specified as a parameter.

* Generics only work with Reference Types
* In addition to using a class type as a bound, you can also use an interface type. In fact, you can specify
multiple interfaces as bounds. Furthermore, a bound can include both a class type and one or more interfaces.
In this case, the class type must be specified first. When a bound includes an interface type, only type arguments
that implement that interface are legal. When specifying a bound that has a class and an interface, or multiple
interfaces, use the & operator to connect them.
* The wildcard argument is specified by the ?, and it represents an unknown type. It is important to understand that
the wildcard does not affect what type of Stats objects can be created. This is governed by the extends clause in the
Stats declaration. The wildcard simply matches any valid Stats object.

Creating a Generic Method
* It is possible to declare a generic method that uses one or more type parameters of its own.
* Furthermore, it is possible to create a generic method that is enclosed within a non-generic class

Generic Interfaces
* Generic interfaces are specified just like generic classes.

Generic Class Hierarchies
* Generic classes can act as a superclass or be a subclass. The key difference between generic and non generic hierarchies
is that in a generic hierarchy, any type arguments needed by a generic superclass must be passed up the hierarchy by all
subclasses.
* It is perfectly acceptable for a non-generic class to be the superclass of a generic subclass.

Run-Time Type Comparisons Within a Generic Hierarchy
* instanceof determines if an object is an instance of a class. It returns true if an object is of the specified type or
can be cast to the specified type. The instanceof operator can be applied to objects of generic classes.
* To see if a class is instanceof a generic class ex: if (iOb instanceof Gen2<?>)...
* This is wrong: if (iOb instanceof Gen2<Integer>)
* The above line will not be able to compile because it attempts to compare iOb with a specific type of of Gen2. Remember
there is no generic type information available at run time.

Casting
* You can cast one instance of a generic class into another only if the two are otherwise compatible and their type arguments
are the same.

Overriding methods in a Generic Class
* A method in a generic class can be overridden just like any other method.

Type inference with generics
* When type inference is used, the declaration syntax for a generic reference and instance creation has this general form
class-name<type-arg-list> var-name = new class-name<>(cons-arg-list)

FYI
* Erasure
  * An important constraint that governed the way that generics were added to Java was the need for compatibility with
  previous versions of java. Simply put, generic code had to be compatible with preexisting, non generic code. Thus any
  changes to the syntax of the Java, or the JVM, had to avoid breaking older code. The way Java implements generics while
  satisfying this constraint is through the use of erasure.
  * In general, here is how erasure works. When your java code is compiled, all generic type information is removed (erased).
  This means replacing type parameters with their bound type, which is Object if no explicit bound is specified, and then applying
  the appropriate casts (as determined by the type arguments) to maintain type compatibility with the types specified by the
  type arguments. The compiler also enforces this type compatibility. This approach to generics means that no type parameters
  exist at run time. They are simply source-code mechanism.

Some Generic Restrictions
* Type parameters can't be instantiated
* No static member can use a type parameter declared by the enclosing class.
* You cannot instantiate an array whose element type is a type parameter.
  * Its ok to assign reference to an existent array
* You cannot create an array of type-specific generic references.
  * You can create an array of references to a generic type if you use a wildcard
  * ex: Gen<?> gens[] = new Gen<?>[10];
* You cannot create generic exception classes
 */
public class MyGenerics {

    public static void main(String[] args){
        Gen<Integer> iOb = new Gen<>(88);
        iOb.showType();

        Gen<String> sOb = new Gen<>("This is a string");
        sOb.showType();

        TwoGen<Integer, String> two_ob = new TwoGen<>(12, "Jeff");
        two_ob.showTypes();

        // showing stats, generic class bounded by Number
        Integer[] numbers = {1, 2, 3};
        Double[] doubles = {1.0, 2.0, 3.0};
        Stats<Integer> integerStats = new Stats<>(numbers);
        Stats<Double> doubleStats = new Stats<>(doubles);

        // showing how X can be bounded by Number and interface Y
        class SampleClass2 extends SampleClass implements Y { }
        X<SampleClass2> x = new X<SampleClass2>(new SampleClass2());

        // showing class with generic constructor
        GenCons genCons = new GenCons(5);
        genCons.showVal();
    }

    // Generic Method
    static <T, V> boolean areEqual(T t, V v){
        return t == v;
    }

}

// Generic class Gen
class Gen<T> {
    T ob; // declare an object of type T

    // Pass the constructor a reference to an object of type T
    public Gen(T ob) {
        this.ob = ob;
    }

    // return ob
    public T getOb() {
        return ob;
    }

    // Show type of T
    void showType(){
        System.out.println("Type of T is " + ob.getClass().getName());
    }
}

// A simple generic class with two type parameters: T and V
class TwoGen<T, V> {
    T ob1;
    V ob2;

    public TwoGen(T ob1, V ob2) {
        this.ob1 = ob1;
        this.ob2 = ob2;
    }

    // show types of T and V
    void showTypes(){
        System.out.println("Type of T is " + ob1.getClass().getName());
        System.out.println("Type of V is " + ob2.getClass().getName());
    }
}

// In this version of Stats, the type argument for T must either Number,
// or a class derived from Number
// that also implements interface Y
class Stats<T extends Number> {
    T[] nums;

    public Stats(T[] nums) {
        this.nums = nums;
    }

    double average(){
        double sum = 0.0;
        for(Number n : nums){
            sum += n.doubleValue();
        }
        return sum / nums.length;
    }

    // Here, Stats<?> matches any Stats object, allowing any two Stats objects to have their averages compared
    // To type bound wildcard add extends like ...sameAvg(Stats<? extends ClassName>)...
    boolean sameAvg(Stats<?> ob){
        if (average() == ob.average())
            return true;
        return false;
    }
}
class SampleClass {
    public String hello(){
        return "Hello World";
    }
}
class X <T extends SampleClass & Y> {
    T sampleClass;

    public X(T sampleClass) {
        this.sampleClass = sampleClass;
    }

    T getSampleClass(){
        return sampleClass;
    }
}
interface Y { }

// class with generic constructor
class GenCons {
    private double val;

    <T extends Number> GenCons(T arg){
        val = arg.doubleValue();
    }

    void showVal(){
        System.out.println(val);
    }
}