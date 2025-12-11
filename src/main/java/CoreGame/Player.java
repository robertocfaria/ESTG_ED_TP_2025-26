package CoreGame;

import Interfaces.*;
import Structures.Stack.ArrayStack;
import Util.Utils;


public class Player implements IPlayer {
    protected static int botNumber = 1;
    protected String name;
    protected int stunned;
    protected int extraRound;
    protected boolean realPlayer;
    protected ArrayStack<IEvent> movementsHistory;
    protected ArrayStack<IDivision> divisionsHistory;
    protected IDivision division;
    protected int numberDivisonsAvancedRound;
    protected ArrayStack<String> nameDivisionsAvancedRound;

    /**
     * Este construtor cria um jogador Real novo.
     *
     * @param name Nome do Jogador
     */
    public Player(String name) {
        this.name = name;
        this.stunned = 0;
        this.extraRound = 0;
        this.realPlayer = true;
        this.division = null;
        this.movementsHistory = new ArrayStack<>();
        this.divisionsHistory = new ArrayStack<>();
    }

    /**
     * Este construtor cria um jogador automático. Usa a variável estatica para definir o número"
     */
    public Player() {
        this.name = "BOT" + botNumber;
        botNumber++;
        this.stunned = 0;
        this.extraRound = 0;
        this.realPlayer = false;
        this.division = null;
        this.movementsHistory = new ArrayStack<>();
        this.divisionsHistory = new ArrayStack<>();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getStunned() {
        return this.stunned;
    }

    @Override
    public void addStunnedRound(int numberOfRounds) {
        this.stunned = numberOfRounds;
    }

    @Override
    public int getExtraRounds() {
        return this.extraRound;
    }

    @Override
    public void setExtraRound(int numberOfRounds) {
        this.extraRound = numberOfRounds;
    }

    @Override
    public boolean isRealPlayer() {
        return realPlayer;
    }

    @Override
    public void setDivision(IDivision division) {
        this.division = division;
    }

    @Override
    public IDivision getDivision() {
        return this.division;
    }

    @Override
    public IEvent getLastEvent() {
        if (this.movementsHistory.isEmpty()) {
            return null;
        }

        return this.movementsHistory.peek();
    }

    @Override
    public IDivision getLastDivision() {
        if (this.divisionsHistory.isEmpty()) {
            return null;
        }

        return this.divisionsHistory.peek();
    }

    @Override
    public void move(IMap maze) {

        if (getStunned() > 0) {
            System.out.println(">>> Estas atordoado e perdes esta vez! <<<");
            addStunnedRound(getStunned() - 1);
            Utils.waitEnter();
            return;
        }

        boolean isTurnActive = true;
        int movesInThisTurn = 0;

        while (isTurnActive) {
            IDivision currentPos = getDivision();

            IDivision nextPos = currentPos.getComportament(maze, this);
            if (nextPos != null && !nextPos.equals(currentPos)) {
                try {
                    IHallway hallway = maze.getEdge(currentPos, nextPos);
                    IEvent event = hallway.getEvent(this);

                    divisionsHistory.push(currentPos);
                    setDivision(nextPos);
                    movesInThisTurn++;

                    System.out.println(">>> SUCESSO! Avançaste para: " + nextPos.getName());

                    // Aplicar Evento do Corredor
                    if (event != null) {
                        System.out.println("Encontraste um evento no corredor!");
                        event.apply(this, isRealPlayer());

                        // globalHistory.push(new HistoryEntry(event));
                        movementsHistory.push(event); // Se ainda usares a stack antiga
                    }

                    if (getStunned() > 0) {
                        System.out.println("!!! O evento deixou-te ATORDOADO! A tua vez acabou. !!!");
                        isTurnActive = false;
                    }

                    System.out.println("Como acertaste, podes continuar a jogar!");
                    //Utils.waitEnter();

                } catch (Exception e) {
                    System.out.println("Erro crítico ao mover: " + e.getMessage());
                    isTurnActive = false;
                }
            }

            else {
                System.out.println(">>> FALHASTE O DESAFIO! <<<");
                if (getExtraRounds() > 0) {
                    System.out.println("MAS ESPERA! Tens " + getExtraRounds() + " Jogada(s) Extra!");
                    System.out.println("Gastaste uma para tentar novamente.");
                    setExtraRound(getExtraRounds() - 1);
                    //Utils.waitEnter();
                } else {
                    System.out.println("A tua vez terminou.");
                    isTurnActive = false;
                    //Utils.waitEnter();
                }
            }
        }
    }
}
