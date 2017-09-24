package wojciechgranicki.kata19;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
class Node implements Comparable<Node> {
    private String value;
    private List<Node> neighbors = new ArrayList<>();

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
