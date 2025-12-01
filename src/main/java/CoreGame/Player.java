package CoreGame;

public class Player implements IPlayer{
    private String name;
    //private Division position;
    private int stunned;
    private int extraRound;
    private boolean realPlayer;
    //private Stack<> movementsHistory;


    public Player() {

    }


    public String getName() {
        return this.name;
    }

    public int getStunned() {
        return this.stunned;
    }

    public void addStunnedRound(int numberOfRounds) {
        this.stunned = numberOfRounds;
    }

    public int getExtraRounds() {
        return this.extraRound;
    }

    public void addExtraRound(int numberOfRounds) {
        this.extraRound = numberOfRounds;
    }

    public boolean isRealPlayer(){
        return realPlayer;
    }
}
