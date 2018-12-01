package com.onedayfirm.gorodabot.json;

import com.onedayfirm.gorodabot.io.FileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    private static final String JSON = String.join("\n", FileReader.readLines("test.json"));

    @Test
    void wrongInitialization() {
        assertThrows(ParseException.class, () -> new JsonParser("wrong"));
    }

    @Test
    void comeDownCorrect() throws ParseException {
        var parser = new JsonParser(JSON);

        assertEquals(parser, parser.comeDown("object"));
    }

    @Test
    void comeDownIncorrect() {
        assertThrows(ParseException.class, () -> new JsonParser(JSON).comeDown("wrong"));
    }

    @Test
    void forEachNotNull() {
        assertDoesNotThrow(() -> new JsonParser(JSON)
                .comeDown("array")
                .forEach(Assertions::assertNotNull)
        );
    }

    @Test
    void forEachException() {
        assertThrows(ParseException.class, () -> new JsonParser(JSON).forEach(x -> {
        }));
    }

    @Test
    void chooseCorrect() throws ParseException {
        assertEquals("value1",
                new JsonParser(JSON)
                        .comeDown("array")
                        .choose(obj -> obj.get("key").equals("value1"))
                        .getValue("key")
        );
    }

    @Test
    void chooseNothing() {
        assertDoesNotThrow(() -> new JsonParser(JSON).comeDown("array").choose(x -> false));
    }

    @Test
    void chooseException() {
        assertThrows(ParseException.class, () -> new JsonParser(JSON).choose(obj -> true));
    }

    @Test
    void getValueCorrect() throws ParseException {
        assertEquals("value", new JsonParser(JSON).getValue("string"));
    }

    @Test
    void getValueKeyNotExists() throws ParseException {
        assertNull(new JsonParser(JSON).getValue("foo"));
    }
}
