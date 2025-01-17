
import java.net.*;
import java.util.*;
import java.io.*;


class ServerThread extends Thread {
    private Socket connection;
    private Server server; 
    public ServerThread(Server server, Socket connection) {
        this.connection=connection;
        this.server=server;
        start();
    }
    
    public void run(){
       try{
           ObjectInputStream in=new ObjectInputStream(connection.getInputStream());
           String message="";
           do{
               try{
            message=(String)in.readObject();}
               catch(ClassNotFoundException e){
                   System.out.println("dude what the heck have u sent!!");
               }
               server.sendToAll(message,connection);
           }while(!message.equals("CLIENT-END"));
       }catch(IOException e){
          
           
       } finally{
           server.removeConnection(connection);
           try{
           connection.close();}catch(IOException e){
               e.printStackTrace();
           }
       }
    }
    
}
