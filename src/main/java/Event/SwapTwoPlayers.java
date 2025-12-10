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

public class SwapTwoPlayers implements IEvent {
    private ListADT<IPlayer> players;

    public SwapTwoPlayers(ListADT<IPlayer> players) {
        this.players = players;
    }

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
            choice = Reader.readInt(1, playersArray.length - 1, "Escolha por qual jogador quer trocar de posição: ");
        }

        IPlayer playerToSwap = playersArray[choice - 1];

        IDivision temp = player.getDivision();
        player.setDivision(playerToSwap.getDivision());
        playerToSwap.setDivision(temp);

        Utils.waitEnter();

        GameVisuals.showSwapTwoPlayersEvent(player.getName(), playerToSwap.getName(), isRealPlayer);
    }
}
