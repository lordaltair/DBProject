package gui;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Ali on 6/30/2016.
 */
public class LoginPage {
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JButton signInButton;
    private String Username;
    private String Password;

    public LoginPage() throws IOException {

        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        Username = usernameTextField.getText();
        Password = passwordTextField.getText();

        String sendstr = normaltojsonlogin(Username,Password);
        outToServer.writeUTF(sendstr);
        outToServer.flush();
        

    }

    private String normaltojsonlogin(String username, String password) {
        String result = null;
        return result;
    }
}
