import java.util.Arrays;

public class KMP {

    int[] lps;

    public KMP(String p) {
        int len = p.length();
        lps = new int[len];
        int i = 0; int j = 1;
        while (i < len - 1) {
            while (p.charAt(i) == p.charAt(j)) {
                lps[i+1] = lps[i] + 1;
                i++; j++;
            }
            j = lps[i];
            i++;
        }
    }

    public static void main(String[] args) {
        String p = "xxbxabcxade";
        KMP kmp = new KMP(p);
        System.out.printf("lps=%s%n", Arrays.toString(kmp.lps));
    }

}
