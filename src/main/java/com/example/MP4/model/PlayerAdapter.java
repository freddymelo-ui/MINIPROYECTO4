package com.example.MP4.model;

import java.util.ArrayList;
import java.util.List;

// Adapts the Player class to represent a human player

public class PlayerAdapter implements IPlayer {
    protected Board board;
    protected List<Ship> ships;

    /**
     * Initializes the ship list for the player.
     * 
     * @return A list of ships with predefined sizes and names.
     */
    private List<Ship> initializeShips() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 4, true)); // Aircraft Carrier
        ships.add(new Ship("Submarine 1", 3, true)); // Submarine 1
        ships.add(new Ship("Submarine 2", 3, true)); // Submarine 2
        ships.add(new Ship("Destroyer 1", 2, true)); // Destroyer 1
        ships.add(new Ship("Destroyer 2", 2, true)); // Destroyer 2
        ships.add(new Ship("Destroyer 3", 2, true)); // Destroyer 3
        ships.add(new Ship("Frigate 1", 1, true)); // Frigate 1
        ships.add(new Ship("Frigate 2", 1, true)); // Frigate 2
        ships.add(new Ship("Frigate 3", 1, true)); // Frigate 3
        ships.add(new Ship("Frigate 4", 1, true)); // Frigate 4
        return ships;
    }

    public PlayerAdapter(Board board) {
        this.board = board;
        // If the board already contains ships (e.g. when loading a saved game), use them.
        if (board.getShips() != null && !board.getShips().isEmpty()) {
            this.ships = board.getShips();
        } else {
            this.ships = initializeShips();
        }
    }

    @Override
    public String playTurn(int row, int col, Board opponentBoard) {
        // Default implementation (to be overridden by subclasses)
        return "Invalid action";
    }

    @Override
    public boolean hasLost() {
        return board.isGameOver();
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
