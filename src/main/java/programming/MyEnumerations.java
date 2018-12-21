package programming;

import java.util.Arrays;
import java.util.Optional;

public class MyEnumerations {

    public static void main(String[] args){
        Arrays.stream(Fruit.values()).forEach(f -> System.out.println(f));
        Optional<Fruit> f = Optional.ofNullable(Fruit.valueOf("Orange"));
        if (f.isPresent())
            System.out.println(f);
        else
            System.out.println("Value not present");

        // Get ordinal value
        System.out.println(Fruit.Apple.ordinal());
    }
}

enum Fruit {
    Apple, Orange, Grapes, Blueberries, Strawberries
}
