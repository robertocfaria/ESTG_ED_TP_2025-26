package Menus;

import CoreGame.GameManager;
import Map.Map;
import Reader.*;

import java.util.Random;

public class StartMenu {

    public static void menu() {
        int opcao = 0, tamanho = 0;
        GameManager game;

        Reader reader = new Reader();

        do {
            displayMenu();
            opcao = reader.readInt(0, 4, "Opcao: ");
            switch (opcao) {
                case 1:
                    Random random = new Random();
                    tamanho = random.nextInt(90) + 10;
                    game = new GameManager(new Map(tamanho));
                    System.out.println("A iniciar o jogo...");
                    game.startGame();
                    break;
                case 2:
                    tamanho = reader.readInt(10, 90, "Insira o numero de desafios que pretende (10 a 90): ");
                    game = new GameManager(new Map(tamanho));
                    System.out.println("A iniciar o jogo...");
                    game.startGame();
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
        System.out.println("| 4 - Carregar jogo por acabar ou ver resultados de jogo terminado");
        System.out.println("| 0 - Sair");
        System.out.println("|----------------------------------------------------------------");
    }
}
