package student.model;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import student.model.formatters.DomainXmlWrapper;
import student.model.net.ApiResponse;
import student.model.net.NetUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DomainNameModelImpl implements DomainNameModel {
    /** the list of records. */
    private List<DNRecord> records;

    /** the database file path. */
    private String database;

    /**
     * constructor to store database path & load XML file into records list.
     * @param database file path like ("data/hostrecords.xml").
     */
    public DomainNameModelImpl(String database) {
        // store file path string into database field.
        this.database = database;
        try {
            // create new XML mapper - tool that knows how to read and write XML.
            XmlMapper mapper = new XmlMapper();
            // opens file at database path as an InputStream.
            FileInputStream fis = new FileInputStream(database);
            // read XML from stream & deserialize it into a List<DNRecord>.
            // TypeReference<List<DNRecord>>(){} : tell Jackson you want back a List of DNRecords.
            // needed because Java's generics erased at runtime.
            records = mapper.readValue(fis, new TypeReference<List<DNRecord>>() { });
        } catch (Exception error) {
            records = new ArrayList<>();  // instead of crashing, start with empty list.
        }
    }

    /**
     * gets records as a list.
     * make sure records are immutable / unmodifiable.
     * @return records list.
     */
    @Override
    public List<DNRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    /**
     * gets single record by hostname.
     * checks if record already exists.
     * @param hostname the hostname to look up.
     * @return the record.
     */
    @Override
    public DNRecord getRecord(String hostname) {
       // loop through each element in records list.
       // each element temp stored as 'record' (single DNRecord).
        for (DNRecord record : records) {
           // for current record in loop, get hostname string compare to hostname param
            if (record.hostname().equals(hostname)) {
               return record;
           }
       }
       // if no hostname match.
        try {
           // take hostname turn into ip address.
           String ip = NetUtils.lookUpIp(hostname);
           // take ip turn into raw XML.
           InputStream stream = NetUtils.getIpDetails(ip);
           // create Jackson mapper.
           XmlMapper mapper = new XmlMapper();
           // read XML from stream, call setters on ApiResponse for each field.
           ApiResponse response = mapper.readValue(stream, ApiResponse.class);
           // pull each field out of ApiResponse, pass to DNRecord constructor in order.
           // hostname comes from method because ApiResponse doesn't know hostname.
           DNRecord newRecord = new DNRecord(hostname, response.getIp(), response.getCity(), response.getRegion(),
                   response.getCountry(), response.getPostal(), response.getLatitude(), response.getLongitude());
           records.add(newRecord);
           saveRecord();
           return newRecord;
       } catch (Exception error) {
           error.printStackTrace();
           return null;
       }
    }

    /**
     * helper method to save records.
     * serialize current records into XML.
     */
    private void saveRecord() {
        try {
            XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            DomainXmlWrapper wrapper = new DomainXmlWrapper(records);
            mapper.writeValue(new File(database), wrapper);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
