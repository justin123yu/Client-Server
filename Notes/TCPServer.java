import java.io.*; 
import java.net.*; 
class TCPServer {
   public static void main(String argv[]) throws Exception 
   {
      String clientSentence = "";
      String capitalizedSentence = "";
      ServerSocket welcomeSocket = new ServerSocket(6789); 
      System.out.println("SERVER is running ... ");

      while(true) {
         capitalizedSentence = "";
         Socket connectionSocket = welcomeSocket.accept();
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
         DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
         while(!clientSentence.equals("3")){
         clientSentence = inFromClient.readLine();
         String prompt = "Write your message \n";
         outToClient.write(prompt.getBytes());
         if(clientSentence.equals("1")){
            clientSentence = inFromClient.readLine();
            capitalizedSentence = clientSentence.toUpperCase() + '\n'; 
         }
         if(clientSentence.equals("2")){
            clientSentence = inFromClient.readLine();
            capitalizedSentence = clientSentence.toLowerCase() + '\n'; 
         }
         System.out.println("FROM CLIENT: " + clientSentence);
         outToClient.writeBytes(capitalizedSentence);
      } 
         }
      
   }
}
