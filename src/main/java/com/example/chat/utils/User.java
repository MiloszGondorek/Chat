package com.example.chat.utils;

import java.io.*;
import java.net.Socket;

public class User {
    Socket socket;
    String sendingMessage = "";
    private String[] messages={"","","",""};

    public User() {
        try {
            socket = new Socket("localhost", 2234);
            new sendMessage().start();
            new getMessage().start();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    class sendMessage extends Thread {
        private void sending() throws IOException {

            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (!sendingMessage.equals("exit")) {
                if (!sendingMessage.equals("")) {
                    outputStream.writeUTF(sendingMessage);
                    outputStream.flush();
                    sendingMessage = "";
                }
            }
        }

        @Override
        public void run() {
            try {
                sending();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    class getMessage extends Thread {
        private void getting() throws IOException {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            while (true) {
                String message = inputStream.readUTF();
                messages[3]=messages[2];
                messages[2]=messages[1];
                messages[1]=messages[0];
                messages[0]=message;
            }
        }

        @Override
        public void run() {
            try {
                getting();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setSendingMessage(String message) {
        sendingMessage = message;
    }

    public String[] getMessages(){
        return messages;
    }
}
