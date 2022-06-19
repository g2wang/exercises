/**
 *
5. Longest Palindromic Substring
Medium

Given a string s, return the longest palindromic substring in s.

Example 1:
Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:
Input: s = "cbbd"
Output: "bb"

Constraints:
1 <= s.length <= 1000
s consist of only digits and English letters.
 */

public class LongestPalindromeSubstringManacher {

    public static void main(String[] args) {
        LongestPalindromeSubstringManacher lps = new LongestPalindromeSubstringManacher(); 
        String[] ss = new String[]{"babad", "cbbd", "aacabdkacaa"};
        for (String s : ss) {
            String ans = lps.longestPalindrome(s);
            System.out.printf("%s -> %s%n", s, ans);
        }
    }

    /**
     * Ref: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
     *
        Manacher's algorithm
        Below is the code for Manacher's algorithm. The algorithm is faster than
        the previous algorithm because it exploits when a palindrome happens inside
        another palindrome.

        For example, consider the input string "abacaba". By the time it gets to the
        "c", Manacher's algorithm will have identified the length of every palindrome
        centered on the letters before the "c". At the "c", it runs a loop to identify
        the largest palindrome centered on the "c": "abacaba". With that knowledge,
        everything after the "c" looks like the reflection of everything before the
        "c". The "a" after the "c" has the same longest palindrome as the "a" before
        the "c". Similarly, the "b" after the "c" has a longest palindrome that is at
        least the length of the longest palindrome centered on the "b" before the "c".
        There are some special cases to consider, but that trick speeds up the
        computation dramatically.
     *
     * Runtime: 17 ms, faster than 96.65% of Java online submissions for Longest Palindromic Substring.
     * Memory Usage: 44.8 MB, less than 53.05% of Java online submissions for Longest Palindromic Substring.
     */

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        StringBuilder sb = new StringBuilder(2*s.length()+1); // s with a bogus character (eg. '|') inserted between each character (including outer boundaries)
        char[] ca = s.toCharArray();
        sb.append('|');
        for (int i = 0; i < ca.length; i++) {
            sb.append(ca[i]).append('|');
        }

        // now sb.length() is 2 * s.length() + 1;

        int[] radii = new int[sb.length()]; // The radius of the longest palindrome centered on each place sb
        int center = 0, radius = 0;

        while (center < sb.length()) {
            // At the start of the loop, Radius is already set to a lower-bound for the longest radius.
            // In the first iteration, Radius is 0, but it can be higher.

            // Determine the longest palindrome starting at (center-Radius) and going to (center+radius)
            int L = center-(radius+1), R = center+(radius+1);
            while (L >= 0 && R < sb.length() && sb.charAt(L) ==  sb.charAt(R)) {
                radius++; 
                L = center-(radius+1);
                R = center+(radius+1);
            }
            // Save the radius of the longest palindrome in the array
            radii[center] = radius;

            // Below, center is incremented.
            // If any precomputed values can be reused, they are.
            // Also, radius may be set to a value greater than 0
            int oldCenter = center;
            int oldRadius = radius;
            center++;

            // radius' default value will be 0, if we reach the end of the following loop. 
            radius = 0;

            while (center <= oldCenter + oldRadius) {
                // Because center lies inside the old palindrome and every character inside
                // a palindrome has a "mirrored" character reflected across its center, we
                // can use the data that was precomputed for the center's mirrored point.
                int mirroredCenter = oldCenter - (center - oldCenter);
                int maxMirroredRadius = oldCenter + oldRadius - center;
                if (radii[mirroredCenter] < maxMirroredRadius) {
                    radii[center] = radii[mirroredCenter];
                    center++;
                } else if (radii[mirroredCenter] > maxMirroredRadius) {
                    radii[center] = maxMirroredRadius;
                    center++;
                } else { // radii[mirroredCenter] = maxMirroredRadius
                    radius = maxMirroredRadius;
                    break;  // exit while loop early
                }
            }
        }

        int maxRadiusIndex = 0;
        int maxRadius = radii[0];
        for (int i = 1; i < radii.length; i++) {
            if (radii[i] >  maxRadius) {
                maxRadiusIndex = i;
                maxRadius = radii[i];
            }
        }

        StringBuilder longestPalindromeInS = new StringBuilder(maxRadius);
        for (int i = maxRadiusIndex-maxRadius; i <= maxRadiusIndex+maxRadius; i++) {
            char c = sb.charAt(i);
            if (c != '|') {
                longestPalindromeInS.append(c);
            }
        }
        return longestPalindromeInS.toString();
    }

}
