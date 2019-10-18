import java.util.Arrays;

public class WordWithAnagramKey {
    private String word = null;
    private String anagramKey = null;

    public WordWithAnagramKey(String word) {
        this.word = word;
        this.anagramKey = findAnagramKey(word);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAnagramKey() {
        return anagramKey;
    }

    public void setAnagramKey(String anagramKey) {
        this.anagramKey = anagramKey;
    }

    public static String findAnagramKey(String word) {
        char[] a = word.toCharArray();
        Arrays.parallelSort(a);
        return new String(a);
    }
}
