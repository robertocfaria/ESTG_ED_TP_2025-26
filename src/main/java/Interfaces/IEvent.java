package Interfaces;

import Structures.Interfaces.ListADT;

public interface IEvent {
    void apply(IPlayer currentPlayer, boolean isRealPlayers);
}