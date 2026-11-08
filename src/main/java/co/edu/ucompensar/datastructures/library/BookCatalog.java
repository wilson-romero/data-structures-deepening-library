package co.edu.ucompensar.datastructures.library;

import java.io.PrintStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * The library's books in a two-dimensional array: one row per book, one column per field.
 * Rows {@code 0 .. size-1} are occupied, with no gaps; the rest are empty.
 */
@Component
public class BookCatalog {

    private final String[][] books;
    private final BookSorter sorter;
    private final BookSearcher searcher;

    /** Number of occupied rows. */
    @Getter
    private int size;

    /** Field the rows are sorted by, or {@code null} if a change may have broken the order. */
    @Getter
    private BookField sortedBy;

    /** Creates the empty matrix: {@code library.capacity} rows by five columns. */
    public BookCatalog(@Value("${library.capacity}") int capacity, BookSorter sorter, BookSearcher searcher) {
        this.books = new String[capacity][BookField.values().length];
        this.sorter = sorter;
        this.searcher = searcher;
    }

    /** Maximum number of books (rows of the matrix). */
    public int capacity() {
        return books.length;
    }

    /** True when there is no row left. */
    public boolean isFull() {
        return size == books.length;
    }

    /** True when no book is registered. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Writes the book in the first free row; the new row breaks any previous order. */
    public void register(Book book) {
        if (isFull()) {
            throw new IllegalStateException("La biblioteca está llena: " + capacity() + " libros.");
        }
        if (search(book.getCode(), SearchMethod.SEQUENTIAL) >= 0) {
            throw new IllegalArgumentException("Ya existe un libro con el código " + book.getCode() + ".");
        }
        books[size] = book.toRow();
        size++;
        sortedBy = null;
    }

    /** Replaces the row of the book with the same code; the code never changes, so an order by code survives. */
    public void update(Book book) {
        int row = requireRow(book.getCode());
        books[row] = book.toRow();
        if (sortedBy != BookField.CODE) {
            sortedBy = null;
        }
    }

    /** Removes the book and moves every row below it up one place, so the order is kept. */
    public Book delete(int code) {
        int row = requireRow(code);
        Book removed = Book.fromRow(books[row]);
        for (int i = row; i < size - 1; i++) {
            books[i] = books[i + 1];
        }
        books[size - 1] = new String[BookField.values().length];
        size--;
        return removed;
    }

    /** Row of the book with {@code code}, or -1; binary search requires the rows sorted by code. */
    public int search(int code, SearchMethod method) {
        if (method == SearchMethod.BINARY && sortedBy != BookField.CODE) {
            throw new IllegalStateException("La búsqueda binaria necesita los libros ordenados por código.");
        }
        return searcher.search(books, size, code, method);
    }

    /** The book stored in an occupied row. */
    public Book bookAt(int row) {
        if (row < 0 || row >= size) {
            throw new IllegalArgumentException("La fila " + row + " no tiene libro.");
        }
        return Book.fromRow(books[row]);
    }

    /** Sorts the occupied rows by the field with the chosen method and remembers the field. */
    public void sortBy(BookField field, SortMethod method) {
        sorter.sort(books, size, field, method);
        sortedBy = field;
    }

    /** Prints the occupied rows as a table, walking the matrix row by row and column by column. */
    public void print(PrintStream out) {
        BookField[] fields = BookField.values();
        out.printf("%-6s", "Fila");
        for (BookField field : fields) {
            out.printf("%-" + field.getWidth() + "s", field.getLabel());
        }
        out.println();
        for (int i = 0; i < size; i++) {
            out.printf("%-6d", i);
            for (BookField field : fields) {
                out.printf("%-" + field.getWidth() + "s", books[i][field.getColumn()]);
            }
            out.println();
        }
    }

    private int requireRow(int code) {
        int row = search(code, SearchMethod.SEQUENTIAL);
        if (row < 0) {
            throw new IllegalArgumentException("No existe un libro con el código " + code + ".");
        }
        return row;
    }
}
