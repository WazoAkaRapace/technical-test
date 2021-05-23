package io.github.wazoakarapace.lawnsimulation.mower;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MowerPosition {


    private int x;
    private int y;

    public MowerPosition(MowerPosition from) {
        this.x = from.getX();
        this.y = from.getY();
    }

    public void addToX(int n) {
        x += n;
    }

    public void addToY(int n) {
        y += n;
    }

    public void goSouth() {
        this.addToY(-1);
    }

    public void goNorth() {
        this.addToY(1);
    }

    public void goWest() {
        this.addToX(-1);
    }

    public void goEast() {
        this.addToX(1);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }

}
