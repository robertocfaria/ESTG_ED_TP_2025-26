package Exporters;

import CoreGame.HistoryEntry;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;
import Structures.Stack.ArrayStack;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * Utility class responsible for exporting the entire game history to an external
 * file format, specifically JSON.
 * <p>
 * It collects history data from all players, structures it into a JSON format,
 * handles file path creation, and writes the output to a timestamped file.
 */
public class ExportGameHistory {

    /**
     * Exports the history of all players in the game to a JSON file.
     * <p>
     * The file is created in the {@code src/main/resources/gamehistory} directory
     * with a unique filename based on the current timestamp (e.g., {@code game_YYYYMMDD_HHmmss.json}).
     * The JSON structure includes the game date and a list of players, each containing
     * their name, type, and a chronological list of history entries (movement and events).
     *
     * @param players The {@link ListADT} of {@link IPlayer} objects whose history will be exported.
     * @return {@code true} if the export was successful; {@code false} otherwise (e.g., due to an IO error).
     */
    public static boolean exportToJson(ListADT<IPlayer> players) {

        String folderPath = "src/main/resources/gamehistory";
        java.io.File directory = new java.io.File(folderPath);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = directory.getAbsolutePath() + java.io.File.separator + "game_" + timestamp + ".json";

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"game_date\": \"").append(timestamp).append("\",\n");
        json.append("  \"players\": [\n");

        Iterator<IPlayer> it = players.iterator();

        while (it.hasNext()) {
            IPlayer player = it.next();

            json.append("    {\n");
            json.append("      \"name\": \"").append(player.getName()).append("\",\n");
            json.append("      \"type\": \"").append(player.isRealPlayer() ? "Human" : "Bot").append("\",\n");
            json.append("      \"history\": [\n");

            ArrayStack<HistoryEntry> stack = player.getHistoryCopy();

            ArrayStack<HistoryEntry> chronological = new ArrayStack<>();
            while (!stack.isEmpty()) {
                chronological.push(stack.pop());
            }

            while (!chronological.isEmpty()) {
                HistoryEntry entry = chronological.pop();

                if (entry == null) { continue; }

                String fullLog = entry.toString().replace("\"", "'").replace("\n", " ");

                json.append("        {\n");
                json.append("          \"event_log\": \"").append(fullLog).append("\"\n");

                if (!chronological.isEmpty()) {
                    json.append("        },\n");
                } else {
                    json.append("        }\n");
                }
            }

            json.append("      ]\n");

            if (it.hasNext()) {
                json.append("    },\n");
            } else {
                json.append("    }\n");
            }
        }

        json.append("  ]\n");
        json.append("}");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(json.toString());
            System.out.println("Histórico exportado com sucesso para: " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao exportar histórico: " + e.getMessage());
            return false;
        }
    }
}