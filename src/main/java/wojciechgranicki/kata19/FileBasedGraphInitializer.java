package wojciechgranicki.kata19;


import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by wojciechgranicki on 24.09.2017.
 */
public class FileBasedGraphInitializer extends BasicGraphInitializer {
    private static final Logger logger = Logger.getLogger(FileBasedGraphInitializer.class.getName());

    FileBasedGraphInitializer() {
        this.alphabet = new HashSet<>(40);
    }


    public Map<Integer, Set<String>> loadDictionary(String filePath) throws IOException {
        logger.info("Loading dictionary...");
        wordsGroupedByLength = new HashMap<>(); //assignments here assert method reusability
        nodes = new HashMap<>();

        InputStream stream = (Kata19SolutionImpl.class.getResourceAsStream(filePath));

        boolean initAlphabet = false;

        if (alphabet.isEmpty())
            initAlphabet = true;

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        reader.read();// first char is whitespace (at least on Mac)
        int i = reader.read();
        while (isNonWhiteSpaceCharacterType(i)) {
            String word = readWord(i, reader, initAlphabet);
            if (word != null)
                addToDictionary(word);

            i = reader.read();
        }
        logger.info("Done.");
        return wordsGroupedByLength;
    }

    private String readWord(int i, BufferedReader reader, boolean initAlphabet) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        while (isNonWhiteSpaceCharacterType(i)) {
            char c = (char) i;
            if (!alphabet.contains(c)) {
                if (initAlphabet)
                    alphabet.add(c);
                else {
                    moveToTheNextWord(i, reader);
                    return null;
                }
            }
            stringBuilder.append(c);
            i = reader.read();

        }
        return stringBuilder.toString();
    }

    private boolean isNonWhiteSpaceCharacterType(int c) {
        return !(Character.isWhitespace(c) || Character.isSpaceChar(c) || Character.getType(c) == 0 || c == -1);
    }

    private void moveToTheNextWord(int i, Reader reader) throws IOException {
        while (isNonWhiteSpaceCharacterType(i)) i = reader.read();
    }


}
