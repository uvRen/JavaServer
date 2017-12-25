import java.io.IOException;
import java.net.ServerSocket;

public class IncomingConnections implements Runnable {

    private ServerSocket serverSocket;
    
    public IncomingConnections(ServerSocket socket) {
        this.serverSocket = socket;
    }
    
    @Override
    public void run() {
        System.out.println("Server listen for incoming connections");
        while(true) {
            try {
                System.out.println("Listen...");
                serverSocket.accept();
                System.out.println("Client connected!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
