import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.Test;
import student.model.DomainNameModel.DNRecord;
import student.model.formatters.DataFormatter;
import student.model.formatters.Formats;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import student.model.formatters.DomainXmlWrapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class TestDataFormatter {

    /**
     * test prettyPrint() returns correct string.
     * test calls write() --> calls prettyPrint() --> calls prettySingle().
     * ByteArrayOutputStream: writes everything into an in-memory byte array.
     * DataFormatter.write() only requires some OutputStream — it doesn't care which kind.
     */
    @Test
    public void testPrettyPrint() {
        DNRecord record = new DNRecord("hostname.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataFormatter.write(records, Formats.PRETTY, baos);
        String actual = baos.toString();
        String expected = "hostname.com\n"
                + "             IP: 0.0.0.0\n"
                + "       Location: city, region, country, 00000\n"
                + "    Coordinates: 0.0, 0.0\n"
                + "\n";
        assertEquals(expected, actual);
    }

    /**
     * Produces output structure for Jackson CSV.
     * Because I don't yet know what Jackson's CSV output looks like.
     * <p>
     * Schema:
     * hostname,ip,city,region,country,postal,latitude,longitude
     * Record:
     * google.com,0.0.0.0,city,region,country,00000,0.0,0.0
     *
     * @throws Exception
     */
    @Test
    public void experimentCsv() throws Exception {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(DNRecord.class).withHeader();

        String result = mapper.writer(schema).writeValueAsString(record);
        System.out.println("RESULT START>>>" + result + "<<<RESULT END");
    }

    /**
     * Produces output structure for Jackson CSV for multiple records.
     * Because I don't yet know what Jackson's CSV output looks like.
     * <p>
     * Schema:
     * hostname,ip,city,region,country,postal,latitude,longitude
     * Record:
     * google.com,0.0.0.0,city,region,country,00000,0.0,0.0
     * github.com,1.1.1.1,city2,region2,country2,11111,1.0,1.0
     *
     * @throws Exception
     */
    @Test
    public void experimentCsvMulti() throws Exception {
        DNRecord record1 = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        DNRecord record2 = new DNRecord("github.com", "1.1.1.1", "city2",
                "region2", "country2", "11111", 1, 1);
        List<DNRecord> records = List.of(record1, record2);

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(DNRecord.class).withHeader();

        String result = mapper.writer(schema).writeValueAsString(records);
        System.out.println("RESULT START>>>" + result + "<<<RESULT END");
    }

    /**
     * test writeCsv() returns correct headers & values in correct format.
     * expected structure based on experimentCsv().
     */
    @Test
    public void testWriteCsv() {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataFormatter.write(records, Formats.CSV, baos);
        String actual = baos.toString();
        String expected = "hostname,ip,city,region,country,postal,latitude,longitude\n"
                + "google.com,0.0.0.0,city,region,country,00000,0.0,0.0\n";
        assertEquals(expected, actual);
    }

    /**
     * Produces output structure for JSON.
     * Because I don't yet know what JSON output looks like.
     *
     * Records:
     * [{"hostname":"google.com","ip":"0.0.0.0",
     * + "city":"city","region":"region","country":"country",
     * +"postal":"00000","latitude":0.0,"longitude":0.0}].
     * @throws Exception
     */
    @Test
    public void experimentJson() throws Exception {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(records);
        System.out.println("RESULT START>>>" + result + "<<<RESULT END");
    }

    /**
     * test writeJson() returns correct format.
     * expected structure based on experimentJson().
     */
    @Test
    public void testWriteJson() {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataFormatter.write(records, Formats.JSON, baos);
        String actual = baos.toString();
        String expected = "[{\"hostname\":\"google.com\",\"ip\":\"0.0.0.0\","
                + "\"city\":\"city\",\"region\":\"region\",\"country\":\"country\","
                + "\"postal\":\"00000\",\"latitude\":0.0,\"longitude\":0.0}]";
        assertEquals(expected, actual);
    }

    /**
     * Produces output structure for XML with correct tags.
     * Because I don't yet know what XML output looks like.
     *
     * @throws Exception
     */
    @Test
    public void experimentXml() throws Exception {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        XmlMapper mapper = new XmlMapper();
        DomainXmlWrapper wrapper = new DomainXmlWrapper(records);
        String result = mapper.writeValueAsString(wrapper);
        System.out.println("RESULT START>>>" + result + "<<<RESULT END");
    }

    /**
     * Produces output structure for XML with correct tags.
     * Because I don't yet know what XML output looks like.
     * Added: SerializationFeature.INDENT_OUTPUT
     * @throws Exception
     */
    @Test
    public void experimentXmlIndented() throws Exception {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);

        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        DomainXmlWrapper wrapper = new DomainXmlWrapper(records);
        String result = mapper.writeValueAsString(wrapper);
        System.out.println("RESULT START>>>" + result + "<<<RESULT END");
    }

    /**
     * test writeXml() returns the correct values, tags, structure.
     * expected structure based on experimentXmlIndented().
     */
    @Test
    public void testWriteXml() {
        DNRecord record = new DNRecord("google.com", "0.0.0.0", "city",
                "region", "country", "00000", 0, 0);
        List<DNRecord> records = List.of(record);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataFormatter.write(records, Formats.XML, baos);
        String actual = baos.toString();
        String expected = "<domainList>\n"
                + "  <domain>\n"
                + "    <hostname>google.com</hostname>\n"
                + "    <ip>0.0.0.0</ip>\n"
                + "    <city>city</city>\n"
                + "    <region>region</region>\n"
                + "    <country>country</country>\n"
                + "    <postal>00000</postal>\n"
                + "    <latitude>0.0</latitude>\n"
                + "    <longitude>0.0</longitude>\n"
                + "  </domain>\n"
                + "</domainList>\n";
        assertEquals(expected, actual);
    }

}
