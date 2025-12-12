import DTOs.MapExporter;
import DTOs.MapImporter;
import Interfaces.IDivision;
import Interfaces.IHallway;
import Map.Map;
import Menus.StartMenu;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        //StartMenu.menu();

        System.out.println("=== Teste de Export/Import do Mapa ===");

        // 1. Criar mapa original
        Map original = new Map(10);
        String path = "map_export.json";

        // 2. Exportar
        MapExporter exporter = new MapExporter();
        exporter.exportToJson(original, path);
        System.out.println("Mapa exportado para: " + path);

        // 3. Importar
        MapImporter importer = new MapImporter();
        Map loaded = importer.importFromJson(path);
        System.out.println("Mapa importado com sucesso.");

        // 4. Validação

        // Verificar número de divisões
        if (original.getVertices().length != loaded.getVertices().length)
            throw new RuntimeException("ERRO: Número de divisões não coincide.");

        // Verificar tipos das divisões
        for (int i = 0; i < original.getVertices().length; i++) {
            IDivision d1 = original.getVertices()[i];
            IDivision d2 = loaded.getVertices()[i];

            if (d1 != null && d2 != null) {
                if (!d1.getClass().equals(d2.getClass()))
                    throw new RuntimeException("ERRO: Tipo da divisão " + i + " não coincide.");
            }
        }

        // Verificar matriz adjacente
        IHallway[][] m1 = original.getAdjMatrix();
        IHallway[][] m2 = loaded.getAdjMatrix();

        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {

                boolean h1 = m1[i][j] != null;
                boolean h2 = m2[i][j] != null;

                if (h1 != h2)
                    throw new RuntimeException("ERRO: Conexão perdida entre " + i + " e " + j);
            }
        }

        System.out.println("Validação concluída com sucesso!");
        System.out.println("O mapa exportado e importado é equivalente ao original.");
    }
}
