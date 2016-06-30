package gui;

import code.Client.Client;
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

    public LoginPage() throws IOException {

        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

        while(true) {
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Username = usernameTextField.getText();
                    Password = passwordTextField.getText();

                    JSONObject sendrecievejson = normaltojsonlogin(Username, Password);
                    try {
                        String modifiedsentence = null;

                        outToServer.writeUTF(sendrecievejson.toJSONString());
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
                            new Client(clientSocket , Username);
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
        }
    }

    private String jsontonormallogin(String sendrecievestr) {
        String result = null;
        JSONParser parser=new JSONParser();

        try {
            Object obj = parser.parse(sendrecievestr);
            JSONArray array = (JSONArray)obj;
            JSONObject obj2 = (JSONObject)array.get(0);
            result = obj2.get("ack").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

    private JSONObject normaltojsonlogin(String username, String password) {
        JSONObject obj = new JSONObject();

        obj.put("username", username);
        obj.put("password",password);

        return obj;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
