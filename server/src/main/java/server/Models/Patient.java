package server.Models;


import java.sql.Date;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String socialSecurityNumber;
    private String gender;
    private String insuranceProvider;
    private int primaryCareProviderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public int getPrimaryCareProviderId() {
        return primaryCareProviderId;
    }

    public void setPrimaryCareProviderId(int primaryCareProviderId) {
        this.primaryCareProviderId = primaryCareProviderId;
    }

    public Patient() {
    }

    public Patient(int id, String firstName, String lastName, Date dateOfBirth, String socialSecurityNumber, String gender, String insuranceProvider, int primaryCareProviderId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.socialSecurityNumber = socialSecurityNumber;
        this.gender = gender;
        this.insuranceProvider = insuranceProvider;
        this.primaryCareProviderId = primaryCareProviderId;
    }
}




