package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.entities.Experience;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}
