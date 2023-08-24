package map;


/** The class that implements the Map interface using closed hashing;
 *  uses double hashing to resolve collisions */
public class HashTableClosedHashingDH implements Map
{
    private HashEntry[] map;
    private int capacity;
    private int size;
    private int hash2cap;
    private final float LOAD_LIMIT = 0.6f;


    public HashTableClosedHashingDH(int capacity)
    {
        map = new HashEntry[capacity];
        this.capacity = capacity;
        int next = capacity / 2 + 1; if (next % 2 == 0) next++;
        hash2cap = nextCap(next);
        size = 0;
    }

    /**
     * Return true if the map contains a (key, value) pair associated with this key,
     * otherwise return false.
     *
     * @param key key
     * @return true if the key (and the corresponding value) is the in map
     */
    public boolean containsKey(String key)
    {
        int h = HashEntry.hash(key, capacity);
        int i = 0;
        int k = HashEntry.hash2(key, hash2cap);
        while (map[(h + i*k) % capacity] != null)
        {
            if (map[(h + i*k) % capacity].getKey().compareTo(key) == 0) return true;
            i++;
        }
        return false;
    }

    /**
     * Add (key, value) to the map.
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
        int i = 0;
        int k = HashEntry.hash2(key, hash2cap);
        while (map[(h + i*k) % capacity] != null && !map[(h + i*k) % capacity].isDeleted()) i++;
        map[(h + i*k) % capacity] = new HashEntry(key, value);
    }

    private void rehash()
    {
        int next = nextCap(capacity * 2 + 1);
        HashTableClosedHashingDH rehashed = new HashTableClosedHashingDH(next);
        for (int i = 0; i < capacity; i++)
        {
            if (map[i] == null) continue;
            rehashed.put(map[i].getKey(), map[i].getValue());
        }
        map = rehashed.map;
        capacity = rehashed.capacity;
        hash2cap = rehashed.hash2cap;
    }

    private int nextCap(int x)
    {
        for (int i = 3; i < x / 2; i += 2)
            if (x % i == 0) return (nextCap(x + 2));

        return x;
    }


    /**
     * Return the value associated with the given key or null, if the map does not contain the key.
     * If the key is null, throw IllegalArgumentException.
     *
     * @param key key
     * @return value associated value
     */
    public Object get(String key) throws IllegalArgumentException
    {
        if (key == null) throw new IllegalArgumentException();

        int ii;
        int h = ii = HashEntry.hash(key, capacity);
        int i = 0;
        int k = HashEntry.hash2(key, hash2cap);
        while (true)
        {
            if (map[(h + i*k) % capacity] == null) return null;
            if (map[(h + i*k) % capacity].isDeleted())
            {
                i++; i %= capacity;
                if (i == ii) return null;
                continue;
            }
            if (map[(h + i*k) % capacity].getKey().compareTo(key) == 0) return map[(h + i*k) % capacity].getValue();
            i++; i %= capacity;
            if (i == ii) return null;
        }
    }

    /**
     * Remove a (key, value) entry if it exists.
     * Return the previous value associated with the given key, otherwise return null
     *
     * @param key key
     * @return previous value
     */
    public Object remove(String key) throws IllegalArgumentException
    {
        if (key == null) throw new IllegalArgumentException();
        if (size == 0) return null;

        int ii;
        int h = ii = HashEntry.hash(key, capacity);
        int i = 0;
        int k = HashEntry.hash2(key, hash2cap);
        while (true)
        {
            if (map[(h + i*k) % capacity] == null) return null;
            if (map[(h + i*k) % capacity].isDeleted())
            {
                i++; i %= capacity;
                if (i == ii) return null;
                continue;
            }
            if (map[(h + i*k) % capacity].getKey().compareTo(key) == 0)
            {
                size--;
                Object g = map[(h + i*k) % capacity].getValue();
                map[(h + i*k) % capacity].setDeleted(true);
                return g;
            }
            i++; i %= capacity;
            if (i == ii) return null;
        }
    }

    /**
     * Return the actual number of elements in the map.
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