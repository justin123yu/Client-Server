import java.io.*; 
import java.net.*; 

class TCPClient {
   public static void main(String argv[]) throws Exception 
   {
      String sentence;
      String modifiedSentence;
      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      Socket clientSocket = new Socket("127.0.0.1", 6789); 
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream()); 
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader( clientSocket.getInputStream()));
      while(true){
      System.out.println("1. Uppercase Message \n2. Lowercase Message\n3. Exit");
      sentence = inFromUser.readLine();
      if(sentence.equals("3")){
         clientSocket.close();
         break;
      }
      outToServer.writeBytes(sentence + '\n');
      modifiedSentence = inFromServer.readLine(); 
      System.out.println("FROM SERVER: " + modifiedSentence);
      sentence = inFromUser.readLine();
      outToServer.writeBytes(sentence + '\n');
      modifiedSentence = inFromServer.readLine(); 
      System.out.println("FROM SERVER: " + modifiedSentence);
      }
   } 
}
