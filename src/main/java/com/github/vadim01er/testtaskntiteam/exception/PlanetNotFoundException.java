package com.github.vadim01er.testtaskntiteam.exception;

/**
 * The type PlanetNotFoundException.
 */
public class PlanetNotFoundException extends Exception {
    /**
     * Instantiates a new PlanetNotFoundException.
     *
     * @param id the id ({@link Long})
     */
    public PlanetNotFoundException(Long id) {
        super("Planet by '" + id + "' not found.");
    }
}
