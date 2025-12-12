package Menus;

import CoreGame.GameManager;
import Exporters.MapExporter;
import Importers.MapImporter;
import Interfaces.IHallway;
import Map.*;
import Reader.*;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.DoubleLinkedUnorderedList;

import java.util.Iterator;
import java.util.Random;

public class StartMenu {
    public static Random rand = new Random();

    public static void menu() {
        UnorderedListADT<Maze> mapsList;
        Maze[] loadedMaps;
        int opcao;
        int tamanho;
        GameManager game;
        IHallway hallway;
        Maze maze;

        String mazesFilePath = "src/main/resources/mazes.json";
        MapExporter exporter = new MapExporter();
        MapImporter importer = new MapImporter();
        mapsList = new DoubleLinkedUnorderedList<>();

        try {
            loadedMaps = importer.importArrayFromJson(mazesFilePath);

            for (int i = 0; i < loadedMaps.length; i++) {
                mapsList.addToRear(loadedMaps[i]);
            }
            System.out.println(mapsList.size() + " mapas carregados");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapas guardados, a iniciar um conjunto vazio");
            mapsList = new DoubleLinkedUnorderedList<>();
        }

        do {
            displayMenu();
            opcao = Reader.readInt(0, 4, "Opcao: ");

            Maze newMap = null;
            game = new GameManager();

            switch (opcao) {
                case 1:
                    tamanho = rand.nextInt(90) + 10;
                    newMap = new Maze(tamanho);
                    break;
                case 2:
                    tamanho = Reader.readInt(10, 90, "Insira o numero de desafios que pretende (10 a 90): ");
                    newMap = new Maze(tamanho);
                    break;
                case 3:
                    if (mapsList.isEmpty()) {
                        System.out.println("Não existem mapas para carregar, por favor gere um primeiro (Opção 1 ou 2). ");
                        break;
                    }

                    System.out.println("Mapas disponíveis");

                    int index = 1;
                    for (Maze map : mapsList) {
                        System.out.printf("%d. %s\n", index++, map.getName());
                    }


                    int mapChoice = Reader.readInt(1, mapsList.size(), "Escolha o labirinto que deseja: ") - 1;
                    Maze chosenMap = null;

                    index = 0;
                    for (Maze map : mapsList) {
                        if (index == mapChoice) {
                            chosenMap = map;
                        }

                        index++;
                    }

                    System.out.println("A iniciar jogo com " + chosenMap.getName());
                    game.startGame(chosenMap);
                    break;

                default:
                    new SelectGameHistory();
            }

            if (newMap != null) {
                mapsList.addToRear(newMap);

                Maze[] newMapArray = new Maze[mapsList.size()];
                try {
                    Iterator<Maze> iterator = mapsList.iterator();

                    for (int i = 0; i < newMapArray.length; i++) {
                        newMapArray[i] = iterator.next();
                    }

                    exporter.exportArrayToJson(newMapArray, mazesFilePath);
                    System.out.println("Mapa adicionado ao json");

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
        System.out.println("| 4 - Ver resultado de jogos terminados");
        System.out.println("| 0 - Sair");
        System.out.println("|----------------------------------------------------------------");
    }

    private static void firstMenu() {
        int option;
        do {
            System.out.println("|----------------------------------------------------------------");
            System.out.println("|- Bem vindo ao Jogo do Labirinto!");
            System.out.println("|----------------------------------------------------------------");
            System.out.println("| 1 - Novo Jogo");
            System.out.println("| 2 - Ver resultado de jogos terminados");
            System.out.println("| 0 - Sair");
            System.out.println("|----------------------------------------------------------------");

            option = Reader.readInt(0, 2, "Escolha uma opcao");

            switch (option) {
                case 1:
                    secondMenu();
                    break;
                case 2:
                    new SelectGameHistory();
                    break;
                default:
                    System.out.println("Saindo...");
            }
        } while (option != 0);
    }

    private static void displayMazes(UnorderedListADT<Maze> mazesList) {
        Iterator<Maze> iterator = mazesList.iterator();

        System.out.println("===Mapas disponíveis===");

        int index = 1;
        while (iterator.hasNext()) {
            System.out.printf("%d. %s", index++, iterator.next().getName());
        }
    }

    private static void secondMenu() {
        int option;
        int mapSize;

        UnorderedListADT<Maze> mapsList = new DoubleLinkedUnorderedList<>();
        Maze[] loadedMaps;

        MapImporter importer = new MapImporter();

        GameManager game;

        try {
            loadedMaps = importer.importArrayFromJson(MapExporter.FILEPATH);

            for (int i = 0; i < loadedMaps.length; i++) {
                mapsList.addToRear(loadedMaps[i]);
            }
            System.out.println(mapsList.size() + " mapas carregados");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapas guardados, a iniciar um conjunto vazio");
            mapsList = new DoubleLinkedUnorderedList<>();
        }

        do {
            System.out.println("|----------------------------------------------------------------");
            System.out.println("|- Bem vindo ao Jogo do Labirinto!");
            System.out.println("|----------------------------------------------------------------");
            System.out.println("| 1 - Mapa Random");
            System.out.println("| 2 - Criar um Mapa");
            System.out.println("| 3 - Escolher um Mapa");
            System.out.println("| 0 - Sair");
            System.out.println("|----------------------------------------------------------------");

            option = Reader.readInt(0, 3, "Escolha uma opcao: ");

            Maze newMap = null;
            game = new GameManager();

            switch (option) {
                case 1:
                    mapSize = rand.nextInt(90) + 10;
                    newMap = new Maze(mapSize);
                    game.startGame(newMap);
                    break;

                case 2:
                    mapSize = Reader.readInt(10, 90, "Insira o numero de desafios que pretende (10 a 90): ");
                    newMap = new Maze(mapSize);
                    game.startGame(newMap);
                    break;

                case 3:
                    if (mapsList.isEmpty()) {
                        System.out.println("Não existem mapas para carregar, por favor gere um primeiro (Opção 1 ou 2). ");
                        break;
                    }

                    displayMazes(mapsList);

                    int mapChoice = Reader.readInt(1, mapsList.size(), "Escolha o labirinto que deseja: ") - 1;
                    Maze chosenMap = null;

                    int index = 0;
                    for (Maze map : mapsList) {
                        if (index == mapChoice) {
                            chosenMap = map;
                        }

                        index++;
                    }

                    System.out.println("A iniciar jogo com " + chosenMap.getName());
                    game.startGame(chosenMap);
                    break;

                default:
                    System.out.println("Saindo...");
            }

            if (newMap != null) {
                exportMapsToJson(newMap, mapsList);
            }

        } while (option != 0);
    }

    private static void exportMapsToJson(Maze newMap, UnorderedListADT<Maze> mapsList) {
        MapExporter exporter = new MapExporter();

        mapsList.addToRear(newMap);

        try {
            exporter.exportListADTToJson(mapsList, MapExporter.FILEPATH);
            System.out.println("Mapa adicionado ao json");
        } catch (Exception e) {
            System.out.println("Erro ao guardar mapa" + e.getMessage());
        }
    }
}
