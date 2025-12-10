package Menus;

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