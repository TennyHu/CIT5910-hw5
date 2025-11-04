import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class WordRecommender {

    public WordRecommender(String dictionaryFile) throws FileNotFoundException {
        // store the length - [string1, string2...] pair
        HashMap<Integer, ArrayList<String>> stringLen = new HashMap<>();

        FileInputStream dictionary = new FileInputStream(dictionaryFile);
        Scanner scanner = new Scanner(dictionary);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();
            int len = word.length();
            if (stringLen.containsKey(len)) {   // this string length key already been added
                stringLen.get(len).add(word);
            } else {    // create a new ArrayList and add to map
                ArrayList<String> list = new ArrayList<>();
                list.add(word);
                stringLen.put(len, list);
            }
        }

        scanner.close();

        //System.out.println(stringLen.get(20));

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
    Return an ArrayList consisting of topN number of word suggestions.
    Sorted from most to least “left-right similarity” to the misspelled word.
    Input: String word, need to be modified
           int tolerance, the max difference in string length
           double commonPercent, between 0.0 and 1.0, the % of shared chars -> char in common
           int topN, the # of edit suggestions for parameter word -> depends on similarity
    Output: ArrayList<String>, the suggested words
     */
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
      // TODO: change this!
      return null;
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
        System.out.println("the left similarity is " + result);
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

        System.out.println("the right similarity is " + result);
        return result;
    }






    public static void main(String[] args) throws IOException {
//        FileInputStream file = new FileInputStream("engDictionary.txt");
        WordRecommender recommender = new WordRecommender("engDictionary.txt");
        System.out.println("Hi!");
        double similarity = recommender.getSimilarity("aghast", "gross");
        System.out.println(similarity);
    }
  }