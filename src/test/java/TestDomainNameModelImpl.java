import org.junit.jupiter.api.Test;
import student.model.DomainNameModel;
import student.model.DomainNameModel.DNRecord;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDomainNameModelImpl {

    /**
     * test getRecords() for DomainNameModelImpl.
     * tests that hostname & ip for each are correct.
     * tests that all three records in XML are correct.
     */
    @Test
    public void testGetRecords() {
        DomainNameModel model = DomainNameModel.getInstance("data/hostrecords.xml");
        List<DNRecord> records = model.getRecords();

        assertEquals("www.github.com", records.get(0).hostname());
        assertEquals("140.82.112.3", records.get(0).ip());

        assertEquals("www.northeastern.edu", records.get(1).hostname());
        assertEquals("23.194.127.178", records.get(1).ip());

        assertEquals("www.google.com", records.get(2).hostname());
        assertEquals("142.250.69.228", records.get(2).ip());
        assertEquals(3, records.size());
    }

    /**
     * tests getInstance() from DomainNameModel.
     * should return a DomainNameModelImpl object.
     * return should be not null.
     */
    @Test
    public void testGetInstance() {
        DomainNameModel model = DomainNameModel.getInstance("data/hostrecords.xml");
        assertNotNull(model);
    }

    /**
     * tests getRecords() list cant be modified.
     */
    @Test
    public void testRecordsUnmodified() {
        DomainNameModel model = DomainNameModel.getInstance("data/hostrecords.xml");
        List<DNRecord> records = model.getRecords();
        assertThrows(UnsupportedOperationException.class, () -> {
            records.add(new DNRecord("test.com", "0.0.0.0", "city",
                    "region", "country", "00000", 0, 0));
        });
    }

    /**
     * test getRecord() returns correct record for existing hostname.
     * uses hostname in file --> no network call.
     * check the returned record has correct data.
     * confirm that "check file first" logic works.
     */
    @Test
    public void testGetRecord() {
        DomainNameModel model = DomainNameModel.getInstance("data/hostrecords.xml");
        DNRecord record = model.getRecord("www.github.com");
        assertNotNull(record);
        assertEquals("www.github.com", record.hostname());
        assertEquals("140.82.112.3", record.ip());
    }
}
