package wojciechgranicki.kata19;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
class Node implements Comparable<Node> {
    private String value;
    private Set<Node> neighbors = new HashSet<>();

    public Node(String value) {
        this.value = value;
    }

    public void addNeighbour(Node neighbor) {
        neighbors.add(neighbor);
    }

    public Set<Node> getNeighbors() {
        return neighbors;
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

    @Override
    public String toString() {
        return "Node{" +
                "value='" + value + '\'' +
                '}';
    }
}
