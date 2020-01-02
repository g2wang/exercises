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
import java.util.TreeMap;
import java.util.ArrayDeque;

public class SearchSuggestion {

    class Node {
        char val;
        Node parent;
        TreeMap<Character, Node> children;
        boolean isWord;
    }

    public static void main(String[] args) {
        String[] products = new String[]{
            "mobile","mouse","moneypot","monitor","mousepad"
        };
        String searchWord = "mouse";
        // String[] products = new String[]{"havana"};
        // String searchWord = "tatiana";
        SearchSuggestion ss = new SearchSuggestion();
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
        List<List<String>> ans = new ArrayList<>();
        if (searchWord == null || searchWord.length() == 0) return ans;
        long start = System.currentTimeMillis();
        Node root = new Node();
        root.val = 0;
        root.children = new TreeMap<>();
        for (String p : products) {
            addToTrie(root, p);
        }
        long afterTrie = System.currentTimeMillis();
        // query
        char[] cs = searchWord.toCharArray();
        Node n = root;
        for (char c : cs) {
            Node k = (n == null? null : n.children.get(c)); 
            List<String> top3 = new ArrayList<>();
            if (k != null) fillTop3(k, top3);
            ans.add(top3);
            n = k;
        }
        long afterQuery = System.currentTimeMillis();
        System.out.printf("time building trie: %d; time doing query: %d\n",
                afterTrie - start, afterQuery - afterTrie);
        return ans;
    }

    private void fillTop3(Node k, List<String> top3) {
        if (k.isWord) {
            top3.add(getString(k));
            if (top3.size() == 3) return;
        }
        for (Map.Entry<Character, Node> e : k.children.entrySet()) {
            Node n = e.getValue();
            if (top3.size() < 3) {
                fillTop3(n, top3);
            }
        }
    } 

    private String getString(Node n) {
        if (n == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(n.val);
        Node p = n.parent;
        while (p != null && p.val != 0) {
            sb.append(p.val);
            p = p.parent;
        }
        return sb.reverse().toString();
    }

    private void addToTrie(Node node, String p) {
        if (p == null || p.length() == 0) return;
        Node n = node.children.get(p.charAt(0)); 
        if (n == null) { 
            n = new Node();
            n.parent = node;
            n.children = new TreeMap<>();
            n.val = p.charAt(0); 
            node.children.put(n.val, n);
        }
        if (p.length() == 1) {
            n.isWord = true;
        } else {
            addToTrie(n, p.substring(1));
        }
    }

}
