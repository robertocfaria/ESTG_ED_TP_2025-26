package Menus;

import Interfaces.IPlayer;

public class GameVisuals {

    // Largura padrão para as caixas de texto
    private static final int WIDTH = 60;

    /**
     * Mostra um cabeçalho simples para configuração.
     */
    public static void showPlayerConfigHeader() {
        System.out.println();
        System.out.println("+----------------------------------------------------------+");
        System.out.println("|                 CONFIGURACAO DA EQUIPA                   |");
        System.out.println("+----------------------------------------------------------+");
    }

    /**
     * Feedback visual quando um jogador é adicionado.
     */
    public static void showPlayerAdded(String name, int playerNumber) {
        // Removemos o '✓' e usamos 'OK'
        System.out.printf("   [Jog. %d] %-30s ... ADICIONADO [OK]\n", playerNumber, name);
    }

    /**
     * Separador subtil.
     */
    public static void showNextPlayerSeparator() {
        System.out.println("   ........................................................");
    }

    /**
     * Mostra o separador da Ronda atual.
     */
    public static void showRoundSeparator(int roundNumber) {
        String title = "RONDA " + roundNumber;
        System.out.println();
        printLine("+", "-", "+");
        printCenteredLine(title);
        printLine("+", "-", "+");
    }

    /**
     * Destaca a vez do jogador atual.
     */
    public static void showPlayerTurn(String playerName) {
        System.out.println("\n    >>> A JOGAR: " + playerName.toUpperCase() + " <<<");
        System.out.println("    --------------------");
    }

    /**
     * Mostra o ecrã de Vitória.
     */
    public static void showVictory(String playerName) {
        System.out.println();
        printLine("+", "=", "+"); // Usa '=' para destacar mais
        printCenteredLine("* * * V I T O R I A  * * *"); // Sem acentos para segurança
        printLine("|", "-", "|");
        printCenteredLine("");
        printCenteredLine("PARABENS " + playerName.toUpperCase() + "!");
        printCenteredLine("Encontraste a saida da masmorra.");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }

    /**
     * Mostra visualmente o evento ExtraPlays.
     */
    public static void showExtraPlaysEvent(String playerName, int amount, boolean isRealPlayer) {
        if (!isRealPlayer) {
            return;
        }

        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("E V E N T O  E S P E C I A L");
        printLine("|", "-", "|");
        printCenteredLine("EXTRA JOGADAS");
        printCenteredLine("");
        printCenteredLine("O jogador " + playerName.toUpperCase());
        printCenteredLine("recebe +" + amount + " jogadas!");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }

    /**
     * Mostra visualmente o evento RollBack.
     */
    public static void showRollBackEvent(String playerName, String divisionName, boolean isRealPlayer) {
        if (!isRealPlayer) {
            return;
        }

        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("E V E N T O  E S P E C I A L");
        printLine("|", "-", "|");
        printCenteredLine("R O L L B A C K");
        printCenteredLine("");
        printCenteredLine("O jogador " + playerName.toUpperCase());
        printCenteredLine("foi movido de volta para:");
        printCenteredLine(divisionName.toUpperCase());
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }

    /**
     * Mostra visualmente o evento ShuffleAllPlayers.
     */
    public static void showShuffleAllPlayersEvent(boolean isRealPlayer) {
        if (!isRealPlayer) {
            return;
        }

        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("E V E N T O  E S P E C I A L");
        printLine("|", "-", "|");
        printCenteredLine("S H U F F L E  A L L");
        printCenteredLine("");
        printCenteredLine("As posicoes de todos os jogadores");
        printCenteredLine("foram baralhadas!");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }

    /**
     * Mostra visualmente o evento StunnedPlays.
     */
    public static void showStunnedPlaysEvent(String playerName, int amount, boolean isRealPlayer) {
        if (!isRealPlayer) {
            return;
        }

        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("E V E N T O  E S P E C I A L");
        printLine("|", "-", "|");
        printCenteredLine("S T U N N E D");
        printCenteredLine("");
        printCenteredLine("O jogador " + playerName.toUpperCase());
        printCenteredLine("ficou atordoado por " + amount + " ronda(s)!");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }


    /**
     * Mostra visualmente a lista de jogadores para troca,
     * com cada opcao centrada na moldura.
     */
    public static void showSwapChoiceMenu(IPlayer[] players) {
        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("T R O C A  D E  P O S I C O E S");
        printLine("|", "-", "|");
        printCenteredLine("Selecione o jogador para trocar:");
        printCenteredLine("");

        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                String option = (i + 1) + ") " + players[i].getName();
                printCenteredLine(option);
            }
        }

        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }


    /**
     * Mostra visualmente o evento SwapTwoPlayers.
     */
    public static void showSwapTwoPlayersEvent(String player1, String player2, boolean isRealPlayer) {
        if (!isRealPlayer) {
            return;
        }

        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("E V E N T O  E S P E C I A L");
        printLine("|", "-", "|");
        printCenteredLine("S W A P  P O S I C O E S");
        printCenteredLine("");
        printCenteredLine("Os jogadores:");
        printCenteredLine(player1.toUpperCase());
        printCenteredLine("e");
        printCenteredLine(player2.toUpperCase());
        printCenteredLine("trocarao de posicao!");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }


    /**
     * Mostra o ecrã de Game Over.
     */
    public static void showGameOver() {
        System.out.println();
        printLine("+", "=", "+");
        printCenteredLine("!!!  GAME OVER  !!!");
        printLine("|", "-", "|");
        printCenteredLine("");
        printCenteredLine("A tua jornada termina aqui...");
        printCenteredLine("Tenta novamente!");
        printCenteredLine("");
        printLine("+", "=", "+");
        System.out.println();
    }

    // --- MÉTODOS AUXILIARES ---

    private static void printLine(String left, String mid, String right) {
        System.out.print(left);
        for (int i = 0; i < WIDTH; i++) {
            System.out.print(mid);
        }
        System.out.println(right);
    }

    private static void printCenteredLine(String text) {
        // Removemos caracteres especiais que possam contar mal o tamanho
        int padding = (WIDTH - text.length()) / 2;
        int extra = (WIDTH - text.length()) % 2;

        System.out.print("|"); // Barra vertical simples
        for (int i = 0; i < padding; i++) System.out.print(" ");
        System.out.print(text);
        for (int i = 0; i < padding + extra; i++) System.out.print(" ");
        System.out.println("|");
    }
}