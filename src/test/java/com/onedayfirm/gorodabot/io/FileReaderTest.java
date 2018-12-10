package com.onedayfirm.gorodabot.io;

import com.onedayfirm.gorodabot.ExitException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    private static final String PATH = "test.json";

    @Test
    void readLinesCorrect() {
        var lines = FileReader.readLines(PATH);

        assertTrue(lines.size() > 0);
        assertEquals("{", lines.get(0));
    }

    @Test
    void readLinesNoFile() {
        assertThrows(ExitException.class, () -> FileReader.readLines("foo.bar"));
    }

    @Test
    void openForReadingCorrect() {
        try (var in = FileReader.openForReading(PATH)) {
            assertNotNull(in);
            assertDoesNotThrow((ThrowingSupplier<Integer>) in::read);
        } catch (Exception exception) {
            fail();
        }
    }

    @Test
    void openForReadingNoFile() {
        assertThrows(NullPointerException.class, () -> FileReader.openForReading("foo"));
    }
}
