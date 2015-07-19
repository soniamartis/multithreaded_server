import java.net.*;
import java.io.*;
import java.util.*;
public class Server {
   
    private ArrayList l=new ArrayList();
    private ServerSocket server;
    
    public Server(int port)throws IOException{
        listen(port);
    }
    public static void main(String args[])throws IOException{
        int port=Integer.parseInt(args[0]);
        new Server(port);
    }
    
    public void listen(int port)throws IOException{
        server=new ServerSocket(port);
        System.out.println("listening on port"+server);
        while(true){
            Socket connection=server.accept();
            System.out.println("connection from "+connection);
            ObjectOutputStream out=new ObjectOutputStream(connection.getOutputStream());
            l.add(out);
            new ServerThread(this,connection);
        }
    }
    
    public void sendToAll(final String message,Socket connection){
        synchronized(l){
            Iterator itr=l.iterator();
            while(itr.hasNext()){
                ObjectOutputStream out=(ObjectOutputStream)itr.next();
                try{
                if(out==connection.getOutputStream())
                continue;
                out.writeObject(message);
                }catch(IOException e){
                    e.printStackTrace();
                }}
            }
        }
    
    
    public void removeConnection(Socket connection){
        synchronized(l){
            System.out.println("removing "+connection);
            try{
            l.remove(connection.getOutputStream());}
            catch(IOException e){
                System.out.println("disconnected");
            }
        }
    }
    
}


