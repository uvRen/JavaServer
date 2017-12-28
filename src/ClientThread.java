import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.DefaultMessage;
import message.NewClientMessage;

/**
 * This class should represent a thread that is created for
 * each Client that connects to the server. It should listen
 * for incoming messages from the client.
 * @author Simon Berntsson
 *
 */
public class ClientThread implements Runnable {

    private int id;
    private Socket socket;
    
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    public ClientThread(Socket socket, int id) {
        this.socket = socket;
        this.id = id;
    }
    
    private void setupConnections() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Thread that listen for incoming messages from client
     */
    @Override
    public void run() {
        setupConnections();
        System.out.println("ClientThread created, listening on client");
        sendObjectToClient(new NewClientMessage(id));
        Object input = null;
        while(true) {
            try {
                input = in.readObject();
                if(input == null)
                    break;
                handleMessage(input);
            } catch (ClassNotFoundException | IOException e) {
                System.out.println("Client " + id + " disconnected");
                break;
            }
        }
    }
    
    /**
     * Send an object from the server to the client
     * @param o Object that you want to send to the client
     */
    public void sendObjectToClient(Object o) {
        try {
            out.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Handles incoming messages from client and determine how to 
     * handle the incoming data
     * @param input Object received
     */
    private void handleMessage(Object input) {
        if(input instanceof DefaultMessage) {
            DefaultMessage dm = (DefaultMessage)input;
            System.out.println("DefaultMessage: " + dm.getMessage());
        } else {
            System.out.println("Not yet implemented");
        }
    }

}
