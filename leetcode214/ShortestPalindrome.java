/**
 * 214. Shortest Palindrome
Hard
￼
Given a string s, you can convert it to a palindrome by adding characters in front of it. Find and return the shortest palindrome you can find by performing this transformation.

Example 1:
Input: s = "aacecaaa"
Output: "aaacecaaa"

Example 2:
Input: s = "abcd"
Output: "dcbabcd"

Constraints:

0 <= s.length <= 5 * 104
s consists of lowercase English letters only.
 */

public class ShortestPalindrome {

    public static void main(String[] args) {
        String s = "abcd";
        ShortestPalindrome sp = new ShortestPalindrome();
        String ans = sp.shortestPalindrome(s);
        System.out.printf("  s: %s%nans: %s%n", s, ans);
    }

    public String shortestPalindrome(String s) {
        if (s == null || s.length() <= 1) return s;
        StringBuilder sb = new StringBuilder();
        String s1 = s;
        while (!isPalindrome(s1)) {
            int newLen = s1.length()-1;
            sb.append(s1.charAt(newLen));
            s1 = s1.substring(0, newLen);
        }
        return sb.toString() + s;
    }

    private boolean isPalindrome(String s) {
        boolean isPalindrome = true;
        boolean isEven = s.length()%2 == 0;
        int mid = s.length()/2;
        String left = "";
        String right = "";
        if (isEven) {
            left = s.substring(0, mid);
            right = s.substring(mid);
        } else {
            left = s.substring(0, mid);
            right = s.substring(mid+1);
        }
        for (int i = 0; i < mid; i++) {
            int ri = mid - 1 - i;
            if (left.charAt(i) != right.charAt(ri)) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome; 
    }
}
