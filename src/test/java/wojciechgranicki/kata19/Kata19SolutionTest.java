package wojciechgranicki.kata19;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static wojciechgranicki.kata19.Result.NOT_FOUND;

/**
 * Created by wojciechgranicki on 18.09.2017.
 */
public class Kata19SolutionTest {
    private static final String SMALL_WORD_LIST = "/smallWordList.txt";
    private static final String BIG_WORD_LIST = "/wordlist.txt";

    private Kata19Solution kata19Solution;
    private FileBasedGraphInitializer fileBasedGraphInitializer;
    private List<Chain> chains;

    class Chain {
        String begin;
        String end;
        int size;

        public Chain(String begin, String end, int size) {
            this.begin = begin;
            this.end = end;
            this.size = size;
        }
    }

    @Before
    public void setup() {
        fileBasedGraphInitializer = new FileBasedGraphInitializer();
        kata19Solution = new Kata19SolutionImpl();
        chains = Arrays.asList(
                new Chain("lead", "gold", 4),
                new Chain("gold", "lead", 4),
                new Chain("ruby", "code", 5),
                new Chain("code", "ruby", 5),
                new Chain("dog", "cat", 4),
                new Chain("cat", "dog", 4)
        );


    }

    @Test
    public void findShortestWordChainSmallTest() throws Exception {
        fileBasedGraphInitializer.loadDictionary(SMALL_WORD_LIST);
        Map<String, Node> graph = fileBasedGraphInitializer.createGraph();
        checkDistancesAndPaths(graph);
    }

    @Test
    public void findShortestWordChainBigTest() throws Exception {
        fileBasedGraphInitializer.loadDictionary(BIG_WORD_LIST);
        Map<String, Node> graph = fileBasedGraphInitializer.createGraph();
        checkDistancesAndPaths(graph);
    }

    @Test
    public void wordDoesntExist() {
        Result r1 = kata19Solution.findShortestWordChain(null, null);
        assertEquals(NOT_FOUND, r1);
    }

    @Test
    public void chainDoesntExist() {
        Node n1 = new Node("TEST");
        Node n2 = new Node("KESB");
        Node n3 = new Node("BEST");
        n1.addNeighbour(n3);
        n2.addNeighbour(n1);
        n3.addNeighbour(n1);


        Result r1 = kata19Solution.findShortestWordChain(n1, n2);
        assertEquals(NOT_FOUND, r1);
    }

    private void checkDistancesAndPaths(Map<String, Node> graph) {
        for (Chain chain : chains) {
            Result r1 = kata19Solution.findShortestWordChain(graph.get(chain.begin), graph.get(chain.end));
            List<Node> path = r1.getPath();
            assertEquals(chain.size, r1.getDistance());
            assertEquals(chain.begin, path.get(0).getValue());
            assertEquals(chain.end, path.get(path.size() - 1).getValue());
            assertIsChain(path);
        }


    }

    private static void assertIsChain(List<Node> path) {
        for (int i = 1; i < path.size(); i++)
            assertAreNeighbors(path.get(i - 1).getValue(), path.get(i).getValue());
    }

    private static void assertAreNeighbors(String w1, String w2) {
        int distance = 0;

        if (w1.length() == w2.length())
            for (int i = 0; i < w1.length(); i++)
                if (w1.charAt(i) != w2.charAt(i))
                    distance++;

        assertEquals(true, distance == 1);

    }

}