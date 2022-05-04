package com.example.chat.utils;

import java.io.*;
import java.net.Socket;

public class User {
    Socket socket;
    String sendingMessage = "";
    private String[] messages = {"", "", "", ""};
    sendMessage send;
    getMessage get;
    public User() {
        try {
            socket = new Socket("localhost", 2234);
            send=new sendMessage();
            get=new getMessage();
            send.start();
            get.start();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    class sendMessage extends Thread {
        boolean shouldRun=true;
        private void sending() throws IOException {
            System.out.println("Uzytkownik");
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            while (shouldRun) {
                if (!sendingMessage.equals("")) {
                    outputStream.writeUTF(sendingMessage);
                    outputStream.writeBoolean(false);
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
                System.out.println("Sending error");
            }
        }

        public void stopThread(){
            shouldRun=false;
        }
    }

    class getMessage extends Thread {
    boolean shouldRun=true;
        private void getting() throws IOException, InterruptedException {
            while (shouldRun) {
                try {
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    String message = inputStream.readUTF();
                    System.out.println(message);
                    messages[3] = messages[2];
                    messages[2] = messages[1];
                    messages[1] = messages[0];
                    messages[0] = message;

                } catch (Exception e) {
                    messages[3] = messages[2];
                    messages[2] = messages[1];
                    messages[1] = messages[0];
                    messages[0] = "Zostales rozlaczony";
                    break;
                }

                sleep(100);
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

        public void stopThread(){
            shouldRun=false;
        }
    }

    public void setSendingMessage(String message) {
        sendingMessage = message;
    }

    public String[] getMessages() {
        return messages;
    }

    public void disconnect() {
        try {
            send.stopThread();
            get.stopThread();
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF("");
            outputStream.writeBoolean(true);
            outputStream.flush();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
