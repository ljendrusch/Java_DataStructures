package suffixTrees;

import java.io.*;
import java.util.List;

/** The driver class for SuffixTree */
public class SuffixTreeDriver
{
    public static void main(String[] args) throws IOException
    {
        processStrings("input/inputStringsSimple");
    }

    /** Process strings from the given file. See comments inside the method */
    public static void processStrings(String filename) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader(filename));

        for (int i = 0; i < 5; i++)
        {
            String str = br.readLine();
            SuffixTree suffixTree = new SuffixTree(str);

            PrintWriter pw = new PrintWriter(str + "Results.txt");

            System.out.println(suffixTree);
            pw.println(suffixTree);

            String strs1[] = br.readLine().split(", ");
            for (String s : strs1)
            {
                int x = suffixTree.containsSuffix(s);
                System.out.print(x + " ");
                pw.print(x + " ");
            }
            System.out.println();
            pw.println();

            String strs2[] = br.readLine().split(", ");
            for (String s : strs2)
            {
                int x = suffixTree.containsSuffix(s);
                System.out.print(x + " ");
                pw.print(x + " ");
            }
            System.out.println();
            pw.println();


            String strs3[] = br.readLine().split(", ");
            for (String s : strs3)
            {
                List<Integer> li = suffixTree.getSubstringIndices(s);
                System.out.print(li + " ");
                pw.print(li + " ");
            }
            System.out.println();
            pw.println();

            String strs4[] = br.readLine().split(", ");
            for (String s : strs4)
            {
                List<Integer> li = suffixTree.getSubstringIndices(s);
                System.out.print(li + " ");
                pw.print(li + " ");
            }
            System.out.println();
            pw.println();

            br.readLine();
        }
    }
}