import java.util.Arrays;

public class FirstIndexOf {

    public static void main(String[] args) {
        FirstIndexOf f = new FirstIndexOf();
        int[] a = new int[]{1, 3, 4};
        int[] b = new int[]{1, 3, 4};
        int ans = f.firstIndexOf(a, b);
        System.out.printf("%s, %s -> %d%n", Arrays.toString(a), Arrays.toString(b), ans);
    }

    /**
     * if a contains b, return the first index of b in a, otherwise, return -1;
     */
    public int firstIndexOf(int[] a, int[] b) {
        if (b.length > a.length) return -1;
        int ans = -1;
        for (int i = 0; i <= a.length - b.length; i++) {
            boolean found = true;
            for (int j = 0; j < b.length; j++) {
                if (a[i+j] != b[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                ans = i;
                break;
            }
        }
        return ans;
    }

}
