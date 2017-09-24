package wojciechgranicki.kata19;

import java.net.URISyntaxException;

/**
 * Created by wojciechgranicki on 18.09.2017.
 */
public interface Kata19Solution {
    String NOT_FOUND = "NOT_FOUND";

    void loadDictionary(String filePath) throws URISyntaxException;

    Result findShortestWordChain(String begin, String end);


}
