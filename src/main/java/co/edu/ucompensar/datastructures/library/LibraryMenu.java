package co.edu.ucompensar.datastructures.library;

import java.io.PrintStream;
import java.util.NoSuchElementException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/** Console menu of the library, one {@code switch} case per operation. */
@Component
@RequiredArgsConstructor
public class LibraryMenu implements CommandLineRunner {

    private static final int MAX_CODE = 999_999;
    private static final int MAX_PAGES = 10_000;
    private static final String MENU = """

            ===== Biblioteca Universidad Compensar =====
            1. Registrar libro
            2. Actualizar libro
            3. Eliminar libro
            4. Buscar libro
            5. Ordenar libros
            6. Mostrar libros
            0. Salir""";

    private final BookCatalog catalog;
    private final ConsoleInput input;
    private final PrintStream out = System.out;

    /** Shows the menu until the user chooses 0 or the input ends. */
    @Override
    public void run(String... args) {
        boolean running = true;
        while (running) {
            out.println(MENU);
            try {
                int option = input.readInt("Opción: ", 0, 6);
                switch (option) {
                    case 1 -> register();
                    case 2 -> update();
                    case 3 -> delete();
                    case 4 -> search();
                    case 5 -> sort();
                    case 6 -> show();
                    case 0 -> running = false;
                    default -> out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                running = false;
            }
        }
        out.println("Hasta luego.");
    }

    private void register() {
        if (catalog.isFull()) {
            out.println("No hay espacio: la biblioteca ya tiene " + catalog.capacity() + " libros.");
            return;
        }
        int code = input.readInt("Código (1 a " + MAX_CODE + "): ", 1, MAX_CODE);
        if (catalog.search(code, SearchMethod.SEQUENTIAL) >= 0) {
            out.println("Ya existe un libro con el código " + code + ".");
            return;
        }
        catalog.register(readBook(code));
        out.println("Libro registrado. Hay " + catalog.getSize() + " de " + catalog.capacity() + " libros.");
    }

    private void update() {
        if (warnIfEmpty()) {
            return;
        }
        int code = input.readInt("Código del libro a actualizar: ", 1, MAX_CODE);
        int row = catalog.search(code, SearchMethod.SEQUENTIAL);
        if (row < 0) {
            out.println("No existe un libro con el código " + code + ".");
            return;
        }
        out.println("Datos actuales: " + describe(catalog.bookAt(row)));
        out.println("Escriba los datos nuevos (el código no cambia).");
        catalog.update(readBook(code));
        out.println("Libro actualizado: " + describe(catalog.bookAt(row)));
    }

    private void delete() {
        if (warnIfEmpty()) {
            return;
        }
        int code = input.readInt("Código del libro a eliminar: ", 1, MAX_CODE);
        if (catalog.search(code, SearchMethod.SEQUENTIAL) < 0) {
            out.println("No existe un libro con el código " + code + ".");
            return;
        }
        out.println("Libro eliminado: " + describe(catalog.delete(code)));
    }

    private void search() {
        if (warnIfEmpty()) {
            return;
        }
        out.println("Método de búsqueda: 1. Secuencial  2. Binaria");
        SearchMethod method = SearchMethod.values()[input.readInt("Método: ", 1, 2) - 1];
        int code = input.readInt("Código a buscar: ", 1, MAX_CODE);
        if (method == SearchMethod.BINARY && catalog.getSortedBy() != BookField.CODE) {
            out.println("La búsqueda binaria solo sirve si los libros están ordenados por el código que se busca.");
            out.println("No lo están: primero los ordeno por código con el método de inserción.");
            catalog.sortBy(BookField.CODE, SortMethod.INSERTION);
        }
        int row = catalog.search(code, method);
        if (row < 0) {
            out.println("Búsqueda " + method.getLabel().toLowerCase() + ": no hay ningún libro con el código " + code + ".");
        } else {
            out.println("Búsqueda " + method.getLabel().toLowerCase() + ": encontrado en la fila " + row + ": "
                    + describe(catalog.bookAt(row)));
        }
    }

    private void sort() {
        if (warnIfEmpty()) {
            return;
        }
        out.println("Campo: 1. Código  2. Nombre  3. Autor  4. Materia  5. Páginas");
        BookField field = BookField.values()[input.readInt("Campo: ", 1, 5) - 1];
        out.println("Método: 1. Burbuja  2. Selección  3. Inserción");
        SortMethod method = SortMethod.values()[input.readInt("Método: ", 1, 3) - 1];
        catalog.sortBy(field, method);
        out.println("Libros ordenados por " + field.getLabel().toLowerCase() + " con el método "
                + method.getLabel().toLowerCase() + ":");
        catalog.print(out);
    }

    private void show() {
        if (warnIfEmpty()) {
            return;
        }
        catalog.print(out);
    }

    private Book readBook(int code) {
        String name = input.readText("Nombre: ");
        String author = input.readText("Autor: ");
        String subject = input.readText("Materia: ");
        int pages = input.readInt("No. de páginas (1 a " + MAX_PAGES + "): ", 1, MAX_PAGES);
        return new Book(code, name, author, subject, pages);
    }

    private boolean warnIfEmpty() {
        if (catalog.isEmpty()) {
            out.println("No hay libros registrados.");
            return true;
        }
        return false;
    }

    private String describe(Book book) {
        return book.getCode() + " | " + book.getName() + " | " + book.getAuthor() + " | " + book.getSubject()
                + " | " + book.getPages() + " páginas";
    }
}
