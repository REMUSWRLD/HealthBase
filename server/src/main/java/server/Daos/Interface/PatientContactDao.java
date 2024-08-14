package server.Daos.Interface;

import server.Models.PatientContact;

public interface PatientContactDao {
    PatientContact getPatientContactByPatientId(int id);

    void createPatientContact(PatientContact patientContact);

    void updatePatientContact(PatientContact patientContact);

    void deletePatientContact(int id);
}
