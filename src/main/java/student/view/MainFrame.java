package student.view;
import student.controller.GuiController;
import student.model.DomainNameModel;
import student.model.formatters.Formats;
import javax.swing.JFileChooser;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


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
    /** button for show all. */
    private JButton showAllButton;
    /** list of domains as a String. */
    private JList<String> domainList;
    /** button for export. */
    private JButton exportButton;
    /** map panel for points. */
    private MapPanel mapPanel;
    /** display map button. */
    private JButton mapButton;
    /** to remember the latest looked-up record. */
    private DomainNameModel.DNRecord currentRecord;
    /** drop down option for export formats. */
    private JComboBox<Formats> exportOptions;


    /**
     * constructor for building and displaying window.
     */
    public MainFrame() {
        // creates Controller for View to use.
        controller = new GuiController(DomainNameModel.getInstance());
        setTitle("Domain Name Lookup"); // title setup.
        setSize(750, 400);
        // end program when window closed.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // setup for main window/ topPanel
        JPanel topPanel = new JPanel(new FlowLayout());
        enterHostname = new JTextField(20); // text box size.
        lookupButton = new JButton("Lookup");  // lookup button.
        showAllButton = new JButton("Show all");
        exportButton = new JButton("Export");
        exportOptions = new JComboBox<>(Formats.values());
        mapButton = new JButton("Display Map");
        topPanel.add(new JLabel("Hostname:")); // add label.
        topPanel.add(showAllButton);
        topPanel.add(enterHostname);  // add text field.
        topPanel.add(lookupButton);   // add button.
        topPanel.add(exportButton);
        topPanel.add(exportOptions);
        topPanel.add(mapButton);
        add(topPanel, BorderLayout.NORTH); // location for top panel.

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

        // lookup action listener.
        // when click happens call 'event' run code.
        lookupButton.addActionListener(event -> {
            // reads text typed in text field.
            String hostname = enterHostname.getText();
            // create new swing worker - with the result type (DNRecord) - no progress updates (Void).
            SwingWorker<DomainNameModel.DNRecord, Void> worker = new SwingWorker<>() {
                @Override
                // swing worker will call controller method lookupHostname()
                protected DomainNameModel.DNRecord doInBackground() {
                    return controller.lookupHostname(hostname);
                }
                @Override
                // done() auto called after doInBackground() runs
                // runs on 'original worker'
                protected void done() {
                    try {
                        // get record returned from doInBackground()
                        DomainNameModel.DNRecord record = get();
                        // check to make sure record not null.
                        if (record != null) {
                            hostnameLabel.setText("Hostname: " + record.hostname());
                            ipLabel.setText("IP: " + record.ip());
                            cityLabel.setText("City: " + record.city());
                            regionLabel.setText("Region: " + record.region());
                            countryLabel.setText("Country: " + record.country());
                            coordinatesLabel.setText("Coordinates: " + record.latitude() + ", " + record.longitude());
                            currentRecord = record;
                        } else {
                            hostnameLabel.setText("Hostname not found/invalid.");
                            currentRecord = null;
                        }
                    } catch (Exception error) {
                        error.printStackTrace();
                    }
                }
            };
            worker.execute(); // starts swing worker (following the jobs above)
        });

        // show all action listener.
        showAllButton.addActionListener(event -> {
            List<DomainNameModel.DNRecord> records = controller.getAllRecords();
            // create new array with number of spots from records.size().
            String[] hostnames = new String[records.size()];
            // for each in DNRecord, get the hostname, put it in domainList.
            for (int i = 0; i < records.size(); i++) {
                hostnames[i] = records.get(i).hostname();
            }
            domainList.setListData(hostnames);
        });

        // export button action listener.
        exportButton.addActionListener(event -> {
            JFileChooser fileChooser = new JFileChooser();  // create new file chooser.
            // opens the dialog window for MainFame, user click gets stored as 'result'.
            int result = fileChooser.showSaveDialog(this);
            // if user clicks "save/open" (APPROVED_OPTION).
            if (result == fileChooser.APPROVE_OPTION) {
                // get selected file, store as 'File' object.
                File selectedFile = fileChooser.getSelectedFile();
                Formats selectedFormat = (Formats) exportOptions.getSelectedItem();
                try {
                    // creates/opens file at location user clicked.
                    FileOutputStream outputFile = new FileOutputStream(selectedFile);
                    // gets all records in Model to export.
                    List<DomainNameModel.DNRecord> records = controller.getAllRecords();
                    // call Controller export method - records to write, format, destination.
                    controller.export(records, selectedFormat, outputFile);
                    outputFile.close();  // close file stream.
                } catch (Exception error) {
                    error.printStackTrace();
                }
            }
        });

        // map button action listener
        mapButton.addActionListener(event -> {
            if (currentRecord == null) {
                JOptionPane.showMessageDialog(this, "Look up a hostname first.");
                return;
            }
            // creates a new second window as 'child' of MainFrame.
            JDialog mapWindow = new JDialog(this, "Map");
            MapPanel mapPanel = new MapPanel();
            mapPanel.setPreferredSize(new Dimension(600, 400));
            mapPanel.setPoint(currentRecord.latitude(), currentRecord.longitude());
            mapWindow.add(mapPanel);
            mapWindow.pack(); // automatically sizes window to fit contents.
            mapWindow.setVisible(true); // show map.
        });

        // create new JList.
        domainList = new JList<>();
        // create scroll bar
        JScrollPane listScrollBar = new JScrollPane(domainList);
        add(listScrollBar, BorderLayout.SOUTH); // location for domainList.
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
