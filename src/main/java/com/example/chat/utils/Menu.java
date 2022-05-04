package com.example.chat.utils;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        new ChangeScene(stage, 0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
