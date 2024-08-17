package server.Daos;

import java.util.*;

import server.Daos.Interface.PatientDao;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import server.Models.Patient;
import javax.sql.DataSource;

@Component
public class JdbcPatientDao implements PatientDao {
    private final JdbcTemplate template;
    public JdbcPatientDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient ORDER BY firstName ASC";
        try {
            SqlRowSet results = template.queryForRowSet(sql);
            while (results.next()) {
                patients.add(mapRowToPatient(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (Exception e) {
            throw new DaoException("An error occurred while retrieving patients", e);
        }
        return patients;
    }

    @Override
    public List<Patient> getPatientsByPrimaryCareProviderId(int id) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient WHERE pcpid =? ORDER BY firstName ASC";
        try {
            SqlRowSet results = template.queryForRowSet(sql, id);
            while (results.next()) {
                patients.add(mapRowToPatient(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (Exception e) {
            throw new DaoException("An error occurred while retrieving patients for provider with ID " + id, e);
        }
        return patients;
    }

    @Override
    public Patient getPatientById(int id) {
        Patient patient = null;
        String sql = "SELECT * FROM Patient WHERE id =? ";
        try {
            SqlRowSet results = template.queryForRowSet(sql, id);
            if (results.next()) {
                patient = mapRowToPatient(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (Exception e) {
            throw new DaoException("An error occurred while retrieving patient with ID " + id, e);
        }
        return patient;
    }

    @Override
    public void createPatient(Patient newPatient) {
        String sql = "INSERT INTO Patient (firstname, lastname, dob, ssn, gender, insuranceProvider, pcpid) VALUES (?,?,?,?,?,?,?)";
        try {
            template.update(sql, newPatient.getFirstName(), newPatient.getLastName(), newPatient.getDateOfBirth(), newPatient.getSocialSecurityNumber(), newPatient.getGender(), newPatient.getInsuranceProvider(), newPatient.getPrimaryCareProviderId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred creating a patient:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void updatePatient(Patient updatedPatient) {
        String sql = "UPDATE Patient SET firstname =?, lastname =?, dob =?, ssn =?, gender =?, insuranceProvider =?, pcpid =? WHERE id =?";
        try {
            template.update(sql, updatedPatient.getFirstName(), updatedPatient.getLastName(), updatedPatient.getDateOfBirth(), updatedPatient.getSocialSecurityNumber(), updatedPatient.getGender(), updatedPatient.getInsuranceProvider(), updatedPatient.getPrimaryCareProviderId(), updatedPatient.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred updating the patient with ID " + updatedPatient.getId(), e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void deletePatient(int id) {
        String sql = "DELETE FROM Patient WHERE id =?";
        try {
            template.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (Exception e) {
            throw new DaoException("An error occurred while deleting patient with ID " + id, e);
        }
    }


    private Patient mapRowToPatient(SqlRowSet rs){
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFirstName(rs.getString("firstname"));
        patient.setLastName(rs.getString("lastname"));
        patient.setDateOfBirth(rs.getDate("dob"));
        patient.setSocialSecurityNumber(rs.getString("ssn"));
        patient.setGender(rs.getString("gender"));
        patient.setInsuranceProvider(rs.getString("insuranceProvider"));
        patient.setPrimaryCareProviderId(rs.getInt("pcpid"));
        return patient;
    }
}

