import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class WordRecommender {
    private String dictionaryFile;
    private HashMap<Integer, ArrayList<String>> stringLen;

    public WordRecommender(String dictionaryFile) throws FileNotFoundException {
        this.dictionaryFile = dictionaryFile;
        this.stringLen = new HashMap<>();

        try {
            FileInputStream dictionary = new FileInputStream(dictionaryFile);
            Scanner scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                int len = word.length();
                if (this.stringLen.containsKey(len)) {   // this string length key already been added
                    this.stringLen.get(len).add(word);
                } else {    // create a new ArrayList and add to map
                    ArrayList<String> list = new ArrayList<>();
                    list.add(word);
                    this.stringLen.put(len, list);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }

    /*
    Return a double, consisting the similarity (commonPercent) for word1 and word2
    (left align + right align) / 2 = similarity
    Input: String word1, input word
           String word2, word suggestion
    Output: double, the % of similarity between input words
     */
    public double getSimilarity(String word1, String word2) {
      double left = getLeftSimilarity(word1, word2);
      double right = getRightSimilarity(word1, word2);

      return (left + right) / 2;
    }

    /*
    Return a double, showing the percentage of characters in common between String word1 and word2
    |aSet AND bSet| / |aSet OR bSet|
    Input: String word1
           String word2
    Output: double commonPercentage
     */
    public double getCommon(String word1, String word2) {
        HashSet<Character> set1 = new HashSet<>();
        HashSet<Character> set2 = new HashSet<>();
        for (char i : word1.toCharArray()) {
            set1.add(i);
        }
        for (char i : word2.toCharArray()) {
            set2.add(i);
        }

        int intersection = setIntersection(set1, set2);
        int union = setUnion(set1, set2);
        return (double)intersection / union;
    }

    /*
    Return an ArrayList consisting of topN number of word suggestions.
    Sorted from most to least “left-right similarity” to the misspelled word.
    Input: String word, need to be modified
           int tolerance, the max difference in string length
           double commonPercent, between 0.0 and 1.0, the % of shared chars -> char in common
           int topN, the # of edit suggestions for parameter word -> depends on similarity
    Output: ArrayList<String>, the suggested words
     */
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {

        int wordLen = word.length();
        ArrayList<String> filter = new ArrayList<>();
        ArrayList<String> suggestions = new ArrayList<>();
        HashMap<Integer, ArrayList<String>> stringLen = this.stringLen;

        // 1. find candidate word with length of accepted tolerance
        for (int i = wordLen - tolerance; i <= wordLen + tolerance; i++) {
            if (stringLen.containsKey(i)) {
                filter.addAll(stringLen.get(i));
            }
        }

        // 2. delete strings not qualified for the common Percent shared
        for (int i = filter.size() - 1; i >= 0; i--) {
            double commoon = getCommon(filter.get(i), word);
            if (commoon < commonPercent) {
                filter.remove(i);
            }
        }

        // 3. rank the topN candidates based on left-right similarity
        for (String s : filter) {
            double similarity = getSimilarity(s, word);
            int insertIndex = 0;
            for (int i = 0; i < suggestions.size(); i++) {
                if (getSimilarity(suggestions.get(i), word) >= similarity) { // move the smaller similarity to the back
                    insertIndex = i + 1;
                } else {    // when the current element in suggestions has smaller similarity
                    break;  // append this s word to the front of suggestions
                }
            }
            suggestions.add(insertIndex, s);
        }

        // Testing
        System.out.println("--------------------------------");
        for (String s : suggestions) {
            double similarity = getSimilarity(s, word);
            System.out.println("Word: " + s + " Similarity: " + similarity);
        }

        // 4. Save the topN suggestions
        if (suggestions.size() >= topN) {
            suggestions = new ArrayList<>(suggestions.subList(0, topN));
        }


        // TESTING
        System.out.println("--------------------------------");
        for (String s : suggestions) {
            System.out.println(s);
        }

        return suggestions;
    }

    /*
    Return the HashMap containing String length - ArrayList<String> pairs
    Input: None
    Output: None
     */
    public HashMap<Integer, ArrayList<String>> getStringLen() {
        return this.stringLen;
    }

    /*
    Return a double, consisting the left similarity (commonPercent) for word1 and word2
    Input: String word1, input word
           String word2, word suggestion
    Output: double, the % of left similarity between input words
     */
    public double getLeftSimilarity(String word1, String word2) {
        double result = 0.0;
        ArrayList<Character> list1 = new ArrayList<>();
        ArrayList<Character> list2 = new ArrayList<>();
        char[] array1 = word1.toCharArray();
        char[] array2 = word2.toCharArray();

        for (int i = 0; i < array1.length; i++) {
            list1.add(array1[i]);
        }
        for (int i = 0; i < array2.length; i++) {
            list2.add(array2[i]);
        }

        int i = 0;
        while (i < list1.size() && i < list2.size()) {
            if (list1.get(i).equals(list2.get(i))) {
                result++;
            }
            i++;
        }

        return result;
    }


    /*
    Return a double, consisting the right similarity (commonPercent) for word1 and word2
    Input: String word1, input word
           String word2, word suggestion
    Output: double, the % of right similarity between input words
     */
    public double getRightSimilarity(String word1, String word2) {
        double result = 0.0;
        ArrayList<Character> list1 = new ArrayList<>();
        ArrayList<Character> list2 = new ArrayList<>();
        char[] array1 = word1.toCharArray();
        char[] array2 = word2.toCharArray();

        for (int i = array1.length - 1; i >= 0; i--) {
            list1.add(array1[i]);
        }
        for (int i = array2.length - 1; i >= 0; i--) {
            list2.add(array2[i]);
        }

        int i = 0;
        while (i < list1.size() && i < list2.size()) {
            if (list1.get(i).equals(list2.get(i))) {
                result++;
            }
            i++;
        }

        return result;
    }

    /*
    Return the number of characters set1 and set2 share in common
    Input: HashSet set1
           Hashset set2
    Output: int # of characters
     */
    public int setIntersection(HashSet<Character> set1, HashSet<Character> set2) {
        int result = 0;
        for (Character word : set1) {
            if (set2.contains(word)) {
                result++;
            }
        }
        return result;
    }

    /*
    Return the number of characters contained in either set1 or set2
    Input: HashSet set1
           Hashset set2
    Output: int # of characters
    */
    public int setUnion(HashSet<Character> set1, HashSet<Character> set2) {
        HashSet<Character> union = new HashSet<>();
        union.addAll(set1);
        union.addAll(set2);
        return union.size();
    }



    public static void main(String[] args) throws IOException {
        WordRecommender recommender = new WordRecommender(".src/engDictionary.txt");
        System.out.println("Hi!");
        ArrayList<String> suggestions = recommender.getWordSuggestions("ewook", 2, 0.5, 5);

    }
  }