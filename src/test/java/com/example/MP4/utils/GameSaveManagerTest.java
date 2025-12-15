package com.example.MP4.utils;

import com.example.MP4.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameSaveManagerTest {

    @Test
    public void testSaveLoadRoundtrip() throws Exception {
        Board humanBoard = new Board();
        Board machineBoard = new Board();
        HumanAdapter human = new HumanAdapter(humanBoard);
        MachineAdapter machine = new MachineAdapter(machineBoard);

        Game game = new Game(human, machine);

        // place ships for both
        machine.placeShips();
        // place ships for human using player ships
        var ships = human.getShips();
        for (int i = 0; i < ships.size(); i++) {
            Ship s = ships.get(i);
            int row = i; // simple layout to avoid conflicts
            int col = 0;
            s.setHorizontal(true);
            boolean placed = humanBoard.placeShip(s, row, col);
            if (!placed) {
                // try next row
                placed = humanBoard.placeShip(s, (row + 1) % Board.BOARD_SIZE, col);
            }
            assertTrue(placed, "Failed to place human ship " + s.getName());
        }

        // register some shots
        machineBoard.registerShot(0, 0);
        humanBoard.registerShot(1, 1);

        File tmp = Files.createTempFile("game-save-test", ".ser").toFile();
        tmp.deleteOnExit();

        GameSaveManager.saveGame(tmp, game);
        Game loaded = GameSaveManager.loadGame(tmp);

        assertNotNull(loaded);
        // Compare basic board state: number of ships
        assertEquals(game.human.getBoard().getShips().size(), loaded.human.getBoard().getShips().size());
        assertEquals(game.machine.getBoard().getShips().size(), loaded.machine.getBoard().getShips().size());

        // Compare a shot position
        assertEquals(game.machine.getBoard().isHit(0,0), loaded.machine.getBoard().isHit(0,0));
        assertEquals(game.human.getBoard().isHit(1,1), loaded.human.getBoard().isHit(1,1));
    }
}

