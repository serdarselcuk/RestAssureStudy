package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

public class Contact    {

private int contactId;
private String emailAddress;
private String phone;
@SerializedName("premanentAddress")
private String permanentAdress;

public Contact(){}

    public Contact(int contactId, String emailAddress, String phone, String permanentAdress) {
        this.contactId = contactId;
        this.emailAddress = emailAddress;
        this.phone = phone;
        this.permanentAdress = permanentAdress;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPermanentAdress() {
        return permanentAdress;
    }

    public void setPermanentAdress(String permanentAdress) {
        this.permanentAdress = permanentAdress;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", emailAddress='" + emailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", permanentAdress='" + permanentAdress + '\'' +
                '}';
    }
}
