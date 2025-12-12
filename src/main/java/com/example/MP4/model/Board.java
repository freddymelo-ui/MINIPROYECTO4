package com.example.MP4.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    public static final int BOARD_SIZE = 10; // Size of the board (10x10 grid)

    private final char[][] grid; // Grid representing the board

    private final List<Ship> ships; // List of ships placed on the board

    // Characters representing each cell state

    private static final char EMPTY = '-'; // Empty cell

    private static final char SHIP = 'S'; // Cell occupied by a ship

    private static final char HIT = 'X'; // Successfully hit cell

    private static final char MISS = 'O'; // Missed shot cell

    /**
     * Constructor of the Board class. Initializes the grid and the ship list.
     */
    public Board() {
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        ships = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Initializes the grid with empty cells.
     */
    private void initializeGrid() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                grid[row][col] = EMPTY;
            }
        }
    }

    /**
     * Places a ship on the board if the position is valid.
     *
     * @param ship     The ship to place.
     * @param startRow Starting row.
     * @param startCol Starting column.
     * @return true if the ship was placed successfully, false otherwise.
     */
    public boolean placeShip(Ship ship, int startRow, int startCol) {
        if (!isPlacementValid(ship, startRow, startCol)) {
            return false;
        }

        // Set ship coordinates
        ship.setCoordinates(startRow, startCol);

        // Mark ship cells on the grid
        for (int[] coord : ship.getCoordinates()) {
            grid[coord[0]][coord[1]] = SHIP;
        }

        ships.add(ship); // Add ship to the list

        return true;
    }

    /**
     * Checks whether a ship placement is valid.
     *
     * @param ship     The ship to validate.
     * @param startRow Starting row.
     * @param startCol Starting column.
     * @return true if placement is valid, false otherwise.
     */
    public boolean isPlacementValid(Ship ship, int startRow, int startCol) {
        for (int i = 0; i < ship.getSize(); i++) {
            int row = ship.isHorizontal() ? startRow : startRow + i;
            int col = ship.isHorizontal() ? startCol + i : startCol;

            // Check boundaries
            if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
                return false;
            }

            // Check if the cell is already occupied
            if (grid[row][col] != EMPTY) {
                return false;
            }
        }

        return true;
    }

    /**
     * Registers a shot on the board and returns its result.
     *
     * @param row Row of the shot.
     * @param col Column of the shot.
     * @return The result of the shot ("miss", "hit", or "sunk").
     */
    public String registerShot(int row, int col) {

        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return "invalid"; // Invalid shot
        }

        if (grid[row][col] == MISS || grid[row][col] == HIT) {
            return "already shot"; // Cell was already shot
        }

        if (grid[row][col] == SHIP) {

            grid[row][col] = HIT; // Mark hit cell

            // Identify which ship was hit
            for (Ship ship : ships) {
                if (ship.registerHit(row, col)) {
                    if (ship.isSunk()) {
                        return "sunk"; // Ship completely sunk
                    }
                    return "hit"; // Ship hit but not sunk
                }
            }
        }

        // If no ship was hit, it is a miss
        grid[row][col] = MISS;

        return "miss";
    }

    /**
     * Checks if all ships on the board have been sunk.
     *
     * @return true if all ships are sunk, false otherwise.
     */
    public boolean isGameOver() {

        System.out.println("Checking if all ships are sunk...");

        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                System.out.println("Ship " + ship.getName() + " is not sunk.");
                return false;
            }
        }

        System.out.println("All ships are sunk. Game over.");
        return true;
    }

    /**
     * Checks if a specific cell is a hit.
     *
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the cell is a hit, false otherwise.
     */
    public boolean isHit(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        return grid[row][col] == HIT;
    }

    /**
     * Checks if a specific cell is a miss.
     *
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the cell is a miss, false otherwise.
     */
    public boolean isMiss(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        return grid[row][col] == MISS;
    }

    /**
     * Prints the current state of the board (for debugging).
     */
    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    // Getter for ship list
    public List<Ship> getShips() {
        return ships;
    }

    /**
     * Resets the board to its initial empty state.
     */
    public void reset() {
        initializeGrid();
        ships.clear();
    }

    /**
     * Checks if a specific cell is occupied by a ship.
     *
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the cell is occupied, false otherwise.
     */
    public boolean occupiesCell(int row, int col) {
        return grid[row][col] == SHIP;
    }

    public char[][] getGrid() {
        return grid;
    }
}
