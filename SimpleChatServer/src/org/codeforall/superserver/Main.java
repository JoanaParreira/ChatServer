package org.codeforall.superserver;

public class Main {
    public static void main(String[] args) {
        Client client1 = new Client("localhost", 9092);

        Thread thread1 = new Thread(client1);

        thread1.start();
    }
}
