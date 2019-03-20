package programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionalProgramming {

    public static void main(String[] args){
        // using lambda
        Compute compute = (x) -> x % 2 == 0;
        List<Integer> numbers = Arrays.asList(1, 5, 3, 8, 10);

        numbers = filter(compute, numbers);
        for(int i=0; i < numbers.size(); i++)
            System.out.println(numbers.get(i));
    }

    /*
    Note: Instead of Compute, you can use java's Acceptor-type method, the Predicate<T> Interface
    This interface has one method test(), which we'll have change below compute -> test. Also, note that
    this function instead of passing a Compute object, we will pass a lambda expression
     */
    public static List<Integer> filter(Compute c, List<Integer> numbers){
        List<Integer> n = new ArrayList<>();
        numbers.forEach(num -> {
            if (c.compute(num))
                n.add(num);
        });
        return n;
    }

}

interface Compute {
    boolean compute(int x);
}