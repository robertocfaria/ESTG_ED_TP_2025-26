package Interfaces;

public interface IPlayer {

    void move(IMap maze);

    String getName();

    int getStunned();

    void addStunnedRound(int numberOfRounds);

    int getExtraRounds();

    void setExtraRound(int numberOfRounds);

    boolean isRealPlayer();

    void setDivision(IDivision division);

    IDivision getDivision();

    IEvent getLastEvent();

    IDivision getLastDivision();

    void addHistoryMove(IDivision division, String log);

    void addHistoryEvent(IEvent event);

    void printFullHistory();
}
