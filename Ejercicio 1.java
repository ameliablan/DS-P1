public class StringCount {

    // String Count (contar el nº de palabras de una cadena sabiendo que están separadas por 1 o más espacios)
    public static int countWords(String text) {
        // Devuelve 0 si el texto es nulo o está vacío.
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            // Una palabra empieza cuando encontramos un carácter distinto de espacio
            // al principio o después de uno o más espacios.
            if (text.charAt(i) != ' ' && (i == 0 || text.charAt(i - 1) == ' ')) {
                count++;
            }
        }

        return count;
    }

    // Cuenta cuántas veces aparece un carácter, diferenciando mayúsculas y minúsculas.
    public static int countChar(String text, char c) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    // Cuenta cuántas veces aparece un carácter ignorando mayúsculas y minúsculas.
    public static int countCharIgnoringCase(String text, char c) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        char lowerC = Character.toLowerCase(c);
        char upperC = Character.toUpperCase(c);
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            char current = text.charAt(i);
            if (current == lowerC || current == upperC) {
                count++;
            }
        }
        return count;
    }

    /**
     * Valida si una contraseña es segura.
     * Debe tener al menos 8 caracteres, una mayúscula, una minúscula,
     * un dígito y uno de estos caracteres especiales: ?, # o ,.
     */
    public static boolean isPasswordSafe(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (ch == '?' || ch == '#' || ch == ',') {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
