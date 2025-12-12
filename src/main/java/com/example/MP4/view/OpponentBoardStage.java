package com.example.MP4.view;

import com.example.MP4.controller.OpponentBoardController;
import com.example.MP4.model.Board;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// for the opponent's (machine's) board window

public class OpponentBoardStage extends Stage {

    private final OpponentBoardController controller;

    /**
     * Constructor for OpponentBoardStage.
     *
     * @param machineBoard Machine's board to display.
     * @throws IOException If the FXML file cannot be loaded.
     */
    public OpponentBoardStage(Board machineBoard) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/MP4/opponent-board-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setMachineBoard(machineBoard);

        setScene(new Scene(root));
        setTitle("Machine Board");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/MP4/images/favicon.png"))));
        setResizable(false);
    }

    /**
     * Gets the controller associated with OpponentBoardStage.
     *
     * @return OpponentBoardController.
     */
    public OpponentBoardController getController() {
        return controller;
    }
}
