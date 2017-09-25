package wojciechgranicki.kata19;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public interface GraphInitializer {

    Map<String, Node> createGraph();

    default Set<Character> createEnglishAlphabet() {
        HashSet<Character> alphabet = new HashSet<>();
        // English alphabet + '
        for (char i = 'a'; i <= 'z'; i++)
            alphabet.add(i);
        for (char i = 'A'; i <= 'Z'; i++)
            alphabet.add(i);
        alphabet.add('\'');
        return alphabet;
    }

}
