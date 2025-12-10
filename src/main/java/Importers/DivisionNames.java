package Importers;

import Structures.List.ArrayUnorderedList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class responsible for importing textual data for the game.
 * This class focuses specifically on reading and processing division names
 * from external JSON file.
 */
public class DivisionNames {

    /**
     * Reads a text file (with a JSON structure) and extracts division names
     * into an unordered list.
     * This method implements a <b>fail-safe strategy</b>: if the file is not found
     * or a read error occurs, the method catches the exception, logs the error
     * to the console, and returns a list containing default generic names.
     * This ensures the game execution is not interrupted
     * due to missing configuration files.
     *
     * @param filePath The relative or absolute path to the file containing the names.
     * @return An {@link ArrayUnorderedList} containing the strings read from the file
     * or, in case of error, a list with default generic names.
     */
    public static ArrayUnorderedList<String> importNames(String filePath) {

        ArrayUnorderedList<String> namesList = new ArrayUnorderedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"") && !line.contains("division_names")) {
                    int startQuote = line.indexOf("\"");
                    int endQuote = line.lastIndexOf("\"");

                    if (startQuote != -1 && endQuote != -1 && endQuote > startQuote) {
                        String cleanName = line.substring(startQuote + 1, endQuote);
                        namesList.addToRear(cleanName);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro Crítico: O ficheiro com os nomes da sala nao foi encontrado. (" + filePath + ")");
            namesList.addToRear("Sala Misteriosa");
            namesList.addToRear("Corredor Escuro");
            namesList.addToRear("Laboratório Abandonado");
            namesList.addToRear("Cave Húmida");
            namesList.addToRear("Torre Alta");
            namesList.addToRear("Masmorra");
            namesList.addToRear("Sótão Empoeirado");
            namesList.addToRear("Jardim Oculto");
        } catch (IOException e) {
            System.err.println("Erro de Leitura: " + e.getMessage());
            namesList.addToRear("Sala Misteriosa");
            namesList.addToRear("Corredor Escuro");
            namesList.addToRear("Laboratório Abandonado");
            namesList.addToRear("Cave Húmida");
            namesList.addToRear("Torre Alta");
            namesList.addToRear("Masmorra");
            namesList.addToRear("Sótão Empoeirado");
            namesList.addToRear("Jardim Oculto");
        }

        return namesList;
    }
}