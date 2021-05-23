package io.github.wazoakarapace.lawnsimulation;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

@Log4j2
class MowerApplicationTest {

    public static final String OK_FILE = "src/test/resources/Lawn.txt";


    @Test
    void shouldRunSimulationWithoutException() {
        try {
            new MowerApplication(OK_FILE).start();
        } catch (Exception e) {
            fail(e);
        }
    }

}