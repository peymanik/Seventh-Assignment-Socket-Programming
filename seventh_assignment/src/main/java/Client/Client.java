package Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int PORT = 4000;
    public static final String IP = "127.0.0.1";
    public static String name;
    public static void main(String[] args) throws IOException {
        Socket client = new Socket(IP, PORT);
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        System.out.println("Enter your name:");
        Scanner scanner = new Scanner(System.in);
        name = scanner.nextLine();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ServerHandler serverHandler = new ServerHandler(client);
        new Thread(serverHandler).start();
        out.writeUTF(name + ": joined!");
        String userInput = "";
        while (!userInput.equals("disconnected")) {
            userInput = reader.readLine();
            if (userInput == null)
                userInput = "disconnected";
            out.writeUTF("[" + name + "] " + userInput);
        }
    }
}