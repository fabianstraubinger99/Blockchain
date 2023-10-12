package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.company.Main.getNameFromJson;

public class JsonTest {

    @Test
    void test() {

        //given
        String input = "{\"name\":\"hendrik\"}";

        //when
        String output = getNameFromJson(input);

        //then
        Assertions.assertEquals("hendrik", output);
    }
}
