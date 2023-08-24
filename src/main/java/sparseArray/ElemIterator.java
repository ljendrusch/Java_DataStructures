package sparseArray;

/** ElemIterator interface - for iterating over the elements of a row or a column */
public interface ElemIterator extends java.util.Iterator<MatrixElem>
{
    boolean iteratingRow();
    boolean iteratingCol();
    int nonIteratingIndex();
    MatrixElem next();
    boolean hasNext();
}
