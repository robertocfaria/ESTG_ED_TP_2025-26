package CoreGame;

import Event.ExtraPlays;
import Interfaces.*;
import Structures.List.ArrayList;
import Structures.List.ArrayUnorderedList;
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
    protected ArrayStack<HistoryEntry> fullHistory;
    protected int totMoves;


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
        this.fullHistory = new ArrayStack<>();
        this.totMoves = 0;
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
        this.fullHistory = new ArrayStack<>();
        this.totMoves = 0;
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
            System.out.println(">>> Estás atordoado e perdes esta vez! <<<");
            addStunnedRound(getStunned() - 1);
            // Regista que perdeu a vez (opcional)
            addHistoryMove(this.division, "Perdeu a vez por estar atordoado.");
            if(isRealPlayer()) {
                Utils.waitEnter();
            }
            return;
        }

        boolean isTurnActive = true;
        int movesInThisTurn = 0;
        ArrayUnorderedList<String> divisionNames = new ArrayUnorderedList<>();

        while (isTurnActive) {
            if(totMoves > 0) {
                System.out.println("\n-- Hisorico do jogo/jogada --");
                System.out.println(">> Desde o inicio do jogo ja passou por: " + totMoves + " casas");
                System.out.println(">> Esta jogada ja fez " + movesInThisTurn + " movimentos: ");
                for (String division : divisionNames) {
                    System.out.print(division.toString() + " -> ");
                }
                System.out.println("\n-- Fim do hisorico do jogo/jogada --");
            }
            System.out.println();

            IDivision currentPos = getDivision();
            IDivision nextPos = currentPos.getComportament(maze, this);

            if (nextPos != null && !nextPos.equals(currentPos)) {
                try {
                    if (!this.fullHistory.isEmpty() && this.fullHistory.peek().getTypeString().equals("MOVEMENT")) {
                        this.fullHistory.peek().setNextDivision(nextPos.getName());
                    }

                    IHallway hallway = maze.getEdge(currentPos, nextPos);
                    IEvent event = hallway.getEvent(this);

                    divisionsHistory.push(currentPos);
                    setDivision(nextPos);
                    System.out.println(">>> SUCESSO! Avançaste para: " + nextPos.getName());
                    if (event != null) {
                        System.out.println("Encontraste um evento no corredor!");
                        event.apply(this, isRealPlayer());
                        addHistoryEvent(event);
                    }

                    if (nextPos instanceof Map.GoalDivision) {
                        System.out.println("Chegaste ao destino final!");
                        addHistoryMove(nextPos, "!!! VITORIA - JOGO TERMINADO !!!");
                        isTurnActive = false;
                    } else if (getStunned() > 0) {
                        System.out.println("!!! O evento deixou-te ATORDOADO! A tua vez acabou. !!!");
                        isTurnActive = false;
                    } else {
                        System.out.println("Como acertaste, podes continuar a jogar!");
                    }

                } catch (Exception e) {
                    System.out.println("Erro crítico ao mover: " + e.getMessage());
                    isTurnActive = false;
                }
            }
            else {
                System.out.println(">>> FALHASTE O DESAFIO! <<<");
                if (getExtraRounds() > 0) {
                    System.out.println("MAS ESPERA! Tens " + getExtraRounds() + " Jogada(s) Extra!");
                    setExtraRound(getExtraRounds() - 1);
                } else {
                    System.out.println("A tua vez terminou.");
                    isTurnActive = false;
                }
            }
        }
    }

    @Override
    public void addHistoryMove(IDivision division, String log) {
        HistoryEntry entry = new HistoryEntry(division, log);
        this.fullHistory.push(entry);
    }

    @Override
    public void addHistoryEvent(IEvent event) {
        HistoryEntry entry = new HistoryEntry(event);
        this.fullHistory.push(entry);
    }

    @Override
    public void printFullHistory() {
        System.out.println("\n--- LOG do " + this.name.toUpperCase() + " ---");
        Structures.Stack.ArrayStack<HistoryEntry> temp = new Structures.Stack.ArrayStack<>();

        // er e Esvaziar
        while (!this.fullHistory.isEmpty()) {
            try {
                HistoryEntry entry = this.fullHistory.pop();
                if (entry != null) {
                    System.out.println(entry.toString());
                    temp.push(entry); // Guarda no temporário!
                }
            } catch (Exception e) { break; }
        }

        //REPOR (CRUCIAL: Se isto falhar, o JSON sai vazio)
        while (!temp.isEmpty()) {
            try {
                this.fullHistory.push(temp.pop());
            } catch (Exception e) { break; }
        }
        System.out.println("-----------------------------------");
    }

    public ArrayStack<HistoryEntry> getHistoryCopy() {

        ArrayStack<HistoryEntry> copy = new ArrayStack<>();
        ArrayStack<HistoryEntry> temp = new ArrayStack<>();

        while (!this.fullHistory.isEmpty()) {
            temp.push(this.fullHistory.pop());
        }

        //Repor na original E encher a cópia
        while (!temp.isEmpty()) {
            HistoryEntry entry = temp.pop();
            this.fullHistory.push(entry); // Devolve à original
            copy.push(entry);             // Mete na cópia
        }

        return copy;
    }



}
