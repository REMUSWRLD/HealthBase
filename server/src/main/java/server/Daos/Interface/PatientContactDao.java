package server.Daos.Interface;

import server.Models.PatientContact;

public interface PatientContactDao {
    PatientContact getPatientContactByPatientId(int id);

    void createPatientContact(int id, PatientContact patientContact);

    void updatePatientContact(PatientContact patientContact);

    void deletePatientContact(int id);
}
