package com.example.MP4.model.state;

import java.io.Serializable;

/**
 * Serializable container for the whole game snapshot used to save/restore play.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    public final BoardState humanBoard;
    public final BoardState machineBoard;
    public final String winner; // "Human" or "Machine" or null
    public final long savedAt;

    /**
     * Create a GameState snapshot.
     * @param humanBoard snapshot of human board
     * @param machineBoard snapshot of machine board
     * @param winner winner name or null
     * @param savedAt epoch millis when saved
     */
    public GameState(BoardState humanBoard, BoardState machineBoard, String winner, long savedAt) {
        this.humanBoard = humanBoard;
        this.machineBoard = machineBoard;
        this.winner = winner;
        this.savedAt = savedAt;
    }
}
