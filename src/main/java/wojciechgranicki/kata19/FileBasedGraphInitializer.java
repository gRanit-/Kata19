package wojciechgranicki.kata19;


import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

import static java.util.Collections.EMPTY_MAP;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializer implements GraphInitializer {
    private static final Logger logger = Logger.getLogger(FileBasedGraphInitializer.class.getName());
    private Map<Integer, Set<String>> wordsGroupedByLength = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();
    private List<Character> alphabet;


    FileBasedGraphInitializer() {
        alphabet = new LinkedList<>();
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
                Set<String> words = wordsGroupedByLength.get(word.length());
                if (words == null) {
                    words = new HashSet<>();
                    wordsGroupedByLength.put(word.length(), words);
                }
                words.add(word);
            }
        }
        logger.info("Done.");
        return wordsGroupedByLength;
    }

    public Map<Integer, Set<String>> getWordsGroupedByLength() {
        return wordsGroupedByLength;
    }

    @Override
    public Map<String, Node> createGraph() {
        if (wordsGroupedByLength == null || wordsGroupedByLength.isEmpty())
            return EMPTY_MAP;

        logger.info("Creating graph...");
        for (Map.Entry<Integer, Set<String>> neighbors : wordsGroupedByLength.entrySet()) {
            Set<String> sameLengthWords = neighbors.getValue();
            for (String word : sameLengthWords)
                connectNeighbors(putIfAbsent(word), sameLengthWords);

        }
        logger.info("Done.");
        return nodes;
    }

    private Node putIfAbsent(String word) {
        Node node = nodes.get(word);
        if (node == null) {
            node = new Node(word);
            nodes.put(word, node);
        }
        return node;
    }

    private void connectNeighbors(Node node, Set<String> sameLengthWords) {
        int wordLen = node.getValue().length();
        int allPossibleNeighbors = alphabet.size() * wordLen;
        String word = node.getValue();
        if (sameLengthWords.size() > allPossibleNeighbors)
            for (Character c : alphabet)
                for (int i = 0; i < wordLen; i++) {
                    String possibleNeighbor = substituteCharacterAtIndex(word, c, i);
                    if (sameLengthWords.contains(possibleNeighbor))
                        addNeighbor(node, possibleNeighbor);
                }
        else
            sameLengthWords.stream()
                    .filter(possibleNeighbor -> areNeighbors(word, possibleNeighbor))
                    .forEach(possibleNeighbor -> addNeighbor(node, possibleNeighbor));
    }

    private String substituteCharacterAtIndex(String word, Character c, int i) {
        return word.substring(0, i) + c + word.substring(i + 1, word.length());
    }

    private void addNeighbor(Node node, String neighborWord) {
        Node neighbor = putIfAbsent(neighborWord);
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
