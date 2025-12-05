package Event;

import CoreGame.IPlayer;

public class ExtraPlays extends Event {
    private static final int EXTRA_PLAYS = 2;

    public ExtraPlays(IPlayer player) {
        super(player);
    }

    @Override
    public void apply() {
        System.out.println(EXTRA_PLAYS + "extra plays to " + this.player.getName());

        player.addExtraRound(EXTRA_PLAYS);
    }
}
