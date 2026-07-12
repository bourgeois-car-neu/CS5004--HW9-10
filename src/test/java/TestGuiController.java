import student.controller.GuiController;
import student.model.DomainNameModel;
import student.model.DomainNameModel.DNRecord;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;


public class TestGuiController {
    /**
     * test lookupHostname().
     * test that hostname saved in XML returns correct record.
     */
    @Test
    public void testLookupHostnameExist() {
        GuiController controller = new GuiController(DomainNameModel.getInstance("data/hostrecords.xml"));
        DNRecord record = controller.lookupHostname("www.github.com");
        assertEquals("www.github.com", record.hostname());
        assertEquals("140.82.112.3", record.ip());
    }

    /**
     * test lookupHostname() for empty String.
     * test when no hostname, returns null.
     */
    @Test
    public void testLookupHostnameEmpty() {
        GuiController controller = new GuiController(DomainNameModel.getInstance("data/hostrecords.xml"));
        DNRecord record = controller.lookupHostname("");
        assertNull(record);
    }

    /**
     * test lookupHostname() for null hostname.
     * test when no hostname, returns null.
     */
    @Test
    public void testLookupHostnameNull() {
        GuiController controller = new GuiController(DomainNameModel.getInstance("data/hostrecords.xml"));
        DNRecord record = controller.lookupHostname(null);
        assertNull(record);
    }

    /**
     * test getAllRecords().
     * tests that all records from the XML are returned.
     */
    @Test
    public void testGetAllRecords() {
        GuiController controller = new GuiController(DomainNameModel.getInstance("data/hostrecords.xml"));
        List<DNRecord> records = controller.getAllRecords();
        assertEquals(3, records.size());
        assertEquals("www.github.com", records.get(0).hostname());
    }
}
