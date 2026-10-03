package e2;
public class Distance {

    /**
     * Función principal del ejercicio.
     * Aplica las reglas de sentarse y levantarse hasta que el aula se estabiliza.
     */
    public static char[][] seatingPeople(char[][] layout) {

        // Validación layaout (el parámetro)
        validateLayout(layout);

        // Copiamos el layout inicial para trabajar sobre él
        char[][] current = copy(layout);

        while (true) {
            // Calculamos la siguiente iteración sin modificar la actual
            char[][] next = computeNext(current);

            // Si no hay cambios (si next es igual que current) , hemos llegado al estado final
            if (areEqual(current, next)) {
                return next;
            }

            // Si hay cambios, seguimos iterando
            current = next;
        }
    }

    /**
     * Comprueba que el layout es válido:
     * - No es null
     * - No es ragged (todas las filas tienen la misma longitud / es una matriz regular)
     * - Solo contiene '.' o 'A'
     */

    // La función es privae en vez de public ya que es una función interna que ayuda a la principal (public función)
    private static void validateLayout(char[][] layout) {
        // Si el aula no existe devuelve error
        if (layout == null) {
            throw new IllegalArgumentException("Layout null");
        }

        int cols = layout[0].length; // guarda cuantás columnas tiene la primera fila

        for (char[] row : layout) { // fila por fila revisando el aula

            // Si el número de columnas no coincide con la primera -> devuelve matriz irregular
            if (row.length != cols) {
                throw new IllegalArgumentException("Layout ragged"); // ragged = irregular
            }

            // Revisamos los carácteres
            for (char c : row) {
                // Si un carácter difrente a  "." o "A" entonces devuelve error
                if (c != '.' && c != 'A') {  // # no puede aparecer en la matriz entrada tampoco
                    throw new IllegalArgumentException("Invalid character: " + c);
                }
            }
        }
    }

    /**
     * Crea una copia del layout.
     */
    private static char[][] copy(char[][] layout) {
        // Crea matriz vacía del mismo tamaño que layaout
        char[][] result = new char[layout.length][layout[0].length];
        for (int i = 0; i < layout.length; i++) {  // copia fila por fila
            // Copia fila completa de layaout a result ( System.arraycopy copia arrays enteros )
            System.arraycopy(layout[i], 0, result[i], 0, layout[0].length);
        }
        return result; // devuelve copia del aula
    }

    /**
     * Calcula la siguiente iteración aplicando las reglas:
     * - Sentarse: 'A' → '#' si no tiene vecinos '#'
     * - Levantarse: '#' → 'A' si tiene ≥ 4 vecinos '#'
     * - '.' permanece igual
     */
    private static char[][] computeNext(char[][] current) {
        int rows = current.length;
        int cols = current[0].length;

        char[][] next = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                char seat = current[r][c];

                if (seat == '.') {
                    // Los asientos inválidos nunca cambian
                    next[r][c] = '.';
                    continue;
                }

                int occupiedNeighbors = countAdjacent(current, r, c);

                if (seat == 'A') {
                    // Regla de sentarse: si no hay vecinos ocupados
                    next[r][c] = (occupiedNeighbors == 0) ? '#' : 'A';
                } else { // seat == '#'
                    // Regla de levantarse: si tiene 4 o más vecinos ocupados
                    next[r][c] = (occupiedNeighbors >= 4) ? 'A' : '#';
                }
            }
        }

        return next;
    }

    /**
     * Cuenta los vecinos ocupados ('#') alrededor de una posición.
     * Se revisan las 8 posiciones adyacentes.
     */
    private static int countAdjacent(char[][] layout, int r, int c) {
        int count = 0;

        // Movimientos relativos a la posición actual
        int[] moves = {-1, 0, 1};

        for (int dr : moves) {
            for (int dc : moves) {

                // Saltamos la posición central (dr=0, dc=0)
                if (dr == 0 && dc == 0) continue;

                int nr = r + dr;
                int nc = c + dc;

                // Comprobamos límites
                if (nr >= 0 && nr < layout.length &&
                        nc >= 0 && nc < layout[0].length) {

                    if (layout[nr][nc] == '#') {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    /**
     * Compara dos matrices para ver si son iguales.
     */
    private static boolean areEqual(char[][] a, char[][] b) {
        for (int r = 0; r < a.length; r++) {
            for (int c = 0; c < a[0].length; c++) {
                if (a[r][c] != b[r][c]) return false;
            }
        }
        return true;
    }
}
