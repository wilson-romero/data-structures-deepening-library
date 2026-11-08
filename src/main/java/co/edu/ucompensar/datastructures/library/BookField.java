package co.edu.ucompensar.datastructures.library;

import java.text.Collator;
import java.util.Locale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The five fields of a book; each one is a column of the books matrix. */
@Getter
@RequiredArgsConstructor
public enum BookField {

    CODE(0, "Código", true, 8),
    NAME(1, "Nombre", false, 34),
    AUTHOR(2, "Autor", false, 24),
    SUBJECT(3, "Materia", false, 20),
    PAGES(4, "Páginas", true, 8);

    /** Spanish alphabetical order: ignores case and keeps an accented letter next to its base letter. */
    private static final Collator SPANISH = spanishCollator();

    private final int column;
    private final String label;
    private final boolean numeric;
    private final int width;

    /** Negative, zero or positive if the first row goes before, together with or after the second one by this field. */
    public int compare(String[] first, String[] second) {
        String a = first[column];
        String b = second[column];
        return numeric ? Integer.compare(Integer.parseInt(a), Integer.parseInt(b)) : SPANISH.compare(a, b);
    }

    private static Collator spanishCollator() {
        Collator collator = Collator.getInstance(Locale.of("es", "CO"));
        collator.setStrength(Collator.SECONDARY);
        return collator;
    }
}
