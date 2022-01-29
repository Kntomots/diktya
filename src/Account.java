import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {
    private String username;
    private int authToken;
    private List<Message> messageBox=new ArrayList<>();
    public Account(String username,int token){
        this.username=username;
        this.authToken=token;


    }
    public void setUsername(String username){
        this.username=username;

    }
    public void setAuthToken(int authToken){this.authToken=authToken;}
    public String getUsername(){
        return username;
    }
    public int getSizeOfMassages(){return messageBox.size();}
    public void deleteMessage(int x){
        messageBox.remove(x);
    }
    public int getAuthToken(){
        return authToken;
    }
    public Message getMessage(int a){
        return messageBox.get(a);
    }
    public void addMessage(Message a){
        messageBox.add(a);
    }
}
