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
    /** label for hostname. */
    private JLabel hostnameLabel;
    /** label for IP. */
    private JLabel ipLabel;
    /** label for city. */
    private JLabel cityLabel;
    /** label for region. */
    private JLabel regionLabel;
    /** label for country. */
    private JLabel countryLabel;
    /** label for coordinates.  */
    private JLabel coordinatesLabel;

    /**
     * constructor for building and displaying window.
     */
    public MainFrame() {
        // creates Controller for View to use.
        controller = new GuiController(DomainNameModel.getInstance());
        // title setup.
        setTitle("Domain Name Lookup");
        setSize(600, 400);
        // end program when window closed.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup for main window.
        JPanel topPanel = new JPanel(new FlowLayout());

        enterHostname = new JTextField(20); // text box size.
        lookupButton = new JButton("Lookup");  // lookup button.
        topPanel.add(new JLabel("Hostname:")); // add label.
        topPanel.add(enterHostname);  // add text field.
        topPanel.add(lookupButton);   // add button.
        add(topPanel, BorderLayout.NORTH); // location for top panel.

        // when button clicked, run code.
        lookupButton = new JButton("Lookup");
        // when click happens call 'event' run code.
        lookupButton.addActionListener(event -> {
            // reads text typed in text field.
            String hostname = enterHostname.getText();
            // View calls Controller.
            DomainNameModel.DNRecord record = controller.lookupHostname(hostname);
            // check to make sure record not null.
            if (record != null) {
                hostnameLabel.setText("Hostname: " + record.hostname());
                ipLabel.setText("IP: " + record.ip());
                cityLabel.setText("City: " + record.city());
                regionLabel.setText("Region: " + record.region());
                countryLabel.setText("Country: " + record.country());
                coordinatesLabel.setText("Coordinates: " + record.latitude() + ", " + record.longitude());
            } else {
                hostnameLabel.setText("Hostname not found/invalid.");
            }
        });

        // set up for result panel.
        JPanel resultPanel = new JPanel(new GridLayout(6, 1));
        hostnameLabel = new JLabel("Hostname: ");
        ipLabel = new JLabel("IP: ");
        cityLabel = new JLabel("City: ");
        regionLabel = new JLabel("Region: ");
        countryLabel = new JLabel("Country: ");
        coordinatesLabel = new JLabel("Coordinates: ");

        // add result panel.
        resultPanel.add(hostnameLabel);
        resultPanel.add(ipLabel);
        resultPanel.add(cityLabel);
        resultPanel.add(regionLabel);
        resultPanel.add(countryLabel);
        resultPanel.add(coordinatesLabel);
        add(resultPanel, BorderLayout.CENTER);
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
