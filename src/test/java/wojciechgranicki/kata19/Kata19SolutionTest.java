package wojciechgranicki.kata19;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Created by wojciechgranicki on 18.09.2017.
 */
public class Kata19SolutionTest {
    Logger logger = Logger.getLogger(Kata19SolutionTest.class.getName());
    Kata19Solution kata19Solution = new Kata19SolutionImpl();


    @Test
    public void loadDictionary() throws Exception {

    }

    @Test
    public void findShortestWordChainSmallTest() throws Exception {

        kata19Solution.loadDictionary("/smallWordList.txt");

        Result r1 = kata19Solution.findShortestWordChain("gold", "lead");
        assertEquals(r1.getDistance(), 4);
        assertEquals(r1.pathToString(), "gold->goad->load->lead");
        Result r2 = kata19Solution.findShortestWordChain("lead", "gold");
        assertEquals(r2.getDistance(), 4);
        assertEquals(r2.pathToString(), "lead->load->goad->gold");
        Result r3 = kata19Solution.findShortestWordChain("ruby", "code");
        assertEquals(r3.getDistance(), 6);
        assertEquals(r3.pathToString(), "ruby->rubs->robs->rods->rode->code");
        Result r4 = kata19Solution.findShortestWordChain("cat", "dog");
        assertEquals(r4.getDistance(), 4);
        assertEquals(r4.pathToString(), "cat->cot->cog->dog");

    }

    @Test
    public void findShortestWordChainBigTest() throws Exception {

        kata19Solution.loadDictionary("/wordlist.txt");

//        logger.info(kata19Solution.findShortestWordChain("gold", "lead"));
//        logger.info(kata19Solution.findShortestWordChain("lead", "gold"));
//        logger.info(kata19Solution.findShortestWordChain("ruby", "code"));
//        logger.info(kata19Solution.findShortestWordChain("cat", "dog"));

    }

}