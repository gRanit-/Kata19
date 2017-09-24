package wojciechgranicki.kata19;

import java.net.URISyntaxException;

/**
 * Created by wojciechgranicki on 18.09.2017.
 */
public interface Kata19Solution {

    void loadDictionary(String filePath) throws URISyntaxException;
    String findShortestWordChain(String begin, String end);


}
