package co.edu.ucompensar.datastructures.library;

import static co.edu.ucompensar.datastructures.library.BookField.AUTHOR;
import static co.edu.ucompensar.datastructures.library.BookField.CODE;
import static co.edu.ucompensar.datastructures.library.BookField.NAME;
import static co.edu.ucompensar.datastructures.library.BookField.PAGES;
import static co.edu.ucompensar.datastructures.library.BookField.SUBJECT;

import lombok.Value;

/** A book as the user types it; the catalog stores it as one row of text cells. */
@Value
public class Book {

    int code;
    String name;
    String author;
    String subject;
    int pages;

    /** The book as a matrix row: one cell per field, in the column that {@link BookField} gives it. */
    public String[] toRow() {
        String[] row = new String[BookField.values().length];
        row[CODE.getColumn()] = String.valueOf(code);
        row[NAME.getColumn()] = name;
        row[AUTHOR.getColumn()] = author;
        row[SUBJECT.getColumn()] = subject;
        row[PAGES.getColumn()] = String.valueOf(pages);
        return row;
    }

    /** Reads a book back from a matrix row. */
    public static Book fromRow(String[] row) {
        return new Book(
                Integer.parseInt(row[CODE.getColumn()]),
                row[NAME.getColumn()],
                row[AUTHOR.getColumn()],
                row[SUBJECT.getColumn()],
                Integer.parseInt(row[PAGES.getColumn()]));
    }
}
