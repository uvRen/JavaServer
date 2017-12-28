import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket socket;
    private int port;
    private ExecutorService threadHandler;
    private List<ClientThread> clients;
    
    /**
     * Creates a ServerSocket
     * @param port Defines which port the server should listen on
     * @return <b>true</b> if server was created successfully
     * @throws IOException
     */
    public boolean startServer(int port) throws IOException {
        this.port = port;
        this.socket = new ServerSocket(port);
        this.threadHandler = Executors.newSingleThreadExecutor();
        this.clients = new ArrayList<ClientThread>();
        return socket.isBound();
    }
    
    /**
     * ServerSocket starts to listen for incoming connections.
     * This function will start a new Thread dedicated for
     * listening for incoming connections.
     */
    public void startListenForIncomingConnections() {
        threadHandler.execute(new IncomingConnections(this, socket));
    }
    
    /**
     * Gets which port the server is listen on
     * @return Port
     */
    public int getPort() {
        return this.port;
    }
    
    /**
     * Add a new client to the list of all connected clients
     * @param client Client to add to the list
     */
    public void addClient(ClientThread client) {
        synchronized (clients) {
            clients.add(client);
        }
    }
    
}
