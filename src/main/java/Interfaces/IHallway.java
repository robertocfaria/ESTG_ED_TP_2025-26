package Interfaces;

import Structures.Interfaces.ListADT;

public interface IHallway {
    IEvent getEvent(IPlayer player);

    void setPlayers(ListADT<IPlayer> players);
}
