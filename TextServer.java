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
         System.out.print("User's Choice is: " + options);
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
                  outToClient.writeBytes("Status: Message sent successful" + "\r\n");
                  System.out.println("Recieve a message for "+ user);
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
