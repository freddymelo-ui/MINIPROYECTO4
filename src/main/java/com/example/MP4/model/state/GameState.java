package com.example.MP4.model.state;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    public final BoardState humanBoard;
    public final BoardState machineBoard;
    public final String winner; // "Human" or "Machine" or null
    public final long savedAt;

    public GameState(BoardState humanBoard, BoardState machineBoard, String winner, long savedAt) {
        this.humanBoard = humanBoard;
        this.machineBoard = machineBoard;
        this.winner = winner;
        this.savedAt = savedAt;
    }
}

