package org.codeforall.superserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    private final int portServer = 9092;
    LinkedList<ServerWorker> list = new LinkedList<ServerWorker>();
    ServerSocket serverSocket;
    Socket clientSocket;
    ServerWorker serverWorker;

    public void begin() throws IOException {
        try {

                serverSocket = new ServerSocket(portServer);

            while (!serverSocket.isClosed()) {
                clientSocket = serverSocket.accept();
                serverWorker = new ServerWorker(this, clientSocket);
                System.out.println("Hello, i'm a new client");
                list.add(serverWorker);

                Thread thread = new Thread(serverWorker);
                thread.start();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        serverSocket.close();
        clientSocket.close();
    }

    public void sendAll(String message, String name) throws IOException {
        for (ServerWorker serverWorker : list) {
            if (!(serverWorker.getClientUsername() == name)) {
                serverWorker.out.write(message);
                serverWorker.out.newLine();
                serverWorker.out.flush();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.begin();
    }

}

