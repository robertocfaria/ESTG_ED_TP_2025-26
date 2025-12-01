package CoreGame;

public interface IPlayer {

    public String getName();

    public  int getStunned();

    public void addStunnedRound(int numberOfRounds);

    public int getExtraRounds();

    public void addExtraRound(int numberOfRounds);

    public boolean isRealPlayer();

    /**
     * TODO: FALTA ADICIONAR METODOS RELACIONADOS COM A POSIÇÃO
     */


}
