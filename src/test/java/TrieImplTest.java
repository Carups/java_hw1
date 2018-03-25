import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
class RandomGenString {
    private final char[] alphabet = {'a', 'A', 'b', 'B', 'c', 'C',
            'd', 'D', 'e', 'E', 'f', 'F', 'g', 'G', 'h', 'H', 'i', 'I',
            'j', 'J', 'k', 'K', 'l', 'L', 'm', 'M', 'n', 'N', 'o', 'O',
            'p', 'P', 'q', 'Q', 'r', 'R', 's', 'S', 't', 'T', 'u', 'U',
            'v', 'V', 'w', 'W', 'x', 'X', 'y', 'Y', 'z', 'Z'};
    private final Random random;

    public String getRandomString(int length) {
        char[] buf = new char[length];
        for (int idx = 0; idx < length; idx++) {
            buf[idx] = alphabet[random.nextInt(alphabet.length)];
        }
        return new String(buf);
    }

    RandomGenString()
    {
        random = new Random();
    }
}

public class TrieImplTest {
    RandomGenString rgs = new RandomGenString();
    @Test
    public void regularCase1() {
        final int testSize = 1000;
        final int stringSize = 100;
        TrieImpl trieImpl = new TrieImpl();
        String[] vocabular = new String[testSize];
        for (int i = 0; i < testSize; i++) {
            vocabular[i] = rgs.getRandomString(stringSize);
            trieImpl.add(vocabular[i]);
        }
        for (int i = 0; i < testSize; i++) {
            assertTrue(trieImpl.contains(vocabular[i]));
        }
        assertTrue(trieImpl.size() == testSize);
        for (int i = 0; i < testSize; i++) {
            trieImpl.remove(vocabular[i]);
        }
        assertTrue(trieImpl.size() == 0);
        for (int i = 0; i < testSize; i++) {
            assertFalse(trieImpl.contains(vocabular[i]));
        }

    }
    private int triviaPrefix(String[] vocabular, String prefix){
        int ans = 0;
        for (String word :
                vocabular) {
            boolean isPrefix = true;
            for(int i = 0; i < prefix.length(); ++i)
            {
                if (prefix.charAt(i) != word.charAt(i))
                {
                    isPrefix = false;
                    break;
                }
            }
            if (isPrefix == true){
                ans++;
            }
        }
        return ans;
    }
    @Test
    public void regularCase2() {
        final int testSize = 1000;
        final int prefSize = 50;
        final int stringSize = 100;

        TrieImpl trieImpl = new TrieImpl();
        String[] vocabular = new String[testSize];
        for (int i = 0; i < testSize; i++) {
            vocabular[i] = rgs.getRandomString(stringSize);
            trieImpl.add(vocabular[i]);
        }
        for (int i = 0; i < testSize; i++) {
            assertTrue(trieImpl.contains(vocabular[i]));
        }
        Random rng = new Random();
        String[] prefix = new String[prefSize];
        for (int i = 0; i < prefSize; i++){
            prefix[i] = rgs.getRandomString(rng.nextInt(stringSize / 10));
            assertTrue(trieImpl.howManyStartsWithPrefix(prefix[i]) == triviaPrefix(vocabular, prefix[i]));
        }
        for (int i = 0; i < testSize; i++) {
            trieImpl.remove(vocabular[i]);
        }
        for (int i = 0; i < prefSize; i++){
            assertTrue(trieImpl.howManyStartsWithPrefix(prefix[i]) == 0);
        }

    }
    @Test
    public void trieSpecificTest1() {
        TrieImpl trieImpl = new TrieImpl();
        trieImpl.add("abacaba");
        assertFalse(trieImpl.contains("abac"));
        trieImpl.add("a");
        assertFalse(trieImpl.contains("abac"));
        trieImpl.remove("abac");
        assertFalse(trieImpl.contains("abac"));
        assertTrue(trieImpl.size() == 2);
        trieImpl.remove("a");
        assertTrue(trieImpl.size() == 1);
    }

    @Test
    public void trieSpecificTest2() {
        TrieImpl trieImpl = new TrieImpl();
        trieImpl.add("aba");
        trieImpl.add("abacaba");
        trieImpl.remove("abacaba");
        assertTrue(trieImpl.contains("aba"));
    }
    @Test
    public void trieSpecificTest3() {
        TrieImpl trieImpl = new TrieImpl();
        assertTrue(trieImpl.add("a"));
        assertFalse(trieImpl.add("a"));
        assertTrue(trieImpl.add("aba"));
        assertFalse(trieImpl.add("aba"));
        assertTrue(trieImpl.remove("a"));
        assertFalse(trieImpl.remove("a"));
    }

}