package wojciechgranicki.kata19;

import java.util.List;

import static wojciechgranicki.kata19.Kata19Solution.NOT_FOUND;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class Result {

    private int distance;
    private List<Node> path;

    public Result(List<Node> path) {
        this.distance = path.size();
        this.path = path;
    }

    public String pathToString() {
        return path.stream()
                .map(Node::getValue)
                .reduce((x, y) -> y + "->" + x).orElse(NOT_FOUND);
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }
}
