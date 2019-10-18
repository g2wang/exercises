public class Decode2 {

    public static void main(String[] args) {
        Decode2 p = new Decode2();
        String encodedS = "aux23sss45dxq67899yyll999k9999999999";
        int repeat = 129902;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= repeat; i++) {
            String k = p.decodeAtIndex(encodedS, i);
        }
        long mytime = System.currentTimeMillis() - start; 
        System.out.println("mytime: " + mytime);

        start = System.currentTimeMillis();
        for (int i = 1; i <= repeat; i++) {
            String k = p.decodeAtIndexStandard(encodedS, i);
        }
        long histime = System.currentTimeMillis() - start; 
        System.out.println("histime: " + histime);
    }

    public String decodeAtIndex(String S, int K) {
        int decodedIndex = -1;
        char decodedHead = S.charAt(0);
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c >= '0' && c <= '9') {
                int d = c - '0';
                if (d >= 2) {
                    int length = decodedIndex + 1;
                    int offset = K - 1 - decodedIndex;
                    int q = offset / length;
                    if (q < d - 1) {
                        int r = offset % length;
                        if (r == 0) {
                            return String.valueOf(decodedHead);
                        } else {
                            return decodeAtIndex(S, r);
                        }
                    } else {
                        decodedIndex += length * (d - 1);
                        if (decodedIndex == K - 1) {
                            return String.valueOf(decodedHead);
                        }
                    }
                }
           } else {
                decodedIndex++;
                decodedHead = c;
                if (decodedIndex == K - 1) {
                    return String.valueOf(decodedHead);
                }
            }
        }
        return null;
    }

    public String decodeAtIndexStandard(String S, int K) {
        long size = 0;
        int N = S.length();

        // Find size = length of decoded string
        for (int i = 0; i < N; ++i) {
            char c = S.charAt(i);
            if (Character.isDigit(c))
                size *= c - '0';
            else
                size++;
        }

        for (int i = N-1; i >= 0; --i) {
            char c = S.charAt(i);
            K %= size;
            if (K == 0 && Character.isLetter(c))
                return Character.toString(c);

            if (Character.isDigit(c))
                size /= c - '0';
            else
                size--;
        }

        throw null;
    }

}
