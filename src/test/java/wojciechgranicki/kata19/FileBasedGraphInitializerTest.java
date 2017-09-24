package wojciechgranicki.kata19;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializerTest {
    FileBasedGraphInitializer graphInitializer;

    @Before
    public void setup() {
        graphInitializer = new FileBasedGraphInitializer();
    }

    @Test
    public void loadDictionary() throws Exception {
        Map<Integer, Set<String>> wordsBySize = graphInitializer.loadDictionary("/wordlist.txt");

        int wordCount = wordsBySize.entrySet().stream()
                .map(e -> e.getKey() * e.getValue().size())
                .reduce((x, y) -> x + y)
                .orElse(0);

        assertEquals(338882, wordCount);
    }

    @Test
    public void createGraph() throws Exception {

    }

}