public class MethodsDemo {
    // Takes an int parameter; returns an int
    public static int square(int n) {
        return n * n;
    }

    // Overload: same method name, different parameter type
    public static double square(double n) {
        return n * n;
    }

    // My own methods to test
    public static int divide(int x, int y) {
        return x / y;
    }

    public static float divide(float x, float y) {
        return x / y;
    }

    public static void main(String[] args) {
        int intResult = square(4);          // calls the int version
        double doubleResult = square(2.5);  // calls the double version — compiler picks by argument type

        int intDivideResult = divide(6, 3);
        float floatDivideResult = divide(20, 3);

        System.out.println("square(4) = " + intResult);
        System.out.println("square(2.5) = " + doubleResult);

        System.out.println("divide(6, 3) = " + intDivideResult);
        System.out.println("divide(20, 3) = " + floatDivideResult);
    }
}