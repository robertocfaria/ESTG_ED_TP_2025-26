package CoreGame;

import Map.IDivision;
import Structures.Stack.ArrayStack;


public abstract class Player implements IPlayer{
    protected static int botNumber = 1;
    protected String name;
    protected int stunned;
    protected int extraRound;
    protected boolean realPlayer;
    //protected ArrayStack<> movementsHistory; TODO aqui temos de criar um "Evento para guardar o movimento que o jogador fez"
    protected IDivision division;


    /**
     * Este construtor cria um jogador Real novo.
     * @param name  Nome do Jogador
     */
    public Player(String name) {
        this.name = name;
        this.stunned = 0;
        this.extraRound = 0;
        this.realPlayer = true;
        this.division = null;
        //TODO movementsHistory = new stack<>
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
        //TODO movementsHistory = new stack<>
    }

    @Override
    public abstract void move();

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
    public boolean isRealPlayer(){
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
}
