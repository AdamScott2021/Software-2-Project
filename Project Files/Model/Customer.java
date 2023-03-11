package Model;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * this is the Customer Class/Model. it is used to pass all information regarding Customers to the proper place.
 */
public class Customer {
    private String zip;
    private String phone;
    private String name;
    private String address;
    private String division;
    private int id;
    private int divisionID;
    private String country;
    private Date createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Customer(int customerID, String customerName, String customerAddress, String customerZip, String customerPhone, int divisionID )
    {
    this.id = customerID;
    this.name = customerName;
    this.address = customerAddress;
    this.zip = customerZip;
    this.phone = customerPhone;
    this.divisionID = divisionID;
    }

    public Customer(String name, String address, String zip, String phone, int divisionID)
    {
        this.name = name;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.divisionID = divisionID;
    }


    public Customer(String customerName) {
        this.name = customerName;
    }

    public Customer(int customerID2, String customerName) {
        this.id = customerID2;
        this.name = customerName;
    }

    public Customer(int customerID, String customerName, String customerAddress, String customerZip, String customerPhone, String country, String division, int divisionID) {
        this.id = customerID;
        this.name = customerName;
        this.address = customerAddress;
        this.zip = customerZip;
        this.phone = customerPhone;
        this.country = country;
        this.division = division;
        this.divisionID = divisionID;
    }

    public String getDivision(){return division;}

    public void setDivision(String division) { this.division = division; }

    public int getCustomerID() {return id;}

    public void setCustomerID(int customerID) { this.id = id; }

    public String getZipCode() {
        return zip;
    }

    public void setZipCode(int zipCode) {
        this.zip = zip;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phone = phone;
    }

    public String getCustomerName() {
        return name;
    }

    public void setCustomerName(String name) { this.name = name; }

    public String getCustomerAddress() {
        return address;
    }

    public void setCustomerAddress(String address) { this.address = address; }

    public int getDivisionID() { return divisionID; }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getCountry() { return country; }

    public void setCountry(int divisionID) {
        this.country = country;
    }

    public Date getCreateDate() { return createDate; }

    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public LocalDateTime getLastUpdate() { return lastUpdate; }

    public void setLastUpdate(LocalDateTime lastUpdate) { this.lastUpdate = lastUpdate; }

    public String getLastUpdatedBy() { return lastUpdatedBy; }

    public void setLastUpdatedBy(String lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    @Override
    public String toString(){
        return name;
    }

}

