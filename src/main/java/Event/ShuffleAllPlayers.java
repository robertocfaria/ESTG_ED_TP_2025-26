package Event;

import CoreGame.IPlayer;
import Structures.Interfaces.UnorderedListADT;

public class ShuffleAllPlayers {
    private UnorderedListADT<IPlayer> players;

    public ShuffleAllPlayers(UnorderedListADT<IPlayer> players) {
        this.players = players;
    }

    public void apply() {
        // obter randoms e adicionar em uma lista nao ordenada
    }
}
