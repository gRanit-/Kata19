package wojciechgranicki.kata19;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static java.util.Collections.EMPTY_MAP;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializer implements GraphInitializer {
    private static final Logger logger = Logger.getLogger(FileBasedGraphInitializer.class.getName());

    private Map<Integer, Set<String>> wordsGroupedByLength = new HashMap<>();
    private Map<String, Node> nodes = new HashMap<>();
    private Set<Character> alphabet;


    FileBasedGraphInitializer() {
        this.alphabet = new HashSet<>(40);
    }


    public Map<Integer, Set<String>> loadDictionary(String filePath) throws IOException {
        logger.info("Loading dictionary...");
        InputStream stream = (Kata19SolutionImpl.class.getResourceAsStream(filePath));
        boolean initAlphabet = false;
        if (alphabet.isEmpty())
            initAlphabet = true;

        try (InputStreamReader reader = new InputStreamReader(stream, "UTF-8")) {
            int c;
            while ((c = reader.read()) != -1) {
                String word = readWord(reader, c, initAlphabet);
                if (!word.isEmpty())
                    addToDictionary(word);
            }
        }
        logger.info("Done.");
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

    public void setAlphabet(Set<Character> alphabet) {
        this.alphabet = alphabet;
    }


    private String readWord(InputStreamReader reader, int currChar, boolean initAlphabet) throws IOException {
        String word = "";
        while (!Character.isWhitespace(currChar) && currChar != -1) {
            if (Character.isLetter(currChar) || currChar == '\'') {
                char c = (char) currChar;
//                if (!initAlphabet && !alphabet.contains(c))
//                    return "";
//                else
                    alphabet.add(c);
                word += c;
            } else
                return "";
            currChar = reader.read();
        }
        return word;
    }

    private void addToDictionary(String word) {
        Set<String> words = wordsGroupedByLength.get(word.length());
        if (words == null) {
            words = new HashSet<>();
            wordsGroupedByLength.put(word.length(), words);
        }
        words.add(word);
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
            for (Character c : alphabet) // iterate over all possible neighbors
                for (int i = 0; i < wordLen; i++) {
                    String possibleNeighbor = substituteCharacterAtIndex(word, c, i);
                    System.out.println(possibleNeighbor);
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
