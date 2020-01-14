/**
1268. Search Suggestions System
Medium

Given an array of strings products and a string searchWord. We want to design a system that suggests at most three product names from products after each character of searchWord is typed. Suggested products should have common prefix with the searchWord. If there are more than three products with a common prefix return the three lexicographically minimums products.

Return list of lists of the suggested products after each character of searchWord is typed. 

Example 1:

Input: products = ["mobile","mouse","moneypot","monitor","mousepad"], searchWord = "mouse"
Output: [
["mobile","moneypot","monitor"],
["mobile","moneypot","monitor"],
["mouse","mousepad"],
["mouse","mousepad"],
["mouse","mousepad"]
]
Explanation: products sorted lexicographically = ["mobile","moneypot","monitor","mouse","mousepad"]
After typing m and mo all products match and we show user ["mobile","moneypot","monitor"]
After typing mou, mous and mouse the system suggests ["mouse","mousepad"]

Example 2:

Input: products = ["havana"], searchWord = "havana"
Output: [["havana"],["havana"],["havana"],["havana"],["havana"],["havana"]]
Example 3:

Input: products = ["bags","baggage","banner","box","cloths"], searchWord = "bags"
Output: [["baggage","bags","banner"],["baggage","bags","banner"],["baggage","bags"],["bags"]]
Example 4:

Input: products = ["havana"], searchWord = "tatiana"
Output: [[],[],[],[],[],[],[]]
 
Constraints:

1 <= products.length <= 1000
There are no repeated elements in products.
1 <= Σ products[i].length <= 2 * 10^4
All characters of products[i] are lower-case English letters.
1 <= searchWord.length <= 1000
All characters of searchWord are lower-case English letters. 
 */

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SearchSuggestionLightWeightTrie {

    private static final int MAX_PER_SEARCH = 3;

    class Node {
        Map<Character, Node> childrenMap = new HashMap<>();
        Map<Character, List<Integer>> wordIndicesMap = new HashMap<>();
    }

    public static void main(String[] args) {
        String[] products = new String[]{
            "mobile","mouse","moneypot","monitor","mousepad"
        };
        String searchWord = "mouse";
        // String[] products = new String[]{"havana"};
        // String searchWord = "tatiana";
        SearchSuggestionLightWeightTrie ss = new SearchSuggestionLightWeightTrie();
        List<List<String>> ans = ss.suggestedProducts(products, searchWord);
        System.out.println("[");
        for (int i = 0; i < ans.size(); i++) {
            List<String> l = ans.get(i);
            System.out.printf("%s%s\n", Arrays.toString(l.toArray(new String[]{})),
                    i == ans.size()-1?"":","); 
        }
        System.out.println("]");
    }


    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Arrays.sort(products);
        List<List<String>> ans = new ArrayList<>(searchWord.length());
        for (int i = 0; i < searchWord.length(); i++) {
            ans.add(new ArrayList<String>(MAX_PER_SEARCH));
        }
        Node root = new Node();
        for (int i = 0; i < products.length; i++) {
            addToTrie(root, products[i], i); 
        }        

        // query
        char[] ca = searchWord.toCharArray();
        Node n = root;
        for (int i = 0; i < ca.length; i++) {
            if (n == null) break; 
            char c = ca[i];
            List<Integer> wordIndices = n.wordIndicesMap.get(c);
            if (wordIndices != null) {
                for (int index : wordIndices) {
                    ans.get(i).add(products[index]);
                }
            }
            n = n.childrenMap.get(c);
        }        

        return ans;
    }
    
    private void addToTrie(Node node, String p, int i) {
        char c = p.charAt(0);
        Node child = node.childrenMap.get(c); 
        if (child == null) {
            child = new Node();
            node.childrenMap.put(c, child); 
        }
        List<Integer> wordIndices = node.wordIndicesMap.get(c);
        if (wordIndices == null) {
            wordIndices = new ArrayList<>(MAX_PER_SEARCH);
            node.wordIndicesMap.put(c, wordIndices); 
        }
        if (wordIndices.size() < MAX_PER_SEARCH) wordIndices.add(i);
        if (p.length() > 1) addToTrie(child, p.substring(1), i);  
    }

}
