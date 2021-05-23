package io.github.wazoakarapace.lawnsimulation.simulation;

import io.github.wazoakarapace.lawnsimulation.lawn.Lawn;
import io.github.wazoakarapace.lawnsimulation.mower.Action;
import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Log4j2
public class SimulationRunner {

    private List<Mower> mowers;
    private Lawn lawn;

    public SimulationRunner(SimulationConfig config) {
        mowers = config.getMowers();
        lawn = config.getLawn();
    }

    public List<String> startSimulation() {
        log.info("Starting simulation...");
        ExecutorService threadPool = Executors.newFixedThreadPool(mowers.size());
        mowers.stream().filter(Mower::hasNextAction).forEach(mower -> threadPool.submit(() -> {
            while (mower.hasNextAction()) {
                Action action = mower.popNextAction();
                switch (action) {
                    case L:
                        mower.turnLeft();
                        break;
                    case R:
                        mower.turnRight();
                        break;
                    case F:
                        lawn.validateAndMoveForward(mower);
                }
            }
        }));
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.error(e, e);
            Thread.currentThread().interrupt();
        }
        log.info("Simulation finished");
        return mowers.stream().map(Mower::toString).collect(Collectors.toList());
    }
}
