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

    Map<Integer, SortedSet<String>> wordsByItsSize = new HashMap<>();
    Map<String, Node> nodes = new HashMap<>();
    final List<Character> alphabet;

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
        int i = 0;
        try (Scanner scanner = new Scanner(stream)) {

            while (scanner.hasNext()) {
                String word = scanner.next();
                SortedSet<String> words = wordsByItsSize.get(word.length());
                if (words == null) {
                    words = new TreeSet<>();
                    wordsByItsSize.put(word.length(), words);
                }
                words.add(word);
                nodes.put(word, new Node(word));
                i++;
            }
        }

        logger.info("Creating graph...");
        createGraph();
        logger.info("Done.");
        System.out.println(System.currentTimeMillis() - start);


    }

    private void createGraph() {
        for (Map.Entry<Integer, SortedSet<String>> neighbors : wordsByItsSize.entrySet()) {
            for (String word : neighbors.getValue()) {
                Node node = nodes.get(word);
                if (neighbors.getValue().size() > alphabet.size() * word.length())
                    for (Character c : alphabet)
                        for (int i = 0; i < word.length(); i++) {
                            String possibleN = word.substring(0, i) + c + word.substring(i + 1, word.length());
                            if (neighbors.getValue().contains(possibleN))
                                addNeighbour(node, possibleN);
                        }
                else {
                    neighbors.getValue().stream()
                            .filter(possibleNeighbor -> areNeighbors(word, possibleNeighbor))
                            .forEach(possibleNeighbor -> addNeighbour(node, possibleNeighbor));

                }

            }
        }
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

    public String findShortestWordChain(String begin, String end) {
        long start = System.currentTimeMillis();
        logger.info("Searching graph...");

        if (begin.length() != end.length())
            return null;

        SortedSet<String> possibleNeighbors = wordsByItsSize.get(begin.length());

        if (possibleNeighbors == null)
            return null;

        List<Node> queue = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        Node endNode = nodes.get(end);
        Node beginNode = nodes.get(begin);
        beginNode.dist = 0;

        queue.add(beginNode);

        // String path = begin;
        Map<Node, Node> parentNodes = new HashMap<>();
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

        //  logger.info(System.currentTimeMillis() - start + "");
        return path.stream()
                .map(Node::getValue)
                .reduce((x, y) -> y + "->" + x).get();

    }

    private void calculateCost(Node begin, Node end) {

    }

    class Node implements Comparable<Node> {
        String value;
        int dist = -1;
        List<Node> neighbors = new ArrayList<>();

        public Node(String value) {
            this.value = value;
        }

        public void addNeighbour(Node neighbor) {
            neighbors.add(neighbor);
        }

        public List<Node> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(List<Node> neighbors) {
            this.neighbors = neighbors;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;

            Node node = (Node) o;

            return value.equals(node.value);

        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public int compareTo(Node o) {
            return this.value.compareTo(o.value);
        }
    }


}
