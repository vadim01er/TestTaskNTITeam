package com.github.vadim01er.testtaskntiteam.exception;

public class PlanetNotFoundException extends Exception {

    public PlanetNotFoundException(Long id) {
        super("Planet by '" + id + "'not found.");
    }
}
