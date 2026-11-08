package co.edu.ucompensar.datastructures.library;

import org.springframework.stereotype.Component;

/** Sorts the occupied rows of the books matrix in ascending order by one field. */
@Component
public class BookSorter {

    /** Sorts rows {@code 0 .. size-1} by {@code field} with the chosen method. */
    public void sort(String[][] rows, int size, BookField field, SortMethod method) {
        switch (method) {
            case BUBBLE -> bubbleSort(rows, size, field);
            case SELECTION -> selectionSort(rows, size, field);
            case INSERTION -> insertionSort(rows, size, field);
        }
    }

    /** Bubble sort (guide, Figure 7): compares each row with the next one and swaps them if they are out of order. */
    private void bubbleSort(String[][] rows, int size, BookField field) {
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (field.compare(rows[j], rows[j + 1]) > 0) {
                    swap(rows, j, j + 1);
                }
            }
        }
    }

    /** Selection sort (guide, Figure 13): finds the smallest remaining row and puts it in position {@code i}. */
    private void selectionSort(String[][] rows, int size, BookField field) {
        for (int i = 0; i < size - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < size; j++) {
                if (field.compare(rows[j], rows[smallest]) < 0) {
                    smallest = j;
                }
            }
            if (smallest != i) {
                swap(rows, i, smallest);
            }
        }
    }

    /** Insertion sort (guide, Figure 10): takes each row and shifts the larger ones right until its place is free. */
    private void insertionSort(String[][] rows, int size, BookField field) {
        for (int p = 1; p < size; p++) {
            String[] aux = rows[p];
            int j = p - 1;
            while (j >= 0 && field.compare(aux, rows[j]) < 0) {
                rows[j + 1] = rows[j];
                j--;
            }
            rows[j + 1] = aux;
        }
    }

    /** Exchanges two whole rows: in Java a matrix is an array of rows, so the five fields move together. */
    private void swap(String[][] rows, int i, int j) {
        String[] temporal = rows[i];
        rows[i] = rows[j];
        rows[j] = temporal;
    }
}
