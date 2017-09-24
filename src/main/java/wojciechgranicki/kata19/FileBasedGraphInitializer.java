package wojciechgranicki.kata19;


import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Collections.EMPTY_MAP;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializer implements GraphInitializer {
    static final Logger logger = Logger.getLogger(FileBasedGraphInitializer.class.getName());
    private Map<Integer, Set<String>> wordsByItsSize = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();
    private List<Character> alphabet;

    FileBasedGraphInitializer() {
        alphabet = new ArrayList<>();
        for (char i = 'a'; i <= 'z'; i++)
            alphabet.add(i);
        for (char i = 'A'; i <= 'Z'; i++)
            alphabet.add(i);
        alphabet.add('\'');
    }

    public Map<Integer, Set<String>> loadDictionary(String filePath) {
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
                //  nodes.put(word, new Node(word));

                words.add(word);
            }
        }
        logger.info("Done.");
        return wordsByItsSize;
    }

    public Map<Integer, Set<String>> getWordsByItsSize() {
        return wordsByItsSize;
    }

    @Override
    public Map<String, Node> createGraph() {
        if (wordsByItsSize == null || wordsByItsSize.isEmpty())
            return EMPTY_MAP;

        logger.info("Creating graph...");
        for (Map.Entry<Integer, Set<String>> neighbors : wordsByItsSize.entrySet()) {
            Set<String> possibleNeighbors = neighbors.getValue();
            for (String word : possibleNeighbors) {

                Node node = nodes.get(word);
                if (node == null) {
                    node = new Node(word);
                    nodes.put(word, node);
                }

                connectNeighbors(node, possibleNeighbors);
            }
        }
        logger.info("Done.");
        return nodes;
    }

    private void connectNeighbors(Node node, Set<String> possibleNeighbors) {
        int wordLen = node.getValue().length();
        String word = node.getValue();
        if (possibleNeighbors.size() > alphabet.size() * wordLen)
            for (Character c : alphabet)
                for (int i = 0; i < wordLen; i++) {
                    String possibleN = substituteCharacterAtIndex(word, c, i);
                    if (possibleNeighbors.contains(possibleN))
                        addNeighbour(node, possibleN);
                }
        else
            possibleNeighbors.stream()
                    .filter(possibleNeighbor -> areNeighbors(word, possibleNeighbor))
                    .forEach(possibleNeighbor -> addNeighbour(node, possibleNeighbor));
    }

    private String substituteCharacterAtIndex(String word, Character c, int i) {
        return word.substring(0, i) + c + word.substring(i + 1, word.length());
    }

    private void addNeighbour(Node node, String neighborWord) {
        Node neighbor = nodes.get(neighborWord);
        if (neighbor == null) {
            neighbor = new Node(neighborWord);
            nodes.put(neighborWord, neighbor);
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
}
