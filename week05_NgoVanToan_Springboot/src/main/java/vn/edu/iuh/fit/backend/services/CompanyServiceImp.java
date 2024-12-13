package vn.edu.iuh.fit.backend.services;

import org.springframework.data.domain.Page;
import vn.edu.iuh.fit.backend.entities.Company;

import java.util.List;

public interface CompanyServiceImp {
    List<Company> getAll();

    Company getById(Long id);

    void save(Company company);

    Company update(Company company);

    void delete(Long id);

    public Page<Company> getAllByPaging(int pageNo, int size, String sortField, String sortDir);

}
