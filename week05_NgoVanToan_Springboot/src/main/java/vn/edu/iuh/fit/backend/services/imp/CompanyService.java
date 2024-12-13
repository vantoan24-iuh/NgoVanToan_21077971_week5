package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Company;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.respositories.CompanyRepository;
import vn.edu.iuh.fit.backend.services.CompanyServiceImp;

import java.util.List;

@Service
public class CompanyService implements CompanyServiceImp {
    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        return companyRepository.findById(company.getId()).map(existingCompany -> {
            existingCompany.setCompName(company.getCompName());
            existingCompany.setAddress(company.getAddress());
            existingCompany.setPhone(company.getPhone());
            existingCompany.setEmail(company.getEmail());

            return companyRepository.save(existingCompany);
        }).orElseThrow(() -> new IllegalArgumentException("Company not found with ID: " + company.getId()));
    }

    @Override
    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Page<Company> getAllByPaging(int pageNo, int size, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        Pageable page = PageRequest.of(pageNo, size, sort);
        return companyRepository.findAll(page);
    }


}
