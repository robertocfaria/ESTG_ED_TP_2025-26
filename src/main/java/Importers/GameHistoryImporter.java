package Importers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class GameHistoryImporter {

    /**
     * Procura ficheiros de histórico e devolve os seus nomes.
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
     * Lê um ficheiro JSON específico e imprime o seu conteúdo formatado na consola.
     * Versão compatível (apenas caracteres simples).
     * @param filename O nome do ficheiro (ex: "game_2025.json")
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

            // Parsing manual linha a linha
            while ((line = br.readLine()) != null) {
                line = line.trim();

                // 1. Data do Jogo
                if (line.startsWith("\"game_date\":")) {
                    String date = extractValue(line);
                    System.out.println("\n------------------------------------------------");
                    System.out.println("- DATA DO JOGO: " + date);
                    System.out.println("------------------------------------------------");
                }
                // 2. Nome do Jogador
                else if (line.startsWith("\"name\":")) {
                    String name = extractValue(line);
                    System.out.println("\n > JOGADOR: " + name.toUpperCase());
                }
                // 3. Tipo (Humano/Bot)
                else if (line.startsWith("\"type\":")) {
                    String type = extractValue(line);
                    System.out.println("   TIPO: " + type);
                    System.out.println("   HISTORICO DE ACOES:");
                    System.out.println("   -------------------");
                }
                // 4. Log do Evento
                else if (line.startsWith("\"event_log\":")) {
                    String log = extractValue(line);
                    System.out.println("   * " + log);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
    }

    private static String extractValue(String line) {
        int firstQuote = line.indexOf("\"", line.indexOf(":"));
        int lastQuote = line.lastIndexOf("\"");

        if (firstQuote != -1 && lastQuote != -1 && lastQuote > firstQuote) {
            return line.substring(firstQuote + 1, lastQuote);
        }
        return "---";
    }

    private static File getFolder() {
        String path = "src/main/resources/gamehistory";
        File folder = new File(path);

        if (!folder.exists() || !folder.isDirectory()) {
            folder = new File("saved_games");
        }
        return folder;
    }
}