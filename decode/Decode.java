public class Decode {

    public static void main(String[] args) {
        Decode p = new Decode();
        String answer = "hhhahhhahhha";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 12; i++) {
            String k = p.decodeAtIndex("h3a3", i);
            sb.append(k);
        }
        System.out.println("result:" + sb);
        System.out.println("answer:" + answer);
        System.out.println("result is correct?" + answer.equals(sb.toString()));
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

                    /*
                    for (int j = 0; j < d - 1; j++) {
                        decodedIndex += length;
                        if (decodedIndex == K - 1) {
                            return String.valueOf(decodedHead);
                        } else if (decodedIndex > K - 1) {
                            decodedIndex -= length;
                            int offset = K - 1 - decodedIndex;
                            return decodeAtIndex(S, offset);
                        }
                    }
                    */
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

}
