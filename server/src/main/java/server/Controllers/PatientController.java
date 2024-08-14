package server.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import server.Daos.Interface.PatientDao;
import server.Models.Patient;

import server.Daos.Interface.PatientContactDao;
import server.Models.PatientContact;

import server.Daos.Interface.DemographicsDao;
import server.Models.Demographics;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientDao patientDao;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientDao.getAllPatients();
    }

    @GetMapping("/pcp/{id}")
    public List<Patient> getPatientsByPrimaryCareProviderId(@PathVariable int id) {
        return patientDao.getPatientsByPrimaryCareProviderId(id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable int id) {
        return patientDao.getPatientById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPatient(@RequestBody Patient patient) {
        patientDao.createPatient(patient);
    }

    @PutMapping("/{id}")
    public void updatePatient(@PathVariable int id, @RequestBody Patient updatedPatient){
        updatedPatient.setId(id);
        patientDao.updatePatient(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public void deletePatient(@PathVariable int id) {patientDao.deletePatient(id);}

    //PATIENT CONTACT CONTROLLER
    @Autowired
    private PatientContactDao patientContactDao;

    @GetMapping("/{id}/contact")
    public PatientContact getPatientContactByPatientId(@PathVariable int id) {
        return patientContactDao.getPatientContactByPatientId(id);
    }

    @PostMapping("/{id}/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPatientContact(@PathVariable int id, @RequestBody PatientContact patientContact) {
        patientContact.setPatientId(id);
        patientContactDao.createPatientContact(patientContact);
    }

    @PutMapping("/{id}/contact")
    public void updatePatientContact(@PathVariable int id, @RequestBody PatientContact updatedPatientContact) {
        updatedPatientContact.setPatientId(id);
        patientContactDao.updatePatientContact(updatedPatientContact);
    }

    @DeleteMapping("/{id}/contact")
    public void deletePatientContact(@PathVariable int id) {
        patientContactDao.deletePatientContact(id);
    }

    //DEMOGRAPHICS CONTROLLER
    @Autowired
    private DemographicsDao demographicsDao;

    @GetMapping("/{id}/demographics")
    public Demographics getDemographicsByPatientId(@PathVariable int id) {
        return demographicsDao.getDemographicsByPatientId(id);
    }

    @PostMapping("/{id}/demographics")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDemographics(@PathVariable int id, @RequestBody Demographics demographics) {
        demographics.setPatientId(id);
        demographicsDao.createDemographics(demographics);
    }

    @PutMapping("/{id}/demographics")
    public void updateDemographics(@PathVariable int id, @RequestBody Demographics updatedDemographics) {
        updatedDemographics.setPatientId(id);
        demographicsDao.updateDemographics(updatedDemographics);
    }

    @DeleteMapping("/{id}/demographics")
    public void deleteDemographics(@PathVariable int id) {
        demographicsDao.deleteDemographics(id);
    }
}
