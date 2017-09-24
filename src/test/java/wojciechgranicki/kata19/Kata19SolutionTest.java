package wojciechgranicki.kata19;

import org.junit.Test;

/**
 * Created by wojciechgranicki on 18.09.2017.
 */
public class Kata19SolutionTest {
    Kata19Solution kata19Solution = new Kata19SolutionImpl();


    @Test
    public void loadDictionary() throws Exception {

    }

    @Test
    public void findShortestWordChain() throws Exception {

        kata19Solution.loadDictionary("/wordlist.txt");

        System.out.println(kata19Solution.findShortestWordChain("gold", "lead"));
        System.out.println(kata19Solution.findShortestWordChain("ruby", "code"));
        System.out.println(kata19Solution.findShortestWordChain("cat", "dog"));

    }

}