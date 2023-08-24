package map;


/** A custom LinkedList class to be used in the open hashing / separate chaining implementation
 * of a hash table. You need class Node too.
 */
public class LinkedList
{
    private class Node
    {
        HashEntry entry;
        Node next;

        Node(HashEntry entry)
        {
            this.entry = entry;
            next = null;
        }
    }

    private Node head;
    private int size;


    public void insert(HashEntry entry)
    {
        size++;

        if (head == null) head = new Node(entry);
        else
        {
            Node n = head;
            int i = size;
            while (i > 2) { n = n.next; i--; }
            n.next = new Node(entry);
        }
    }

    public HashEntry[] getAll()
    {
        HashEntry[] hearr = new HashEntry[size];

        Node n = head;
        for (int i = 0; i < size; i++)
        {
            hearr[i] = n.entry;
            n = n.next;
        }

        return hearr;
    }

    public Object get(String key)
    {
        Node n = head;
        while (n != null)
        {
            if (n.entry.getKey().compareTo(key) == 0) return n.entry.getValue();
            n = n.next;
        }

        return null;
    }

    public int indexOf(String key)
    {
        Node n = head;
        for (int i = 0; i < size; i++)
        {
            if (n.entry.getKey().compareTo(key) == 0) return i;
            n = n.next;
        }

        return -1;
    }

    public int size() { return size; }

    public Object remove(String key)
    {
        if (size == 0) return null;
        if (size == 1)
        {
            if (head.entry.getKey().compareTo(key) == 0)
            {
                size--;
                Object g = head.entry.getValue();
                head = null;
                return g;
            }
            return null;
        }

        Node n = head.next;
        Node prev = head;
        for (int i = 1; i < size; i++)
        {
            if (n.entry.getKey().compareTo(key) == 0)
            {
                size--;
                Object g = n.entry.getValue();
                prev.next = n.next;
                return g;
            }

            prev = n;
            n = n.next;
        }

        return null;
    }

    public String toString()
    {
        StringBuilder stb = new StringBuilder(head.entry.toString());

        Node n = head.next;
        for (; n != null; n = n.next)
        {
            stb.append(", ").append(n.entry);
        }
        return stb.toString();
    }
}