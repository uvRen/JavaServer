import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket socket;
    private int port;
    private ExecutorService threadHandler;
    
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
        return socket.isBound();
    }
    
    /**
     * ServerSocket starts to listen for incoming connections.
     * This function will start a new Thread dedicated for
     * listening for incoming connections.
     */
    public void startListenForIncomingConnections() {
        threadHandler.execute(new IncomingConnections(socket));
    }
    
    /**
     * Gets which port the server is listen on
     * @return Port
     */
    public int getPort() {
        return this.port;
    }
}
