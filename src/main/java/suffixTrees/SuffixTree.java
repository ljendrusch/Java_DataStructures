package suffixTrees;


import java.util.ArrayList;
import java.util.List;


public class SuffixTree
{

    private Node root;
    private final static int ASCII = 97;


    /**
     * Create a SuffixTree for a given string s.
     * Iterate backwards, and call insert method to insert each suffix into the tree.
     */
    public SuffixTree(String s)
    {
        root = new Node();
        for (int i = s.length()-1; i >= 0 ; i--)
        {
            insert(s.substring(i), i);
        }
    }


    private class Node
    {
        private Node[] children;
        private char value;
        private ArrayList<TailData> tailData;


        public Node()
        {
            children = new Node[26];
        }

        public Node(char c)
        {
            children = new Node[26];
            value = c;
        }

        public Node(char c, String str, int ind)
        {
            children = new Node[26];
            value = c;
            addTail(str, ind);
        }


        public void addTail(String str, int ind)
        {
            if (tailData == null) tailData = new ArrayList<>(1);
            tailData.add(new TailData(str, ind));
        }
    }


    private static class TailData
    {
        private String string;
        private int index;


        public TailData(String str, int ind)
        {
            string = str;
            index = ind;
        }
    }


    /** Insert a new suffix (that starts at index ind in the string) into the suffix tree */
    public void insert(String word, int ind) { root = insert(word.toLowerCase(), ind, root); }
    /**
     * Insert a new suffix that starts at index = ind, into the suffix tree with
     * the given root.
     */
    private Node insert(String word, int ind, Node tree)
    {
        if (word.compareTo("") == 0) return tree;

        if (tree == null) { tree = new Node(); }

        Node ciq = tree;
        for (int depth = 0; depth < word.length();)
        {
            int i = getChildIndex(word.substring(depth));
            if (ciq.children[i] == null) ciq.children[i] = new Node(word.charAt(depth));
            ciq = ciq.children[i];
            depth++;
            if (depth == word.length()) ciq.addTail(word, ind);
        }

        return tree;
    }


    /**
     * Return a suffix tree as a string in human readable form (using preorder traversal and indentations). For the root,
     * print "Root" instead of an empty string. See project description for details.
     * */
    public String toString() { return toString(root, 0); }
    /**
     * A private recursive method that returns the string representation of the suffix tree with the given root
     * with i indentations. If i = 0, the value at the root should not be indented.
     * If i = 1, there should be one space printed before the value at the root.
     * If i = 2, there should be two spaces etc.
     */
    private String toString(Node tree, int i)
    {
        StringBuilder blr = new StringBuilder();

        for (int j = 0; j < i; j++) blr.append("~");
        if (i == 0) blr.append("Root");
        else blr.append(tree.value);
        if (tree.tailData != null)
        {
            for (TailData td: tree.tailData)
            {
                blr.append(", ");
                blr.append(td.index);
            }
        }
        blr.append("\n");

        for (Node n: tree.children)
            if (n != null) blr.append(toString(n, i + 1));

        return blr.toString();
    }


    /**
     * Return an index in the array of children that corresponds to the first letter of the given word. If
     * the first letter is 'a', the method returns 0; if the first letter is 'b', the method returns 1 etc.
     * @param word
     * @return
     */
    public int getChildIndex(String word) { return word.charAt(0) - ASCII; }


    /**
     * Return true if a string represented by a given suffix tree contains a given
     * word. Return false otherwise.
     */
    public boolean containsSubstring(String word) { return containsSubstring(word, root); }
    /**
     * Return true if a string represented by the suffix tree with the given root,
     * contains a given word. Return false otherwise.
     * Should be recursive and make use of the suffix tree.
     */
    private boolean containsSubstring(String word, Node tree)
    {
        if (tree.children[getChildIndex(word)] != null) return containsSubstring(word.substring(1), tree.children[getChildIndex(word)]);
        return false;
    }


    /**
     * Check if a string represented by a given suffix tree contains a given
     * word, and if yes, return the list of indices where each occurrence of word starts.
     * Should be sorted in ascending order.
     * Example: if the suffix tree is built for the word "banana" and we call this method on "ana",
     * the method should return [1, 3].
     */
    public List<Integer> getSubstringIndices(String word)
    {
        List<Integer> indices = new ArrayList<>();
        return getSubstringIndices(word, root, indices);
    }
    /**
     * Check if a string represented by the suffix tree with the given root,
     * contains a given word. Return the List of indices where the
     * substring occurrences start. Should be recursive and make use of the suffix tree.
     */
    private List<Integer> getSubstringIndices(String word, Node tree, List<Integer> indices)
    {
        if (word == null)
        {
            for (Node n: tree.children)
            {
                if (n != null) getSubstringIndices(null, n, indices);
            }

            if (tree.tailData != null)
            {
                for (TailData td: tree.tailData)
                {
                    indices.add(td.index);
                }
            }
        }
        else if (tree.children[getChildIndex(word)] != null)
        {
            getSubstringIndices(word.length() > 1 ? word.substring(1) : null, tree.children[getChildIndex(word)], indices);
        }

        return indices;
    }


    /**
     * Return the number of occurrences of a given word in the string, represented by the suffix tree
     */
    public int numOccurrences(String word)
    {
        return getSubstringIndices(word).size();
    }


    /**
     * If the suffix tree contains a given suffix, return the index where it starts in the original string,
     * otherwise return -1.
     */
    public int containsSuffix(String suffix) { return containsSuffix(suffix, root); }
    /**
     * If a given suffix tree contains a given suffix, return its index, otherwise return -1.
     * Should be recursive and make use of the suffix tree.
     */
    private int containsSuffix(String suffix, Node tree)
    {
        if (suffix == null) return tree.tailData == null ? -1 : tree.tailData.get(0).index;
        if (tree.children[getChildIndex(suffix)] != null) return containsSuffix(suffix.length() > 1 ? suffix.substring(1) : null, tree.children[getChildIndex(suffix)]);
        return -1;
    }
}