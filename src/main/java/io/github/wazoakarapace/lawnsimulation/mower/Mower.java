package io.github.wazoakarapace.lawnsimulation.mower;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Queue;

@Log4j2
@Getter
@AllArgsConstructor
public class Mower {

    private final MowerPosition position;

    private Orientation orientation;

    private final Queue<Action> actions;

    /**
     * Return the next {@link Action} in queue. The returned action is removed from the queue.
     *
     * @return Next {@link Action} to perform for this mowner, null if there's none.
     */
    public Action popNextAction() {
        return actions.poll();
    }

    public void turnRight() {
        if (orientation.ordinal() == 3) {
            orientation = Orientation.N;
        } else {
            orientation = Orientation.values()[orientation.ordinal() + 1];
        }
    }

    public void turnLeft() {
        if (orientation.ordinal() == 0) {
            orientation = Orientation.W;
        } else {
            orientation = Orientation.values()[orientation.ordinal() - 1];
        }
    }

    /**
     * Use this methode to know where the mower could be if it go forward one time.
     * <p>
     * Result is base on its current position and {@link Orientation}
     *
     * @return The {@link MowerPosition} on the grid if it go forward one time.
     */
    public MowerPosition getNextForwardPosition() {
        MowerPosition result = new MowerPosition(this.position);
        forwardPosition(result);
        return result;
    }

    private void forwardPosition(MowerPosition result) {
        switch (orientation) {
            case N:
                result.goNorth();
                break;
            case E:
                result.goEast();
                break;
            case S:
                result.goSouth();
                break;
            case W:
                result.goWest();
                break;
        }
    }

    /**
     * Move the mower forward by one unit based on its {@link Orientation}
     */
    public void goForward() {
        forwardPosition(this.position);
    }

    public boolean hasNextAction() {
        return !actions.isEmpty();
    }

    @Override
    public String toString() {
        return position.toString() + " " + orientation.toString();
    }
}
