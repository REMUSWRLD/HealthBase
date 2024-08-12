package server.Daos;

import server.Models.Patient;

import java.util.*;

public interface PatientDao {
    List<Patient> getAllPatients();

    List<Patient> getPatientsByPrimaryCareProviderId(int id);

    Patient getPatientById(int id);

    void createPatient(Patient patient);

    void updatePatient(Patient patient);

    void deletePatient(int id);
}

