package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.company.Main.getNameFromJson;

public class JsonTest {

    @Test
    void testGoodInput() {

        //given
        String input = "{\"name\":\"hendrik\"}";

        //when
        String output = getNameFromJson(input);

        //then
        Assertions.assertEquals("hendrik", output);
    }

    @Test
    void testBadInput() {

        //given
        String input = "BAD";

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> Main.getNameFromJson(input));
    }

    @Test
    void testBadInput2() {

        //given
        String input = "{\"nams\":\"hendrik\"}";

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> Main.getNameFromJson(input));
    }

    @Test
    void testNullInput() {

        //given
        String input = null;

        //then
        final NullPointerException ex = Assertions.assertThrows(NullPointerException.class,
                () -> Main.getNameFromJson(input));
        Assertions.assertEquals("input must not be null", ex.getMessage());
    }
}
