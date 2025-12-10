package CoreGame;

import Interfaces.IEvent;
import Interfaces.IMap;
import Interfaces.IDivision;
import Interfaces.IPlayer;
import Structures.Stack.ArrayStack;


public abstract class Player implements IPlayer {
    protected static int botNumber = 1;
    protected String name;
    protected int stunned;
    protected int extraRound;
    protected boolean realPlayer;
    protected ArrayStack<IEvent> movementsHistory;
    protected ArrayStack<IDivision> divisionsHistory;
    protected IDivision division;


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
        this.realPlayer = true;
        this.division = null;
        this.movementsHistory = new ArrayStack<>();
        this.divisionsHistory = new ArrayStack<>();
    }

    @Override
    public abstract void move(IMap maze);

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
    public void addExtraRound(int numberOfRounds) {
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
}
