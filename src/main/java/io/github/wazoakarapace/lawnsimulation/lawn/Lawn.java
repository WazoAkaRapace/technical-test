package io.github.wazoakarapace.lawnsimulation.lawn;

import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import io.github.wazoakarapace.lawnsimulation.mower.MowerPosition;
import lombok.Getter;

@Getter
public class Lawn {

    private int sizeX;
    private int sizeY;

    private boolean[][] occupiedLand;

    public Lawn(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        occupiedLand = new boolean[sizeX + 1][sizeY + 1];
    }

    /**
     * This methode validate that a given {@link Mower} can move to its next position without getting out of bounds or collide with an other {@link Mower}.
     * If validation is successful, the mower is moved to its next position
     *
     * @param mower The Mower you want to move forward
     */
    public void validateAndMoveForward(Mower mower) {
        MowerPosition nextPosition = mower.getNextForwardPosition();
        MowerPosition currentPosition = mower.getPosition();
        if (isPositionValid(nextPosition)) {
            synchronized (this) {
                if (!occupiedLand[nextPosition.getX()][nextPosition.getY()]) {
                    placeMower(nextPosition);
                    removeMower(currentPosition);
                    mower.goForward();
                }
            }
        }
    }

    /**
     * This methode check is the give {@link MowerPosition} is in the lawn boundaries.
     *
     * @param position The {@link MowerPosition} to test.
     * @return true if the position is in the grid, false otherwise
     */
    private boolean isPositionValid(MowerPosition position) {
        return position.getX() >= 0 && position.getY() >= 0 && position.getY() <= sizeY && position.getX() <= sizeX;
    }

    /**
     * Set a position on the grid as occupied by a mower.
     *
     * @param position the position of the new mower on the grid
     */
    public void placeMower(MowerPosition position) {
        occupiedLand[position.getX()][position.getY()] = true;
    }

    /**
     * Set a position on the grid as unoccupied.
     *
     * @param position the position to set unoccupied
     */
    public void removeMower(MowerPosition position) {
        occupiedLand[position.getX()][position.getY()] = false;
    }

    @Override
    public String toString() {
        return sizeX + "X" + sizeY;
    }
}
