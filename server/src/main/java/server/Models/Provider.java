package server.Models;

public class Provider {
    private int id;
    private String firstName;
    private String lastName;
    private String credentials;
    private String specialty;
    private boolean isAcceptingNewPatients;
    private String languagesSpoken;

    public Provider() {
    }

    public Provider(int id, String firstName, String lastName, String credentials, String specialty, boolean isAcceptingNewPatients, String languagesSpoken) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.credentials = credentials;
        this.specialty = specialty;
        this.isAcceptingNewPatients = isAcceptingNewPatients;
        this.languagesSpoken = languagesSpoken;
    }

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

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credential) {
        this.credentials = credential;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public boolean isAcceptingNewPatients() {
        return isAcceptingNewPatients;
    }

    public void setAcceptingNewPatients(boolean acceptingNewPatients) {
        isAcceptingNewPatients = acceptingNewPatients;
    }

    public String getLanguagesSpoken() {
        return languagesSpoken;
    }

    public void setLanguagesSpoken(String languagesSpoken) {
        this.languagesSpoken = languagesSpoken;
    }
}
