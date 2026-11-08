package co.edu.ucompensar.datastructures.library;

import org.springframework.stereotype.Component;

/** Finds the row of a book by its code among the occupied rows of the books matrix. */
@Component
public class BookSearcher {

    /** Row index of the book with {@code code}, or -1; binary search needs the rows sorted by code. */
    public int search(String[][] rows, int size, int code, SearchMethod method) {
        return switch (method) {
            case SEQUENTIAL -> sequentialSearch(rows, size, code);
            case BINARY -> binarySearch(rows, size, code);
        };
    }

    /** Sequential search (guide, Figure 16): compares the code of every row, from the first one, until it matches. */
    private int sequentialSearch(String[][] rows, int size, int code) {
        for (int index = 0; index < size; index++) {
            if (codeAt(rows, index) == code) {
                return index;
            }
        }
        return -1;
    }

    /** Binary search (guide, Figure 20): looks at the middle row and discards the half where the code cannot be. */
    private int binarySearch(String[][] rows, int size, int code) {
        int firstIndex = 0;
        int lastIndex = size - 1;
        while (firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            int middleCode = codeAt(rows, middleIndex);
            if (middleCode == code) {
                return middleIndex;
            } else if (middleCode < code) {
                firstIndex = middleIndex + 1;
            } else {
                lastIndex = middleIndex - 1;
            }
        }
        return -1;
    }

    private int codeAt(String[][] rows, int index) {
        return Integer.parseInt(rows[index][BookField.CODE.getColumn()]);
    }
}
