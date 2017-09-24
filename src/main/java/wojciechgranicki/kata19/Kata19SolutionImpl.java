package wojciechgranicki.kata19;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by wojciechgranicki on 23.09.2017.
 */
public class Kata19SolutionImpl implements Kata19Solution {
    private static final Logger logger = Logger.getLogger(Kata19SolutionImpl.class.getName());


    public Result findShortestWordChain(Node beginNode, Node endNode) {
        logger.info("Searching graph...");

        if (areNullOrHaveDifferentLengths(beginNode, endNode))
            return Result.NOT_FOUND;

        List<Node> queue = new ArrayList<>();
        Set<Node> visited = new HashSet<>();

        Map<Node, Node> parentNode = new HashMap<>();

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
                        parentNode.put(n, curr);
                    });

            visited.add(curr);
        }

        logger.info("Done.");
        List<Node> path = extractPath(parentNode, beginNode, endNode);

        if (path.size() <= 1)
            return Result.NOT_FOUND;
        else
            return new Result(path);

    }

    private List<Node> extractPath(Map<Node, Node> parentNode, Node beginNode, Node endNode) {
        List<Node> path = new ArrayList<>();
        Node node = endNode;
        while (node != null) {
            path.add(node);
            if (node == beginNode)
                break;
            node = parentNode.get(node);
        }
        Collections.reverse(path);
        return path;
    }


    private boolean areNullOrHaveDifferentLengths(Node beginNode, Node endNode) {
        return beginNode == null || endNode == null || beginNode.getValue().length() != endNode.getValue().length();

    }

}
