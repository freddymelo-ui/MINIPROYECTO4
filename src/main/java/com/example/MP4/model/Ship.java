package com.example.MP4.model;

import java.util.ArrayList;
import java.util.List;

// Represents a ship in the Battleship game
public class Ship {
    private final String name; // Ship name (e.g., "Carrier", "Destroyer")
    private final int size; // Ship size (number of cells it occupies)
    private boolean isHorizontal; // Orientation: true = horizontal, false = vertical
    private final List<int[]> coordinates; // List of occupied coordinates (row, column)
    private int hits; // Number of hits received

    /**
     * Constructor for the Ship class.
     * 
     * @param name         Ship name.
     * @param size         Ship size (number of cells it occupies).
     * @param isHorizontal Ship orientation (true = horizontal, false = vertical).
     */
    public Ship(String name, int size, boolean isHorizontal) {
        this.name = name;
        this.size = size;
        this.isHorizontal = isHorizontal;
        this.coordinates = new ArrayList<>();
        this.hits = 0;
    }

    /**
     * Sets the ship's coordinates based on its initial position and orientation.
     * 
     * @param startRow Starting row.
     * @param startCol Starting column.
     * @return true if the coordinates are set successfully.
     */
    public boolean setCoordinates(int startRow, int startCol) {
        coordinates.clear(); // Clears any previously assigned coordinates
        for (int i = 0; i < size; i++) {
            int row = isHorizontal ? startRow : startRow + i;
            int col = isHorizontal ? startCol + i : startCol;
            coordinates.add(new int[] { row, col });
        }
        return true;
    }

    /**
     * Checks whether the ship occupies a specific cell.
     * 
     * @param row Row to check.
     * @param col Column to check.
     * @return true if the ship occupies the cell, false otherwise.
     */
    public boolean occupiesCell(int row, int col) {
        return coordinates.stream().anyMatch(coord -> coord[0] == row && coord[1] == col);
    }

    /**
     * Registers a hit on the ship at the specified cell.
     * 
     * @param row Row of the hit.
     * @param col Column of the hit.
     * @return true if the hit is valid, false otherwise.
     */
    public boolean registerHit(int row, int col) {
        if (occupiesCell(row, col)) {
            hits++;
            return true;
        }
        return false;
    }

    /**
     * Sets the ship's orientation.
     * 
     * @param horizontal true if the ship is horizontal, false if vertical.
     */
    public void setHorizontal(boolean horizontal) {
        this.isHorizontal = horizontal;
    }

    /**
     * Checks whether the ship is sunk (all its cells have been hit).
     * 
     * @return true if the ship is sunk, false otherwise.
     */
    public boolean isSunk() {
        return hits >= size;
    }

    /**
     * Retrieves the ship's name.
     * 
     * @return The ship name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the ship's size.
     * 
     * @return The ship size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the ship's orientation.
     * 
     * @return true if the ship is horizontal, false if vertical.
     */
    public boolean isHorizontal() {
        return isHorizontal;
    }

    /**
     * Retrieves the coordinates occupied by the ship.
     * 
     * @return List of coordinates (row, column) occupied by the ship.
     */
    public List<int[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Retrieves the number of hits received.
     * 
     * @return The number of hits.
     */
    public int getHits() {
        return hits;
    }
}
