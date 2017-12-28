import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is listening for incoming connections to the server.
 * When a client connects to the server a new thread is created
 * that listen for incoming messages from that client. 
 * @author Simon Berntsson
 *
 */
public class IncomingConnections implements Runnable {

    private ServerSocket serverSocket;
    private Server server;
    private int uniqueID = 0;
    
    public IncomingConnections(Server server, ServerSocket socket) {
        this.server = server;
        this.serverSocket = socket;
    }
    
    @Override
    public void run() {
        Socket s = null;
        System.out.println("Server listen for incoming connections");
        while(true) {
            try {
                System.out.println("Listen...");
                s = serverSocket.accept();
                System.out.println("Client connected!");
                ClientThread client = new ClientThread(s, uniqueID);
                new Thread(client).start();
                server.addClient(client);
                uniqueID++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
