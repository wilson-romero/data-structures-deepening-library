package co.edu.ucompensar.datastructures.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** University library on a two-dimensional array: register, update, delete, search and sort books. */
@SpringBootApplication
public class LibraryApplication {

    /** Starts Spring; the console menu runs as a {@code CommandLineRunner}. */
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }
}
