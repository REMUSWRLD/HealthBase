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

import server.Daos.Interface.ProviderDao;
import server.Models.Provider;

import server.Daos.Interface.ProviderContactDao;
import server.Models.ProviderContact;

@RestController
@RequestMapping("/api/providers")
@CrossOrigin
public class ProviderController {

    @Autowired
    private ProviderDao providerDAO;

    @GetMapping
    public List<Provider> getAllProviders() {
        return providerDAO.getAllProviders();
    }

    @GetMapping("/{id}")
    public Provider getProviderById(@PathVariable int id) {
        return providerDAO.getProviderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProvider(@RequestBody Provider newProvider) {
        providerDAO.createProvider(newProvider);
    }

    @PutMapping("/{id}")
    public void updateProvider(@PathVariable int id, @RequestBody Provider updatedProvider) {
        updatedProvider.setId(id);
        providerDAO.updateProvider(updatedProvider);
    }

    @DeleteMapping
    public void deleteProvider(@PathVariable int id) {
        providerDAO.deleteProvider(id);
    }

    //CONTACT CONTROLLER

    @Autowired
    private ProviderContactDao providerContactDao;

    @GetMapping("/{id}/contact")
    public ProviderContact getProviderContactByPCPId(@PathVariable int id) {
        return providerContactDao.getProviderContactByPCPId(id);
    }

    @PostMapping("/{id}/contact")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProviderContact(@PathVariable int id, @RequestBody ProviderContact newProviderContact) {
        newProviderContact.setProviderId(id);
        providerContactDao.createProviderContact(newProviderContact);
    }

    @PutMapping("/{id}/contact")
    public void updateProviderContact(@PathVariable int id, @RequestBody ProviderContact updatedProviderContact) {
        updatedProviderContact.setProviderId(id);
        providerContactDao.updateProviderContact(updatedProviderContact);
    }

    @DeleteMapping("/{id}/contact")
    public void deleteProviderContact(@PathVariable int id) {
        providerContactDao.deleteProviderContact(id);
    }
}
