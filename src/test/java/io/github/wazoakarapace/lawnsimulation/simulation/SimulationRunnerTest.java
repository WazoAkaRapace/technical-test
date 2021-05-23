package io.github.wazoakarapace.lawnsimulation.simulation;

import io.github.wazoakarapace.lawnsimulation.lawn.Lawn;
import io.github.wazoakarapace.lawnsimulation.mower.Action;
import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import io.github.wazoakarapace.lawnsimulation.mower.MowerPosition;
import io.github.wazoakarapace.lawnsimulation.mower.Orientation;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

import static io.github.wazoakarapace.lawnsimulation.mower.Action.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
class SimulationRunnerTest {

    @Test
    void shouldRunTheSimulation() {
        List<Action> actions1 = Arrays.asList(L, F, L, F, L, F, L, F, F);
        List<Action> actions2 = Arrays.asList(F, F, R, F, F, R, F, R, R, F);
        SimulationConfig config = new SimulationConfig(Arrays.asList(new Mower(new MowerPosition(1, 2), Orientation.N, new ArrayDeque<>(actions1)),
                new Mower(new MowerPosition(3, 3), Orientation.E, new ArrayDeque<>(actions2))), new Lawn(5, 5));
        config.getLawn().placeMower(new MowerPosition(1, 2));
        config.getLawn().placeMower(new MowerPosition(3, 3));

        SimulationRunner simulationRunner = new SimulationRunner(config);
        List<String> result = simulationRunner.startSimulation();
        assertTrue(result.contains("1 3 N"));
        assertTrue(result.contains("5 1 E"));
    }

}