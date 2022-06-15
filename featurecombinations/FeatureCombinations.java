/**
Given a map<String, Set<String>> featureToValues to represent possible values for each feature name.
Like: {"name" : ["A", "B", "C"],
         "Phone" : ["1", "2"] }
And give you a list of feature names we care about (feature names can have duplicates). Generate all possible combinations (one feature value of a feature can’t be used twice). 
Also, we don't care about the order of the feature value of the same feature. If the list has duplicate features, like [“name”, “name”], then A, B and B, A are treated as the same combination, you only need to output it once.

[“name”, “name”],

A, B
A, C
B, C

One example:
Feature list we interested is [“name”, “phone”, “name”]. Then, the expected output are 
[“A”, “1”, “B”], [“A”, “1”, “C”], [“B”, “1”, “C”], [“A”, “2”, “B”], [“A”, “2”, “C”], [“B”, “2”, “C”]
*/

import java.util.*;

public class FeatureCombinations {

    public static void main(String[] args) {

        Map<String, Set<String>> featureToValues = new HashMap<>();
        List<String> featureList = new ArrayList<>();

        String feature = "name";
        Set<String> values = new HashSet<>();
        values.add("A"); values.add("B"); values.add("C");
        featureList.add(feature);
        featureToValues.put(feature, values);

        feature = "phone";
        values = new HashSet<>();
        values.add("1"); values.add("2");
        // featureList.add(feature);
        featureToValues.put(feature, values);

        featureList.add("name");
        featureList.add("name");

        FeatureCombinations fb = new FeatureCombinations();
        List<List<String>> ans = fb.generateAllPossibleComb(featureToValues, featureList);
        System.out.printf("ans.size(): %d%n", ans.size());

        for (List<String> list : ans) {
            System.out.printf("%s%n", list);
        }
    }

    public List<List<String>> generateAllPossibleComb(Map<String, Set<String>> featureToValues, List<String> featureList) {

        if (featureList.isEmpty()) return new ArrayList<>();

        String feature = featureList.get(0);
        Set<String> valSet = featureToValues.get(feature);

        List<List<String>> ans = new ArrayList<>();

        Map<String, Set<String>> newFeatureToValues = new HashMap<>(featureToValues);
        Set<String> newValSet = new HashSet<>(valSet);
        newFeatureToValues.put(feature, newValSet);

        List<String> newFeatureList = new ArrayList<>(featureList);
        newFeatureList.remove(0);

        for (String val : valSet) {

            newValSet.remove(val);

            if (newFeatureList.isEmpty()) {
                List<String> comb = new ArrayList<>();
                comb.add(val);
                ans.add(comb);
            } else {
                List<List<String>> tailList = generateAllPossibleComb(newFeatureToValues, newFeatureList);
                for (List<String> tail : tailList) {
                    List<String> comb = new ArrayList<>();
                    comb.add(val);
                    comb.addAll(tail);
                    ans.add(comb);
                }
            }
        }

        return ans;
    }

}

