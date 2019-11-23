package programming;

import static java.util.Calendar.*;

public class NewStuffz {

    public static void main(String[] args){
        switchStatements();
    }

    private static void textBlocks() {
        // Lines below are considered new lines
        String t = """
        <h1>Hello Jefferson</h1>
        <p>This is paragraph</p>
        """;
        System.out.println(t);
    }

    private static void newStringMethods() {
        // formatted = format
        String greeting = "Hi %s, how are you?".formatted("Jefferson");
        System.out.println(greeting);

        // translateEscapes(): This returns the string with escape sequences (e.g. \r)
        // translated into the appropriate unicode value
        /*
        * stripIndent(): This removes incidental spaces from the string. This is useful
        if you are reading multi-line strings and want to apply the same elimination of
        incidental whitespace that happens with an explicit declaration.
        * translateEscapes(): This returns the string with escape sequences (e.g. \r)
        translated into the appropriate unicode value
         */
    }

    private static void switchStatements() {
        int dayOfWeek = TUESDAY;

        int numberOfLetters = switch (dayOfWeek) {
            case MONDAY, FRIDAY, SUNDAY -> 6;
            case TUESDAY -> 7;
            case THURSDAY, SATURDAY -> 8;
            case WEDNESDAY -> 9;
            default -> throw new IllegalStateException("Huh?: " + dayOfWeek);
        };

        // You can also use the old style to assign to variables.
        // You can use yield instead of break
        String dailyMessage = switch (dayOfWeek) {
            case MONDAY, TUESDAY:
                yield "Just getting started :(";
            case WEDNESDAY:
                yield "Half way there";
            case THURSDAY:
                yield "Almost there, you can do it!";
            case FRIDAY:
                yield "You did it!!";
            case SATURDAY:
                yield "Enjoy!";
            case SUNDAY:
                yield "Yes, it's over!";
            default:
                throw new IllegalStateException("I don't know what to say!");
        };

        System.out.println("Number of letters in Tuesday is " + numberOfLetters);
        System.out.println("Daily message: " + dailyMessage);
    }

}
