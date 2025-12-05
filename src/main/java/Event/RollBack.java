package Event;

import CoreGame.IPlayer;
import Report.Report;

public class RollBack extends Event {
    public RollBack(IPlayer player) {
        super(player);
    }

    @Override
    public void apply() {
        System.out.println("Rolling back to the last position!!");

        //Report lastPos = this.player.getLastMove(); método para fazer peek() do relatorio, deve ficar guardado que ele foi para tras

        //this.player.setDivision(lastPos.getPosition());
    }
}
