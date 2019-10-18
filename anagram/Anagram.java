import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

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
        Map<String, List<String>> anagramMap = new HashMap<>();
        for (String w : words) {
            String k = findAnagramKey(w);
            List<String> group = anagramMap.get(k);
            if (group == null) {
                group = new ArrayList<>();
                anagramMap.put(k, group);
            }
            group.add(w);
        }

        for (List<String> list : anagramMap.values()) {
            System.out.println(Arrays.toString(list.toArray(new String[]{})));
        }
    }

    public static String findAnagramKey(String word) {
        char[] a = word.toCharArray();
        Arrays.parallelSort(a);
        return new String(a);
    }

}
