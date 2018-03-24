package server.essp.tc.remind;

public class MainServer {
    public MainServer() {
    }
     public static void main(String[] args) {
         SendMail logic=new SendMail();
         logic.Send();
     }
}
