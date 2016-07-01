package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Ali on 7/1/2016.
 */
public class MainFrame {
    public JPanel MainPanel;

    public MainFrame() {
        JPanel loginPanel = new LoginPage(MainPanel).LoginPanel;
        JPanel firstpanel = new First().firstpanel;
        MainPanel.add(loginPanel, "0");
        MainPanel.add(firstpanel, "1");
        ((CardLayout)MainPanel.getLayout()).show(MainPanel, "0");

    }
}
