package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSkillRepository extends JpaRepository<vn.edu.iuh.fit.backend.entities.JobSkill, Long> {
}
