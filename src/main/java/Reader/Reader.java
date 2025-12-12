package Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility class for reading user input from the console (System.in).
 * It provides methods for safely reading integers within a specified range
 * and general text strings, handling potential I/O and format exceptions.
 */
public class Reader {

    /**
     * Reads an integer from the user's input, ensuring that the input is a valid
     * integer and falls within the specified minimum and maximum options (inclusive).
     * The method prompts the user repeatedly until valid input is received.
     *
     * @param minOption The minimum acceptable integer value (inclusive).
     * @param maxOption The maximum acceptable integer value (inclusive).
     * @param message The prompt message displayed to the user.
     * @return The validated integer input from the user, or -1 if an I/O error occurs.
     */
    public static int readInt(int minOption, int maxOption, String message) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            int numero = -1;
            do {
                System.out.print(message);
                String linha = reader.readLine();
                try {
                    numero = Integer.parseInt(linha);
                } catch (NumberFormatException e) {
                    System.out.println("Por favor introduza um numero");
                }
                if (numero < minOption || numero > maxOption) {
                    System.out.println("Por favor digite um numero valido.");
                }
            } while (numero < minOption || numero > maxOption);
            return numero;
        } catch (IOException e) {
            System.out.println("Erro de entrada.");
        }
        return -1;
    }

    /**
     * Reads a single line of text input from the user.
     *
     * @param message The prompt message displayed to the user.
     * @return The string input from the user, or an empty string if an I/O error occurs.
     */
    public static String readString(String message) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print(message);
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao ler a entrada.");
            return "";
        }
    }
}