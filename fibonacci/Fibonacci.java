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
        System.out.printf("fib(1): %d%n", f1);
        long f = 1;
        for (long i = 2; i <= N; i++) {
           f = f0 + f1;
           System.out.printf("fib(%d): %d%n", i, f);
           f0 = f1;
           f1 = f;
        }
        System.out.printf("the least significant 5 digits of the fibo(%d) is: %d%n", N, f%100000L);
        System.out.printf("the most significant digits of the fibo(%d) preceding the least significant 5 digits is: %d%n", N,  (f/100000L)%10L);
        System.out.println("done.");
    }
}
