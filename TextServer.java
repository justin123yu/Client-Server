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

      while(true) {
         String options = "";
         String response = "";
         Socket connectionSocket = welcomeSocket.accept();
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         options = inFromClient.readLine(); 
         System.out.println("FROM CLIENT: " + options);
         int option = Integer.parseInt(options);
         switch (option) {
            case 0:
               while(!response.equals("Access Granted")){
                  response = inFromClient.readLine();
                  String[] account = response.split(" ");
                  System.out.println("Account: " +response);
                  if(account.length == 2 && accounts.containsKey(account[0])){
                     if(accounts.get(account[0]).equals(account[1])){
                        outToClient.writeBytes("Access Granted" + "\r\n");
                        break;
                     } else {
                        outToClient.writeBytes("Access Denied – Username/Password Incorrect" + "\r\n");
                     }
                  } else {
                     outToClient.writeBytes("Access Denied – Username/Password Incorrect" + "\r\n");
                  }

               }
               break;
            case 1:
               int i = 1;
               outToClient.writeBytes("User List: " + "\r\n");
               for(String key : accounts.keySet()){
                  response = "User "+ i +" "+ key;
                  outToClient.writeBytes(response + "\n");
                  System.out.println(response);
                  i++;
               }
               outToClient.writeBytes("\r\n");
               break;
            case 2:
               String user = inFromClient.readLine();
               if(accounts.containsKey(user)){
                  String msg = inFromClient.readLine();
                  System.out.println("User: " + user + " Message: " + msg);
                  message.get(user).add(msg);
                  outToClient.writeBytes("Message Sent" + "\r\n");
                  System.out.println("Message Sent");
               } else {
                  outToClient.writeBytes("User does not exist" + "\r\n");
                  System.out.println("User does not exist");
               }
               break;
            case 3:
               String name = inFromClient.readLine();
               System.out.println("User: " + name);
               if(accounts.containsKey(name)){
                  List<String> msgs = message.get(name);
                  for(String m : msgs){
                     outToClient.writeBytes(m + "\r\n");
                     System.out.println(m);
                  }
                  outToClient.writeBytes("\r\n");
                  message.get(name).clear();
               } else {
                  outToClient.writeBytes("User does not exist" + "\r\n");
                  System.out.println("User does not exist");
               }
               break;
            default:
               break;
         }
      } 
   }
}
