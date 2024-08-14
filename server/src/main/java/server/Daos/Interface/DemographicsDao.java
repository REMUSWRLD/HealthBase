package server.Daos.Interface;

import server.Models.Demographics;

public interface DemographicsDao {
    Demographics getDemographicsByPatientId(int id);

    void createDemographics(Demographics demographics);

    void updateDemographics(Demographics demographics);

    void deleteDemographics(int id);
}
