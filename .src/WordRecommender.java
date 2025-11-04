import java.util.ArrayList;

public class WordRecommender {

    public WordRecommender(String dictionaryFile) {

    }



    /*
    Return a double, consisting the similarity (commonPercent) for word1 and word2
    (left align + right align) / 2 = similarity
    Input: String word1
           String word2
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
    Input: String word1
           String word2
    Output: double, the % of left similarity between input words
     */
    public double getLeftSimilarity(String word1, String word2) {

        return 0.0;
    }


    /*
    Return a double, consisting the right similarity (commonPercent) for word1 and word2
    Input: String word1
           String word2
    Output: double, the % of right similarity between input words
     */
    public double getRightSimilarity(String word1, String word2) {
        return 0.0;
    }
  }