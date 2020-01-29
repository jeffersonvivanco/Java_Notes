package programming;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;

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

class Movie {

    enum Type {
        REGULAR(PriceService::computeRegularPrice),
        NEW_RELEASE(PriceService::computeNewReleasePrice),
        CHILDREN(PriceService::computeChildrenPrice);

        public final BiFunction<PriceService, Integer, Integer> priceAlgo;

        Type(BiFunction<PriceService, Integer, Integer> priceAlgo) {
            this.priceAlgo = priceAlgo;
        }
    }

    private final Type type;

    public Movie(Type type) {
        this.type = type;
    }
}

class PriceService {
    public int computeRegularPrice(int days) {
        System.out.printf("computing regular price for %d days%n", days);
        return 0;
    }
    public int computeNewReleasePrice(int days) {
        System.out.printf("computing new release price for %d days%n", days);
        return 0;
    }
    public int computeChildrenPrice(int days) {
        System.out.printf("computing children price for %d days%n", days);
        return 0;
    }
    public int computePrice(Movie.Type type, int days) {
        return type.priceAlgo.apply(this, days);
    }

    public static void main(String[] args) {
        // testing methods
        PriceService priceService = new PriceService();
        priceService.computePrice(Movie.Type.NEW_RELEASE, 4);
    }
}