public class Message {
    private boolean isRead;
    private String sender;
    private String receiver;
    private final int messageID;
    private String body="";
    public Message(String sender,String receiver,String body,boolean isRead,int messId){
        this.body=body;
        this.receiver=receiver;
        this.sender=sender;
        this.isRead=isRead;
        this.messageID=messId;

    }
    public boolean getIsRead(){
        return isRead;
    }
    public void setRead(boolean a){
        this.isRead=a;
    }
    public void setReceiver(String a){
        this.receiver=a;
    }
    public void setSender(String sender){
        this.sender=sender;

    }
    public int getMessageID(){return messageID;}
    public String getSender(){
        return sender;
    }
    public String getReceiver(){
        return receiver;
    }
    public String getBody() {
        return body;
    }
}
