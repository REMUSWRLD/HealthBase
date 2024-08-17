package server.Daos;

import java.util.*;

import server.Daos.Interface.ProviderDao;
import server.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import server.Models.Provider;

import javax.sql.DataSource;

@Component
public class JdbcProviderDao implements ProviderDao {
    private final JdbcTemplate template;

    public JdbcProviderDao(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Provider> getAllProviders() {
        List<Provider> providers = new ArrayList<>();
        String sql = "SELECT * FROM Provider ORDER BY FirstName";
        try {
            SqlRowSet results = template.queryForRowSet(sql);
            while (results.next()) {
                providers.add(mapRowToProvider(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new RuntimeException("Unable to retrieve providers from the database", e);
        }
        return providers;
    }

    @Override
    public Provider getProviderById(int id) {
        Provider provider = null;
        String sql = "SELECT * FROM Provider WHERE ID =?";
        SqlRowSet results = template.queryForRowSet(sql, id);
        while (results.next()) {
            provider = mapRowToProvider(results);
        }
        return provider;
    }

    @Override
    public void createProvider(Provider newProvider) {
        template.update(
                "INSERT INTO Provider (FirstName, LastName, Credentials, Specialty, IsAcceptingNewPatients, LanguagesSpoken) VALUES (?, ?, ?, ?, ?, ?)",
                newProvider.getFirstName(), newProvider.getLastName(), newProvider.getCredentials(), newProvider.getSpecialty(), newProvider.isAcceptingNewPatients(), newProvider.getLanguagesSpoken()
        );
    }

    @Override
    public void updateProvider(Provider provider) {
        template.update(
                "UPDATE Provider SET FirstName =?, LastName =?, Credentials =?, Specialty =?, IsAcceptingNewPatients =?, LanguagesSpoken =? WHERE ID =?",
                provider.getFirstName(), provider.getLastName(), provider.getCredentials(), provider.getSpecialty(), provider.isAcceptingNewPatients(), provider.getLanguagesSpoken(), provider.getId()
        );
    }

    @Override
    public void deleteProvider(int id) {
        try {
            template.update("DELETE FROM Provider WHERE ID =?", id);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Unable to delete provider with ID " + id + " because it is referenced by other entities", e);
        }
    }

    private Provider mapRowToProvider(SqlRowSet rs){
        Provider provider = new Provider();
        provider.setId(rs.getInt("ID"));
        provider.setFirstName(rs.getString("FirstName"));
        provider.setLastName(rs.getString("LastName"));
        provider.setCredentials(rs.getString("Credentials"));
        provider.setSpecialty(rs.getString("Specialty"));
        provider.setAcceptingNewPatients(rs.getBoolean("IsAcceptingNewPatients"));
        provider.setLanguagesSpoken(rs.getString("LanguagesSpoken"));
        return provider;
    }
}
