package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket client;
    private static ArrayList<Socket> clients;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Socket client, ArrayList<Socket> clients) throws IOException {
        this.clients = clients;
        this.client = client;
        this.in = new DataInputStream(client.getInputStream());
        this.out = new DataOutputStream(client.getOutputStream());
    }
    @Override
    public void run() {
        try {
            File file = new File("History.txt");
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null)
                        this.out.writeUTF(line);
                }
            }
            while (true) {
                String clientInput = this.in.readUTF();
                for (Socket aClient : clients) {
                    DataOutputStream out = new DataOutputStream(aClient.getOutputStream());
                    out.writeUTF(clientInput);
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
                writer.write(clientInput);
                writer.newLine();
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}



