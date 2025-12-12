package Event;

import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;

import java.util.Random;

public class StunnedPlays implements IEvent {

    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        int stunnedPlays = 1;
        player.addStunnedRound(stunnedPlays);
        System.out.println("Um mafarrico atirou-te com um pedra. Ficas-te atordoado! Vais perder jogadas, para descansar.\n");
    }
}