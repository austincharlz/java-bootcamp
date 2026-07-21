import java.util.Scanner;

public class DecisionDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Score (0-100): ");
        int score = Integer.parseInt(scanner.nextLine());

        // if / else if / else: first matching branch wins, rest are skipped
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else {
            System.out.println("Grade: F");
        }

        System.out.print("Day number (1-7): ");
        int day = Integer.parseInt(scanner.nextLine());

        // switch: jump straight to the matching case, no chained comparisons
        switch (day) {
            case 1 -> System.out.println("Monday");
            case 2 -> System.out.println("Tuesday");
            case 3 -> System.out.println("Wednesday");
            case 4 -> System.out.println("Thursday");
            case 5 -> System.out.println("Friday");
            case 6 -> System.out.println("Saturday");
            case 7 -> System.out.println("Sunday");
            default -> System.out.println("Not a valid day");
        }

        // Switch case that I made using old switch case syntax
        System.out.print("Payment Method (1 = Debit, 2 = Credit, 3 = Other): ");
        int payment = Integer.parseInt(scanner.nextLine());

        switch (payment) {
            case 1: System.out.println("Debit Card"); break;
            case 2: System.out.println("Credit Card"); break;
            case 3: System.out.println("Other Payment Method"); break;
            default: System.out.println("Not a valid payment method");
        }

        scanner.close();
    }
}