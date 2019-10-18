import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Fibonacci {
    public static void main(String[] args) {
        if (args.length == 0) {
           System.out.println("java fibonacci <N>");
           return;
        }
        long N = Long.valueOf(args[0]);
        long f0 = 0;
        long f1 = 1;
        System.out.println("fib(1): " + f1);
        long f = 1;
        for (long i = 2; i <= N; i++) {
           f = f0 + f1;
           System.out.println(String.format("fib(%3d): %d", i, f));
           f0 = f1;
           f1 = f;
        }
        System.out.println("the least significant 6 digits of the fibo(" + N + ") is: " + f%1000000L);
        System.out.println("the most significant digits of the fibo(" + N + ") preceding the least significant 6 digits is: " + f/1000000L);
        System.out.println("done.");
    }
}
