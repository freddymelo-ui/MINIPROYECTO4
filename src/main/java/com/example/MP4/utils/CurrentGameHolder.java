package com.example.MP4.utils;

import com.example.MP4.model.Game;

/**
 * Simple holder for the currently active Game instance.
 * Allows global access for saving on exit without coupling UI.
 */
public final class CurrentGameHolder {
    private static volatile Game currentGame;

    private CurrentGameHolder() {}

    /**
     * Set the current game instance.
     * @param game game to hold
     */
    public static void set(Game game) {
        currentGame = game;
    }

    /**
     * Get the current game instance.
     * @return current game or null
     */
    public static Game get() {
        return currentGame;
    }

    /**
     * Clear the stored game reference.
     */
    public static void clear() {
        currentGame = null;
    }
}
