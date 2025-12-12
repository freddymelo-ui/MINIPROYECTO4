package com.example.MP4.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void placementValidationAndPlaceShip() {
        Ship ship = new Ship("Duo", 2, true);

        // Out of bounds placement should be invalid
        assertFalse(board.isPlacementValid(ship, 0, 9));

        // Valid placement
        assertTrue(board.isPlacementValid(ship, 0, 0));
        assertTrue(board.placeShip(ship, 0, 0));
        assertTrue(board.occupiesCell(0, 0));
        assertTrue(board.occupiesCell(0, 1));
    }

    @Test
    void registerShotResultsAndFlags() {
        Ship ship = new Ship("Solo", 1, true);
        board.placeShip(ship, 3, 3);

        // Shot that hits
        String r1 = board.registerShot(3, 3);
        assertEquals("sunk", r1);
        assertTrue(board.isHit(3, 3));

        // Shooting same cell again
        String r2 = board.registerShot(3, 3);
        assertEquals("already shot", r2);

        // Shot that misses
        String r3 = board.registerShot(0, 0);
        assertEquals("miss", r3);
        assertTrue(board.isMiss(0, 0));
    }

    @Test
    void gameOverWhenAllSunk() {
        Ship s1 = new Ship("A", 1, true);
        Ship s2 = new Ship("B", 1, true);
        board.placeShip(s1, 0, 0);
        board.placeShip(s2, 1, 1);

        assertFalse(board.isGameOver());

        board.registerShot(0, 0); // sink s1
        assertFalse(board.isGameOver());

        board.registerShot(1, 1); // sink s2
        assertTrue(board.isGameOver());
    }
}
