package Interfaces;

import CoreGame.HistoryEntry;
import Structures.Stack.ArrayStack;

public interface IPlayer {

    void move(IMaze maze);

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

    ArrayStack<HistoryEntry> getHistoryCopy();
}