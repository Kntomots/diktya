import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {

    // driver code
    public static void main(String[] args)
    {
        // establish a connection by providing host and port
        // number
        try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {
           String a=args[0];
           for(int i=1;i< args.length;i++){
               a=a.concat(" ");
              a=a.concat(args[i]);
           }
            // writing to server
            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true);

            // reading from server
            BufferedReader in
                    = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;
            int firstTime=0;


                if(firstTime==0){
                  out.println(a);
                  out.flush();
                  if(args[2].equals("2")  ){
                      String s[]=in.readLine().split("/n");
                      for(int i=0;i<s.length;i++){
                          System.out.println(s[i]);
                      }
                  }else if(args[2].equals("4")){
                      String s[]=in.readLine().split("/n");
                      for(int i=0;i<s.length;i++) {
                          System.out.println(s[i]);
                      }

                  }else
                    System.out.println(in.readLine());
                    firstTime++;

                }



            // closing the scanner object
            sc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
