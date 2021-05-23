package io.github.wazoakarapace.lawnsimulation.simulation;

import io.github.wazoakarapace.lawnsimulation.lawn.Lawn;
import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SimulationConfig {

    private final List<Mower> mowers;
    private final Lawn lawn;
}
