package Model;

import java.time.LocalDateTime;

/**
 * this is the FirstLevelDivision Class/Model. it is used to pass all information regarding First level divisions to the proper place.
 */
public class FirstLevelDivision {
    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdated;
    private String createdBy;
    private String lastUpdatedBy;
    private int countryID;

    public FirstLevelDivision(int divisionID, String division, LocalDateTime createDate, LocalDateTime lastUpdated, String createdBy, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.lastUpdated = lastUpdated;
        this.createdBy = createdBy;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }


    public FirstLevelDivision(int divisionID, String division) {
        this.divisionID = divisionID;
        this.division = division;
    }

    public FirstLevelDivision(String division) {
        this.division = division;
    }

    public FirstLevelDivision(int countryID) {this.countryID = countryID;}

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return division;
    }
}
