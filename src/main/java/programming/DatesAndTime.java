package programming;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DatesAndTime {

    public static void main(String[] args){
        Date endOfTime = new Date(Long.MAX_VALUE);
        System.out.println("Java time overflows on: " + endOfTime);

        findTodaysDateTime();
    }

    private static void findTodaysDateTime(){
        LocalDate dNow = LocalDate.now(); // Gets local date
        System.out.println("Today's date is {yyyy-mm-dd} " + dNow);

        LocalTime tNow = LocalTime.now(); // Gets local time
        System.out.println("Current time is " + tNow);

        LocalDateTime dtNow = LocalDateTime.now(); // Get local date and time
        System.out.println("Local date and time is " + dtNow);

        // Formatting dates and times
        /*

        DateTimeFormatter

        * This class provides an amazing number of possible formatting styles. If you don't want to use one of the
        provided 20 or so predefined formats, you can define your own using DateTimeFormatter.ofPattern(String pattern).
        The "pattern" string can contain any characters, but almost every letter of the alphabet has been defined to mean
        something, in addition to the obvious Y, M, D, h, m, and s. In addition, the quote character and square bracket
        characters are defined, and the sharp sign (#) and curly braces are reserved for future use.
         */
    }
}
