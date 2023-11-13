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
            response = inFromClient.readLine();
            String[] account = response.split(" ");
            if(accounts.containsKey(account[0])){
               if(accounts.get(account[0]).equals(account[1])){
                  outToClient.writeBytes("Access Granted" + "\r\n");
               } else {
                  outToClient.writeBytes("Access Denied" + "\r\n");
               }
            } else {
               outToClient.writeBytes("Access Denied" + "\r\n");
            }
            break;
            case 1:
               int i = 1;
               for(String key : accounts.keySet()){
                  response += "User "+ i +" "+ key + "\n";
                  i++;
               }
               outToClient.writeBytes(response + "\r\n");
            default:
               break;
         }

         // capitalizedSentence = clientSentence.toUpperCase() + '\n'; 
         // outToClient.writeBytes(capitalizedSentence);
      } 
   }
}
