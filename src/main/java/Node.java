import java.util.HashMap;

public class Node {
    HashMap<Character, Node> next = new HashMap<>();
    int availableTerminals = 0;
    boolean isTerminal = false;
}