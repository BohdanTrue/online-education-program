package org.bilko.educationalprogram.dto.user;

public class Main {
    public static void main(String[] args) {
        Integer i = new Integer("10");
        Boolean b = new Boolean("hello");
        System.out.println(i);
        System.out.println(b);
    }

    private static void chin() {
        china();
    }

    private static void china() {
        chin();
    }
}
