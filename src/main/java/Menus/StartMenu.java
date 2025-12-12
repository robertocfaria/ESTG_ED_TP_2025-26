package Menus;

import CoreGame.GameManager;
import DTOs.MapExporter;
import Interfaces.IHallway;
import Map.*;
import Reader.*;

import java.io.IOException;
import java.util.Random;

public class StartMenu {
    public static Random rand = new Random();

    public static void menu() {
        int opcao;
        int tamanho;
        GameManager game;
        IHallway hallway;
        Map maze;

        do {
            displayMenu();
            opcao = Reader.readInt(0, 4, "Opcao: ");
            switch (opcao) {
                case 1:
                    game = new GameManager();
                    tamanho = rand.nextInt(90) + 10;

                    System.out.println("A iniciar o jogo...");
                    maze = new Map(tamanho);
                    //aqui guarda a matriz
                    game.startGame(maze);
                    break;
                case 2:
                    game = new GameManager();
                    tamanho = Reader.readInt(10, 90, "Insira o numero de desafios que pretende (10 a 90): ");

                    System.out.println("A iniciar o jogo...");
                    maze = new Map(tamanho);
                    //aqui guarda a matriz
                    game.startGame(maze);
                    break;
                case 3:

                default:

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
