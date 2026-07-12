package student.controller;

import java.io.OutputStream;
import student.model.DomainNameModel;
import student.model.formatters.Formats;
import java.io.FileOutputStream;

/**
 * A controller to handle the arguments.
 */
public class ArgsController {
    /** Model of the application. */
    private DomainNameModel model;
    /** The format to output. */
    private Formats format = Formats.PRETTY;
    /** The format of the output stream defaulting to System.out. */
    private OutputStream output = System.out;
    /** The hostname to look up. */
    private String hostname = "all"; // default to all
    /** The database file to read from. */
    private String database = DomainNameModel.DATABASE;
    /** If help flag requested. */
    private boolean helpRequest = false;

    /**
     * constructor.
     * .containsValues to handle any valid string rather than hard coding "JSON".
     * @param args
     */
    public ArgsController(String[] args) {
        // loop through every element in args.
        for (int  i = 0; i < args.length; i++) {
            // find "-f"
            if (args[i].equals("-f")) {
                // args[i + 1] look at next element (format value)
                // pass value to Formats.containsValues
                // store answer in format field
                format = Formats.containsValues(args[i + 1]);
                i++;    // skip element after "-f"
            } else if (!args[i].startsWith("-")) {
                hostname = args[i];
            } else if (args[i].equals("-o")) {
                try {
                    output = new FileOutputStream(args[i + 1]);
                } catch (Exception error) {
                    error.printStackTrace();
                }
                i++;    // skip element after "-o"
            } else if (args[i].equals("--data")) {
                database = args[i + 1];
                i++;
            } else if (args[i].equals("-h") || args[i].equals("--help")) {
                helpRequest = true;
            }
        }
    }

    /**
     * getter for format.
     * @return format = Formats.PRETTY
     */
    public Formats getFormat() {
        return format;
    }

    /**
     * getter for output.
     * @return output = System.out
     */
    public OutputStream getOutput() {
        return output;
    }

    /**
     * getter for hostname.
     * @return hostname = "all"
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * getter for database file.
     * @return database string
     */
    public String getDatabase() {
        return database;
    }

    /**
     * getter for help request.
     * @return help request true or false.
     */
    public boolean isHelpRequest() {
        return helpRequest;
    }

    /**
     * Get the help message. Left this here, so you didn't have to write it - however you are free
     * to change it and the file name if you want/need to.
     * 
     * @return the help message
     */
    public String getHelp() {
        return """
                DNInfoApp [hostname|all] [-f json|xml|csv|pretty] [-o file path] [-h | --help] [--data filepath]

                Looks up the information for a given hostname (url) or displays information for
                all domains in the database. Can be output in json, xml, csv, or pretty format.
                If -o file is provided, the output will be written to the file instead of stdout.

                --data is mainly used in testing to provide a different data file, defaults to the hostrecords.xml file.
                """;
    }



}
