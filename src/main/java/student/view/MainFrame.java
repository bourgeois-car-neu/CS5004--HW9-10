package student.view;
import student.controller.GuiController;
import student.model.DomainNameModel;
import javax.swing.*;
import java.awt.*;

/**
 * main window for domain name lookup for GUI.
 */
public class MainFrame extends JFrame {
    /** controller the view talks to. */
    private GuiController controller;
    /** text field for entering hostname. */
    private JTextField enterHostname;
    /** button for lookup. */
    private JButton lookupButton;

    /**
     * constructor for building and displaying window.
     */
    public MainFrame() {
        controller = new GuiController(DomainNameModel.getInstance());
        // title setup.
        setTitle("Domain Name Lookup");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup for main window.
        JPanel topPanel = new JPanel(new FlowLayout());
        enterHostname = new JTextField(20);
        lookupButton = new JButton("Lookup");
        topPanel.add(new JLabel("Hostname:"));
        topPanel.add(enterHostname);
        topPanel.add(lookupButton);
        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * launch GUI
     * @param args na.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
