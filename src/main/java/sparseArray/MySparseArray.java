package sparseArray;

/** Implementation of the SparseArray Interface. */
public class MySparseArray implements SparseArray
{
    private Object defaultValue;
    private MatrixElem head;


    public class MatrixElem implements sparseArray.MatrixElem
    {
        //if base (x || y == 0) only delete if corresponding next is absent
        private int x;
        private int y;
        private Object val;
        private MatrixElem nextx;
        private MatrixElem nexty;


        public MatrixElem(int x, int y, Object val)
        {
            this.x = x;
            this.y = y;
            this.val = val;
            nextx = null;
            nexty = null;
        }


        public int rowIndex() { return y; }
        public int columnIndex() { return x; }
        public Object value() { return val; }
        public void setNextx(MatrixElem n) { this.nextx = n; }
        public void setNexty(MatrixElem n) { this.nexty = n; }
        public MatrixElem iterateRow() { return nextx; }
        public MatrixElem iterateCol() { return nexty; }
    }


    /**
     * An object for iterating over MatrixElem's horizontally or vertically
     */
    public class ElemIterator implements sparseArray.ElemIterator //extends java.util.Iterator<MatrixElem>
    {
        private boolean isXiteration;
        private MatrixElem prev;
        private MatrixElem curr;


        public ElemIterator(boolean isXiteration, MatrixElem curr)
        {
            this.isXiteration = isXiteration;
            this.prev = null;
            this.curr = curr;
        }


        public boolean iteratingRow() { return isXiteration; }
        public boolean iteratingCol() { return !isXiteration; }
        public int nonIteratingIndex() { return isXiteration ? curr.y : curr.x; }
        public boolean hasNext() { return curr != null; }
        public MatrixElem next() { MatrixElem g = curr; iterate(); return g; }
        public void iterate() { prev = curr; curr = isXiteration ? curr.iterateRow() : curr.iterateCol(); }
    }


    /**
     * An object for iterating over a MySparseArray horizontally then vertically
     */
    public class ColumnIterator implements sparseArray.ColumnIterator //extends java.util.Iterator<sparseArray.ElemIterator>
    {
        private ElemIterator columns;


        public ColumnIterator(MatrixElem head)
        {
            columns = new ElemIterator(true, head);
        }


        public boolean hasNext() { return columns.curr != null; }
        public ElemIterator next()
        {
            ElemIterator g = new ElemIterator(false, columns.curr);
            columns.iterate();
            return g;
        }
    }


    /**
     * An object for iterating over a MySparseArray vertically then horizontally
     */
    public class RowIterator implements sparseArray.RowIterator //extends java.util.Iterator<sparseArray.ElemIterator>
    {
        private ElemIterator rows;


        public RowIterator(MatrixElem head)
        {
            rows = new ElemIterator(false, head);
        }


        public boolean hasNext() { return rows.curr != null; }
        public ElemIterator next()
        {
            ElemIterator g = new ElemIterator(true, rows.curr);
            rows.iterate();
            return g;
        }
    }

    /**
     * Sets the default value for the sparse array
     * @param defaultValue default value
     */
    public MySparseArray(Object defaultValue)
    {
        this.defaultValue = defaultValue;
        head = new MatrixElem(0, 0, defaultValue);
    }

    /**
     * Getter for the default value
     * @return Returns the default value
     */
    @Override
    public Object getDefaultValue() { return defaultValue; }

    /**
     * Returns an iterator over the rows
     * @return iterator over rows
     */
    @Override
    public RowIterator iterateRows() { return new RowIterator(head); }

    /**
     * Returns an iterator over the columns
     * @return iterator over columns
     */
    @Override
    public ColumnIterator iterateColumns() { return new ColumnIterator(head); }

    /**
     * Gets element at the given row and column
     * @param row row
     * @param col column
     * @return element at 2d index
     */
    @Override
    public Object elementAt(int row, int col)
    {
        ElemIterator xiter = new ElemIterator(true, head);
        while (xiter.hasNext() && xiter.curr.x < col) xiter.iterate();
        if (xiter.curr == null || xiter.curr.x != col) return defaultValue;

        ElemIterator yiter = new ElemIterator(false, xiter.curr);
        while (yiter.hasNext() && yiter.curr.y < row) yiter.iterate();
        if (yiter.curr == null || yiter.curr.y != row) return defaultValue;

        return yiter.curr.val;
    }

    /**
     * Modifies the value at a given row, column,
     * or inserts the node for this row, column in the sparse array
     * if it did not exist before.
     * If value is the default value, then the node should be deleted from
     * the sparse array
     * @param row row
     * @param col column
     * @param value value of the element
     */
    @Override
    public void setValue(int row, int col, Object value)
    {
        if (row < 0 || col < 0) return;
        if (row == 0 && col == 0) { head.val = value; return; }
        if (value == defaultValue) { remove(row, col); return; }

        if (col != 0)
        {
            ElemIterator xiter = new ElemIterator(true, head);
            while (xiter.hasNext() && xiter.curr.x < col) xiter.iterate();

            MatrixElem column;
            if (xiter.curr == null || xiter.curr.x != col)
            {
                column = new MatrixElem(col, 0, row == 0 ? value : defaultValue);
                xiter.prev.nextx = column;
                if (xiter.curr != null) column.nextx = xiter.curr;
            }
            else column = xiter.curr;

            if (row != 0)
            {
                ElemIterator yiter = new ElemIterator(false, column);
                while (yiter.hasNext() && yiter.curr.y < row) yiter.iterate();

                if (yiter.curr == null || yiter.curr.y != row)
                {
                    MatrixElem rowww = new MatrixElem(col, row, value);
                    yiter.prev.nexty = rowww;
                    if (yiter.curr != null) rowww.nexty = yiter.curr;
                }
                else yiter.curr.val = value;
            }
        }

        if (row != 0)
        {
            ElemIterator yiter = new ElemIterator(false, head);
            while (yiter.hasNext() && yiter.curr.y < row) yiter.iterate();

            MatrixElem rowww;
            if (yiter.curr == null || yiter.curr.y != row)
            {
                rowww = new MatrixElem(0, row, col == 0 ? value : defaultValue);
                yiter.prev.nexty = rowww;
                if (yiter.curr != null) rowww.nexty = yiter.curr;
            }
            else rowww = yiter.curr;

            if (col != 0)
            {
                ElemIterator xiter = new ElemIterator(true, rowww);
                while (xiter.hasNext() && xiter.curr.x < col) xiter.iterate();

                if (xiter.curr == null || xiter.curr.x != col)
                {
                    MatrixElem column = new MatrixElem(col, row, value);
                    xiter.prev.nextx = column;
                    if (xiter.curr != null) column.nextx = xiter.curr;
                }
                else xiter.curr.val = value;
            }
        }
    }

    /**
     * Helper for setValue when value input = defaultValue
     * @param row row
     * @param col column
     */
    public void remove(int row, int col)
    {
        if (col != 0)
        {
            ElemIterator xiter = new ElemIterator(true, head);
            while (xiter.hasNext() && xiter.curr.x < col) xiter.iterate();

            if (xiter.hasNext() && xiter.curr.x == col)
            {
                if (row == 0)
                {
                    if (xiter.curr.nexty == null)
                    {
                        xiter.prev.nextx = xiter.curr.nextx;
                        xiter.curr = null;
                    }
                    else xiter.curr.val = defaultValue;
                }
                else
                {
                    ElemIterator yiter = new ElemIterator(false, xiter.curr);
                    while (yiter.hasNext() && yiter.curr.y < row) yiter.iterate();

                    if (yiter.hasNext() && yiter.curr.y == row)
                    {
                        yiter.prev.nexty = yiter.curr.nexty;
                        yiter.curr = null;

                        if (yiter.prev.y == 0 && yiter.prev.nexty == null && yiter.prev.val == defaultValue)
                        {
                            xiter.prev.nextx = xiter.curr.nextx;
                            xiter.curr = null;
                        }
                    }
                }
            }
        }

        if (row != 0)
        {
            ElemIterator yiter = new ElemIterator(false, head);
            while (yiter.hasNext() && yiter.curr.y < row) yiter.iterate();

            if (yiter.hasNext() && yiter.curr.y == row)
            {
                if (col == 0)
                {
                    if (yiter.curr.nextx == null)
                    {
                        yiter.prev.nexty = yiter.curr.nexty;
                        yiter.curr = null;
                    }
                    else yiter.curr.val = defaultValue;
                }
                else
                {
                    ElemIterator xiter = new ElemIterator(true, yiter.curr);
                    while (xiter.hasNext() && xiter.curr.x < col) xiter.iterate();

                    if (xiter.hasNext() && xiter.curr.x == col)
                    {
                        xiter.prev.nextx = xiter.curr.nextx;
                        xiter.curr = null;

                        if (xiter.prev.x == 0 && xiter.prev.nextx == null && xiter.prev.val == defaultValue)
                        {
                            yiter.prev.nexty = yiter.curr.nexty;
                            yiter.curr = null;
                        }
                    }
                }
            }
        }
    }

    /**
     * Print info of all elements of a MySparseArray that have non-default values
     */
    public void print()
    {
        RowIterator r = iterateRows();
        while (r.hasNext())
        {
            ElemIterator elmItr = r.next();
            while (elmItr.hasNext())
            {
                MatrixElem me = elmItr.next();
                if (me.value() == defaultValue) continue;

                System.out.print("row: " + me.rowIndex() + " ");
                System.out.print("col: " + me.columnIndex() +  " ");
                System.out.print("val: " + me.value());
                System.out.println();
            }
        }
    }
}
