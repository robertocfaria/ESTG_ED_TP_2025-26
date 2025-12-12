package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;
import Structures.Interfaces.ListADT;

import java.util.Random;

/**
 * Represents a significant, global event that shuffles the positions of all players
 * in the game. Each player is randomly assigned a new division from the set of
 * divisions previously occupied by all players.
 */
public class ShuffleAllPlayers implements IEvent {
    private Random rand = new Random();
    private ListADT<IPlayer> players;

    /**
     * Constructs a ShuffleAllPlayers event.
     *
     * @param players The {@link ListADT} containing all players in the game.
     */
    public ShuffleAllPlayers(ListADT<IPlayer> players) {
        this.players = players;
    }

    /**
     * Applies the shuffling effect to all players.
     * <p>
     * The process involves:
     * <ul>
     * <li>Collecting the current division of every player.</li>
     * <li>Randomly shuffling this array of divisions using a Fisher-Yates shuffle.</li>
     * <li>Assigning the newly shuffled divisions back to the players in their original list order.</li>
     * </ul>
     *
     * @param player The {@link IPlayer} who encountered the event (though the effect is global).
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException If the list of players is empty, preventing the shuffle.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        if (this.players.isEmpty()) {
            throw new InvalidPlayersCountException("ShuffleAllPlayers event requires at least 1 player");
        }

        IDivision[] playersDivision = new IDivision[this.players.size()];

        int index = 0;
        for (IPlayer p : this.players) {
            playersDivision[index++] = p.getDivision();
        }

        IDivision temp;
        for (int i = 0; i < playersDivision.length; i++) {
            index = this.rand.nextInt(i + 1);

            temp = playersDivision[index];
            playersDivision[index] = playersDivision[i];
            playersDivision[i] = temp;
        }

        index = 0;
        for (IPlayer p2 : this.players) {
           p2.setDivision(playersDivision[index++]);
        }

        System.out.println("Entras-te numa festa, e todos os jogadores trocaram de salas. O que se passou?");
    }
}