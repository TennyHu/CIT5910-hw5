import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordRecommenderTest {

    @Test
    public void similaritySameLTest1() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 4.0;
        double actualSimilarity = wordRecommender.getSimilarity("apple", "apply");
        assertEquals(expectedSimilarity, actualSimilarity);
    }


    @Test
    public void similaritySameLTest2() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 2;
        double actualSimilarity = wordRecommender.getSimilarity("retake", "rebook");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similaritySameLTest3() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 2.0;
        double actualSimilarity = wordRecommender.getSimilarity("train", "chair");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest1() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("aghast", "gross");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest2() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("cat", "cats");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityDiffLTest3() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 1.5;
        double actualSimilarity = wordRecommender.getSimilarity("run", "running");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest1() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 0.0;
        double actualSimilarity = wordRecommender.getSimilarity("abc", "xyz");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest2() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 1.0;
        double actualSimilarity = wordRecommender.getSimilarity("a", "a");
        assertEquals(expectedSimilarity, actualSimilarity);
    }

    @Test
    public void similarityEdgeTest3() throws FileNotFoundException {
        WordRecommender wordRecommender = new WordRecommender(".src/engDictionary.txt");
        double expectedSimilarity = 0.0;
        double actualSimilarity = wordRecommender.getSimilarity("", "something");
        assertEquals(expectedSimilarity, actualSimilarity);
    }


}
