package server.Models;

public class Demographics {
    private int id;
    private int patientId;
    private int age;
    private String religion;
    private String race;
    private String ethnicity;
    private String preferredLanguage;
    private String maritalStatus;

    public Demographics() {
    }

    public Demographics(int id, int patientId, int age, String religion, String race, String ethnicity, String preferredLanguage, String maritalStatus) {
        this.id = id;
        this.patientId = patientId;
        this.age = age;
        this.religion = religion;
        this.race = race;
        this.ethnicity = ethnicity;
        this.preferredLanguage = preferredLanguage;
        this.maritalStatus = maritalStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
