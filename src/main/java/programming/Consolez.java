package programming;

public class Consolez {

    public static void main(String[] args) {
        String name = System.console().readLine("What is your name? \n");
        System.out.println("Hello " + name);
        char password [] = System.console().readPassword("Please enter password\n");
        System.out.println("Thank you!");
    }
}
