package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.print("Gib eine Zahl zum hashen ein: ");
        Scanner scanner = new Scanner(System.in);
        long in = scanner.nextLong();


        String res = getHashcode(in);

        System.out.println(res);

    }

    public static String getHashcode(long in) {
        String input = Long.toString(in);
        int length = input.length();


        int res = 0;
        for(int i = 0; i < length; i++) {
            res += Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        return String.format("%8s", res).replace(' ', '0');
    }
}
