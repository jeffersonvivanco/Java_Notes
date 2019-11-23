package programming;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DatesAndTime {

    public static void main(String[] args){
        Date endOfTime = new Date(Long.MAX_VALUE);
        System.out.println("Java time overflows on: " + endOfTime);

//        findTodaysDateTime();
//        parsingStringsIntoDates();
//        addSubtractFromADateOrCalendar();
        legacyDates();
    }

    private static void findTodaysDateTime(){
        LocalDate dNow = LocalDate.now(); // Gets local date
        System.out.println("Today's date is {yyyy-mm-dd} " + dNow);

        LocalTime tNow = LocalTime.now(); // Gets local time
        System.out.println("Current time is " + tNow);

        LocalDateTime dtNow = LocalDateTime.now(); // Get local date and time
        System.out.println("Local date and time is " + dtNow);

    }

    private static void parsingStringsIntoDates() {
        String d = "1994-11-10";
        LocalDate birthday = LocalDate.parse(d);
        System.out.println("My birthday is on " + birthday); // 1994-11-10

        String d2 = "1994-11-10T11:11";
        LocalDateTime exactBirthday = LocalDateTime.parse(d2);
        System.out.println("Exact birthday is on " + exactBirthday);

        // with DateTimeFormatter
        DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        var d3 = "11/10/1994";
        LocalDate birthday2 = LocalDate.parse(d3, df);
        System.out.println("Birthday in another format " + birthday2);
    }

    private static void addSubtractFromADateOrCalendar() {
        LocalDate today = LocalDate.now();
        Period p = Period.ofDays(1);
        System.out.println("Tomorrow is " + today.plus(p));
    }

    private static void legacyDates() {
        // There and back again, via Date
        Date legacyDate = new Date();
        LocalDateTime newDate = LocalDateTime.ofInstant(legacyDate.toInstant(), ZoneId.systemDefault());
        System.out.println(newDate);

        // From LocalDateTime to Date
        LocalDateTime today = LocalDateTime.now();
        Date todayDate = Date.from(today.atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(todayDate);

        // From LocalDate to Date
        LocalDate today2 = LocalDate.now();
        Date todayDate2 = Date.from(today2.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println(today2 + "<= LocalDate => Date " + todayDate2);

        // And via Calendar
        Calendar c = Calendar.getInstance();
        LocalDateTime newCal = LocalDateTime.ofInstant(c.toInstant(), ZoneId.systemDefault());
        System.out.println(newCal);
    }
}
