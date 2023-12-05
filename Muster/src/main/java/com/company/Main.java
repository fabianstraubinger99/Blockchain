package com.company;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Anwendung startet");

        String input = "{\"name\":\"hendrik\"}";

        String result = getNameFromJson(input);

        System.out.println(result);


    }

    public static String getNameFromJson(String json) {
        if(json == null) {
            throw new NullPointerException("input must not be null");
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        logger.info("JSON erfolgreich geparst");
        JsonObject object = element.getAsJsonObject();
        String name = object.get("name").getAsString();
        logger.info("Name : {}", name);
        return name;
    }


    public static void getHashcode() {
        System.out.print("Gib eine Zahl zum hashen ein: ");
        Scanner scanner = new Scanner(System.in);
        long in = scanner.nextLong();

        String input = Long.toString(in);
        int length = input.length();

        int res = 0;
        for (int i = 0; i < length; i++) {
            res += Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        System.out.println(String.format("%8s", res).replace(' ', '0'));

    }


}
