package com.example.chat.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChangeScene{
    URL[] urls = {
            ChangeScene.class.getResource("/com/example/chat/menu-view.fxml"),      // id 0
            ChangeScene.class.getResource("/com/example/chat/server-view.fxml"),    // id 1
            ChangeScene.class.getResource("/com/example/chat/user-view.fxml"),      // id 2
    };

    String[] title={
            "Menu",
            "Server",
            "User"
    };

    public ChangeScene(Stage stage, int id) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(urls[id]);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(title[id]);
        stage.setScene(scene);
    }
}
