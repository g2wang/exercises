import java.util.Arrays;
import java.util.List;

public class Palindrome {
    public static void main(String[] args){
        List<Object> in = Arrays.asList("MadamamAdam", "IamNotAPalindrome", 56677665L, 88389932L);
        for (Object o : in) {
            System.out.println(o + " is " + (isPalindrome(o)?"":"NOT ") + "a palindrome");
        }
    }

    public static boolean isPalindrome(Object o){
        if (o instanceof Long) {
            return isPalindrome((((Long)o).longValue()));
        }
        return isPalindrome(o.toString());
    }

    public static boolean isPalindrome(long n){
        return n == reverseNum(n);
    }

    public static long reverseNum(long n){
        long q = n / 10, r = n % 10, v = r;
        while (q > 0) {
            r = q % 10;
            q = q / 10;
            v = v * 10 + r;
        }
        return v;
    }

    public static boolean isPalindrome(String s){
        s = s.toLowerCase();
        int i = 0, j = s.length() - 1;
        while (i++ < j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        return true;
    }
}
