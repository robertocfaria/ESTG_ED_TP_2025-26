package Menus;

import CoreGame.GameManager;
import Reader.*;

public class StartMenu {

    public static void menu() {
        int opcao = 0;
        Reader reader = new Reader();


        do {
            displayMenu();
            opcao = reader.readInt(0, 4, "Opcao: ");
            switch (opcao) {
                case 1:
                    //aqui temos de gerar um novo mapa random
                    GameManager game = new GameManager();
                    game.startGame();
                case 2:
                    //pedir ao utilizador para introduzir o tamanho do mapa.

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
        System.out.println("| 4 - Carregar jogo por acabar");
        System.out.println("| 0 - Sair");
        System.out.println("|----------------------------------------------------------------");
    }
}
