import java.util.HashMap;
import java.util.Map;


class Node {
    HashMap<Character, Node> next = new HashMap<>();
    int availableTerminals = 0;
    boolean isTerminal = false;
}

interface Trie {
    /**
     * Expected complexity: O(|element|)
     * @return <tt>true</tt> if this set did not already contain the specified
     *         element
     */
    boolean add(String element);

    /**
     * Expected complexity: O(|element|)
     */
    boolean contains(String element);

    /**
     * Expected complexity: O(|element|)
     * @return <tt>true</tt> if this set contained the specified element
     */
    boolean remove(String element);

    /**
     * Expected complexity: O(1)
     */
    int size();

    /**
     * Expected complexity: O(|prefix|)
     */
    int howManyStartsWithPrefix(String prefix);
}

public class TrieImpl implements Trie {
    @Override
    public boolean add (String element)
    {
        if (!this.contains(element)) {
            Node current = root_;
            for (int index = 0; index < element.length(); ++index) {
                Node next = current.next.get(element.charAt(index));
                if (next == null)
                {
                    next = new Node();
                    current.next.put(element.charAt(index), next);
                }
                current.availableTerminals++;
                current = next;
            }
            current.availableTerminals++;
            current.isTerminal = true;
            return true;
        }
        else {
            return false;
        }

    }
    @Override
    public boolean remove (String element)
    {
        if (this.contains(element)){
            Node current = root_;
            root_.availableTerminals--;
            for (int index = 0; index < element.length(); ++index) {
                Node next = current.next.get(element.charAt(index));

                next.availableTerminals--;
                if (next.availableTerminals == 0)
                {
                    current.next.remove(element.charAt(index));
                    return true;
                }
                current = next;
            }
            current.isTerminal = false;
            return true;
        }
        return false;
    }
    @Override
    public boolean contains (String element)
    {
        Node current = root_;
        for (int index = 0; index < element.length(); ++index) {
            current = current.next.get(element.charAt(index));
            if(current == null) {
                return false;
            }
        }
        if (!current.isTerminal)
            return false;
        else
            return true;
    }
    @Override
    public int size ()
    {
        return root_.availableTerminals;
    }

    private Node root_ = new Node();
    @Override
    public int howManyStartsWithPrefix(String prefix)
    {
        Node current = root_;
        for (int index = 0; index < prefix.length(); ++index) {
            current = current.next.get(prefix.charAt(index));
            if (current == null)
                return 0;
        }
        return current.availableTerminals;
    }

    public static void main(String[] args)
    {
        TrieImpl tr = new TrieImpl();
        tr.add("asdf");
        tr.add("asdq");
        System.out.println(tr.howManyStartsWithPrefix("asdf"));
        tr.add("asdf");
        System.out.println(tr.howManyStartsWithPrefix("asd"));
        tr.remove("asdf");
        System.out.println(tr.howManyStartsWithPrefix("asdf"));

    }
}
