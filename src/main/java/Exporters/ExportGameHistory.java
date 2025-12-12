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

public class ExportGameHistory {

    /**
     * Exporta o histórico de todos os jogadores para um ficheiro JSON.
     * Guarda apenas a representação textual (toString) de cada evento.
     * @param players A lista de jogadores do jogo atual.
     * @return true se guardou com sucesso, false se houve erro.
     */
    public static boolean exportToJson(ListADT<IPlayer> players) {

        String folderPath = "src/main/resources/gamehistory";
        java.io.File directory = new java.io.File(folderPath);

        // Garantir que a pasta existe
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

            // 1. Obter cópia do histórico
            ArrayStack<HistoryEntry> stack = player.getHistoryCopy();

            // 2. Inverter para ordem cronológica
            ArrayStack<HistoryEntry> chronological = new ArrayStack<>();
            while (!stack.isEmpty()) {
                chronological.push(stack.pop());
            }

            // 3. Escrever no JSON
            while (!chronological.isEmpty()) {
                HistoryEntry entry = chronological.pop();

                // Ignorar nulos
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

            json.append("      ]\n"); // Fecha array history

            // Se houver mais jogadores, fecha o objeto e põe vírgula
            if (it.hasNext()) {
                json.append("    },\n");
            } else {
                json.append("    }\n");
            }
        }

        json.append("  ]\n"); // Fecha array players
        json.append("}");     // Fecha objeto principal

        // 4. Escrever no ficheiro
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