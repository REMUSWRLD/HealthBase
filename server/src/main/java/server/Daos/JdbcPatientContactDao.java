package server.Daos;

import server.Daos.Interface.PatientContactDao;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import server.Models.PatientContact;
import javax.sql.DataSource;

@Component
public class JdbcPatientContactDao implements PatientContactDao {
    private final JdbcTemplate template;
    public JdbcPatientContactDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public PatientContact getPatientContactByPatientId(int id) {
        String sql = "SELECT * FROM PatientContactDetails WHERE PatientId =?";
        try {
            SqlRowSet results = template.queryForRowSet(sql, id);
            if (results.next()) {
                return mapRowToPatientContact(results);
            } else {
                throw new DaoException("No patient contact found for patient: " + id);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }  catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred retrieving patient contact:", e);
        }
    }

    @Override
    public void createPatientContact(int id, PatientContact patientContact) {
        String sql="INSERT INTO PatientContactDetails (PatientId, PhoneNumber, Email, Address) VALUES (?,?,?,?)";
        try {
            template.update(sql, patientContact.getPatientId(), patientContact.getPhoneNumber(), patientContact.getEmail(), patientContact.getAddress());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred creating a patient contact:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void updatePatientContact(PatientContact patientContact) {
        String sql = "UPDATE PatientContactDetails SET PhoneNumber=?, Email=?, Address=? WHERE PatientId=?";
        try {
            template.update(sql, patientContact.getPhoneNumber(), patientContact.getEmail(), patientContact.getAddress(), patientContact.getPatientId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred updating patient contact:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void deletePatientContact(int id) {
        String sql = "DELETE FROM PatientContactDetails WHERE PatientId=?";
        try {
            template.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred deleting patient contact:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    private PatientContact mapRowToPatientContact(SqlRowSet rs){
        PatientContact patientContact = new PatientContact();
        patientContact.setId(rs.getInt("Id"));
        patientContact.setPatientId(rs.getInt("PatientId"));
        patientContact.setPhoneNumber(rs.getString("PhoneNumber"));
        patientContact.setEmail(rs.getString("Email"));
        patientContact.setAddress(rs.getString("Address"));
        return patientContact;
    }
}
