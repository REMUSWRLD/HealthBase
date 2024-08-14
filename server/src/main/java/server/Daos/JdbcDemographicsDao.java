package server.Daos;

import server.Daos.Interface.DemographicsDao;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import server.Models.Demographics;
import javax.sql.DataSource;

@Component
public class JdbcDemographicsDao implements DemographicsDao {
    private final JdbcTemplate template;

    public JdbcDemographicsDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public Demographics getDemographicsByPatientId(int id) {
        String sql="SELECT * FROM Demographics WHERE patientId =?";
        try {
            SqlRowSet result = template.queryForRowSet(sql, id);
            if (result.next()) {
                return mapRowToDemographics(result);
            } else {
                throw new DataIntegrityViolationException("No demographics record found for id " + id);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to retrieve demographic records for patient: " + id, e);
        }
    }

    @Override
    public void createDemographics(Demographics demographics){
        String sql="INSERT INTO Demographics (patientId, age, religion, race, ethnicity, preferredLanguage, maritalStatus) VALUES (?,?,?,?,?,?,?)";
        try {
            template.update(sql, demographics.getPatientId(), demographics.getAge(), demographics.getReligion(), demographics.getRace(), demographics.getEthnicity(), demographics.getPreferredLanguage(), demographics.getMaritalStatus());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred creating a demographic record for patient: " + demographics.getPatientId(), e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void updateDemographics(Demographics demographics){
        String sql="UPDATE Demographics SET age=?, religion=?, race=?, ethnicity=?, preferredLanguage=?, maritalStatus=? WHERE patientId=?";
        try {
            template.update(sql, demographics.getAge(), demographics.getReligion(), demographics.getRace(), demographics.getEthnicity(), demographics.getPreferredLanguage(), demographics.getMaritalStatus(), demographics.getPatientId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred updating a demographic record for id: " + demographics.getId(), e);
        } catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void deleteDemographics(int id){
        String sql="DELETE FROM Demographics WHERE id =?";
        try {
            template.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred deleting a demographic record for id: " + id, e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }



    private Demographics mapRowToDemographics(SqlRowSet rs){
        Demographics demographics = new Demographics();
        demographics.setId(rs.getInt("id"));
        demographics.setPatientId(rs.getInt("patientId"));
        demographics.setAge(rs.getInt("age"));
        demographics.setReligion(rs.getString("religion"));
        demographics.setRace(rs.getString("race"));
        demographics.setEthnicity(rs.getString("ethnicity"));
        demographics.setPreferredLanguage(rs.getString("preferredLanguage"));
        demographics.setMaritalStatus(rs.getString("maritalStatus"));
        return demographics;


    }
}
