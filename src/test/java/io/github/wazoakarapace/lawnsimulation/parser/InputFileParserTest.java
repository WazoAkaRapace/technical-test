package io.github.wazoakarapace.lawnsimulation.parser;

import io.github.wazoakarapace.lawnsimulation.lawn.Lawn;
import io.github.wazoakarapace.lawnsimulation.mower.Mower;
import io.github.wazoakarapace.lawnsimulation.simulation.SimulationConfig;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
class InputFileParserTest {

    public static final String BASE_PATH = "src/test/resources/parser/";

    public static final String OK_FILE = "LawnOk.txt";

    public static final String KO_Y_FILE = "LawnKOY.txt";
    public static final String KO_Y_LOG_EXPECTED = "y need to be between 0 and 5";

    public static final String KO_X_FILE = "LawnKOX.txt";
    public static final String KO_X_LOG_EXPECTED = "x need to be between 0 and 5";

    public static final String NEGATIVE_X_FILE = "LawnNegativeX.txt";
    public static final String NEGATIVE_X_LOG_EXPECTED = "x need to be positive for lawn size";

    public static final String NEGATIVE_Y_FILE = "LawnNegativeY.txt";
    public static final String NEGATIVE_Y_LOG_EXPECTED = "y need to be positive for lawn size";

    public static final String UNKNOWN_FILE = "LawnDoesNotExist.txt";

    public static final String NO_MOWER_FILE = "LawnNoMower.txt";
    public static final String NO_MOWER_LOG_EXPECTED = "Lawn need at least 1 mower";

    public static final String OVERLAP_FILE = "LawnOverlap.txt";
    public static final String OVERLAP_LOG_EXPECTED = "Overlapping mowers is not authorized";


    @Test
    void readOk() {
        InputFileParser inputFileParser = new InputFileParser(BASE_PATH + OK_FILE);
        SimulationConfig simulationConfig = null;
        try {
            simulationConfig = inputFileParser.read();
        } catch (IOException e) {
            log.error(e);
            fail();
        }
        assertNotNull(simulationConfig);
        Lawn lawn = simulationConfig.getLawn();
        assertNotNull(lawn);

        List<Mower> mowers = simulationConfig.getMowers();
        assertNotNull(mowers);
        assertEquals(2, mowers.size());

        assertEquals(9, mowers.get(0).getActions().size());
        assertEquals(10, mowers.get(1).getActions().size());

    }

    @ParameterizedTest
    @CsvSource({
            KO_Y_FILE + "," + KO_Y_LOG_EXPECTED,
            KO_X_FILE + "," + KO_X_LOG_EXPECTED,
            NO_MOWER_FILE + "," + NO_MOWER_LOG_EXPECTED,
            OVERLAP_FILE + "," + OVERLAP_LOG_EXPECTED,
            NEGATIVE_X_FILE + "," + NEGATIVE_X_LOG_EXPECTED,
            NEGATIVE_Y_FILE + "," + NEGATIVE_Y_LOG_EXPECTED,
    })
    void readKoInvalidContent(String file, String error) {
        InputFileParser inputFileParser = new InputFileParser(BASE_PATH + file);
        try {
            inputFileParser.read();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof InvalidContentException);
            assertEquals(error, e.getMessage());
        }
    }

    @Test
    void readNotFound() {
        InputFileParser inputFileParser = new InputFileParser(BASE_PATH + UNKNOWN_FILE);
        try {
            inputFileParser.read();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);
        }
    }
}