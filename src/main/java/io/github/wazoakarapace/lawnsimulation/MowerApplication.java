package io.github.wazoakarapace.lawnsimulation;

import io.github.wazoakarapace.lawnsimulation.parser.InputFileParser;
import io.github.wazoakarapace.lawnsimulation.simulation.SimulationConfig;
import io.github.wazoakarapace.lawnsimulation.simulation.SimulationRunner;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

@Log4j2
@AllArgsConstructor
public class MowerApplication {

    private String filePath;

    public static void main(String[] args) throws IOException {
        log.info("Starting application...");
        if (args.length != 1) {
            log.error("No file specified. Stopping.");
            return;
        }
        MowerApplication application = new MowerApplication(args[0]);
        application.start();
    }

    public void start() throws IOException {
        InputFileParser inputFileParser = new InputFileParser(filePath);
        SimulationConfig config = inputFileParser.read();
        log.info("File OK, {} mowers, {} lawn", config.getMowers().size(), config.getLawn().toString());
        List<String> strings = new SimulationRunner(config).startSimulation();
        strings.forEach(log::info);
    }
}
