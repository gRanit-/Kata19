package wojciechgranicki.kata19;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializerTest {
    private FileBasedGraphInitializer graphInitializer;
    private final String SMALL_WORD_LIST = "/smallWordList.txt";

    @Before
    public void setup() {
        graphInitializer = new FileBasedGraphInitializer();
    }

    @Test
    public void loadDictionary() throws Exception {
        Map<Integer, Set<String>> wordsBySize = graphInitializer.loadDictionary(SMALL_WORD_LIST);

        int wordCount = wordsBySize.entrySet().stream()
                .map(e -> e.getValue().size())
                .reduce((x, y) -> x + y)
                .orElse(0);

        assertEquals(17, wordCount);

        for (Map.Entry<Integer, Set<String>> e : wordsBySize.entrySet())
            for (String word : e.getValue())
                assertEquals(e.getKey(), (Integer) word.length());
    }

    @Test
    public void createGraph() throws Exception {
        graphInitializer.loadDictionary(SMALL_WORD_LIST);
        Map<String, Node> graph = graphInitializer.createGraph();
        assertEquals(17, graph.size());
        Node cat = graph.get("cat");
        Node cot = graph.get("cot");

        assertEquals("cat", cat.getValue());
        assertEquals("cot", cot.getValue());
        assertEquals("cot", cat.getNeighbors().stream()
                .findFirst().orElse(new Node(""))
                .getValue());
        assertEquals("cat", cot.getNeighbors().stream()
                .findFirst().orElse(new Node(""))
                .getValue());
    }

    @Test
    public void createGraphWithOwnAlphabet() throws IOException {
        graphInitializer.setAlphabet(Stream.of('c', 'o', 't', 'a').collect(Collectors.toSet()));
        graphInitializer.loadDictionary(SMALL_WORD_LIST);
        Map<String, Node> graph = graphInitializer.createGraph();
        assertEquals(2, graph.size());

        graphInitializer.setAlphabet(Stream.of('c', 'o', 't').collect(Collectors.toSet()));
        graphInitializer.loadDictionary(SMALL_WORD_LIST);
        graph = graphInitializer.createGraph();
        assertEquals(1, graph.size());
    }

}