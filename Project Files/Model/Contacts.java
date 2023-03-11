package Model;

/**
 *  * this is the contacts Class/Model. it is used to pass all information regarding contacts to the proper place.
 */
public class Contacts {
    private int contactID;
    private String contactName;
    private String emailAddress;

    public Contacts (int contactID, String contactName) {

        this.contactName = contactName;
        this.contactID = contactID;
    }

    public Contacts(int contactID) {
        this.contactID = contactID;
    }


    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String toString() {
        return contactName;
    }

}
