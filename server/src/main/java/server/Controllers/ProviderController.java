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

import server.Daos.ProviderDao;
import server.Models.Provider;

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
}
