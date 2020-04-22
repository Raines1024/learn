package com.raines.interesting.autoloadJar;

public class Test {

    public static void main(String[] args) {
        try {
            Utils.loadJarsFromAppFolder("lib");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
