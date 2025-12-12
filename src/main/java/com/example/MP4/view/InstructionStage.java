package com.example.MP4.view;

import com.example.MP4.controller.InstructionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// for the instructions and credits window

public class InstructionStage extends Stage {

    private final InstructionController instructionController;

    public InstructionStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/MP4/instruction-view.fxml"));
        Parent root = loader.load();
        instructionController = loader.getController();
        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Instructions and Credits");
        getIcons().add(new Image(String.valueOf(
                getClass().getResource("/com/example/MP4/images/favicon.png"))));
        setResizable(false);
        setWidth(400);
        setHeight(643);
        show();
    }

    // to access the controller from outside
    public InstructionController getInstructionController() {
        return instructionController;
    }

    /**
     * Static inner class that stores the singleton instance
     * of {@code InstructionStage}.
     */
    private static class InstructionStageHolder {
        /**
         * Singleton instance of {@code InstructionStage}.
         */
        private static InstructionStage INSTANCE;
    }

    /**
     * Returns the singleton instance of {@code InstructionStage}, creating it if
     * necessary.
     *
     * @return the unique instance of {@code InstructionStage}.
     * @throws IOException if the FXML file cannot be loaded during creation.
     */
    public static InstructionStage getInstance() throws IOException {
        InstructionStageHolder.INSTANCE = InstructionStageHolder.INSTANCE != null ? InstructionStageHolder.INSTANCE
                : new InstructionStage();

        return InstructionStageHolder.INSTANCE;
    }

    /**
     * Closes the current instance of {@code InstructionStage}
     * and resets the singleton reference to null.
     */
    public static void deleteInstance() {
        InstructionStageHolder.INSTANCE.close();
        InstructionStageHolder.INSTANCE = null;
    }

}
