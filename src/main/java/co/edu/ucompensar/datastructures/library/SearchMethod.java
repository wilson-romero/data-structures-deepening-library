package co.edu.ucompensar.datastructures.library;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The two search methods taught in Unit 2. */
@Getter
@RequiredArgsConstructor
public enum SearchMethod {

    SEQUENTIAL("Secuencial"),
    BINARY("Binaria");

    private final String label;
}
