package Event;

import CoreGame.IPlayer;

import java.util.Random;

public class StunnedPlays extends Event {
    public static Random RAND = new Random();

    public StunnedPlays(IPlayer player) {
        super(player);
    }

    @Override
    public void apply(String description) {
        int stunnedRounds = RAND.nextInt(3) + 1;

        this.player.addStunnedRound(stunnedRounds);

        System.out.println(description + stunnedRounds);
    }
}
