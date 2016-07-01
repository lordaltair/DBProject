package gui;

import code.Client.Client;
import code.PrimitiveClasses.Profile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;

/**
* Created by Ali on 6/30/2016.
*/
public class LoginPage {
    public JPanel LoginPanel;

    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JButton signInButton;
    private String Username;
    private String Password;

<<<<<<< HEAD
    public LoginPage() throws IOException {

        final Socket clientSocket = new Socket("localhost", 6789);
        final DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        final DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        while(true) {
=======
    public LoginPage()
    {

        Socket clientSocket = null;
        try
        {
            clientSocket = new Socket("localhost", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
            final Socket finalClientSocket = clientSocket;
>>>>>>> origin/master
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Username = usernameTextField.getText();
                    Password = passwordTextField.getText();

                    try {
                        String modifiedsentence = login(Username,Password);

                        outToServer.writeUTF(modifiedsentence);
                        outToServer.flush();

                        modifiedsentence = inFromServer.readUTF();
                        modifiedsentence = jsontonormallogin(modifiedsentence);

                        modifiedsentence.trim();
                        StringBuilder sb = new StringBuilder(modifiedsentence);
                        sb.deleteCharAt(0);
                        sb.deleteCharAt(sb.length()-1);
                        if (modifiedsentence.equals("true")) {
                            outToServer.close();
                            inFromServer.close();
                            new First();
                            new Client(finalClientSocket, Username);
                        } else {
                            String infoMessage = "Wrong Username Or Password! Try Again";
                            String TitleMessaage = "Wrong username or password";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    private String signup(String me,Profile profile) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 22);
        obj.put("arg1" , me);
        obj.put("arg2" , profile);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String login(String username,String password) throws IOException {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        obj.put("command" , 23);
        obj.put("arg1" , username);
        obj.put("arg2" , password);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }


    private String jsontonormallogin(String sendrecievestr) {
        String result = null;
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(sendrecievestr);
            JSONArray array = (JSONArray) obj;
            JSONObject obj2 = (JSONObject) array.get(0);
            result = obj2.get("ack").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void createUIComponents() {

    }
}
