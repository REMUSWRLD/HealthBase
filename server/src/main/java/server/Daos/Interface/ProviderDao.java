package server.Daos.Interface;

import server.Models.Provider;
import java.util.*;

public interface ProviderDao {
    List<Provider> getAllProviders();

    Provider getProviderById(int id);

    void createProvider(Provider provider);

    void updateProvider(Provider provider);

    void deleteProvider(int id);
}
