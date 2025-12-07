package CoreGame;

import Map.IDivision;
import Map.IMap;

public interface IPlayer {

    void move(IMap maze);

    String getName();

    int getStunned();

    void addStunnedRound(int numberOfRounds);

    int getExtraRounds();

    void addExtraRound(int numberOfRounds);

    boolean isRealPlayer();

    void setDivision(IDivision division);

    IDivision getDivision();

}
