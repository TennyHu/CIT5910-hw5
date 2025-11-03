import java.util.ArrayList;

public class WordRecommender {

    public WordRecommender(String dictionaryFile) {    
      // TODO: change this!
    }

    /*
    Return a double, consisting the similarity (commonPercent) for word1 and word2
    (left align + right align) / 2 = similarity
    Input: String word1
           String word2
    Output: double, the % of similarity between input words
     */
    public double getSimilarity(String word1, String word2) {
      // TODO: change this!
      return 0.0;
    }

    /*
    Return an ArrayList consisting of topN number of word suggestions.
    Sorted from most to least “left-right similarity” to the misspelled word.
    Input: String word, need to be modified
           int tolerance, the max difference in string length
           double commonPercent, between 0.0 and 1.0, the % of shared chars
           int topN, the # of edit suggestions for parameter word
    Output: ArrayList<String>, the suggested words
     */
    public ArrayList<String> getWordSuggestions(String word, int tolerance, double commonPercent, int topN) {
      // TODO: change this!
      return null;
    }
  
    // You can of course write other methods as well.
  }