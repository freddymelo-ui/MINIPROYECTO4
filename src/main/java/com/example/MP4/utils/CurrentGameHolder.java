package com.example.MP4.utils;

import com.example.MP4.model.Game;

/**
 * Simple holder for the currently active Game instance so the application
 * can save it on exit or access it globally without creating UI components.
 */
public final class CurrentGameHolder {
    private static volatile Game currentGame;

    private CurrentGameHolder() {}

    public static void set(Game game) {
        currentGame = game;
    }

    public static Game get() {
        return currentGame;
    }

    public static void clear() {
        currentGame = null;
    }
}

