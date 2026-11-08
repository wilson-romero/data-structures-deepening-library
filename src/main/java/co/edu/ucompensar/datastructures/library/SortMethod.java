package co.edu.ucompensar.datastructures.library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The three sorting methods taught in Unit 2. */
@Getter
@RequiredArgsConstructor
public enum SortMethod {

    BUBBLE("Burbuja"),
    SELECTION("Selección"),
    INSERTION("Inserción");

    private final String label;
}
