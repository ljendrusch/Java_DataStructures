package sparseArray;

/** RowIterator interface (for iterating over rows) */
public interface RowIterator extends java.util.Iterator<ElemIterator>
{
    ElemIterator next();
    boolean hasNext();
}
