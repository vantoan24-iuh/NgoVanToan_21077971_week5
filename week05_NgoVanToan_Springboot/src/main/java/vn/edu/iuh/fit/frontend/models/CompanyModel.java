package vn.edu.iuh.fit.frontend.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.backend.entities.Company;

import java.util.List;

@Component
public class CompanyModel {
    @Autowired
    private RestTemplate restTemplate;

    public Company getCompanyById(Long id) {
        return restTemplate.getForObject("http://localhost:8080/api/companies/" + id, Company.class);
    }

    public List<Company> getCompanies() {
        return restTemplate.getForObject("http://localhost:8080/api/companies", List.class);
    }
}
