package wojciechgranicki.kata19;

import java.util.Collections;
import java.util.List;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class Result {
    public static final Result NOT_FOUND = new Result(Collections.emptyList());

    private int distance;
    private List<Node> path;

    public Result(List<Node> path) {
        this.distance = path.size();
        this.path = path;
    }

    public String pathToString() {
        return path.stream()
                .map(Node::getValue)
                .reduce((x, y) -> x + "->" + y).orElse(Kata19Solution.NOT_FOUND);
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
