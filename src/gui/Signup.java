package gui;

import code.Client.Client;
import code.PrimitiveClasses.Profile;
import code.PrimitiveClasses.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.lang.model.element.Name;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Ali on 7/2/2016.
 */
public class Signup {
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;
    private JTextField textField5;
    private JButton signUpButton;
    private JTextField textField6;
    private JPanel JP1;
    private JPanel JP2;

    private LoginPage lp;

    public Signup(DataOutputStream outToServer , DataInputStream inFromServer)
    {
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = new User();
                user.setUsername(textField1.getText());
                user.setName(textField2.getText());

                Profile profile = new Profile(user, textField6.getText(), passwordField1.getText(), textField4.getText(), textField5.getText(), textArea1.getText());
                String str = null;
                try {
                    str = lp.signup(profile);
                    outToServer.writeUTF(str);
                    str = inFromServer.readUTF();
                    str = jsontonormallogin(str);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (str.equals("true"))
                {
                    String infoMessage = "You signed Correctly!";
                    String TitleMessaage = "Welcome!";
                    JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.OK_OPTION);
                    JP2.setVisible(false);
                    JP1.setVisible(false);
                } else
                {
                    String infoMessage = "You signed Incorrectly! Please Try Again";
                    String TitleMessaage = "Sorry!";
                    JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }

    private String jsontonormallogin(String sendrecievestr)
    {
        String result = null;
        JSONParser parser = new JSONParser();

        try
        {
            System.out.println(sendrecievestr);
            Object obj = parser.parse(sendrecievestr);
            JSONObject obj2 = (JSONObject) obj;
            result = obj2.get("ack").toString();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return result;
    }

}
