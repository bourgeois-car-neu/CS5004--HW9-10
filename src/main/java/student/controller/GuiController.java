package student.controller;
import student.model.DomainNameModel;
import student.model.DomainNameModel.DNRecord;

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
     * look up hostname via the model.
     * @param hostname hostname to look up
     * @return record for that hostname.
     */
    public DNRecord lookupHostname(String hostname) {
        return model.getRecord(hostname);
    }

}
