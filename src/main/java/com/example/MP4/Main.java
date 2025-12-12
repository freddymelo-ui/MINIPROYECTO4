package com.example.MP4;

import com.example.MP4.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

// FREDDY ALEXANDER MELO BUITRAGO -- 202125498
// VERONICA GRANADOS -- 2123263
// JUAN DAVID SALAZAR -- 2344293

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance();
    }
}
