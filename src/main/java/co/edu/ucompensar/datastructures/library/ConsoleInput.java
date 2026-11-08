package co.edu.ucompensar.datastructures.library;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.springframework.stereotype.Component;

/** Reads from the keyboard and asks again until the value is valid. */
@Component
public class ConsoleInput {

    /** Reads with the console's own charset, so accents typed in a Windows console (cp850) arrive intact. */
    private final Scanner scanner = new Scanner(System.in,
            System.console() != null ? System.console().charset() : Charset.defaultCharset());
    private final PrintStream out = System.out;

    /** An integer between {@code min} and {@code max}, both included. */
    public int readInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // not a number: ask again
            }
            out.println("Valor no válido: escriba un número entero entre " + min + " y " + max + ".");
        }
    }

    /** A text that is not blank, without spaces at the ends. */
    public String readText(String prompt) {
        while (true) {
            out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            out.println("Este dato no puede quedar vacío.");
        }
    }
}
