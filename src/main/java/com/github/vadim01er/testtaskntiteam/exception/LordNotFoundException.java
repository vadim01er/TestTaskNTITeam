package com.github.vadim01er.testtaskntiteam.exception;

/**
 * The type LordNotFoundException.
 */
public class LordNotFoundException extends Exception {
    /**
     * Instantiates a new LordNotFoundException.
     *
     * @param id the id ({@link Long})
     */
    public LordNotFoundException(Long id) {
        super("Lord by '" + id + "' not found.");
    }
}
