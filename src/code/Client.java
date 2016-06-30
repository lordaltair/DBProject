package code;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Ali on 6/30/2016.
 */
public class Client {

    public Client() throws IOException {
        //initial variables
        String modifiedSentence = null;
        String clientname = null;
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        String tmp = clientname + ",HELLO";
        tmp = normaltojson(tmp);
        outToServer.writeUTF(tmp);
        outToServer.flush();

    }

    private String normaltojson(String tmp) {

        return tmp;
    }
}
