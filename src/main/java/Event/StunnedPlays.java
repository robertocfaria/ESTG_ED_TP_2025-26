package Event;

import CoreGame.IPlayer;

import java.util.Random;

public class StunnedPlays extends Event {
    private Random rand;

    public StunnedPlays(IPlayer player) {
        super(player);
    }

    @Override
    public void apply() {
        int stunnedRounds = rand.nextInt(3) + 1;

        this.player.addStunnedRound(stunnedRounds);

        System.out.println("You can't move for " + stunnedRounds + " rounds");
    }
}
