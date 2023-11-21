import java.io.*; 
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
class TextServer {
   public static void main(String argv[]) throws Exception 
   {
      HashMap <String, String> accounts = new HashMap<String, String>();
      accounts.put("Alice", "1234");
      accounts.put("Bob", "5678");
      HashMap <String, List<String>> message = new HashMap<>();
      message.put("Alice", new ArrayList<String>());
      message.put("Bob", new ArrayList<String>());
      ServerSocket welcomeSocket = new ServerSocket(6789); 
      System.out.println("SERVER is running ... ");
      String user = "";
      while(true) {
         String options = "";
         String response = "";
         Socket connectionSocket = welcomeSocket.accept();
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         options = inFromClient.readLine(); 
         System.out.println("");
         System.out.print("User's Choice is: " + options + "\n");
         int option = Integer.parseInt(options);
         switch (option) {
            case 0:
               while(!response.equals("Access Granted")){
                  String[] account = new String[2];
                  response = inFromClient.readLine();
                  account[0] = response;
                  response = inFromClient.readLine();
                  account[1] = response;
                  System.out.println("username: " +account[0] + " password: " + account[1]);
                  user = account[0];
                  if(accounts.containsKey(account[0])){
                     if(accounts.get(account[0]).equals(account[1])){
                        outToClient.writeBytes("Access Granted" + "\r\n");
                        System.out.println("Access Granted");
                        break;
                     } else {
                        outToClient.writeBytes("Access Denied – Username/Password Incorrect" + "\r\n");
                        System.out.println("Access Denied");
                     }
                  } else {
                     outToClient.writeBytes("Access Denied – Username/Password Incorrect" + "\r\n");
                     System.out.println("Access Denied");
                  }

               }
               break;
            case 1:
               int i = 1;
               System.out.println("Returning list of users...");
               for(String key : accounts.keySet()){
                  response = "User "+ i +" "+ key;
                  outToClient.writeBytes(response + "\n");
                  System.out.print(response + "\n");
                  i++;
               }
               outToClient.writeBytes("\r\n");
               break;
            case 2:
               String sender = inFromClient.readLine();
               if(accounts.containsKey(sender)){
                  String msg = inFromClient.readLine();
                  String templateMessage =user + " : " + msg;
                  message.get(sender).add(templateMessage);
                  outToClient.writeBytes("Status: Message sent successful" + "\r\n");
                  System.out.println("Recieve a message for "+ sender);
               } else {
                  outToClient.writeBytes("User does not exist" + "\r\n");
                  System.out.println("User does not exist");
               }
               break;
            case 3:
                  List<String> msgs = message.get(user);
                  if(msgs.size() == 0){
                     System.out.println("No messages");
                     outToClient.writeBytes("No messages" + "\r\n");
                     outToClient.flush();
                  } else {
                     for(String m : msgs){
                        outToClient.writeBytes(m + "\r\n");
                        outToClient.flush();
                        System.out.println(m);
                     }
                  }
               break;
            case 4:
               System.out.println(user +" Logged Out");
               break;
            default:
               outToClient.writeBytes("Invalid input Please Enter an number" + "\r\n");
               System.out.println("Invalid input Option");
               break;
         }
      } 
   }
}
