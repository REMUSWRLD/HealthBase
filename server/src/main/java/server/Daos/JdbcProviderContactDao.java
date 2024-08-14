package server.Daos;

import server.Daos.Interface.ProviderContactDao;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import server.Models.ProviderContact;

import javax.sql.DataSource;

@Component
public class JdbcProviderContactDao implements ProviderContactDao {
    private final JdbcTemplate template;

    public JdbcProviderContactDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public ProviderContact getProviderContactByPCPId(int id) {
        String sql = "SELECT * FROM ProviderContactDetails WHERE ProviderId =?";
        try {
            SqlRowSet results = template.queryForRowSet(sql, id);
            if (results.next()) {
                return mapRowToProviderContact(results);
            } else {
                throw new DaoException("No provider contact found for provider: " + id);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Database connection error", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred retrieving provider contact:", e);
        }
    }

    @Override
    public void createProviderContact(ProviderContact providerContact) {
        String sql = "INSERT INTO ProviderContactDetails (ProviderId, PhoneNumber, Email, Address) VALUES (?,?,?,?)";
        try {
            template.update(sql, providerContact.getProviderId(), providerContact.getPhoneNumber(), providerContact.getEmail(), providerContact.getAddress());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred creating a patient:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void updateProviderContact(ProviderContact providerContact) {
        String sql = "UPDATE ProviderContactDetails SET PhoneNumber =?, Email =?, Address =? WHERE ProviderId =?";
        try {
            template.update(sql, providerContact.getPhoneNumber(), providerContact.getEmail(), providerContact.getAddress(), providerContact.getProviderId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred updating the provider contact:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    @Override
    public void deleteProviderContact(int id) {
        String sql = "DELETE FROM ProviderContactDetails WHERE ProviderId =?";
        try {
            template.update(sql, id);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("An error occurred deleting the provider contact:", e);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database", e);
        }
    }

    private ProviderContact mapRowToProviderContact(SqlRowSet rs){
        ProviderContact providerContact = new ProviderContact();
        providerContact.setId(rs.getInt("Id"));
        providerContact.setProviderId(rs.getInt("ProviderId"));
        providerContact.setPhoneNumber(rs.getString("PhoneNumber"));
        providerContact.setEmail(rs.getString("Email"));
        providerContact.setAddress(rs.getString("Address"));
        return providerContact;
    }
}
