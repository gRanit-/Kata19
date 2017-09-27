package wojciechgranicki.kata19;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.*;

public class BasicGraphInitializerTest {
    BasicGraphInitializer basicGraphInitializer;

    @Before
    public void setup() {
        basicGraphInitializer = new BasicGraphInitializer();
    }

    @Test
    public void setDictionary() throws Exception {
        List<String> words = Arrays.asList("cat", "dog", "cot", "cog", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa");
        basicGraphInitializer.loadDictionary(words);
        Map<Integer, Set<String>> wordsGroupedByLength = basicGraphInitializer.getWordsGroupedByLength();
        for (int i = 3; i <= 7; i++)
            assertNotNull(wordsGroupedByLength.get(i));

        assertEquals(4, wordsGroupedByLength.get(3).size());
        for (int i = 4; i <= 7; i++)
            assertEquals(1, wordsGroupedByLength.get(i).size());

        basicGraphInitializer.setAlphabet(new HashSet<>());
        basicGraphInitializer.loadDictionary(words);
        wordsGroupedByLength = basicGraphInitializer.getWordsGroupedByLength();
        assertEquals(5, wordsGroupedByLength.size());

        basicGraphInitializer.setAlphabet(Stream.of('d', 'o', 'g').collect(Collectors.toSet()));
        basicGraphInitializer.loadDictionary(words);
        wordsGroupedByLength = basicGraphInitializer.getWordsGroupedByLength();
        assertEquals(1, wordsGroupedByLength.size());
    }

    @Test
    public void setAlphabet() throws Exception {
        try {
            basicGraphInitializer.setAlphabet(null);
            fail("Basic Graph Initializer didn't throw exception");
        } catch (IllegalArgumentException e) {

        }
        try {
            basicGraphInitializer.setAlphabet(Stream.of('d', 'o', '\n', ' ')
                    .collect(Collectors.toSet()));
            fail("Basic Graph Initializer didn't throw exception");
        } catch (IllegalArgumentException e) {

        }
    }
}