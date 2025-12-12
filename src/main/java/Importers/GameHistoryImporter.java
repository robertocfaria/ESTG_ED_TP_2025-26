package Importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Utility class responsible for importing, listing, and reading saved game history files.
 * It primarily targets JSON files stored in a designated game history folder and
 * implements basic parsing to display the history log content to the console.
 */
public class GameHistoryImporter {

    /**
     * Retrieves an array of filenames for all JSON files found in the game history folder.
     * The method checks two possible folder paths: the default {@code src/main/resources/gamehistory}
     * and a fallback {@code saved_games}.
     *
     * @return An array of strings containing the names of all files ending with {@code .json};
     * returns an empty array if the folder does not exist or contains no JSON files.
     */
    public static String[] getHistoryFiles() {
        File folder = getFolder();

        if (folder == null || !folder.exists()) {
            return new String[0];
        }

        FilenameFilter jsonFilter = (dir, name) -> name.toLowerCase().endsWith(".json");
        String[] files = folder.list(jsonFilter);

        return (files == null) ? new String[0] : files;
    }

    /**
     * Reads a specific game history JSON file and prints its content in a formatted,
     * human-readable manner to the console.
     * The method attempts to extract and display key information like the game date,
     * player names, player types, and individual action logs.
     *
     * @param filename The name of the JSON file to be read (e.g., {@code game_20251212_100000.json}).
     */
    public static void printHistoryFile(String filename) {
        File folder = getFolder();
        File file = new File(folder, filename);

        if (!file.exists()) {
            System.err.println("Erro: O ficheiro '" + filename + "' nao foi encontrado.");
            return;
        }

        System.out.println("\nA carregar ficheiro...");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("\"game_date\":")) {
                    String date = extractValue(line);
                    System.out.println("\n------------------------------------------------");
                    System.out.println("- DATA DO JOGO: " + date);
                    System.out.println("------------------------------------------------");
                }
                else if (line.startsWith("\"name\":")) {
                    String name = extractValue(line);
                    System.out.println("\n > JOGADOR: " + name.toUpperCase());
                }
                else if (line.startsWith("\"type\":")) {
                    String type = extractValue(line);
                    System.out.println("   TIPO: " + type);
                    System.out.println("   HISTORICO DE ACOES:");
                    System.out.println("   -------------------");
                }
                else if (line.startsWith("\"event_log\":")) {
                    String log = extractValue(line);
                    System.out.println("   * " + log);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
    }

    /**
     * Extracts the string value contained between double quotes after the colon (:)
     * on a JSON-formatted line.
     *
     * @param line The line of text read from the JSON file.
     * @return The extracted string value, or "---" if parsing fails.
     */
    private static String extractValue(String line) {
        int firstQuote = line.indexOf("\"", line.indexOf(":"));
        int lastQuote = line.lastIndexOf("\"");

        if (firstQuote != -1 && lastQuote != -1 && lastQuote > firstQuote) {
            return line.substring(firstQuote + 1, lastQuote);
        }
        return "---";
    }

    /**
     * Determines the location of the game history folder.
     * It checks the primary path {@code src/main/resources/gamehistory} and
     * falls back to {@code saved_games} if the primary path is invalid or does not exist.
     *
     * @return A {@link File} object representing the directory.
     */
    private static File getFolder() {
        String path = "src/main/resources/gamehistory";
        File folder = new File(path);

        if (!folder.exists() || !folder.isDirectory()) {
            folder = new File("saved_games");
        }
        return folder;
    }
}