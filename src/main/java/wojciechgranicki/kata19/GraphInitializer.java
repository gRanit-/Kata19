package wojciechgranicki.kata19;

import java.util.Map;
import java.util.Set;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public interface GraphInitializer {

    Map<String, Node> createGraph(Map<Integer, Set<String>> wordsBySize);

}
