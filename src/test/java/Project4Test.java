import map.*;
import org.junit.Assert;
import org.junit.Test;


/** Test file for Project 4
 * Provides only the most basic testing.
 * You are responsible for thoroughly testing your code on your own. */
public class Project4Test
{
    @Test
    public void testOpenHashingToString()
    {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        String actual = map.toString();
        System.out.println(actual);
        String expected1 = "0: null" + System.lineSeparator() +
                "1: (two, do, false)" + System.lineSeparator() +
                "2: (one, uno, false), (three, tres, false), (five, cinco, false)" + System.lineSeparator() +
                "3: null" + System.lineSeparator() +
                "4: (four, cuantro, false)" + System.lineSeparator() +
                "5: null" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: (six, seis, false)" + System.lineSeparator();

        String expected2 = "0: null" + System.lineSeparator() +
                "1: (two, do, false)" + System.lineSeparator() +
                "2: (five, cinco, false), (three, tres, false), (one, uno, false)" + System.lineSeparator() +
                "3: null" + System.lineSeparator() +
                "4: (four, cuantro, false)" + System.lineSeparator() +
                "5: null" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: (six, seis, false)" + System.lineSeparator();

        if (!expected1.equals(actual) && !expected2.equals(actual) && !expected1.equals(actual.trim()) && !expected2.equals(actual.trim()))
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the hash table.");
    }

    @Test
    public void testOpenHashingContainsKey()
    {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        boolean b1 = map.containsKey("one");
        boolean b2 = map.containsKey("three");
        boolean b3 = map.containsKey("five");
        boolean b4 = map.containsKey("seven");
        Assert.assertEquals(true, b1);
        Assert.assertEquals(true, b2);
        Assert.assertEquals(true, b3);
        Assert.assertEquals(true, !b4);
    }

    @Test
    public void testOpenHashingDelete()
    {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        boolean b1 = map.containsKey("five");
        map.remove("five");
        boolean b2 = map.containsKey("five");
        map.put("five", "cinco");
        boolean b3 = map.containsKey("five");
        Assert.assertEquals(true, b1);
        Assert.assertEquals(true, !b2);
        Assert.assertEquals(true, b3);
    }

    @Test
    public void testOpenHashingGet()
    {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        String s1 = (String) map.get("five");
        String s2 = (String) map.get("two");
        Assert.assertEquals("Expected to get \"cinco\" for the key \"five\"", "cinco", s1);
        Assert.assertEquals("Expected to get \"do\" for the key \"two\"", "do", s2);

    }

    @Test
    public void testOpenHashingReshashing()
    {
        Map map = new HashTableOpenHashing(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        map.put("seven", "siete");
        map.put("rehash", "timeToRehash");
        String actual = map.toString();
        System.out.println(actual);
        String expected1 = "0: (five, cinco, false)" + System.lineSeparator() +
                "1: (seven, siete, false)" + System.lineSeparator() +
                "2: null" + System.lineSeparator() +
                "3: null" + System.lineSeparator() +
                "4: null" + System.lineSeparator() +
                "5: (four, cuantro, false), (rehash, timeToRehash, false)" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: null" + System.lineSeparator() +
                "11: (three, tres, false)" + System.lineSeparator() +
                "12: null" + System.lineSeparator() +
                "13: null" + System.lineSeparator() +
                "14: null" + System.lineSeparator() +
                "15: null" + System.lineSeparator() +
                "16: null" + System.lineSeparator() +
                "17: null" + System.lineSeparator() +
                "18: null" + System.lineSeparator() +
                "19: (one, uno, false)" + System.lineSeparator() +
                "20: (six, seis, false)" + System.lineSeparator() +
                "21: (two, do, false)" + System.lineSeparator() +
                "22: null" + System.lineSeparator();
        String expected2 = "0: (five, cinco, false)" + System.lineSeparator() +
                "1: (seven, siete, false)" + System.lineSeparator() +
                "2: null" + System.lineSeparator() +
                "3: null" + System.lineSeparator() +
                "4: null" + System.lineSeparator() +
                "5: (rehash, timeToRehash, false), (four, cuantro, false)" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: null" + System.lineSeparator() +
                "11: (three, tres, false)" + System.lineSeparator() +
                "12: null" + System.lineSeparator() +
                "13: null" + System.lineSeparator() +
                "14: null" + System.lineSeparator() +
                "15: null" + System.lineSeparator() +
                "16: null" + System.lineSeparator() +
                "17: null" + System.lineSeparator() +
                "18: null" + System.lineSeparator() +
                "19: (one, uno, false)" + System.lineSeparator() +
                "20: (six, seis, false)" + System.lineSeparator() +
                "21: (two, do, false)" + System.lineSeparator() +
                "22: null" + System.lineSeparator();
        if (!expected1.equals(actual) && !expected2.equals(actual) && !expected1.equals(actual.trim()) && !expected2.equals(actual.trim())) {
            System.out.println("Your string = " + System.lineSeparator() + actual);
            Assert.fail("The string returned by your program did not match any of strings that are considered correct representation of the hash table.");
        }
    }

    @Test
    public void testClosedHashingLPContainsKey()
    {
        Map map = new HashTableClosedHashingLP(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        boolean b1 = map.containsKey("one");
        boolean b2 = map.containsKey("five");
        boolean b3 = map.containsKey("seven");
        Assert.assertEquals(true, b1);
        Assert.assertEquals(true, b2);
        Assert.assertEquals(false, b3);
    }

    @Test
    public void testClosedHashingLPToString()
    {
        Map map = new HashTableClosedHashingLP(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        String actual = map.toString();
        String expected = "0: null" + System.lineSeparator() +
                "1: (two, do, false)" + System.lineSeparator() +
                "2: (one, uno, false)" + System.lineSeparator() +
                "3: (three, tres, false)" + System.lineSeparator() +
                "4: (four, cuantro, false)" + System.lineSeparator() +
                "5: (five, cinco, false)" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: (six, seis, false)" + System.lineSeparator();
        String errorMessage = "The string representing a hash table with closed hashing / linear probing is not what is expected";
        expected = expected.trim();
        actual = actual.trim();
        Assert.assertEquals(errorMessage, expected, actual);
    }

    @Test
    public void testClosedHashingLPRemove()
    {
        Map map = new HashTableClosedHashingLP(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.remove("four");
        map.remove("one");
        map.put("five", "cinco");
        map.put("six", "seis");

        String actual = map.toString();
        String expected = "0: null" + System.lineSeparator() +
                "1: (two, do, false)" + System.lineSeparator() +
                "2: (five, cinco, false)" + System.lineSeparator() +
                "3: (three, tres, false)" + System.lineSeparator() +
                "4: (four, cuantro, true)" + System.lineSeparator() +
                "5: null" + System.lineSeparator() +
                "6: null" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: null" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: (six, seis, false)" + System.lineSeparator();
        String errorMessage = "The string representing a hash table with closed hashing / linear probing is not what is expected";
        expected = expected.trim();
        actual = actual.trim();
        Assert.assertEquals(errorMessage, expected, actual);
    }

    @Test
    public void testClosedHashingLPGet()
    {
        Map map = new HashTableClosedHashingLP(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        String val1 = (String) map.get("five");
        String val2 = (String) map.get("three");
        String errorMessage1 = "The get method does not return the correct value for key \"five\"";
        Assert.assertEquals(errorMessage1, "cinco", val1);
        String errorMessage2 = "The get method does not return the correct value for key \"three\"";
        Assert.assertEquals(errorMessage2, "tres", val2);
    }

    @Test
    public void testClosedHashingLPReshashing()
    {
        Map map = new HashTableClosedHashingLP(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");

        map.put("hello", "privet");
        System.out.println(map.toString());

        map.put("bye", "poka");
        map.put("thanks", "spasibo");
        map.put("where", "gde");

        String actual = map.toString();
        String expected = "0: (five, cinco, false)" + System.lineSeparator() +
        "1: (hello, privet, false)" + System.lineSeparator() +
        "2: (bye, poka, false)" + System.lineSeparator() +
        "3: null" + System.lineSeparator() +
        "4: null" + System.lineSeparator() +
        "5: (four, cuantro, false)" + System.lineSeparator() +
        "6: null" + System.lineSeparator() +
        "7: null" + System.lineSeparator() +
        "8: null" + System.lineSeparator() +
        "9: (thanks, spasibo, false)" + System.lineSeparator() +
        "10: null" + System.lineSeparator() +
        "11: (three, tres, false)" + System.lineSeparator() +
        "12: null" + System.lineSeparator() +
        "13: null" + System.lineSeparator() +
        "14: null" + System.lineSeparator() +
        "15: null" + System.lineSeparator() +
        "16: null" + System.lineSeparator() +
        "17: null" + System.lineSeparator() +
        "18: null" + System.lineSeparator() +
        "19: (one, uno, false)" + System.lineSeparator() +
        "20: (six, seis, false)" + System.lineSeparator() +
        "21: (two, do, false)" + System.lineSeparator() +
        "22: (where, gde, false)" + System.lineSeparator();
        actual = actual.trim();
        expected = expected.trim();
        String errorMessage = "The result of insertion after rehashing does not match the expected one";
        Assert.assertEquals(errorMessage, expected, actual);
    }

    @Test
    public void testClosedHashingDHContainsKey()
    {
        Map map = new HashTableClosedHashingDH(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        System.out.println("here = " + map.toString());
        boolean b1 = map.containsKey("one");
        boolean b2 = map.containsKey("three");
        boolean b3 = map.containsKey("five");
        boolean b4 = map.containsKey("seven");
        Assert.assertEquals(true, b1);
        Assert.assertEquals(true, b2);
        Assert.assertEquals(true, b3);
        Assert.assertEquals(true, !b4);
    }

    @Test
    public void testClosedHashingDHToString()
    {
        Map map = new HashTableClosedHashingDH(11);
        map.put("one", "uno");
        map.put("two", "do");
        map.put("three", "tres");
        map.put("four", "cuantro");
        map.put("five", "cinco");
        map.put("six", "seis");
        String actual = map.toString();
        String expected = "0: null" + System.lineSeparator() +
                "1: (two, do, false)" + System.lineSeparator() +
                "2: (one, uno, false)" + System.lineSeparator() +
                "3: null" + System.lineSeparator() +
                "4: (four, cuantro, false)" + System.lineSeparator() +
                "5: null" + System.lineSeparator() +
                "6: (three, tres, false)" + System.lineSeparator() +
                "7: null" + System.lineSeparator() +
                "8: (five, cinco, false)" + System.lineSeparator() +
                "9: null" + System.lineSeparator() +
                "10: (six, seis, false)" + System.lineSeparator();
        String errorMessage = "The string for the hash table with double hashing is incorrect";
        actual = actual.trim();
        expected = expected.trim();
        Assert.assertEquals(errorMessage, expected, actual);
    }
}