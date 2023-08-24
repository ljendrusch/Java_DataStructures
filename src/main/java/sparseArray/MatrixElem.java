package sparseArray;

/**
 * Element of a sparse array.
 * Stores index of the row, index of the column and a value.
 */
public interface MatrixElem
{
    int rowIndex();
    int columnIndex();
    Object value();
}
