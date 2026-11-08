# Estructura de Datos: Actividad de Profundización

Fundación Universitaria Compensar · Ciencia de Datos · Estructura de Datos 2314-2A · Wilson Romero Carrasco

Programa en Java por consola para el registro y manejo de la biblioteca de la universidad. Los
libros se guardan en un **arreglo bidimensional** `String[][]`: una fila por libro y una columna
por dato (código, nombre, autor, materia y número de páginas).

## Cómo correrlo

Con Java 21 o superior y Maven, desde la carpeta del proyecto (donde está el `pom.xml`):

```bash
mvn spring-boot:run
```

Maven compila el proyecto y lo arranca en la misma terminal. Aparece el menú:

```
===== Biblioteca Universidad Compensar =====
1. Registrar libro
2. Actualizar libro
3. Eliminar libro
4. Buscar libro
5. Ordenar libros
6. Mostrar libros
0. Salir
Opción:
```

Se escribe el número de la opción y se sigue lo que va pidiendo. Por ejemplo, `1` pide el código,
el nombre, el autor, la materia y el número de páginas del libro; `5` pide el campo y el método
de ordenamiento y muestra la tabla ordenada. `0` sale del programa.

## Qué hace

Desde un menú se puede:

1. **Registrar** un libro nuevo.
2. **Actualizar** un libro (por su código).
3. **Eliminar** un libro (por su código).
4. **Buscar** un libro por su código, con búsqueda **secuencial** o **binaria**.
5. **Ordenar** los libros: se escoge el campo (código, nombre, autor, materia o páginas) y el
   método (**burbuja**, **selección** o **inserción**).
6. **Mostrar** los libros registrados.

Está hecho con Spring Boot y Lombok como aplicación de consola (sin servidor web). Los
algoritmos de ordenamiento y búsqueda están escritos a mano, siguiendo la guía de la Unidad 2.

## Requisitos y otras formas de correrlo

### 1. Requisitos

- **Java 21 o superior** (probado con Java 21 y Java 26). Con una versión anterior no compila:
  Spring Boot 4 pide Java 17 como mínimo y el código usa `switch` con flecha y bloques de texto
  `"""`, que Java 8 no conoce.
- **Maven** 3.9 o superior.
- **Conexión a internet la primera vez**: Maven descarga Spring Boot y Lombok. El archivo
  comprimido trae solo el código fuente.

### 2. Comprobar la versión de Java

```bash
java -version
mvn -v
```

Las dos deben decir **21 o superior**. Si dicen 1.8 (Java 8) u otra anterior, hay que cambiar de
Java antes de compilar:

- **Con SDKMAN** (Linux, WSL o macOS), solo para esa terminal:

  ```bash
  sdk use java 21.0.6-tem        # o la versión 21+ que tengas: sdk list java
  ```

- **Sin SDKMAN**: apuntar `JAVA_HOME` al JDK 21+ instalado.

  ```bash
  export JAVA_HOME=/ruta/al/jdk-21
  export PATH="$JAVA_HOME/bin:$PATH"
  ```

  En Windows (PowerShell): `$env:JAVA_HOME="C:\ruta\al\jdk-21"; $env:Path="$env:JAVA_HOME\bin;$env:Path"`

### 3. Compilar y correr el jar

En vez de `mvn spring-boot:run` se puede generar el jar y correrlo con Java:

```bash
mvn package
java -jar target/data-structures-deepening-library-1.0.2.jar
```

### En NetBeans

*File → Open Project* sobre esta carpeta y *Run*. NetBeans 31 trabaja con los JDK 21, 25 y 26;
si el proyecto no compila, revisar en *Tools → Java Platforms* que el JDK del proyecto sea 21 o
superior.

## Cómo está organizado

| clase | qué hace |
|---|---|
| `LibraryApplication` | arranca Spring Boot |
| `LibraryMenu` | el menú, con un `switch` por opción |
| `ConsoleInput` | lee del teclado y vuelve a preguntar si el dato no es válido |
| `BookCatalog` | la matriz `String[][]` de libros: registrar, actualizar, eliminar, buscar, ordenar y mostrar |
| `BookSorter` | burbuja, selección e inserción sobre las filas de la matriz |
| `BookSearcher` | búsqueda secuencial y binaria por código |
| `BookField` | los cinco campos: en qué columna va cada uno y cómo se comparan |
| `Book` | un libro tal como lo escribe el usuario; se convierte en una fila de la matriz |
| `SortMethod`, `SearchMethod` | los métodos que se pueden escoger |

La capacidad de la biblioteca (filas de la matriz) está en `src/main/resources/application.properties`
(`library.capacity`, 20 por defecto).
