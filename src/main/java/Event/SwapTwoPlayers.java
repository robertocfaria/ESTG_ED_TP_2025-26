package Event;

import CoreGame.IPlayer;
import Interfaces.IDivision;

public class SwapTwoPlayers extends Event {
    private IPlayer player2;

    public SwapTwoPlayers(IPlayer player1, IPlayer player2) {
        super(player1);
        this.player2 = player2;
    }

    @Override
    public void apply() {
        System.out.printf("Swaping %s and %s positions\n", player.getName(), this.player2.getName());

        IDivision pos2 = this.player2.getDivision();
        this.player2.setDivision(player.getDivision());
        player.setDivision(pos2);
    }

    public IPlayer getPlayer2() {
        return player2;
    }
}
