package com.example.MP4.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void processHumanShotSinksMachineAndSetsWinner() {
        Board humanBoard = new Board();
        Board machineBoard = new Board();

        // place a single-cell ship on machine so human can sink it quickly
        Ship machineShip = new Ship("Target", 1, true);
        machineBoard.placeShip(machineShip, 0, 0);

        HumanAdapter human = new HumanAdapter(humanBoard);
        MachineAdapter machine = new MachineAdapter(machineBoard);

        Game game = new Game(human, machine);

        // Human shoots machine at the ship location
        String result = game.processHumanShot(0, 0);
        assertEquals("sunk", result);

        assertTrue(game.checkVictory());
        assertEquals("Human", game.getWinner());
    }

    @Test
    void processMachineShotSinksHumanAndSetsWinner() {
        Board humanBoard = new Board();
        Board machineBoard = new Board();

        Ship humanShip = new Ship("HumanTarget", 1, true);
        humanBoard.placeShip(humanShip, 2, 2);

        HumanAdapter human = new HumanAdapter(humanBoard);
        MachineAdapter machine = new MachineAdapter(machineBoard);

        Game game = new Game(human, machine);

        String result = game.processMachineShot(2, 2);
        assertEquals("sunk", result);

        assertTrue(game.checkVictory());
        assertEquals("Machine", game.getWinner());
    }
}
