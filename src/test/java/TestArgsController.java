import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import student.controller.ArgsController;
import student.model.formatters.Formats;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import java.io.FileOutputStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestArgsController {
    /**
     * test that ArgsController returns the correct default values.
     * new String[]{} creates new, empty array of Strings.
     */
    @Test
    public void testDefaultValues() {
        ArgsController controller = new ArgsController(new String[]{});
        assertEquals(Formats.PRETTY, controller.getFormat());
        assertEquals(System.out, controller.getOutput());
        assertEquals("all", controller.getHostname());
    }

    /**
     * test getFormat() returns correct JSON structure.
     * -f json → format is Formats.JSON.
     */
    @Test
    public void testFormatJson() {
        ArgsController controller = new ArgsController(new String[]{"-f", "json"});
        assertEquals(Formats.JSON, controller.getFormat());
    }

    /**
     * test getFormat() returns correct XML structure.
     * -f xml → format is Formats.XML.
     */
    @Test
    public void testFormatXml() {
        ArgsController controller = new ArgsController(new String[]{"-f", "xml"});
        assertEquals(Formats.XML, controller.getFormat());
    }

    /**
     * test getFormat() returns correct CSV structure.
     * -f csv → format is Formats.CSV.
     */
    @Test
    public void testFormatCsv() {
        ArgsController controller = new ArgsController(new String[]{"-f", "csv"});
        assertEquals(Formats.CSV, controller.getFormat());
    }

    /**
     * test getFormat() returns correct PRETTY structure.
     * -f pretty → format is Formats.PRETTY.
     */
    @Test
    public void testFormatPretty() {
        ArgsController controller = new ArgsController(new String[]{"-f", "pretty"});
        assertEquals(Formats.PRETTY, controller.getFormat());
    }

    /**
     * test getHostname() returns correct host name.
     */
    @Test
    public void testHostname() {
        ArgsController controller = new ArgsController(new String[]{"google.com"});
        assertEquals("google.com", controller.getHostname());
    }

    /**
     * test getOutput() returns correct type of object.
     * same file in two different FileOutputStream not considered equal.
     * use InstanceOf to compare object type.
     */
    @Test
    public void testOutputType() {
        ArgsController controller = new ArgsController(new String[]{"-o", "test_output.txt"});
        assertInstanceOf(FileOutputStream.class, controller.getOutput());
    }

    /**
     * test getDatabase() returns correct string.
     * --data tag
     */
    @Test
    public void testDatabase() {
        ArgsController controller = new ArgsController(new String[]{"--data", "hello_test"});
        assertEquals("hello_test", controller.getDatabase());
    }

    /**
     * test getHelp() returns true getHelp has "DNInfoApp".
     */
    @Test
    public void testHelp() {
        ArgsController controller = new ArgsController(new String[]{});
        assertTrue(controller.getHelp().contains("DNInfoApp"));
    }

    @Test
    public void testHelpRequest() {
        ArgsController controller = new ArgsController(new String[]{"-h"});
        assertTrue(controller.isHelpRequest());
    }
}
