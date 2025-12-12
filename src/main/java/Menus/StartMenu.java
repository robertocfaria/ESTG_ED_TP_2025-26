package Menus;

import CoreGame.GameManager;
import Exporters.MapExporter;
import Importers.MapImporter;
import Interfaces.IHallway;
import Map.*;
import Reader.*;
import Structures.Interfaces.UnorderedListADT;
import Structures.List.DoubleLinkedUnorderedList;
import Util.Utils;

import java.util.Iterator;
import java.util.Random;

/**
 * Manages the primary interactive console menus for the game, including the main menu
 * for starting a new game or viewing history, and the secondary menu for map selection
 * (random, creation, or loading).
 */
public class StartMenu {
    public static Random rand = new Random();

    /**
     * Displays a numbered list of all available mazes loaded from the game's storage.
     *
     * @param mazesList The {@link UnorderedListADT} containing the {@link Maze} objects.
     */
    private static void displayMazes(UnorderedListADT<Maze> mazesList) {
        Iterator<Maze> iterator = mazesList.iterator();

        System.out.println("======Mapas disponiveis======");

        int index = 1;
        while (iterator.hasNext()) {
            System.out.printf("%d. %s\n", index++, iterator.next().getName());
        }
    }

    /**
     * Imports the list of saved {@link Maze} objects from the designated JSON file.
     * Implements basic error handling, returning an empty list if loading fails.
     *
     * @return An {@link UnorderedListADT} of loaded {@link Maze} objects.
     */
    private static UnorderedListADT<Maze> importMazesFromJson() {
        MapImporter importer = new MapImporter();

        UnorderedListADT<Maze> mapsList;

        try {
            mapsList = importer.importListADTFromJson(MapExporter.FILEPATH);
            System.out.println("Foram carregado: " + mapsList.size() + " mapas");
        } catch (Exception e) {
            System.out.println("Erro ao carregar mapas guardados, a iniciar um conjunto vazio");
            mapsList = new DoubleLinkedUnorderedList<>();
        }

        return mapsList;
    }

    /**
     * Adds a newly generated map to the existing list and exports the entire updated
     * collection back to the JSON storage file.
     *
     * @param newMap The newly created {@link Maze} to be saved.
     * @param mapsList The current {@link UnorderedListADT} of all mazes, to which the new map is added.
     */
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

    /**
     * Displays the main menu loop, allowing the user to choose between starting a
     * new game, viewing history, or exiting.
     */
    public static void Menu() {
        int option;
        do {
            System.out.println("|----------------------------------------------------------------");
            System.out.println("|- Bem vindo ao Jogo do Labirinto!");
            System.out.println("|----------------------------------------------------------------");
            System.out.println("| 1 - Novo Jogo");
            System.out.println("| 2 - Ver resultado de jogos terminados");
            System.out.println("| 0 - Sair");
            System.out.println("|----------------------------------------------------------------");

            option = Reader.readInt(0, 2, "Escolha uma opcao: ");

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

    /**
     * Displays the secondary menu loop for map selection.
     * Options include generating a random map, creating a custom-sized map,
     * or loading a saved map from the list.
     */
    private static void secondMenu() {
        int option;
        int mapSize;

        UnorderedListADT<Maze> mapsList;
        GameManager game;

        mapsList = importMazesFromJson();

        do {
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

                    int mapChoice = Reader.readInt(1, mapsList.size(), "Escolha o labirinto que deseja: ");
                    Maze chosenMap = Utils.getListADTObject(mapChoice - 1, mapsList);


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
}
