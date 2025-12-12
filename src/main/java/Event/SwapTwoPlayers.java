package Event;

import Interfaces.IDivision;
import Interfaces.IPlayer;
import Exceptions.InvalidPlayersCountException;
import Interfaces.IEvent;
import Menus.GameVisuals;
import Reader.Reader;
import Structures.Interfaces.ListADT;
import Util.Utils;

import java.util.Random;

/**
 * Represents a disruptive, targeted event that forces the encountering player
 * to swap their current division with another player in the game.
 * The player can choose the target player if they are a real player, otherwise
 * the target is chosen randomly.
 */
public class SwapTwoPlayers implements IEvent {
    private ListADT<IPlayer> players;

    /**
     * Constructs a SwapTwoPlayers event, requiring the list of all active players.
     *
     * @param players The {@link ListADT} containing all players in the game.
     */
    public SwapTwoPlayers(ListADT<IPlayer> players) {
        this.players = players;
    }

    /**
     * Applies the player swap effect.
     * The encountering player is prompted to choose another player to swap positions with.
     * If the player is a bot, the target is chosen randomly.
     * The divisions of the two players are exchanged.
     *
     * @param player The {@link IPlayer} who encountered and triggered the event.
     * @param isRealPlayer A boolean flag indicating if the player is human-controlled.
     * @throws InvalidPlayersCountException If there are fewer than two players in the game.
     */
    @Override
    public void apply(IPlayer player, boolean isRealPlayer) throws InvalidPlayersCountException {
        if (this.players.size() < 2) {
            throw new InvalidPlayersCountException("SwapTwoPlayers requires at least 2 players in the game");
        }

        IPlayer[] playersArray = new IPlayer[this.players.size()];

        int index = 0;
        for (IPlayer p1 : this.players) {
            if (!p1.equals(player)) {
                playersArray[index++] = p1;
            }
        }

        int choice;
        if (!isRealPlayer) {
            Random rand = new Random();
            choice = rand.nextInt(playersArray.length - 1);
        } else {
            GameVisuals.showSwapChoiceMenu(playersArray);
            choice = Reader.readInt(1, playersArray.length - 1, "Uma bruxa quer que troques de posição com outro jogador. Qual?: ");
        }

        IPlayer playerToSwap = playersArray[choice - 1];

        IDivision temp = player.getDivision();
        player.setDivision(playerToSwap.getDivision());
        playerToSwap.setDivision(temp);

        Utils.waitEnter();

        System.out.println("As posicoes foram trocada! " + player.getName() + " <-> " +  playerToSwap.getName() + "\n");
    }
}