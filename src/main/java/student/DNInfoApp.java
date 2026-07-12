package student;


import student.controller.ArgsController;
import student.model.DomainNameModel;
import java.util.List;
import student.model.DomainNameModel.DNRecord;
/**
 * Main driver for the program.
 * 
 * DO NOT modify the name of this class (we call main directly in our test code).
 * 
 */
public final class DNInfoApp {

    /** Private constructor to prevent instantiation. */
    private DNInfoApp() {
        // empty
    }

    /**
     * Main entry point for the program.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // pass args array into ArgsController
        ArgsController controller = new ArgsController(args);
        // check for help request, print msg, exit
        if (controller.isHelpRequest()) {
            System.out.println(controller.getHelp());
            return;
        }
        // get instance from file path in controller. store result as 'model'.
        DomainNameModel model = DomainNameModel.getInstance(controller.getDatabase());
        List<DNRecord> records;
        if (controller.getHostname().equals("all")) {
            records = model.getRecords();
        } else {
            records = List.of(model.getRecord(controller.getHostname()));
        }
        DomainNameModel.writeRecords(records, controller.getFormat(), controller.getOutput());
    }

}
