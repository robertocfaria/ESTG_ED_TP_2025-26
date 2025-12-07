package Interfaces;

import CoreGame.IPlayer;
import Structures.Interfaces.ListADT;

public interface IEvent {
    void apply(ListADT<IPlayer> players);
}