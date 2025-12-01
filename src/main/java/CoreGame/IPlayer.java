package CoreGame;

import Map.IDivision;

public interface IPlayer {

    void move();

    String getName();

    int getStunned();

    void addStunnedRound(int numberOfRounds);

    int getExtraRounds();

    void addExtraRound(int numberOfRounds);

    boolean isRealPlayer();

    void setDivision(IDivision division);

    IDivision getDivision();

}
