package com.example.chat.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ChangeScene extends Thread{
    public void changeScene(URL url, Stage stage,String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle(title);
        stage.setScene(scene);
    }

    @Override
    public void run() {
        super.run();
    }
}
