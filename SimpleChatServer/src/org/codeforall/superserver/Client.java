package org.codeforall.superserver;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
        private Socket socket;
        private BufferedReader in;
        private BufferedWriter out;

        public Client(String serverAddress, int serverPort) {
            try {
                socket = new Socket(serverAddress, serverPort);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                System.out.println("Enter your name:");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message){
            try {
                    out.write(message);
                    out.newLine();
                    out.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public void sendMessageFromConsole() {
        try {
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = consoleInput.readLine()) != null) {
                sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public void startListening() {
            new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    @Override
    public void run() {
            startListening();
            sendMessageFromConsole();

    }

    public static void main(String[] args) {
            //Client client1 = new Client("127.0.0.1", 9092);
            Client client2 = new Client("localhost", 9092);

            //Thread thread1 = new Thread(client1);
            Thread thread2 = new Thread(client2);

            //thread1.start();
            thread2.start();
    }


}
