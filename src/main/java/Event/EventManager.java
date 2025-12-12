package Event;

import Interfaces.IEvent;
import Interfaces.IPlayer;
import Structures.Interfaces.ListADT;

import java.util.Random;

public class EventManager {
    private Class[] events = new Class[]{ExtraPlays.class, RollBack.class, ShuffleAllPlayers.class, StunnedPlays.class, SwapTwoPlayers.class};
    private Random rand = new Random();


    // --- DISTRIBUIÇÃO DAS PROBABILIDADES (Total = 100%) ---
    // 10% - ExtraPlays (Muito Comum)
    // 35% - StunnedPlays (Comum)
    // 10% - RollBack (Pouco Comum)
    // 35% - Nao acontecer nada!
    //  5% - ShuffleAllPlayers (Raro)
    //  5% - RollBack (Muito Raro)
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
