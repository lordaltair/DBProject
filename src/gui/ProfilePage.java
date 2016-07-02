package gui;

import code.Client.Client;
import code.PrimitiveClasses.FriendList;
import code.PrimitiveClasses.Profile;
import code.PrimitiveClasses.User;
import com.google.gson.Gson;
import com.sun.xml.internal.org.jvnet.mimepull.CleanUpExecutorFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Ali on 7/2/2016.
 */
public class ProfilePage {
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JTextField textField3;
    private JTextField textField4;
    private JTextArea textArea1;
    private JTextField textField5;
    private JButton createChannelButton;
    private JButton createGroupButton;
    private JButton updateProfileButton;
    private JTextField textField6;
    public JPanel ProfPanel;

    public Client client;

    public ProfilePage(String username , Client client) {
        this.client = client;
        String command = null;
        try {

            command = client.getprofiledetail(username);
        client.outToServer.writeUTF(command);

        command = client.inFromServer.readUTF();
        JSONParser parser=new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(command);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        JSONObject jsonobj = (JSONObject) obj;

        Gson gson = new Gson();
        jsonobj.writeJSONString(new StringWriter());
        Profile profile = gson.fromJson(jsonobj.toString(), Profile.class);

        username = username + "Updated!";
        JOptionPane.showMessageDialog(null, username, "Update", JOptionPane.OK_OPTION);
        updateProfilepage(profile);

        } catch (IOException e) {
            e.printStackTrace();
        }

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profile profile = new Profile();
                User user = new User();
                user.setName(textField1.getText());
                user.setUsername(textField2.getText());
                profile.setUser(user);
                profile.setLastName(textField6.getText());
                profile.setPassword(passwordField1.getText());
                profile.setEmail(textField4.getText());
                profile.setBioDescription(textArea1.getText());
                profile.setPhoneNumber(textField5.getText());


                try {
                    String command = client.updateprofile(profile);
                    client.outToServer.writeUTF(command);
                    command = client.inFromServer.readUTF();
                    JOptionPane.showMessageDialog(null, "Profile Updated", "Update", JOptionPane.OK_OPTION);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }

    private void updateProfilepage(Profile profile) {
        textField1.setText(profile.getUser().getName());
        textField2.setText(profile.getUser().getUsername());
        passwordField1.setText(profile.getPassword());
        textField4.setText(profile.getEmail());
        textArea1.setText(profile.getBioDescription());
        textField5.setText(profile.getPhoneNumber());
        textField6.setText(profile.getLastName());
    }
}
