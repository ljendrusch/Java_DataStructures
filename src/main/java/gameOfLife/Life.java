package gameOfLife;

import sparseArray.MySparseArray;
import java.io.*;


/** Uses sparse arrays to play the game of life. Refer to the
 * project description for details. */
public class Life
{
    private boolean log;
    int panHeight;
    int panWidth;
    MySparseArray living;
    MySparseArray neighborCount;
    MySparseArray nextGen;


    public Life()
    {
        log = false;
        panHeight = 0;
        panWidth = 0;
        living = new MySparseArray(0);
        neighborCount = new MySparseArray(0);
        nextGen = new MySparseArray(0);
    }


    public void init(String filename) throws IOException
    {
        int ct = 0;
        int ytot = 0, xtot = 0;
        int yave, xave;
        int ymax = 0, xmax = 0;

        BufferedReader br = new BufferedReader(new FileReader(filename));
        for(String line; (line = br.readLine()) != null; )
        {
            String[] ns = line.split("[ ,]+");
            int row = Integer.parseInt(ns[0]); int col = Integer.parseInt(ns[1]);
            ct++;
            ytot += row; xtot += col;
            ymax = Math.max(ymax, row); xmax = Math.max(xmax, col);
            living.setValue(row, col, 1);
        }

        yave = ytot / ct; xave = xtot / ct;
        panHeight = Math.max(2*yave, ymax);
        panWidth = Math.max(2*xave, xmax);

        br.close();
    }

    public void live()
    {
        if (log) { System.out.println("\ninitial living"); living.print(); System.out.printf("\nelementAt(3,4) test: %s\n", living.elementAt(3,4)); }

        calcNeighbors();

        if (log) { System.out.println("\nneighbors"); neighborCount.print(); }

        calcNextGen();

        if (log) { System.out.println("\nnextgen"); nextGen.print(); }

        living = nextGen;
        neighborCount = new MySparseArray(0);
        nextGen = new MySparseArray(0);

        if (log) { System.out.println("\nfinal living"); living.print(); }
    }

    public void calcNeighbors()
    {
        MySparseArray.RowIterator r = living.iterateRows();
        while (r.hasNext())
        {
            MySparseArray.ElemIterator elmItr = r.next();
            while (elmItr.hasNext())
            {
                MySparseArray.MatrixElem me = elmItr.next();
                if ((int) me.value() > 0)
                {
                    for (int i = -1; i <= 1; i++)
                    {
                        for (int j = -1; j <= 1; j++)
                        {
                            if (Math.abs(i + j) != 1) continue;

                            int row = i + me.rowIndex();
                            if (row < 0 || row > panHeight) continue;
                            int col = j + me.columnIndex();
                            if (col < 0 || col > panWidth) continue;

                            int val = (int) neighborCount.elementAt(row, col);
                            val++;
                            neighborCount.setValue(row, col, val);
                        }
                    }
                }
            }
        }
    }

    public void calcNextGen()
    {
        MySparseArray.RowIterator r = neighborCount.iterateRows();
        while (r.hasNext())
        {
            MySparseArray.ElemIterator elmItr = r.next();
            while (elmItr.hasNext())
            {
                MySparseArray.MatrixElem me = elmItr.next();
                if ((int) me.value() == 3 || ((int) me.value() == 2 && ((int) living.elementAt(me.rowIndex(), me.columnIndex())) > 0))
                {
                    nextGen.setValue(me.rowIndex(), me.columnIndex(), 1);
                }
                else
                {
                    nextGen.setValue(me.rowIndex(), me.columnIndex(), 0);
                }
            }
        }
    }

    public void toFile(String filename) throws IOException
    {
        PrintWriter pw = new PrintWriter(new FileWriter(filename));

        MySparseArray.RowIterator r = living.iterateRows();
        while (r.hasNext())
        {
            MySparseArray.ElemIterator elmItr = r.next();
            while (elmItr.hasNext())
            {
                MySparseArray.MatrixElem me = elmItr.next();
                if (me.value() == living.getDefaultValue()) continue;

                pw.printf("%d, %d\n", me.rowIndex(), me.columnIndex());
            }
        }

        pw.close();
    }


    public static void main(String[] args) throws IOException
    {
        if (args.length != 4)
        {
            System.out.println("Usage: ./Life [input_file.txt] [output_file.txt] [#generations]");
            return;
        }

        int generations = Integer.parseInt(args[3]);
        if (generations < 0)
        {
            System.out.println("Usage: ./Life [input_file.txt] [output_file.txt] [#generations]");
            System.out.println("    #generations must be a positive number");
            return;
        }


        Life pan = new Life();
        pan.init(args[1]);

        if (pan.log) System.out.printf("Max Height: %d, Max Width: %d\n", pan.panHeight, pan.panWidth);

        for (int i = 1; i <= generations; i++)
        {
            if (pan.log) System.out.printf("\n  Pass %d:\n-----------\n", i);
            pan.live();
        }

        pan.toFile(args[2]);
    }
}
