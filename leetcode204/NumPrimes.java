/**
204. Count Primes
Easy 

Count the number of prime numbers less than a non-negative number, n.

Example:

Input: 10
Output: 4

Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7. 

 */

import java.util.Arrays;

public class NumPrimes {
    public static void main(String[] args) {
        int n = 10;
        NumPrimes np = new NumPrimes();
        int numPrimes = np.countPrimes(n);
        System.out.printf("%d -> num primes: %d%n", n, numPrimes);
    }

    /**
     * sieve of Eratosthenes
     */
    public int countPrimes(int n) {
        if (n <= 2) return 0;
        boolean[] isPrime = new boolean[n-1];
        Arrays.fill(isPrime, true);
        for (int i = 2; i < n; i++) {
            if (isPrime[i-2]) {
                long isq = (long)i * (long)i;
                if (isq < n) {
                    for (int j = (int)isq; j < n; j+=i) {
                        isPrime[j-2] = false;
                    }
                }
            }
        }
        
        int c = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i-2]) c++;
        }
        return c;
    }

}
