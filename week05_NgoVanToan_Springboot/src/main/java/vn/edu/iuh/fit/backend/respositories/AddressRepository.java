package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
