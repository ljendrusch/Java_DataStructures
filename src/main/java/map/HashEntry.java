package map;


import java.math.BigInteger;

/** An entry in the hash table. Stores a key, a corresponding value and a boolean flag */
public class HashEntry
{
    private String key;
    private Object value;
    private boolean deleted; // if there was previously a value here that was later deleted.


    /**
     * Constructor for class HashEntry
     * @param key key
     * @param value corresponding value
     */
    public HashEntry(String key, Object value)
    {
        this.key = key;
        this.value  = value;
        this.deleted = false;
    }

    /**
     * Returns the key stored in the entry
     * @return key
     */
    public String getKey() { return key; }

    /**
     * Returns the value stored in the entry
     * @return value
     */
    public Object getValue() { return value; }

    /**
     * Returns true if this entry was deleted
     * @return true if the entry was deleted and false otherwise
     */
    public boolean isDeleted() { return deleted; }

    /** Sets the value of the "deleted" attribute
     *
     * @param flag new boolean value of the "deleted" variable
     */
    public void setDeleted(boolean flag) { deleted = flag; }

    public String toString() { return "(" + key + ", " + value + ", " + deleted + ")"; }

    public static int hash(String key, int capacity)
    {
        BigInteger sum = BigInteger.valueOf((int) key.charAt(key.length()-1));
        for (int i = key.length() - 2; i >= 0 ; i--)
        {
            BigInteger addend = BigInteger.valueOf((int) key.charAt(i));
            BigInteger multiplicand = BigInteger.valueOf(33).modPow(BigInteger.valueOf(key.length() - 1 - i), BigInteger.valueOf(capacity));
            addend = addend.multiply(multiplicand);
            sum = sum.add(addend);
        }

        return sum.mod(BigInteger.valueOf(capacity)).intValue();
    }

    public static int hash2(String key, int hash2cap)
    {
        BigInteger sum = BigInteger.valueOf((int) key.charAt(key.length()-1));
        for (int i = key.length() - 2; i >= 0 ; i--)
        {
            BigInteger addend = BigInteger.valueOf((int) key.charAt(i));
            BigInteger multiplicand = BigInteger.valueOf(33).pow(key.length() - 1 - i);
            addend = addend.multiply(multiplicand);
            sum = sum.add(addend);
        }

        return hash2cap - (sum.mod(BigInteger.valueOf(hash2cap))).intValue();
    }
}