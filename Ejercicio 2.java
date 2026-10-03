package e2;

public class Distance {

    /**
     * Función principal que resuelve el problema.
     * Modifica el estado de los asientos iteración a iteración hasta que
     * no haya más cambios (el sistema se estabilice).
     */
    public static char[][] seatingPeople(char[][] layout) {

        // 1. Primero comprobamos que la matriz de entrada sea correcta
        validateLayout(layout);

        // 2. Hacemos una copia inicial para no modificar los datos originales directamente
        char[][] current = copy(layout);

        // 3. Bucle infinito que romperemos cuando la matriz no cambie respecto al paso anterior
        while (true) {
            // Calculamos el estado de las sillas en el siguiente instante de tiempo
            char[][] next = computeNext(current);

            // Si el estado actual y el siguiente son idénticos, hemos llegado al equilibrio
            if (areEqual(current, next)) {
                return next; // Matriz final estabilizada
            }

            // Si ha habido cambios, actualizamos 'current' para la siguiente iteración
            current = next;
        }
    }

    /**
     * Valida que la matriz cumpla con los requisitos del enunciado:
     * - No debe ser nula.
     * - Debe ser rectangular/cuadrada (todas las filas miden lo mismo, no ragged).
     * - Solo debe contener caracteres válidos ('.' para suelo y 'A' para asiento libre).
     */
    private static void validateLayout(char[][] layout) {
        // Comprobar si la matriz no ha sido inicializada
        if (layout == null) {
            throw new IllegalArgumentException("El layout no puede ser null");
        }

        // Guardamos el número de columnas de la primera fila como referencia
        int numCols = layout[0].length;

        for (int i = 0; i < layout.length; i++) {
            // Si alguna fila tiene una longitud distinta a la primera, la matriz es irregular
            if (layout[i].length != numCols) {
                throw new IllegalArgumentException("La matriz es irregular (ragged array)");
            }

            // Validar los caracteres de cada celda
            for (int j = 0; j < layout[i].length; j++) {
                char c = layout[i][j];
                // En el layout inicial solo se permiten '.' y 'A'
                if (c != '.' && c != 'A') {
                    throw new IllegalArgumentException("Carácter no permitido en la entrada: " + c);
                }
            }
        }
    }

    /**
     * Crea y devuelve una copia independiente (copia profunda) de la matriz.
     * Es necesario para no sobreescribir la matriz previa mientras calculamos la nueva.
     */
    private static char[][] copy(char[][] layout) {
        int rows = layout.length;
        int cols = layout[0].length;
        
        char[][] replica = new char[rows][cols];

        // Recorremos celda por celda copiando los valores
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                replica[i][j] = layout[i][j];
            }
        }

        return replica;
    }

    /**
     * Calcula la matriz del siguiente paso aplicando las reglas del enunciado:
     * - Un asiento libre 'A' se ocupa '#' si NO tiene vecinos ocupados alrededor.
     * - Un asiento ocupado '#' se libera 'A' si tiene 4 o más vecinos ocupados.
     * - El suelo '.' no cambia nunca.
     */
    private static char[][] computeNext(char[][] current) {
        int rows = current.length;
        int cols = current[0].length;

        // Reservamos memoria para la matriz resultado de esta iteración
        char[][] next = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char actual = current[r][c];

                // Regla 1: El suelo permanece igual
                if (actual == '.') {
                    next[r][c] = '.';
                } 
                else {
                    // Contamos cuántas personas ocupadas ('#') hay en las 8 casillas de alrededor
                    int ocupadosVecinos = countAdjacent(current, r, c);

                    // Regla 2: Asiento libre 'A'
                    if (actual == 'A') {
                        if (ocupadosVecinos == 0) {
                            next[r][c] = '#'; // Se sienta alguien
                        } else {
                            next[r][c] = 'A'; // Sigue libre
                        }
                    } 
                    // Regla 3: Asiento ocupado '#'
                    else if (actual == '#') {
                        if (ocupadosVecinos >= 4) {
                            next[r][c] = 'A'; // Se levanta por agobio
                        } else {
                            next[r][c] = '#'; // Sigue ocupado
                        }
                    }
                }
            }
        }

        return next;
    }

    /**
     * Cuenta cuántas casillas adyacentes (en las 8 direcciones) tienen un asiento ocupado ('#').
     */
    private static int countAdjacent(char[][] layout, int r, int c) {
        int contador = 0;

        // Generamos los desplazamientos en fila (dr) y columna (dc) desde -1 hasta +1
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {

                // Ignoramos la propia casilla central (0, 0)
                if (dr == 0 && dc == 0) {
                    continue;
                }

                int nr = r + dr; // Nueva fila
                int nc = c + dc; // Nueva columna

                // Comprobamos que las coordenadas del vecino estén dentro de los límites de la matriz
                if (nr >= 0 && nr < layout.length && nc >= 0 && nc < layout[0].length) {
                    // Si el vecino está ocupado, incrementamos el contador
                    if (layout[nr][nc] == '#') {
                        contador++;
                    }
                }
            }
        }

        return contador;
    }

    /**
     * Función auxiliar para comparar si dos matrices bidimensionales de caracteres son iguales celda a celda.
     */
    private static boolean areEqual(char[][] m1, char[][] m2) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false; // A la primera diferencia, devolvemos false
                }
            }
        }
        return true; // Si terminan los bucles sin diferencias, son iguales
    }
}
