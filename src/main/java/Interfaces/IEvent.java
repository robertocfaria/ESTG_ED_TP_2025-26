package Interfaces;

import CoreGame.IPlayer;
import Structures.Interfaces.UnorderedListADT;

public interface IEvent {
    void apply(String description);

    IPlayer getPlayer();
}