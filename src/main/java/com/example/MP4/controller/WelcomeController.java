package com.example.MP4.controller;

import com.example.MP4.model.Board;
import com.example.MP4.model.Game;
import com.example.MP4.model.HumanAdapter;
import com.example.MP4.model.MachineAdapter;
import com.example.MP4.utils.CurrentGameHolder;
import com.example.MP4.view.PlacementStage;
import com.example.MP4.view.InstructionStage;
import com.example.MP4.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private Button InstructionsButton;

    @FXML
    private Button StartGameButton;

    private final Board humanBoard = new Board();

    private final Board machineBoard = new Board();

    private final HumanAdapter human = new HumanAdapter(humanBoard);

    private final MachineAdapter machine = new MachineAdapter(machineBoard);

    private final Game game = new Game(human, machine);

    public WelcomeController() {
        // Register the newly created game so it can be saved on exit even if the user
        // doesn't progress to the Game stage yet.
        CurrentGameHolder.set(game);
    }

    @FXML
    void HandleInstructions(ActionEvent event) throws IOException {
        InstructionStage.getInstance();
        WelcomeStage.deleteInstance();

    }

    @FXML
    void HandlePlay(ActionEvent event) throws IOException {
        PlacementStage.getInstance().getGameController().setGame(game);
        WelcomeStage.deleteInstance();

    }

}
