package code;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Ali on 6/30/2016.
 */
public class Client {

    public Client(Socket clientSocket) throws IOException {
        //initial variables
        String modifiedSentence = null;
        String clientname = null;
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        modifiedSentence = inFromServer.readUTF();

    }

    private String normaltojson(String tmp) {

        return tmp;
    }
}
