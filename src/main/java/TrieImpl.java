import java.util.HashMap;
import java.util.Map;


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

}
