package Event;

import Interfaces.IEvent;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;

import java.util.Random;

/**
 * Manages the generation of random events that can occur in the game,
 * typically when a player enters a hallway or division.
 * <p>
 * Events are selected based on predetermined probabilities to influence
 * the game flow (e.g., stunning a player, granting extra turns, or swapping positions).
 */
public class EventManager {
    private Class[] events = new Class[]{ExtraPlays.class, RollBack.class, ShuffleAllPlayers.class, StunnedPlays.class, SwapTwoPlayers.class};
    private Random rand = new Random();

    /**
     * Selects and returns a random game event based on a weighted chance distribution.
     * <p>
     * The probabilities are as follows:
     * <ul>
     * <li>0% - 34% (35%): {@code Nothing} (No effect)</li>
     * <li>35% - 69% (35%): {@code StunnedPlays}</li>
     * <li>70% - 79% (10%): {@code RollBack}</li>
     * <li>80% - 89% (10%): {@code ExtraPlays}</li>
     * <li>90% - 94% (5%): {@code ShuffleAllPlayers}</li>
     * <li>95% - 99% (5%): {@code SwapTwoPlayers}</li>
     * </ul>
     *
     * @param players A list of all active players in the game, required for
     * events that affect multiple players (like shuffling or swapping).
     * @return An instance of an {@link IEvent} ready to be applied to a player or the game.
     */
    public IEvent getRandomEvent (ListADT<IPlayer> players) {
        int chance = this.rand.nextInt(100);
        if (chance < 35) {
            return new Nothing();
        } else if (chance < 70) {
            return new StunnedPlays();
        } else if (chance < 80) {
            return new RollBack();
        }else if (chance < 90) {
            return new ExtraPlays();
        } else if (chance < 95) {
            return new ShuffleAllPlayers(players);
        } else {
            return new SwapTwoPlayers(players);
        }
    }
}
