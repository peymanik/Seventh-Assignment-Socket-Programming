package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerHandler implements Runnable {
    private DataInputStream in;
    public ServerHandler(Socket client) throws IOException {
        this.in = new DataInputStream(client.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true)
                System.out  .println(this.in.readUTF());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
