package sparseArray;

/** ColumnIterator interface (for iterating over columns) */
public interface ColumnIterator extends java.util.Iterator<ElemIterator>
{
    ElemIterator next();
    boolean hasNext();
}
