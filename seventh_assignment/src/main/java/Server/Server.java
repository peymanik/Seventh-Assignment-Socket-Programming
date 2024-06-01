package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static final int PORT = 4000;
    private static ArrayList<Socket> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
        ServerSocket listener = null;
        try {
            listener = new ServerSocket(PORT);  
            while (true) {
                Socket client = listener.accept();
                ClientHandler clientThread = new ClientHandler(client, clients);
                clients.add(client);
                pool.execute(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pool.shutdown();
        }
    }
}
