package wojciechgranicki.kata19;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by wojciechgranicki on 23.09.2017.
 */
public class Kata19SolutionImpl implements Kata19Solution {
    static final Logger logger = Logger.getLogger(Kata19SolutionImpl.class.getName());

    Map<Integer, Set<String>> wordsByItsSize = new HashMap<>();
    Map<String, Node> nodes = new HashMap<>();
    List<Character> alphabet;

    Kata19SolutionImpl() {
        alphabet = new ArrayList<>();
        for (char i = 'a'; i <= 'z'; i++)
            alphabet.add(i);
        for (char i = 'A'; i <= 'Z'; i++)
            alphabet.add(i);
        alphabet.add('\'');
    }

    public void loadDictionary(String filePath) throws URISyntaxException {
        long start = System.currentTimeMillis();
        // words.
        logger.info("Loading dictionary...");
        InputStream stream = (Kata19SolutionImpl.class.getResourceAsStream(filePath));

        try (Scanner scanner = new Scanner(stream)) {

            while (scanner.hasNext()) {
                String word = scanner.next();
                Set<String> words = wordsByItsSize.get(word.length());
                if (words == null) {
                    words = new HashSet<>();
                    wordsByItsSize.put(word.length(), words);
                }
                words.add(word);
                nodes.put(word, new Node(word));

            }
        }

        logger.info("Creating graph...");
        createGraph();
        logger.info("Done.");
        System.out.println(System.currentTimeMillis() - start);


    }

    private void createGraph() {
        for (Map.Entry<Integer, Set<String>> neighbors : wordsByItsSize.entrySet()) {
            Set<String> neighborSet = neighbors.getValue();
            for (String word : neighborSet) {
                Node node = nodes.get(word);
                if (neighborSet.size() > alphabet.size() * word.length())
                    for (Character c : alphabet)
                        for (int i = 0; i < word.length(); i++) {
                            String possibleN = substituteCharacterAtIndex(word, c, i);
                            if (neighborSet.contains(possibleN))
                                addNeighbour(node, possibleN);
                        }
                else
                    neighbors.getValue().stream()
                            .filter(possibleNeighbor -> areNeighbors(word, possibleNeighbor))
                            .forEach(possibleNeighbor -> addNeighbour(node, possibleNeighbor));


            }
        }
    }

    private String substituteCharacterAtIndex(String word, Character c, int i) {
        return word.substring(0, i) + c + word.substring(i + 1, word.length());
    }

    private void addNeighbour(Node node, String word) {
        Node neighbor = nodes.get(word);
        if (neighbor == null) {
            neighbor = new Node(word);
            nodes.put(word, neighbor);
        }
        node.addNeighbour(neighbor);
    }

    private boolean areNeighbors(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))
                count++;
            if (count > 1)
                return false;
        }
        return count == 1;
    }

    public Result findShortestWordChain(String begin, String end) {
        long start = System.currentTimeMillis();
        logger.info("Searching graph...");

        if (begin.length() != end.length())
            return null;

        Set<String> possibleNeighbors = wordsByItsSize.get(begin.length());

        if (possibleNeighbors == null)
            return null;

        List<Node> queue = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        Node endNode = nodes.get(end);
        Node beginNode = nodes.get(begin);
        Map<Node, Node> parentNodes = new HashMap<>();

        queue.add(beginNode);

        while (!queue.isEmpty()) {
            Node curr = queue.remove(0);
            if (curr == endNode)
                break;
            curr.getNeighbors().stream()
                    .filter(n -> !visited.contains(n))
                    .forEach(n -> {
                        visited.add(n);
                        queue.add(n);
                        parentNodes.put(n, curr);
                    });

            visited.add(curr);
        }


        logger.info("Done.");

        Node node = endNode;
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            if (node == beginNode)
                break;
            node = parentNodes.get(node);
        }

        return new Result(path);

    }


}
