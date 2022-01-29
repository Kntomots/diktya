import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server class
class Server {
    private static ArrayList<Account> accounts;


    public static void main(String[] args) {
        ServerSocket server = null;
        accounts = new ArrayList<>();

        try {

            server = new ServerSocket(Integer.parseInt(args[0]));
            server.setReuseAddress(true);

            while (true) {

                Socket client = server.accept();

                ClientHandler clientSock
                        = new ClientHandler(client, accounts);

                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
