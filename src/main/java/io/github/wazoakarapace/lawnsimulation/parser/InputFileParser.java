package io.github.wazoakarapace.lawnsimulation.parser;

import io.github.wazoakarapace.lawnsimulation.lawn.Lawn;
import io.github.wazoakarapace.lawnsimulation.mower.Action;
import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import io.github.wazoakarapace.lawnsimulation.mower.MowerPosition;
import io.github.wazoakarapace.lawnsimulation.mower.Orientation;
import io.github.wazoakarapace.lawnsimulation.simulation.SimulationConfig;
import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class InputFileParser {

    private Path pathToFile;

    public InputFileParser(String stringPath) {
        pathToFile = Paths.get(stringPath);
    }

    /**
     * Read and parse the input file, based on the path given during the instantiation.
     * <p>
     * It can throw {@link InvalidContentException} if the content describe invalid initial parameters.
     *
     * @return a configuration to use for starting a simulation
     * @throws IOException in case of IO error or no files found.
     */
    public SimulationConfig read() throws IOException {
        log.info("Reading file {}", pathToFile.getFileName());
        if (!Files.exists(pathToFile)) {
            throw new FileNotFoundException();
        }
        Lawn lawn;
        List<Mower> mowers = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile)) {
            String firstLine = bufferedReader.readLine();
            lawn = parseLawn(firstLine);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String secondLine = bufferedReader.readLine();
                Mower mower = parseMower(line, secondLine, lawn);
                Optional<MowerPosition> overlapping = mowers.stream().map(Mower::getPosition).filter(mowerPosition -> mowerPosition.equals(mower.getPosition())).findAny();
                if (overlapping.isPresent()) {
                    throw new InvalidContentException("Overlapping mowers is not authorized");
                }
                mowers.add(mower);
            }
        }

        if (mowers.isEmpty()) {
            throw new InvalidContentException("Lawn need at least 1 mower");
        }

        return new SimulationConfig(mowers, lawn);
    }

    private Lawn parseLawn(String firstLine) {
        String[] lawnSize = firstLine.split(" ");

        int x = Integer.parseInt(lawnSize[0]);
        if (x < 0) {
            throw new InvalidContentException("x need to be positive for lawn size");
        }

        int y = Integer.parseInt(lawnSize[1]);
        if (y < 0) {
            throw new InvalidContentException("y need to be positive for lawn size");
        }

        return new Lawn(x, y);
    }

    private Mower parseMower(String firstLine, String secondLine, Lawn lawn) {
        String[] positionSplit = firstLine.split(" ");

        int x = Integer.parseInt(positionSplit[0]);
        if (x > lawn.getSizeX() || x < 0) {
            throw new InvalidContentException("x need to be between 0 and " + lawn.getSizeX());
        }

        int y = Integer.parseInt(positionSplit[1]);
        if (y > lawn.getSizeY() || y < 0) {
            throw new InvalidContentException("y need to be between 0 and " + lawn.getSizeY());
        }

        Orientation orientation = Orientation.valueOf(positionSplit[2]);

        MowerPosition mowerPosition = new MowerPosition(x, y);
        String[] actionSplit = secondLine.split("");
        List<Action> actions = Arrays.stream(actionSplit).map(Action::valueOf).collect(Collectors.toList());
        lawn.placeMower(mowerPosition);
        return new Mower(mowerPosition, orientation, new ArrayDeque<>(actions));
    }


}
