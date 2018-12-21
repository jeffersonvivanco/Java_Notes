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
        System.out.println(integerStats.sameAvg(doubleStats));

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
class Stats<T extends Number> implements X, Y {
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

    boolean sameAvg(Stats<?> ob){
        if (average() == ob.average())
            return true;
        return false;
    }
}

interface Y {

}
interface X {}
