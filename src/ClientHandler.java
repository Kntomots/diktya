import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


// ClientHandler class
public class ClientHandler implements Runnable {
    private static int messagesId = 0;
    private Socket clientSocket;
    private ArrayList<Account> accounts;
    private int currentAuthToken;

    private boolean CheckIfExistsUsername(String username) {
        for (Account a : accounts) {
            if (a.getUsername().equals(username)) {

                return true;
            }

        }
        return false;

    }

    private String getMessageFromIndex(int messagesId, int authToken) {
        String messBody = "-1";
        for (Account a : accounts) {
            if (a.getAuthToken() != authToken) {
                continue;
            }
            for (int i = 0; i < a.getSizeOfMassages(); i++) {
                if (a.getMessage(i).getMessageID() == messagesId) {
                    messBody = "(" + a.getMessage(i).getSender() + ")" + a.getMessage(i).getBody();
                    a.getMessage(i).setRead(true);
                    break;

                }
            }
        }
        return messBody;
    }

    private boolean checkIfTokenExist(int token) {
        for (Account a : accounts) {
            if (a.getAuthToken() == token) {
                return true;
            }
        }
        return false;

    }

    private String returnUsernameOfToken(int authToken) {
        for (Account a : accounts) {
            if (a.getAuthToken() == authToken) {
                return a.getUsername();
            }
        }
        return "";
    }

    private int getIndexOfUsername(String username) {
        int i = 0;
        for (Account a : accounts) {
            if (a.getUsername().equals(username)) {
                break;
            }
            i++;
        }
        return i;

    }


    public int createAuthToken() {
        int max = 9999;
        int min = 1000;
        Random rand = new Random();
        int b = (int) (Math.random() * (max - min + 1) + min);
        return b;
    }

    private int getIndexOfToken(int token) {
        int i = 0;
        for (Account a : accounts) {
            if (a.getAuthToken() == token) {
                break;
            }
            i++;
        }
        return i;

    }
    private boolean removeMessage(int messageId,int authToken){
        boolean deleted=false;
        for (Account a : accounts) {
            if (a.getAuthToken() != authToken) {
                continue;
            }
            for (int i = 0; i < a.getSizeOfMassages(); i++) {
                if (a.getMessage(i).getMessageID() == messageId) {
                   a.deleteMessage(i);
                   deleted=true;
                    break;

                }
            }
        }
        return deleted;


    }

    private String getMessageSend(String[] s1) {
        String s = "";
        for (int i = 5; i < s1.length; i++) {
            s = s + s1[i] + " ";

        }
        s = s.trim();
        return s;

    }

    private String codeHandler(String[] s1) {
        if (s1[2].equals("1")) {
            if (!s1[3].matches("[a-zA-Z_0-9]+")) {
                return "Invalid Username";
            } else if (!CheckIfExistsUsername(s1[3])) {
                currentAuthToken = createAuthToken();
                while (checkIfTokenExist(currentAuthToken)) {
                    currentAuthToken = createAuthToken();
                }
                Account a = new Account(s1[3], currentAuthToken);
                accounts.add(a);
                return String.valueOf(currentAuthToken);

            } else if (CheckIfExistsUsername(s1[3])) {
                return "Sorry, the user already exists";

            }
        } else if (s1[2].equals("2")) {
            int i = 1;
            String s = "";
            if (!checkIfTokenExist(Integer.parseInt(s1[3]))) {
                return "Invalid Auth Token";
            }
            for (Account a : accounts) {
                s = s + i + ". " + a.getUsername() + "/n";
                i++;
            }
            return s;

        } else if (s1[2].equals("3")) {
            if (!checkIfTokenExist(Integer.parseInt(s1[3]))) {
                return "Invalid Auth Token";

            }
            if (!CheckIfExistsUsername(s1[4])) {
                return "User does not exist";

            } else {
                messagesId++;
                Message massage = new Message(returnUsernameOfToken(Integer.parseInt(s1[3])), s1[4], getMessageSend(s1), false, messagesId);
                int index = getIndexOfUsername(s1[4]);
                accounts.get(index).addMessage(massage);
                return "OK";

            }

        } else if (s1[2].equals("4")) {
            if (!checkIfTokenExist(Integer.parseInt(s1[3]))) {
                return "Invalid Auth Token";

            }
            int index = getIndexOfToken(Integer.parseInt(s1[3]));
            int i = 0;
            String s = "";
            int size = accounts.get(index).getSizeOfMassages();
            while (i < size) {
                if (!accounts.get(index).getMessage(i).getIsRead()) {
                    s = s + accounts.get(index).getMessage(i).getMessageID() + ". " + "from: " + accounts.get(index).getMessage(i).getSender() + "*" + "/n";
                } else {
                    s = s + accounts.get(index).getMessage(i).getMessageID() + ". " + "from: " + accounts.get(index).getMessage(i).getSender() + "/n";
                }


                i++;

            }
            return s;

        } else if (s1[2].equals("5")) {
            if (!checkIfTokenExist(Integer.parseInt(s1[3]))) {
                return "Invalid Auth Token";

            } else {

                if (getMessageFromIndex(Integer.parseInt(s1[4]), Integer.parseInt(s1[3])).equals("-1")) {
                    return "Message ID does not exist";

                } else
                    return getMessageFromIndex(Integer.parseInt(s1[4]), Integer.parseInt(s1[3]));

            }
        } else if (s1[2].equals("6")) {
            if (!checkIfTokenExist(Integer.parseInt(s1[3]))) {
                return "Invalid Auth Token";
            }else{
                if (getMessageFromIndex(Integer.parseInt(s1[4]), Integer.parseInt(s1[3])).equals("-1")){
                    return "Message does not exist";
                }else
                {
                    if(removeMessage(Integer.parseInt(s1[4]),Integer.parseInt(s1[3]))){
                        return "OK";
                    }
                }
            }


            }


        return null;
    }


    // Constructor
    public ClientHandler(Socket socket, ArrayList<Account> acc) {
        this.clientSocket = socket;
        this.accounts = acc;

    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {

            // get the outputstream of client
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            // get the inputstream of client
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

            String line;

            while ((line = in.readLine()) != null) {

                String s1[] = line.split(" ");

                out.println(codeHandler(s1));


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
