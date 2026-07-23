public class StringBuilderComparison {
    private static final int ITERATIONS = 50_000;

    static String withString() {
        String result = "";
        for (int i = 0; i < ITERATIONS; i++) {
            result += "x";
        }
        return result;
    }

    static String withBuilder() {
        // Initial capacity avoids repeated buffer growth.
        StringBuilder result = new StringBuilder(ITERATIONS);
        for (int i = 0; i < ITERATIONS; i++) {
            result.append('x');
        }
        return result.toString();
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        String stringResult = withString();
        long stringTime = System.nanoTime() - start;

        start = System.nanoTime();
        String builderResult = withBuilder();
        long builderTime = System.nanoTime() - start;

        System.out.printf("withString(): Length: %d, Time = %.3f ms%n", stringResult.length(), stringTime / 1_000_000.0);
        System.out.printf("withBuilder(): Length: %d, Time = %.3f ms%n", builderResult.length(), builderTime / 1_000_000.0);
    }
}