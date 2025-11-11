import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WordRecommenderTest {
    private String inputFile;
    private WordRecommender wordRecommender;

    @BeforeEach
    public void setUp() {
        try {
            this.inputFile = ".src/testDictionary.txt";
            System.out.println("Input file: " + this.inputFile);
            this.wordRecommender = new WordRecommender(this.inputFile);
        } catch(Exception e) {
            System.out.println("File not found!");
        }
    }

    @Test
    public void similaritySameLTest1() {
        double expectedSimilarity = 4.0;
        double actualSimilarity = wordRecommender.getSimilarity("apple", "apply");
        assertEquals(expectedSimilarity, actualSimilarity);
    }


    @Test
    public void similaritySameLTest2() {
        double expectedSimilarity = 2;
        double actualSimilarity = wordRecommender.getSimilarity("retake", "rebook");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similaritySameLTest3() {
        double expectedSimilarity = 2.0;
        double actualSimilarity = wordRecommender.getSimilarity("train", "chair");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest1() {
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("aghast", "gross");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest2() {
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("cat", "cats");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest3() {
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("run", "running");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest1() {
        double expectedSimilarity = 0.0;
        double actualSimilarity = wordRecommender.getSimilarity("abc", "xyz");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest2() {
        double expectedSimilarity = 1.0;
        double actualSimilarity = wordRecommender.getSimilarity("a", "a");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest3() {
        double expectedSimilarity = 0.0;
        double actualSimilarity = wordRecommender.getSimilarity("", "something");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void commonTest1() {
        double expectedCommon = 5.0 / 6.0;
        double actualCommon = wordRecommender.getCommon("committee", "comet");
        assertEquals(expectedCommon, actualCommon);
    }

    @Test
    public void commonTest2() {
        double expectedCommon = 4.0 / 7.0;
        double actualCommon = wordRecommender.getCommon("gardener", "nerdier");
        assertEquals(expectedCommon, actualCommon);
    }

    @Test
    public void getWordsTest1() {
        ArrayList<String> expectedWords = new ArrayList<>(Arrays.asList("cat", "sat", "mat", "hat"));
        ArrayList<String> actualWords =
                wordRecommender.getWordSuggestions("kat", 2, 0.5, 4);
        assertArrayEquals(expectedWords.toArray(), actualWords.toArray());
    }

    @Test
    public void getWordsTest2() {
        ArrayList<String> expectedWords = new ArrayList<>(Arrays.asList("mat", "cat", "sat", "hat"));
        ArrayList<String> actualWords =
                wordRecommender.getWordSuggestions("matt", 2, 0.5, 4);
        assertArrayEquals(expectedWords.toArray(), actualWords.toArray());
    }

    @Test
    public void getWordsTest3() {
        ArrayList<String> expectedWords = new ArrayList<>(Arrays.asList("dog", "log", "fog"));
        ArrayList<String> actualWords =
                wordRecommender.getWordSuggestions("dgo", 2, 0.5, 3);
        assertArrayEquals(expectedWords.toArray(), actualWords.toArray());
    }

    @Test
    public void getWordsTest4() {
        ArrayList<String> expectedWords = new ArrayList<>(Arrays.asList("sun", "run", "fun"));
        ArrayList<String> actualWords =
                wordRecommender.getWordSuggestions("sunn", 2, 0.5, 3);
        assertArrayEquals(expectedWords.toArray(), actualWords.toArray());
    }


}
