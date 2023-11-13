import java.io.*; 
import java.net.*; 

class TextClient {
   public static void main(String argv[]) throws Exception 
   {
      int option = -1;
      while(option != 4){
         System.out.println(
               "0. Connect to the server\n" +
               "1. Get the user list\n" + 
               "2. Send a message\n" + 
               "3. Get my messages\n" + 
               "4. Exit"
               );
         BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
         try{
            option = Integer.parseInt(inFromUser.readLine());
                  Socket clientSocket = new Socket("127.0.0.1", 6789);
                  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                  outToServer.writeBytes(option+ "\r\n");
                  String message = "";
            switch(option){
               case 0:
               while(!message.equals("Access Granted")){
                  BufferedReader inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
                  System.out.println("Please Enter username and password");
                  String account = inFromUser.readLine();
                  outToServer.writeBytes(account + "\r\n");
                  message = inFromServer.readLine();
                  System.out.println("From Server: " + message);
               }
               break;
               case 1:
                  BufferedReader inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
                  message = inFromServer.readLine();
                  while(!message.equals("")){
                     System.out.println(message);
                     message = inFromServer.readLine();
                  }
                  break;
               case 2:
                  break;
               case 3:
                  break;
               case 4:
                  break;
            }
         } catch(NumberFormatException e){
            System.out.println("Invalid input Please Enter an number");
         }
      }
      
      // String sentence;
      // String modifiedSentence;
      // sentence = inFromUser.readLine(); 
      // outToServer.writeBytes(sentence + '\n'); 
      // modifiedSentence = inFromServer.readLine(); 
      // System.out.println("FROM SERVER: " + modifiedSentence);
      // clientSocket.close();
   } 
}
