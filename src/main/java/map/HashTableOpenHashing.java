package map;


public class HashTableOpenHashing implements Map
{
    private LinkedList[] map;
    private int capacity;
    private int size;
    private final float LOAD_LIMIT = 0.6f;

    public HashTableOpenHashing(int capacity)
    {
        map = new LinkedList[capacity];
        this.capacity = capacity;
        size = 0;
    }

    /** Return true if the map contains a (key, value) pair associated with this key,
     *  otherwise return false.
     *
     * @param key  key
     * @return true if the key (and the corresponding value) is the in map
     */
    public boolean containsKey(String key)
    {
        if (map[HashEntry.hash(key, capacity)] == null) return false;
        return map[HashEntry.hash(key, capacity)].indexOf(key) >= 0;
    }

    /** Add (key, value) to the map.
     * Will replace previous value that this key was mapped to.
     * If key is null, throw IllegalArgumentException.
     *
     * @param key
     * @param value associated value
     */
    public void put(String key, Object value) throws IllegalArgumentException
    {
        if (key == null) throw new IllegalArgumentException();

        size++;
        if ((float) size / (float) capacity > LOAD_LIMIT) rehash();

        int h = HashEntry.hash(key, capacity);
        if (map[h] == null) map[h] = new LinkedList();
        map[h].insert(new HashEntry(key, value));
    }

    private void rehash()
    {
        int next = nextCap(capacity * 2 + 1);
        HashTableOpenHashing rehashed = new HashTableOpenHashing(next);
        for (int i = 0; i < capacity; i++)
        {
            if (map[i] == null) continue;
            for (HashEntry he : map[i].getAll())
            {
                rehashed.put(he.getKey(), he.getValue());
            }
        }
        map = rehashed.map;
        capacity = rehashed.capacity;
    }

    private int nextCap(int x)
    {
        for (int i = 3; i < x / 2; i += 2)
            if (x % i == 0) return (nextCap(x+2));

        return x;
    }

    /** Return the value associated with the given key or null, if the map does not contain the key.
     * If the key is null, throw IllegalArgumentException.
     *
     * @param key key
     * @return value associated value
     */
    public Object get(String key) throws IllegalArgumentException
    {
        if (key == null) throw new IllegalArgumentException();

        if (!containsKey(key)) return null;

        return map[HashEntry.hash(key, capacity)].get(key);
    }

    /** Remove a (key, value) entry if it exists.
     * Return the previous value associated with the given key, otherwise return null
     * @param key key
     * @return previous value
     */
    public Object remove(String key) throws IllegalArgumentException
    {
        if (key == null) throw new IllegalArgumentException();
        if (size == 0) return null;

        if (!containsKey(key)) return null;

        size--;
        return map[HashEntry.hash(key, capacity)].remove(key);
    }

    /** Return the actual number of elements in the map.
     *
     * @return number of elements currently in the map.
     */
    public int size() { return size; }

    public String toString()
    {
        StringBuilder stb = new StringBuilder();

        for (int i = 0; i < capacity; i++)
        {
            stb.append(i).append(": ");
            if (map[i] == null) stb.append("null\r\n");
            else stb.append(map[i]).append("\r\n");
        }

        return stb.toString();
    }
}