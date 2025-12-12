package Menus;

import CoreGame.GameManager;
import Exporters.MapExporter;
import Importers.MapImporter;
import Interfaces.IHallway;
import Interfaces.IMap;
import Map.*;
import Reader.*;
import Structures.Interfaces.OrderedListADT;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.ArrayOrderedList;
import Structures.List.ArrayUnorderedList;
import Structures.List.DoubleLinkedUnorderedList;

import java.io.File;
import java.util.Iterator;
import java.util.Random;

public class StartMenu {
    public static Random rand = new Random();

    public static void menu() {
        int opcao;
        int tamanho;
        GameManager game;
        IHallway hallway;
        Map maze;

        MapExporter exporter = new MapExporter();
        MapImporter importer = new MapImporter();
        String mapsFilePath = "src/main/resources/maps.json";
        UnorderedListADT<Map> mapsList = new DoubleLinkedUnorderedList<>();
        Map[] loadedMaps;

        try {
            File file = new File(mapsFilePath);

            loadedMaps = importer.importArrayFromJson(mapsFilePath);

            for (int i = 0; i < loadedMaps.length; i++) {
                mapsList.addToRear(loadedMaps[i]);
            }
            System.out.println("Conjunto de " + mapsList.size() + " mapas carregado");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapas guardados, a iniciar um conjunto vazio");
            mapsList = new DoubleLinkedUnorderedList<>();
        }

        do {
            displayMenu();
            opcao = Reader.readInt(0, 4, "Opcao: ");

            Map newMap = null;
            game = new GameManager();

            switch (opcao) {
                case 1:
                    tamanho = rand.nextInt(90) + 10;
                    newMap = new Map(tamanho);
                    break;
                case 2:
                    tamanho = Reader.readInt(10, 90, "Insira o numero de desafios que pretende (10 a 90): ");
                    newMap = new Map(tamanho);
                    break;
                case 3:
                    if (mapsList.isEmpty()) {
                        System.out.println("Não existem mapas para carregar, por favor gere um primeiro (Opção 1 ou 2). ");
                        break;
                    }

                    System.out.println("Mapas disponíveis");

                    int index = 1;
                    for (Map map : mapsList) {
                        System.out.printf("%d. %s\n", index++, map.getName());
                    }


                    int mapChoice = Reader.readInt(1, mapsList.size(), "Escolha o labirinto que deseja: ") - 1;
                    Map chosenMap = null;

                    index = 0;
                    for (Map map : mapsList) {
                        if (index == mapChoice) {
                            chosenMap = map;
                        }

                        index++;
                    }

                    System.out.println("A iniciar jogo com " + chosenMap.getName());
                    game.startGame(chosenMap);


                    /**
                    System.out.println("--- Selecionar Mapa ---");
                    try {
                        Map[] availableMaps = importer.importArrayFromJson("src/main/resources/maps.json");

                        if (availableMaps == null || availableMaps.length == 0) {
                            System.out.println("Nenhum mapa encontrado em maps.json.");
                            break;
                        }

                        System.out.println("Mapas Disponíveis:");
                        for (int i = 0; i < availableMaps.length; i++) {
                            System.out.println((i + 1) + ". " + availableMaps[i].getName());
                        }

                        int mapChoice = Reader.readInt(1, availableMaps.length, "Escolha o número do mapa: ");

                        Map chosenMap = availableMaps[mapChoice - 1];
                        game = new GameManager();

                        System.out.println("A iniciar o jogo...");
                        game.startGame(chosenMap);
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar ou processar mapas: " + e.getMessage());
                    }*/
                    break;

                default:
            }

            if (newMap != null) {
                mapsList.addToRear(newMap);


                Map[] newMapArray = new Map[mapsList.size()];
                try {
                    Iterator<Map> iterator = mapsList.iterator();

                    for (int i = 0; i < newMapArray.length; i++) {
                        newMapArray[i] = iterator.next();
                    }

                    exporter.exportArrayToJson(newMapArray, mapsFilePath);
                    System.out.println("Mapa adicionado ao ficheiro");

                    game = new GameManager();
                    System.out.println("A iniciar o jogo com: " + newMap.getName());

                    game.startGame(newMap);
                } catch (Exception e) {
                    System.out.println("Erro ao guardar mapa" + e.getMessage());
                }
            }
        } while (opcao != 0);
    }

    private static void displayMenu() {
        System.out.println("|----------------------------------------------------------------");
        System.out.println("|- Bem vindo ao Jogo do Labirinto!");
        System.out.println("|----------------------------------------------------------------");
        System.out.println("| 1 - Novo Jogo -> Mapa Random");
        System.out.println("| 2 - Novo Jogo -> Crie o seu Mapa");
        System.out.println("| 3 - Novo Jogo -> Escolha o Mapa");
        System.out.println("| 4 - Ver resultados de jogo terminados");
        System.out.println("| 0 - Sair");
        System.out.println("|----------------------------------------------------------------");
    }

    private static void menu3() {
        System.out.println("|----------------------------------------------------------------");
        System.out.println("|- Lista de Mapas Existentes ");
        System.out.println("|----------------------------------------------------------------");
        System.out.println("| 1 - 23H00 10/12/2025 - 10 Salas (x conecoes)");

    }
}
