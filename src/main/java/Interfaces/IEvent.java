package Interfaces;

import CoreGame.IPlayer;

public interface IEvent {
    void apply();

    IPlayer getPlayer();
}