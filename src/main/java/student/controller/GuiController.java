package student.controller;
import student.model.DomainNameModel;
import student.model.DomainNameModel.DNRecord;

import java.util.List;

/**
 * controller to talk between GUI (view) and DomainNameModel.
 */
public class GuiController {
    /** model controller talks to. */
    private DomainNameModel model;

    /**
     * constructor for controller.
     * @param model model to use.
     */
    public GuiController(DomainNameModel model) {
        this.model = model;
    }

    /**
     * checks for no hostname first.
     * because java will treat as 'local host' (aka loopback address).
     * look up hostname via the model.
     * @param hostname hostname to look up
     * @return null or record for that hostname.
     */
    public DNRecord lookupHostname(String hostname) {
        // hostname is null or blank.
        if (hostname == null || hostname.isBlank()) {
            return null;
        }
        return model.getRecord(hostname);
    }

    /**
     * gets all records from the model.
     * @return list of all records.
     */
    public List<DNRecord> getAllRecords() {
        return model.getRecords();
    }

}
