package com.example.MP4.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    @Test
    void setCoordinatesAndOccupiesCell() {
        Ship ship = new Ship("TestShip", 3, true);

        assertTrue(ship.setCoordinates(2, 2));
        assertTrue(ship.occupiesCell(2, 2));
        assertTrue(ship.occupiesCell(2, 3));
        assertTrue(ship.occupiesCell(2, 4));
        assertFalse(ship.occupiesCell(3, 2));
    }

    @Test
    void registerHitsAndSunk() {
        Ship ship = new Ship("Small", 2, false);
        ship.setHorizontal(false);
        ship.setCoordinates(0, 0);

        assertFalse(ship.isSunk());
        assertTrue(ship.registerHit(0, 0));
        assertFalse(ship.isSunk());
        assertEquals(1, ship.getHits());

        assertTrue(ship.registerHit(1, 0));
        assertTrue(ship.isSunk());
        assertEquals(2, ship.getHits());

        // hitting a non-occupied cell should return false
        assertFalse(ship.registerHit(5, 5));
    }
}
