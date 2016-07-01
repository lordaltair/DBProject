package gui;

import code.PrimitiveClasses.LoginInfo;
import code.PrimitiveClasses.Profile;
import code.PrimitiveClasses.User;
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

import static code.COMMAND_CODES.LOGIN;
import static code.COMMAND_CODES.SIGN_UP;

/**
 * Created by Ali on 6/30/2016.
 */
public class LoginPage
{
    public JPanel LoginPanel;

    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton loginButton;
    private JButton signInButton;
    private String Username;
    private String Password;
    private MainFrame mainframe;

    public LoginPage(MainFrame mainframe)
    {
        this.mainframe = mainframe;
        Socket clientSocket = null;
        try
        {
            clientSocket = new Socket("localhost", 6789);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
            final Socket finalClientSocket = clientSocket;
            loginButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    Username = usernameTextField.getText();
                    Password = passwordTextField.getText();

                    try
                    {
                        LoginInfo loginInfo = new LoginInfo(Username, Password);
                        String modifiedsentence = login(loginInfo);

                        outToServer.writeUTF(modifiedsentence);
                        outToServer.flush();

                        modifiedsentence = inFromServer.readUTF();
                        modifiedsentence = jsontonormallogin(modifiedsentence);

                        modifiedsentence.trim();
                        if (modifiedsentence.equals("true"))
                        {
                            mainframe.gotoNextFrame(Username, finalClientSocket, outToServer, inFromServer);
                        } else
                        {
                            String infoMessage = "Wrong Username Or Password! Try Again";
                            String TitleMessaage = "Wrong username or password";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }

                }
            });
            signInButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    User user = new User();
                    String str;
                    user.setUsername("altair");
                    user.setName("ali");

                    Profile profile = new Profile(user, "hoseinmardy", "1234", "a.hoseinmardy@gmail.com", "44444444", "salam man shakham");

                    try
                    {
                        str = signup(profile);
                        outToServer.writeUTF(str);
                        str = inFromServer.readUTF();
                        str = jsontonormallogin(str);
                        if (str.equals("true"))
                        {
                            String infoMessage = "You signed Correctly!";
                            String TitleMessaage = "Welcome!";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.OK_OPTION);
                        } else
                        {
                            String infoMessage = "You signed Incorrectly! Please Try Again";
                            String TitleMessaage = "Sorry!";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.ERROR_MESSAGE);
                        }
                        user = new User();
                        user.setUsername("dani");
                        user.setName("danial");

                        profile = new Profile(user, "alihoseini", "1234", "d.daniel@gmail.com", "33333333", "salam man noobam");

                        str = signup(profile);
                        outToServer.writeUTF(str);
                        str = inFromServer.readUTF();
                        str = jsontonormallogin(str);
                        if (str.equals("true"))
                        {
                            String infoMessage = "You signed Correctly!";
                            String TitleMessaage = "Welcome!";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.OK_OPTION);
                        } else
                        {
                            String infoMessage = "You signed Incorrectly! Please Try Again";
                            String TitleMessaage = "Sorry!";
                            JOptionPane.showMessageDialog(null, infoMessage, TitleMessaage, JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }

                }
            });
        } catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    private String signup(Profile profile) throws IOException
    {
        String result;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject args = new JSONObject();

        args.put("profile", profile.toJsonObj());

        obj.put("command", SIGN_UP);
        obj.put("arg", args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
    }

    private String login(LoginInfo logininfo) throws IOException
    {
        String result = null;
        JSONObject obj = new JSONObject();
        StringWriter out = new StringWriter();

        JSONObject args = new JSONObject();

        args.put("logininfo", logininfo.toJsonObj());

        obj.put("command", LOGIN);
        obj.put("arg", args);

        obj.writeJSONString(out);
        result = out.toString();
        return result;
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

    private void createUIComponents()
    {

    }

}
