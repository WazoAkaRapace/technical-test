package io.github.wazoakarapace.lawnsimulation.mower;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.wazoakarapace.lawnsimulation.mower.Action.F;
import static io.github.wazoakarapace.lawnsimulation.mower.Action.L;
import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

    @Test
    void popNextAction() {
        List<Action> actions = Arrays.asList(L);
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.N, new ArrayDeque<>(actions));
        Action action = mower.popNextAction();
        assertEquals(L, action);
        assertEquals(actions.size() - 1, mower.getActions().size());
        assertNull(mower.popNextAction());
    }

    @Test
    void turnRight() {
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.W, new ArrayDeque<>());
        mower.turnRight();
        assertEquals(Orientation.N, mower.getOrientation());
        mower.turnRight();
        assertEquals(Orientation.E, mower.getOrientation());
        mower.turnRight();
        assertEquals(Orientation.S, mower.getOrientation());
        mower.turnRight();
        assertEquals(Orientation.W, mower.getOrientation());
    }

    @Test
    void turnLeft() {
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.N, new ArrayDeque<>());
        mower.turnLeft();
        assertEquals(Orientation.W, mower.getOrientation());
        mower.turnLeft();
        assertEquals(Orientation.S, mower.getOrientation());
        mower.turnLeft();
        assertEquals(Orientation.E, mower.getOrientation());
        mower.turnLeft();
        assertEquals(Orientation.N, mower.getOrientation());
    }

    @Test
    void getNextForwardPosition() {
        List<Action> actions = Arrays.asList(L, F, L, F, L, F, L, F, F);
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.N, new ArrayDeque<>(actions));
        MowerPosition nextForwardPosition = mower.getNextForwardPosition();
        assertEquals(new MowerPosition(1, 1), mower.getPosition());
        assertEquals(new MowerPosition(1, 2), nextForwardPosition);
    }

    @Test
    void goForward() {
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.N, new ArrayDeque<>());
        mower.goForward();
        assertEquals(new MowerPosition(1, 2), mower.getPosition());
        mower.turnRight();
        mower.goForward();
        assertEquals(new MowerPosition(2, 2), mower.getPosition());
        mower.turnRight();
        mower.goForward();
        assertEquals(new MowerPosition(2, 1), mower.getPosition());
        mower.turnRight();
        mower.goForward();
        assertEquals(new MowerPosition(1, 1), mower.getPosition());
    }

    @Test
    void hasAction() {
        List<Action> actions = Arrays.asList(L);
        Mower mower = new Mower(new MowerPosition(1, 1), Orientation.N, new ArrayDeque<>(actions));
        assertTrue(mower.hasNextAction());
        mower.popNextAction();
        assertFalse(mower.hasNextAction());
    }
}