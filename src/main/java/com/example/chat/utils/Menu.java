package com.example.chat.utils;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Menu extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL url = Menu.class.getResource("/com/example/chat/menu-view.fxml");
        new ChangeScene().changeScene(url, stage, "Menu");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
