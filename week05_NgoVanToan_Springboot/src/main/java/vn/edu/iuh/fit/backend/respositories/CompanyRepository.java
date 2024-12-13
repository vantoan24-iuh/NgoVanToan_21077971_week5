package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
