import java.io.*; 
import java.net.*; 

class TextClient {
   public static void main(String argv[]) throws Exception 
   {
      int option = -1;
      boolean access = false;
      while(option != 4){
         System.out.print(
               "============\n"+
               "0. Connect to the server\n" +
               "1. Get the user list\n" + 
               "2. Send a message\n" + 
               "3. Get my messages\n" + 
               "4. Exit\n"+
               "Please enter a choice: "
               );
         BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
         try{
                  option = Integer.parseInt(inFromUser.readLine());
                  System.out.println();
                  if(option  > 0 && !access){
                     System.out.println("Please connect to the server first");
                     continue;
                  }
                  if(option  == 0 && access){
                     System.out.println("You are already connected to the server");
                     continue;
                  }
                  Socket clientSocket = new Socket("127.0.0.1", 6789);
                  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                  outToServer.writeBytes(option+ "\r\n");
                  String message = "";
                  BufferedReader inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
            switch(option){
               case 0:
               System.out.println("Connecting...\n============");
               while(!message.equals("Access Granted")){
                  System.out.print("Please enter the username: ");
                  String account = inFromUser.readLine();
                  outToServer.writeBytes(account + "\r\n");
                  System.out.print("Please enter the password: ");
                  account = inFromUser.readLine();
                  outToServer.writeBytes(account + "\r\n");
                  message = inFromServer.readLine();
                  System.out.println(message);
                  System.out.println("");
               }
                  access = true;
               break;
               case 1:
                  System.out.println("Getting list of users...");
                  System.out.println("========================");
                  while(!(message = inFromServer.readLine()).equals("")){
                     System.out.println(message);
                  }
                  break;
               case 2:
                  System.out.print("Enter a username you want to send a message to: ");
                  String recipient = inFromUser.readLine();
                  outToServer.writeBytes(recipient + "\r\n");
                  System.out.print("Enter the message you want to send: ");
                  String msg = inFromUser.readLine();
                  outToServer.writeBytes(msg + "\r\n");
                  System.out.println(inFromServer.readLine());
                  break;
               case 3:
                  System.out.println("Here are your messages: ");
                  System.out.println("========================");
                  message = inFromServer.readLine();
                  System.out.println(message);
                  while(clientSocket.getInputStream().available() > 0){
                     message = inFromServer.readLine();
                     System.out.println(message);
                  }
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
