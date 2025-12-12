package Map;

import Interfaces.IDivision;
import Interfaces.IMaze;
import Interfaces.IPlayer;
/**
 * Represents the final destination division in the maze.
 * When a player successfully enters this division, the game is typically won.
 * This division overrides the standard behavior to announce victory and prevent further movement.
 */
public class GoalDivision extends Division{

    /**
     * Default constructor required for Jackson deserialization.
     */
    public GoalDivision() {
        super();
    }

    /**
     * Constructs a GoalDivision, setting its name to a fixed value.
     * The argument is ignored as the name is fixed to "DIVISAO FINAL".
     *
     * @param name A string parameter (ignored) used to satisfy the superclass constructor signature.
     */
    public GoalDivision(String name) {
        super("DIVISAO FINAL");
    }

    /**
     * Constructs a GoalDivision, setting its name to a fixed value.
     * The argument is ignored as the name is fixed to "DIVISAO FINAL".
     *
     * @param name A string parameter (ignored) used to satisfy the superclass constructor signature.
     */
    @Override
    public IDivision getComportament(IMaze maze, IPlayer player) {
        System.out.println("Parabéns! GANHASTE!");
        return null;
    }
}