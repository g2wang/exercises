import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagram {
    public static void main(String[] args) {
        Anagram a = new Anagram();
        String[] anagrams = new String[]{
                "abed", "bead", "abet", "beat", "bade", "beta",
                "abets", "baste", "mace", "betas",
                "beast", "abut", "tabu", "tuba", "acme",
                "came", "acre", "care", "race", "acres",
                "castor", "cares", "beats", "races", "scare",
                "actors", "costar", "actress", "recasts", "airmen",
                "marine", "remain", "alert", "alter", "casters", "later",
                "alerted", "altered", "related", "treadle",
                "ales", "leas", "dealing", "sale", "seal",
                "aligned", "leading", "gallery", "wean", "largely",
                "regally", "amen", "mane", "mean", "name",
                "allergy", "anew", "wane", "angel", "angle",
                "antler", "apt", "learnt", "rental", "glean", "pat", "tap" };
        a.groupAnagrams(anagrams);
    }

    private void groupAnagrams(String[] words) {
        Map<String, List<WordWithAnagramKey>> anagramMap =
                Stream.of(words)
                        .map(WordWithAnagramKey::new)
                        .collect(Collectors.groupingBy(WordWithAnagramKey::getAnagramKey));
        for (List<WordWithAnagramKey> list : anagramMap.values()) {
            System.out.print("[ ");
            list.stream()
                    .map(WordWithAnagramKey::getWord)
                    .forEach(x -> System.out.print(x + " "));
            System.out.println("]");
        }
    }
}
