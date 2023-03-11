package Model;

import java.time.LocalDateTime;

/**
 * this is the Users Class/Model. it is used to pass all information regarding Users to the proper place.
 */
public class Users {
    private int userID;
    private String userName;
    private String passWord;
    private LocalDateTime create_Date;
    private LocalDateTime last_Update;
    private String created_By;
    private String lastUpdatedBy;

    public Users(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }

    public Users(int userID, String userName, String passWord, LocalDateTime create_Date, LocalDateTime last_Update, String created_By, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.create_Date = create_Date;
        this.last_Update = last_Update;
        this.created_By = created_By;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Users(int userID, String userName, String passWord, String created_by, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
        this.created_By = created_by;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserID() { return userID; }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public LocalDateTime getCreate_Date() {return create_Date; }

    public void setCreate_Date(LocalDateTime create_Date) {
        this.create_Date = create_Date;
    }

    public LocalDateTime getLast_Update() {
        return last_Update;
    }

    public void setLast_Update(LocalDateTime last_Update) {
        this.last_Update = last_Update;
    }

    public String getCreated_By() {
        return created_By;
    }

    public void setCreated_By(String created_By) {
        this.created_By = created_By;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString(){
        return userName;
    }
}
