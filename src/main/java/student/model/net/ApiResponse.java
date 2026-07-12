package student.model.net;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * temporary container to hold raw XML data that needs to be converted to a DNRecord.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    /** string ip. */
    private String ip;
    /** string city. */
    private String city;
    /** string region. */
    private String region;
    /** string country. */
    private String country;
    /** string postal. */
    private String postal;
    /** double latitude. */
    private double latitude;
    /** double longitude. */
    private double longitude;

    /** empty/default constructor. */
    public ApiResponse() { }

    /**
     * constructor for all values.
     * @param ip ip string address.
     * @param city city string.
     * @param region region string.
     * @param country country string.
     * @param postal postal string.
     * @param latitude latitude double.
     * @param longitude longitude double.
     */
    public ApiResponse(String ip, String city, String region, String country,
                        String postal, double latitude, double longitude) {
        this.ip = ip;
        this.city = city;
        this.region = region;
        this.country = country;
        this.postal = postal;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * get the ip.
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * set the ip.
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * get the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * set the city.
     * @param city the city to set
     */
    public void setCity(String city) {
         this.city = city;
    }

    /**
     * get the region.
     * @return the region
     */
    public String getRegion() {
        return region;
    }

    /**
     * set the region.
     * @param region the region to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * get the country.
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * set the country.
     * @param country the country to set
     */
    public void setCountry(String country) {
         this.country = country;
    }

    /**
     * get the postal.
     * @return the postal
     */
    public String getPostal() {
        return postal;
    }

    /**
     * set the postal.
     * @param postal the postal to set
     */
    public void setPostal(String postal) {
         this.postal = postal;
    }

    /**
     * get the latitude.
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * set the latitude.
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
         this.latitude = latitude;
    }

    /**
     * get the longitude.
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * set the longitude.
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
         this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ApiResponse [ ip=" + ip + ",city=" + city + ", region=" + region
                + ", country=" + country + ", postal=" + postal
                + ", latitude=" + latitude + ", longitude=" + longitude + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

