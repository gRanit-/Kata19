package wojciechgranicki.kata19;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.util.Collections.EMPTY_MAP;

public class BasicGraphInitializer implements GraphInitializer {
    private static final Logger logger = Logger.getLogger(BasicGraphInitializer.class.getName());

    protected Map<Integer, Set<String>> wordsGroupedByLength;
    protected Map<String, Node> nodes;
    protected Set<Character> alphabet = new HashSet<>(40);


    @Override
    public Map<String, Node> createGraph() {
        if (wordsGroupedByLength == null || wordsGroupedByLength.isEmpty())
            return EMPTY_MAP;
        logger.info("Creating graph...");
        for (Map.Entry<Integer, Set<String>> neighbors : wordsGroupedByLength.entrySet()) {
            Set<String> sameLengthWords = neighbors.getValue();
            for (String word : sameLengthWords)
                connectNeighbors(computeIfAbsent(word), sameLengthWords);
        }
        logger.info("Done.");
        return nodes;
    }

    public void loadDictionary(List<String> words, boolean initAlphabet) {
        if (words == null)
            throw new IllegalArgumentException("Dictionary can't be null");
        wordsGroupedByLength = new HashMap<>();
        if (initAlphabet)
            initAlphabetAndAddToDictionary(words);
        else
            addToDictionaryIfHasValidChars(words);

    }

    private void initAlphabetAndAddToDictionary(List<String> words) {
        for (String w : words) {
            IntStream.range(0, w.length())
                    .forEach(i -> alphabet.add(w.charAt(i)));
            addToDictionary(w);
        }
    }

    private void addToDictionaryIfHasValidChars(List<String> words) {
        for (String w : words) {
            boolean invalid = IntStream.range(0, w.length())
                    .anyMatch(i -> !alphabet.contains(w.charAt(i)));
            if (!invalid)
                addToDictionary(w);
        }
    }

    protected void addToDictionary(String word) {
        Set<String> words = wordsGroupedByLength.computeIfAbsent(word.length(), HashSet::new);
        words.add(word);
    }

    private void connectNeighbors(Node node, Set<String> sameLengthWords) {
        int wordLen = node.getValue().length();
        int allPossibleNeighbors = alphabet.isEmpty() ? Integer.MAX_VALUE : alphabet.size() * wordLen;
        String word = node.getValue();
        if (sameLengthWords.size() > allPossibleNeighbors)
            for (Character c : alphabet) // iterate over all possible neighbors
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
        Node neighbor = computeIfAbsent(neighborWord);
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

    private Node computeIfAbsent(String word) {
        return nodes.computeIfAbsent(word, Node::new);
    }


    public void setAlphabet(Set<Character> alphabet) {
        if (alphabet == null)
            throw new IllegalArgumentException("Alphabet can't be null");
        this.alphabet = alphabet;
    }

    public void setDictionary(Map<Integer, Set<String>> wordsGroupedByLength) {
        if (wordsGroupedByLength == null)
            throw new IllegalArgumentException("Dictionary can't be null");
        this.wordsGroupedByLength = wordsGroupedByLength;
    }


    public Map<Integer, Set<String>> getWordsGroupedByLength() {
        return wordsGroupedByLength;
    }
}
