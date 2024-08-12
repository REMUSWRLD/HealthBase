package server.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import server.Daos.PatientDao;
import server.Models.Patient;

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

}
