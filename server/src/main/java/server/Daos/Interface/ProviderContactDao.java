package server.Daos.Interface;
import server.Models.ProviderContact;

public interface ProviderContactDao {
    ProviderContact getProviderContactByPCPId(int id);

    void createProviderContact(ProviderContact providerContact);

    void updateProviderContact(ProviderContact providerContact);

    void deleteProviderContact(int id);
}
